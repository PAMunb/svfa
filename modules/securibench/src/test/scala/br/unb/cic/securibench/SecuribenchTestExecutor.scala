package br.unb.cic.securibench

import java.io.File
import java.nio.file.{Files, Paths}
import org.scalatest.FunSuite
import securibench.micro.MicroTestCase

/**
 * Phase 1: Execute Securibench tests and save results to disk
 * This class runs the actual SVFA analysis and saves results for later metrics computation
 */
abstract class SecuribenchTestExecutor extends FunSuite {

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

      // Filter for MicroTestCase implementations
      discoveredClasses.filter { className =>
        try {
          val clazz = Class.forName(className)
          classOf[MicroTestCase].isAssignableFrom(clazz) &&
          !clazz.isInterface &&
          !java.lang.reflect.Modifier.isAbstract(clazz.getModifiers)
        } catch {
          case _: Throwable => false
        }
      }.toList
    } catch {
      case e: Exception =>
        println(s"Error during class discovery: ${e.getMessage}")
        List.empty[String]
    }
  }

  def executeTests(packageName: String): (Int, Int, Int) = {
    println(s"=== PHASE 1: EXECUTING TESTS FOR $packageName ===")
    
    // Clear previous results
    TestResultStorage.clearResults(packageName)
    
    val files = getJavaFilesFromPackage(packageName)
    val (totalTests, passedTests, failedTests) = executeTests(files, packageName)
    
    println(s"=== EXECUTION COMPLETE: $totalTests tests executed ===")
    println(s"Results: $passedTests passed, $failedTests failed")
    println(s"Results saved to: ${TestResultStorage.getResultsDirectory(packageName).getAbsolutePath}")
    
    (totalTests, passedTests, failedTests)
  }

  def executeTests(files: List[AnyRef], packageName: String): (Int, Int, Int) = {
    var totalTests = 0
    var passedTests = 0
    var failedTests = 0
    
    files.foreach {
      case list: List[_] =>
        val (subTotal, subPassed, subFailed) = this.executeTests(list.asInstanceOf[List[AnyRef]], packageName)
        totalTests += subTotal
        passedTests += subPassed
        failedTests += subFailed
      case className: String => 
        val (testTotal, testPassed, testFailed) = executeTest(className, packageName)
        totalTests += testTotal
        passedTests += testPassed
        failedTests += testFailed
      case list: java.nio.file.Path => 
        val (pathTotal, pathPassed, pathFailed) = executeTestFromPath(list, packageName)
        totalTests += pathTotal
        passedTests += pathPassed
        failedTests += pathFailed
      case _ =>
    }
    
    (totalTests, passedTests, failedTests)
  }

  def executeTest(className: String, packageName: String): (Int, Int, Int) = {
    try {
      val clazz = Class.forName(className)
      val testName = className.split("\\.").last

      println(s"Executing: $testName")
      
      val svfa = new SecuribenchTest(className, entryPointMethod())
      val startTime = System.currentTimeMillis()
      
      svfa.buildSparseValueFlowGraph()
      val conflicts = svfa.reportConflictsSVG()
      
      val endTime = System.currentTimeMillis()
      val executionTime = endTime - startTime

      val expected = clazz
        .getMethod("getVulnerabilityCount")
        .invoke(clazz.getDeclaredConstructor().newInstance())
        .asInstanceOf[Int]
      
      val found = conflicts.size
      
      // Convert conflicts to serializable format
      val conflictStrings = conflicts.map(_.toString).toList

      val result = TestExecutionResult(
        testName = testName,
        packageName = packageName,
        className = className,
        expectedVulnerabilities = expected,
        foundVulnerabilities = found,
        executionTimeMs = executionTime,
        conflicts = conflictStrings
      )

      TestResultStorage.saveTestResult(result)
      
      val passed = found == expected
      val status = if (passed) "✅ PASS" else "❌ FAIL"
      println(s"  $testName: $found/$expected conflicts - $status (${executionTime}ms)")
      
      (1, if (passed) 1 else 0, if (passed) 0 else 1)
      
    } catch {
      case e: Exception =>
        println(s"❌ ERROR executing $className: ${e.getMessage}")
        e.printStackTrace()
        (1, 0, 1) // Count as failed test
    }
  }

  def executeTestFromPath(file: java.nio.file.Path, packageName: String): (Int, Int, Int) = {
    var fileName = file.toString.replace(".class", "").replace("/", ".")
    fileName = fileName.split(packageName).last
    val className = s"$packageName$fileName"
    executeTest(className, packageName)
  }

  test(s"execute tests for ${basePackage()}") {
    val (totalTests, passedTests, failedTests) = executeTests(basePackage())
    
    // Provide clear summary
    println()
    println(s"📊 EXECUTION SUMMARY:")
    println(s"   Total tests: $totalTests")
    println(s"   Passed: $passedTests")
    println(s"   Failed: $failedTests")
    println(s"   Success rate: ${if (totalTests > 0) (passedTests * 100 / totalTests) else 0}%")
    println()
    
    // Note: We don't fail the SBT test even if SVFA analysis fails
    // This is intentional - we want to save results for analysis
    // The "success" refers to technical execution, not analysis accuracy
    println("ℹ️  Note: SBT 'success' indicates technical execution completed.")
    println("   Individual test results show SVFA analysis accuracy.")
  }
}
