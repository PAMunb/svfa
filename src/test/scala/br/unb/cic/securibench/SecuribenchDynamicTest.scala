package br.unb.cic.securibench

import br.unb.cic.metrics.CustomMetrics
import org.scalatest.FunSuite
import securibench.micro.MicroTestCase

abstract class SecuribenchDynamicTest extends FunSuite with CustomMetrics {

  def basePackage(): String

  def entryPointMethod(): String

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
          .filter {
//            case f if Files.isDirectory(f) => getJavaFilesFromPackage(s"$packageName.${f.getFileName.toString}")
            case f if f.toString.endsWith(".class") => {
              try {
                val className = f.getFileName.toString.split("/").last.replace(".class", "")
                val fullClassName = s"${packageName}.$className"
                val clazz = Class.forName(fullClassName)
                classOf[MicroTestCase].isAssignableFrom(clazz) &&
                  ! clazz.isInterface &&
                  ! java.lang.reflect.Modifier.isAbstract(clazz.getModifiers)
              } catch {
                case _ => false
              }
            }
            case _ => false
          }
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

      val svfa = new SecuribenchBaseTest(className, entryPointMethod())
      svfa.buildSparseValueFlowGraph()
      val conflicts = svfa.reportConflictsSVG()

      val clazz = Class.forName(className)
      val expected = clazz.getMethod("getVulnerabilityCount").invoke(clazz.getDeclaredConstructor().newInstance()).asInstanceOf[Int]
      val found = conflicts.size

      this.compute(expected, found, className)
    }
    this.reportSummary(packageName)
  }

  test(s"running testsuite from ${basePackage()}") {
    generateDynamicTests(basePackage())
    assert(this.vulnerabilities() == this.vulnerabilitiesFound())
  }
}