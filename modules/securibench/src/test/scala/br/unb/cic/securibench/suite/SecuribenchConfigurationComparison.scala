package br.unb.cic.securibench.suite

import br.unb.cic.securibench.{ConfigurableSecuribenchTest, SecuribenchTest}
import br.unb.cic.soot.svfa.jimple.SVFAConfig
import org.scalatest.FunSuite

/**
 * Comprehensive configuration comparison for Securibench Inter test suite.
 * 
 * This test suite runs the same Inter test cases with different SVFA configurations
 * to compare analysis results, performance, and accuracy across different settings.
 */
class SecuribenchConfigurationComparison extends FunSuite {

  val testPackage = "securibench.micro.inter"
  val entryPoint = "doGet"

  test("Inter test suite: Configuration comparison") {
    val configurations = Map(
      "Default" -> SVFAConfig.Default,
      "Fast" -> SVFAConfig.Fast,
      "Precise" -> SVFAConfig.Precise,
      "Intraprocedural" -> SVFAConfig.Default.copy(interprocedural = false),
      "Field-Insensitive" -> SVFAConfig.Default.copy(fieldSensitive = false),
      "No-Taint-Propagation" -> SVFAConfig.Default.copy(propagateObjectTaint = false),
      "Minimal" -> SVFAConfig(
        interprocedural = false,
        fieldSensitive = false,
        propagateObjectTaint = false
      )
    )

    println(s"\n=== SECURIBENCH INTER CONFIGURATION COMPARISON ===")
    println(f"${"Configuration"}%-20s ${"Conflicts"}%-10s ${"Expected"}%-10s ${"Accuracy"}%-10s ${"Time"}%-10s")
    println("-" * 70)

    val results = configurations.map { case (configName, config) =>
      val startTime = System.currentTimeMillis()
      
      // Discover test cases
      val testRunner = new ConfigurableSecuribenchTest {
        override def basePackage(): String = testPackage
        override def entryPointMethod(): String = entryPoint
        override def svfaConfig: SVFAConfig = config
      }
      
      val testCases = testRunner.getJavaFilesFromPackage(testPackage)
      var totalFound = 0
      var totalExpected = 0
      var totalTime = 0L
      var passedTests = 0
      
      testCases.foreach {
        case className: String =>
          try {
            val clazz = Class.forName(className)
            val svfa = new SecuribenchTest(className, entryPoint, config)
            
            val testStartTime = System.currentTimeMillis()
            svfa.buildSparseValueFlowGraph()
            val conflicts = svfa.reportConflictsSVG()
            val testEndTime = System.currentTimeMillis()
            
            val expected = clazz
              .getMethod("getVulnerabilityCount")
              .invoke(clazz.getDeclaredConstructor().newInstance())
              .asInstanceOf[Int]
            
            val found = conflicts.size
            totalFound += found
            totalExpected += expected
            totalTime += (testEndTime - testStartTime)
            
            if (found == expected) passedTests += 1
            
          } catch {
            case e: Exception =>
              println(s"Error processing $className with $configName: ${e.getMessage}")
          }
        case _ =>
      }
      
      val endTime = System.currentTimeMillis()
      val totalExecutionTime = endTime - startTime
      val accuracy = if (totalExpected > 0) (totalFound.toDouble / totalExpected * 100) else 0.0
      
      println(f"$configName%-20s $totalFound%-10d $totalExpected%-10d ${accuracy}%-10.1f%%${totalExecutionTime}%-10dms")
      
      (configName, totalFound, totalExpected, accuracy, totalExecutionTime, passedTests, testCases.size)
    }

    println("-" * 70)
    println(s"Total test cases: ${results.head._7}")
    
    // Analysis summary
    println(s"\n=== CONFIGURATION ANALYSIS ===")
    
    val bestAccuracy = results.maxBy(_._4)
    val fastestConfig = results.minBy(_._5)
    val mostConflicts = results.maxBy(_._2)
    
    println(s"Best accuracy: ${bestAccuracy._1} (${bestAccuracy._4}%.1f%%)")
    println(s"Fastest execution: ${fastestConfig._1} (${fastestConfig._5}ms)")
    println(s"Most conflicts found: ${mostConflicts._1} (${mostConflicts._2} conflicts)")
    
    // Verify that we have meaningful results
    assert(results.nonEmpty, "Should have configuration results")
    assert(results.exists(_._2 > 0), "At least one configuration should find conflicts")
    
    // Print detailed comparison
    println(s"\n=== DETAILED COMPARISON ===")
    results.foreach { case (name, found, expected, accuracy, time, passed, total) =>
      val passRate = if (total > 0) (passed.toDouble / total * 100) else 0.0
      println(s"$name:")
      println(s"  Conflicts: $found/$expected (${accuracy}%.1f%% accuracy)")
      println(s"  Tests passed: $passed/$total (${passRate}%.1f%% pass rate)")
      println(s"  Execution time: ${time}ms")
      println()
    }
  }

  test("Basic test suite: Performance comparison") {
    val basicPackage = "securibench.micro.basic"
    val configurations = Map(
      "Fast" -> SVFAConfig.Fast,
      "Default" -> SVFAConfig.Default,
      "Precise" -> SVFAConfig.Precise
    )

    println(s"\n=== SECURIBENCH BASIC PERFORMANCE COMPARISON ===")
    
    configurations.foreach { case (configName, config) =>
      val startTime = System.currentTimeMillis()
      
      val testRunner = new ConfigurableSecuribenchTest {
        override def basePackage(): String = basicPackage
        override def entryPointMethod(): String = entryPoint
        override def svfaConfig: SVFAConfig = config
      }
      
      val testCases = testRunner.getJavaFilesFromPackage(basicPackage).take(5) // Limit to first 5 for performance test
      var totalConflicts = 0
      
      testCases.foreach {
        case className: String =>
          try {
            val svfa = new SecuribenchTest(className, entryPoint, config)
            svfa.buildSparseValueFlowGraph()
            val conflicts = svfa.reportConflictsSVG()
            totalConflicts += conflicts.size
          } catch {
            case e: Exception =>
              println(s"Error in performance test for $className: ${e.getMessage}")
          }
        case _ =>
      }
      
      val endTime = System.currentTimeMillis()
      val executionTime = endTime - startTime
      
      println(s"$configName: $totalConflicts conflicts, ${executionTime}ms (${testCases.size} test cases)")
    }
  }
}
