package br.unb.cic.metrics

import org.scalatest.FunSuite


class MetricsTest extends FunSuite with CustomMetrics {

  test("precision returns 0.0 when denominator is zero") {
    val testName = "precisionZeroDenom"
    compute(0, 0, testName)
    assert(precision(testName) == 0.0)
  }

  test("recall returns 0.0 when denominator is zero") {
    val testName = "recallZeroDenom"
    compute(0, 0, testName)
    assert(recall(testName) == 0.0)
  }

  test("f1Score returns 0.0 when both precision and recall are zero") {
    val testName = "f1Zero"
    compute(0, 0, testName)
    assert(f1Score(testName) == 0.0)
  }

  test("passRate returns 0.0 when denominator is zero") {
    val testName = "passRateZeroDenom"
    // No compute call, so passed/failed remain zero
    assert(passRate(testName) == 0.0)
  }

  test("precision, recall, f1Score, and passRate normal cases") {
    val testName = "normalCase"
    // Simulate 3 passed, 1 failed, 8 TP, 2 FP, 2 FN
    for (_ <- 1 to 3) reportPassedTest(testName)
    reportFailedTest(testName)
    reportTruePositives(testName, 8)
    reportFalsePositives(testName, 2)
    reportFalseNegatives(testName, 2)
    assert(precision(testName) == 0.8)
    assert(recall(testName) == 0.8)
    assert(f1Score(testName) == 0.8)
    assert(passRate(testName) == 75.0)
  }

  test("compute method updates metrics for true positive case") {
    val testName = "tpCase"
    compute(5, 5, testName)
    val m = metricsFor(testName)
    assert(m.truePositives == 5)
    assert(m.passedTests == 1)
    assert(m.failedTests == 0)
    assert(m.trueNegatives == 0)
  }

  test("compute method updates metrics for true negative case") {
    val testName = "tnCase"
    compute(0, 0, testName)
    val m = metricsFor(testName)
    assert(m.trueNegatives == 1)
    assert(m.passedTests == 1)
    assert(m.failedTests == 0)
    assert(m.truePositives == 0)
  }

  test("compute method updates metrics for false positive case") {
    val testName = "fpCase"
    compute(2, 5, testName)
    val m = metricsFor(testName)
    assert(m.falsePositives == 3)
    assert(m.failedTests == 1)
    assert(m.passedTests == 0)
  }

  test("compute method updates metrics for false negative case") {
    val testName = "fnCase"
    compute(5, 2, testName)
    val m = metricsFor(testName)
    assert(m.falseNegatives == 3)
    assert(m.failedTests == 1)
    assert(m.passedTests == 0)
  }
}
