package br.unb.cic.securibench

import java.io.File
import java.nio.file.{Files, Paths}
import br.unb.cic.metrics.TestResult
import org.scalatest.FunSuite
import securibench.micro.MicroTestCase

abstract class SecuribenchRuntimeTest extends FunSuite with TestResult {

  def basePackage(): String

  def entryPointMethod(): String

  def getJavaFilesFromPackage(packageName: String): List[AnyRef] = {
    discoverMicroTestCasesUsingReflection(packageName)
  }

  private def discoverMicroTestCasesUsingReflection(packageName: String): List[AnyRef] = {
    import scala.collection.JavaConverters._
    try {
      val classLoader = Thread.currentThread().getContextClassLoader
      val packagePath = packageName.replace('.', '/')
      val resources = classLoader.getResources(packagePath)
      val discoveredClasses = scala.collection.mutable.ListBuffer[String]()

      resources.asScala.foreach { url =>
        if (url.getProtocol == "file") {
          val dir = new File(url.toURI)
          if (dir.exists() && dir.isDirectory) {
            val classFiles = dir.listFiles().filter(_.getName.endsWith(".class")).filter(_.isFile)
            classFiles.foreach { classFile =>
              val className = classFile.getName.replace(".class", "")
              val fullClassName = s"$packageName.$className"
              discoveredClasses += fullClassName
            }
          }
        } else if (url.getProtocol == "jar") {
          val jarConnection = url.openConnection().asInstanceOf[java.net.JarURLConnection]
          val jarFile = jarConnection.getJarFile
          jarFile.entries().asScala
            .filter(entry => entry.getName.startsWith(packagePath) && entry.getName.endsWith(".class"))
            .filter(entry => !entry.getName.contains("$"))
            .foreach { entry =>
              val className = entry.getName.replace(packagePath + "/", "").replace(".class", "")
              if (!className.contains("/")) {
                val fullClassName = s"$packageName.$className"
                discoveredClasses += fullClassName
              }
            }
        }
      }

      discoveredClasses.flatMap { fullClassName =>
        try {
          val clazz = Class.forName(fullClassName, false, classLoader)
          if (classOf[MicroTestCase].isAssignableFrom(clazz) && 
              !clazz.isInterface && 
              !java.lang.reflect.Modifier.isAbstract(clazz.getModifiers) && 
              java.lang.reflect.Modifier.isPublic(clazz.getModifiers)) {
            Some(fullClassName.asInstanceOf[AnyRef])
          } else {
            None
          }
        } catch {
          case _: ClassNotFoundException => None
          case _: Throwable => None
        }
      }.toList
    } catch {
      case e: Exception =>
        println(s"Error discovering classes in $packageName using reflection: ${e.getMessage}")
        getJavaFilesFromPackageClasspath(packageName)
    }
  }

  private def getJavaFilesFromPackageClasspath(packageName: String): List[AnyRef] = {
    val classPath = System.getProperty("java.class.path")
    val paths = classPath.split(File.pathSeparator)

    paths.flatMap { path =>
      val packagePath = packageName.replace('.', '/')
      val fullPath = Paths.get(path, packagePath)
      if (Files.exists(fullPath) && Files.isDirectory(fullPath)) {

        val filesBySubdir: List[AnyRef] = Files
          .walk(fullPath)
          .filter(Files.isDirectory(_))
          .map[List[AnyRef]](d =>
            getJavaFilesFromPackageClasspath(s"$packageName.${d.getFileName.toString}")
          )
          .filter(_.nonEmpty)
          .toArray
          .toList

        val filesByDir = Files
          .walk(fullPath)
          .filter {
            case f if f.toString.endsWith(".class") => {
              try {
                val className =
                  f.getFileName.toString.split("/").last.replace(".class", "")
                val fullClassName = s"${packageName}.$className"
                val clazz = Class.forName(fullClassName)
                classOf[MicroTestCase].isAssignableFrom(clazz) &&
                !clazz.isInterface &&
                !java.lang.reflect.Modifier.isAbstract(clazz.getModifiers)
              } catch {
                case _: Throwable => false
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

  def generateRuntimeTests(packageName: String): Unit = {
    val files = getJavaFilesFromPackage(packageName)
    this.generateRuntimeTests(files, packageName)
    this.reportSummary(packageName)
  }

  def generateRuntimeTests(files: List[AnyRef], packageName: String): Unit = {
    files.foreach {
      case list: List[_] =>
        this.generateRuntimeTests(list.asInstanceOf[List[AnyRef]], packageName)
      case className: String => generateRuntimeTests(className, packageName)
      case list: java.nio.file.Path => generateRuntimeTestsFromPath(list, packageName)
      case _                        =>
    }
  }

  def generateRuntimeTests(className: String, packageName: String): Unit = {
    try {
      val clazz = Class.forName(className)

      val svfa = new SecuribenchTest(className, entryPointMethod())
      svfa.buildSparseValueFlowGraph()
      val conflicts = svfa.reportConflictsSVG()
      val executionTime = svfa.executionTime()

      val expected = clazz
        .getMethod("getVulnerabilityCount")
        .invoke(clazz.getDeclaredConstructor().newInstance())
        .asInstanceOf[Int]
      val found = conflicts.size

      this.compute(expected, found, className, executionTime)
    } catch {
      case e: Exception =>
        println(s"Error processing test case $className: ${e.getMessage}")
    }
  }

  def generateRuntimeTestsFromPath(file: java.nio.file.Path, packageName: String): Unit = {
    var fileName = file.toString.replace(".class", "").replace("/", ".")
    fileName = fileName.split(packageName).last;
    val className = s"$packageName$fileName"
    generateRuntimeTests(className, packageName)
  }

  test(s"running testsuite from ${basePackage()}") {
    generateRuntimeTests(basePackage())
    assert(this.vulnerabilities() == this.vulnerabilitiesFound())
  }
}
