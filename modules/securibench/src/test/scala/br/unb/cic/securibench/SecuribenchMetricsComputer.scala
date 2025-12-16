package br.unb.cic.securibench

import org.scalatest.FunSuite
import br.unb.cic.metrics.TestResult

/**
 * Phase 2: Compute metrics from previously executed test results
 * This class loads saved test results and computes accuracy metrics
 */
abstract class SecuribenchMetricsComputer extends FunSuite with TestResult {

  def basePackage(): String

  def computeMetrics(packageName: String): Unit = {
    println(s"=== PHASE 2: COMPUTING METRICS FOR $packageName ===")
    
    val results = TestResultStorage.loadTestResults(packageName)
    
    if (results.isEmpty) {
      println(s"❌ No test results found for $packageName")
      println(s"   Please run the test executor first!")
      return
    }

    println(s"Loaded ${results.size} test results")
    println()

    // Process each result and compute metrics
    results.foreach { result =>
      this.compute(
        expected = result.expectedVulnerabilities,
        found = result.foundVulnerabilities, 
        testName = result.testName,
        executionTime = result.executionTimeMs
      )
    }

    // Print summary table
    printSummaryTable(results, packageName)
    
    // Print overall statistics
    printOverallStatistics(results)
  }

  private def printSummaryTable(results: List[TestExecutionResult], packageName: String): Unit = {
    val packageDisplayName = packageName.split("\\.").last
    
    println(s"- **$packageDisplayName** - failed: ${failedTests(results)}, passed: ${passedTests(results)} of ${results.size} tests - (${successRate(results)}%)")
    println("|      Test      | Found | Expected | Status | TP | FP | FN | Precision | Recall | F-score |")
    println("|:--------------:|:-----:|:--------:|:------:|:--:|:--:|:---|:---------:|:------:|:-------:|")
    
    results.sortBy(_.testName).foreach { result =>
      val status = if (result.foundVulnerabilities == result.expectedVulnerabilities) "✅" else "❌"
      val tp = math.min(result.foundVulnerabilities, result.expectedVulnerabilities)
      val fp = math.max(0, result.foundVulnerabilities - result.expectedVulnerabilities)
      val fn = math.max(0, result.expectedVulnerabilities - result.foundVulnerabilities)
      
      val precision = if (result.foundVulnerabilities > 0) tp.toDouble / result.foundVulnerabilities else 0.0
      val recall = if (result.expectedVulnerabilities > 0) tp.toDouble / result.expectedVulnerabilities else 0.0
      val fscore = if (precision + recall > 0) 2 * precision * recall / (precision + recall) else 0.0
      
      println(f"| ${result.testName}%-12s |     ${result.foundVulnerabilities}%d |        ${result.expectedVulnerabilities}%d |      $status |  $tp%d |  $fp%d |   $fn%d |      ${precision}%.2f |   ${recall}%.2f |    ${fscore}%.2f |")
    }
    
    // Total row
    val totalFound = results.map(_.foundVulnerabilities).sum
    val totalExpected = results.map(_.expectedVulnerabilities).sum
    val totalTP = results.map(r => math.min(r.foundVulnerabilities, r.expectedVulnerabilities)).sum
    val totalFP = results.map(r => math.max(0, r.foundVulnerabilities - r.expectedVulnerabilities)).sum
    val totalFN = results.map(r => math.max(0, r.expectedVulnerabilities - r.foundVulnerabilities)).sum
    
    val totalPrecision = if (totalFound > 0) totalTP.toDouble / totalFound else 0.0
    val totalRecall = if (totalExpected > 0) totalTP.toDouble / totalExpected else 0.0
    val totalFscore = if (totalPrecision + totalRecall > 0) 2 * totalPrecision * totalRecall / (totalPrecision + totalRecall) else 0.0
    
    val passedCount = results.count(r => r.foundVulnerabilities == r.expectedVulnerabilities)
    
    println(f"| TOTAL         |     $totalFound%d |       $totalExpected%d |   $passedCount%d/${results.size}%d |  $totalTP%d |  $totalFP%d |  $totalFN%2d |      ${totalPrecision}%.2f |   ${totalRecall}%.2f |    ${totalFscore}%.2f |")
  }

  private def printOverallStatistics(results: List[TestExecutionResult]): Unit = {
    println()
    println("=== OVERALL STATISTICS ===")
    
    val totalTests = results.size
    val passedTests = results.count(r => r.foundVulnerabilities == r.expectedVulnerabilities)
    val failedTests = totalTests - passedTests
    
    val totalExecutionTime = results.map(_.executionTimeMs).sum
    val avgExecutionTime = if (totalTests > 0) totalExecutionTime.toDouble / totalTests else 0.0
    
    val totalVulnerabilities = results.map(_.expectedVulnerabilities).sum
    val totalFound = results.map(_.foundVulnerabilities).sum
    
    println(f"Tests: $totalTests%d total, $passedTests%d passed, $failedTests%d failed")
    println(f"Success Rate: ${successRate(results)}%.1f%%")
    println(f"Vulnerabilities: $totalFound%d found, $totalVulnerabilities%d expected")
    println(f"Execution Time: ${totalExecutionTime}ms total, ${avgExecutionTime}%.1fms average")
    println()
    
    // Show slowest tests
    val slowestTests = results.sortBy(-_.executionTimeMs).take(3)
    println("Slowest Tests:")
    slowestTests.foreach { result =>
      println(f"  ${result.testName}: ${result.executionTimeMs}ms")
    }
  }

  private def failedTests(results: List[TestExecutionResult]): Int = {
    results.count(r => r.foundVulnerabilities != r.expectedVulnerabilities)
  }

  private def passedTests(results: List[TestExecutionResult]): Int = {
    results.count(r => r.foundVulnerabilities == r.expectedVulnerabilities)
  }

  private def successRate(results: List[TestExecutionResult]): Double = {
    if (results.isEmpty) 0.0
    else (passedTests(results).toDouble / results.size) * 100.0
  }

  test(s"compute metrics for ${basePackage()}") {
    computeMetrics(basePackage())
    
    // Load results for assertion
    val results = TestResultStorage.loadTestResults(basePackage())
    val totalExpected = results.map(_.expectedVulnerabilities).sum
    val totalFound = results.map(_.foundVulnerabilities).sum
    
    // This assertion can be customized based on your requirements
    // For now, we just ensure we have some results
    assert(results.nonEmpty, s"No test results found for ${basePackage()}")
    
    // Optional: Assert that we found the expected number of vulnerabilities
    // assert(totalFound == totalExpected, s"Expected $totalExpected vulnerabilities, found $totalFound")
  }
}
