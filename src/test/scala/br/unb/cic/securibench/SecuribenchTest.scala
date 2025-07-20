package br.unb.cic.securibench

import java.io.File
import java.nio.file.{Files, Paths}
import br.unb.cic.metrics.CustomMetrics
import org.scalatest.FunSuite
import securibench.micro.MicroTestCase

abstract class SecuribenchTest extends FunSuite with CustomMetrics {

  def basePackage(): String

  def entryPointMethod(): String

  def getJavaFilesFromPackage(packageName: String): List[AnyRef] = {
    val classPath = System.getProperty("java.class.path")
    val paths = classPath.split(File.pathSeparator)
    
    paths.flatMap { path =>
      val packagePath = packageName.replace('.', '/')
      val fullPath = Paths.get(path, packagePath)
      if (Files.exists(fullPath) && Files.isDirectory(fullPath)) {

        val filesBySubdir: List[AnyRef] = Files.walk(fullPath)
          .filter(Files.isDirectory(_))
          .map[List[AnyRef]](d => getJavaFilesFromPackage(s"$packageName.${d.getFileName.toString}"))
          .filter(_.nonEmpty)
          .toArray
          .toList

        val filesByDir = Files.walk(fullPath)
          .filter {
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
        filesByDir ++ filesBySubdir
      } else {
        List.empty[String]
      }
    }.toList
  }

  def generateDynamicTests(packageName: String): Unit = {
    val files = getJavaFilesFromPackage(packageName)
    this.generateDynamicTests(files, packageName)
    this.reportSummary(packageName)
  }

  def generateDynamicTests(files: List[AnyRef], packageName: String): Unit = {
    files.foreach {
      case list: List[AnyRef] => this.generateDynamicTests(list, packageName)
      case list : java.nio.file.Path => generateDynamicTests(list, packageName)
      case _ =>
    }
  }

  def generateDynamicTests(file: AnyRef, packageName: String): Unit = {
      var fileName = file.toString.replace(".class", "").replace("/",".")
      fileName = fileName.split(packageName).last;
      val className = s"$packageName$fileName"
      val clazz = Class.forName(className)

      val svfa = new SecuribenchBaseTest(className, entryPointMethod())
      svfa.buildSparseValueFlowGraph()
      val conflicts = svfa.reportConflictsSVG()

      val expected = clazz.getMethod("getVulnerabilityCount").invoke(clazz.getDeclaredConstructor().newInstance()).asInstanceOf[Int]
      val found = conflicts.size

      this.compute(expected, found, className)
    }

  test(s"running testsuite from ${basePackage()}") {
    generateDynamicTests(basePackage())
    assert(this.vulnerabilities() == this.vulnerabilitiesFound())
  }
}