package br.unb.cic.securibench

import br.unb.cic.metrics.CustomMetrics
import br.unb.cic.soot.graph._
import jdk.internal.platform.Metrics
import org.scalatest.FunSuite
import securibench.micro.MicroTestCase
import soot.jimple.{AssignStmt, InvokeExpr, InvokeStmt}

class SecuribenchDynamicTest extends FunSuite with CustomMetrics {

  def getJavaFilesFromPackage(packageName: String): List[AnyRef] = {
    import java.io.File
    import java.nio.file.{Files, Paths}
    
    val classPath = System.getProperty("java.class.path")
    val paths = classPath.split(File.pathSeparator)
    
    paths.flatMap { path =>
      val packagePath = packageName.replace('.', '/')
      val fullPath = Paths.get(path, packagePath)
      if (Files.exists(fullPath) && Files.isDirectory(fullPath)) {
        Files.walk(fullPath)
          .filter(_.toString.endsWith(".class"))
          .map[String](_.toString)
           .filter(path => {
             try {
               val className = path.split("/").last.replace(".class", "")
               val fullClassName = s"${packageName}.$className"
               val clazz = Class.forName(fullClassName)
               classOf[MicroTestCase].isAssignableFrom(clazz) && 
               ! clazz.isInterface && 
               ! java.lang.reflect.Modifier.isAbstract(clazz.getModifiers)
             } catch {
               case _ => false
             }
           })
          .toArray
          .toList
      } else {
        List.empty[String]
      }
    }.toList
  }

  def generateDynamicTests(packageName: String): Unit = {
    val files = getJavaFilesFromPackage(packageName)
    
    files.foreach { file =>
      val fileName = file.toString.split("/").last.replace(".class", "")
      val className = s"$packageName.$fileName"

      val svfa = new SecuribenchTest(className, "doGet")
      svfa.buildSparseValueFlowGraph()
      val conflicts = svfa.reportConflictsSVG()

      val clazz = Class.forName(className)
      val expected = clazz.getMethod("getVulnerabilityCount").invoke(clazz.getDeclaredConstructor().newInstance()).asInstanceOf[Int]
      val found = conflicts.size

      this.compute(expected, found, className)
    }
    this.reportSummary()

    test(s"running tests at $packageName") {
      assert(this.vulnerabilities() == this.vulnerabilitiesFound())
    }
  }

  // Generate tests for different packages
 generateDynamicTests("securibench.micro.aliasing")
 generateDynamicTests("securibench.micro.arrays")
 generateDynamicTests("securibench.micro.basic")
 generateDynamicTests("securibench.micro.collections")
 generateDynamicTests("securibench.micro.datastructures")
 generateDynamicTests("securibench.micro.factories")
 generateDynamicTests("securibench.micro.inter")
 generateDynamicTests("securibench.micro.session")
 generateDynamicTests("securibench.micro.strong_updates")
}