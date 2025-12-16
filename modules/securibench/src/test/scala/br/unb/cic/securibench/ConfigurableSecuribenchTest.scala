package br.unb.cic.securibench

import br.unb.cic.soot.svfa.jimple.SVFAConfig
import br.unb.cic.metrics.TestResult
import org.scalatest.FunSuite

/**
 * Configuration-aware Securibench test suite.
 * 
 * This class allows running Securibench tests with different SVFA configurations
 * to compare analysis results and performance across different settings.
 */
abstract class ConfigurableSecuribenchTest extends FunSuite with TestResult {

  def basePackage(): String
  def entryPointMethod(): String

  /**
   * Override this method to specify the SVFA configuration for this test suite.
   * Default uses command-line/environment configuration, falling back to SVFAConfig.Default.
   */
  def svfaConfig: SVFAConfig = SecuribenchConfig.getConfiguration()

  /**
   * Get a human-readable name for the current configuration.
   */
  def configurationName: String = {
    val config = svfaConfig
    val parts = List(
      if (config.interprocedural) "Interprocedural" else "Intraprocedural",
      if (config.fieldSensitive) "FieldSensitive" else "FieldInsensitive", 
      if (config.propagateObjectTaint) "WithTaintPropagation" else "NoTaintPropagation",
      config.callGraphAlgorithm.name
    )
    parts.mkString("-")
  }

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
          val dir = new java.io.File(url.toURI)
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
          if (classOf[securibench.micro.MicroTestCase].isAssignableFrom(clazz) && 
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
        List.empty[AnyRef]
    }
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
      case _ =>
    }
  }

  def generateRuntimeTests(className: String, packageName: String): Unit = {
    try {
      val clazz = Class.forName(className)

      // Use the configured SVFA settings
      val svfa = new SecuribenchTest(className, entryPointMethod(), svfaConfig)
      svfa.buildSparseValueFlowGraph()
      val conflicts = svfa.reportConflictsSVG()
      val executionTime = svfa.executionTime()

      val expected = clazz
        .getMethod("getVulnerabilityCount")
        .invoke(clazz.getDeclaredConstructor().newInstance())
        .asInstanceOf[Int]
      val found = conflicts.size

      this.compute(expected, found, className, executionTime)
      
      // Log configuration-specific information
      val testName = className.split("\\.").last
      val status = if (found == expected) "✅ PASS" else "❌ FAIL"
      println(s"$testName [$configurationName]: $found/$expected conflicts - $status (${executionTime}ms)")
      
    } catch {
      case e: Exception =>
        println(s"Error processing test case $className with configuration $configurationName: ${e.getMessage}")
    }
  }

  test(s"running testsuite from ${basePackage()} with configuration $configurationName") {
    generateRuntimeTests(basePackage())
    // Note: We don't assert exact match here since different configurations may produce different results
    // This allows us to compare configurations without failing tests
    println(s"Configuration $configurationName completed: ${this.vulnerabilitiesFound()}/${this.vulnerabilities()} vulnerabilities found")
  }
}

/**
 * Fast configuration test suite for performance-critical scenarios.
 */
abstract class FastSecuribenchTest extends ConfigurableSecuribenchTest {
  override def svfaConfig: SVFAConfig = SVFAConfig.Fast
}

/**
 * Precise configuration test suite for accuracy-critical scenarios.
 */
abstract class PreciseSecuribenchTest extends ConfigurableSecuribenchTest {
  override def svfaConfig: SVFAConfig = SVFAConfig.Precise
}

/**
 * Intraprocedural configuration test suite for method-local analysis.
 */
abstract class IntraproceduralSecuribenchTest extends ConfigurableSecuribenchTest {
  override def svfaConfig: SVFAConfig = SVFAConfig.Default.copy(interprocedural = false)
}

/**
 * Field-insensitive configuration test suite for performance.
 */
abstract class FieldInsensitiveSecuribenchTest extends ConfigurableSecuribenchTest {
  override def svfaConfig: SVFAConfig = SVFAConfig.Default.copy(fieldSensitive = false)
}
