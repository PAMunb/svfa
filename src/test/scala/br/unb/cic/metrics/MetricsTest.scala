package br.unb.cic.metrics

import org.scalatest.FunSuite


class MetricsTest extends FunSuite with CustomMetrics {

  test("precision returns 0.0 when denominator is zero") {
    this.truePositives = 0
    this.falsePositives = 0
    assert(precision == 0.0)
  }

  test("recall returns 0.0 when denominator is zero") {
    this.truePositives = 0
    this.falseNegatives = 0
    assert(recall == 0.0)
  }

  test("f1Score returns 0.0 when both precision and recall are zero") {
    this.truePositives = 0
    this.falsePositives = 0
    this.falseNegatives = 0
    assert(f1Score == 0.0)
  }

  test("passRate returns 0.0 when denominator is zero") {
    this.passedTests = 0
    this.failedTests = 0
    assert(passRate == 0.0)
  }

  test("precision, recall, f1Score, and passRate normal cases") {
    this.truePositives = 8
    this.falsePositives = 2
    this.falseNegatives = 2
    this.passedTests = 3
    this.failedTests = 1
    assert(precision == 0.8)
    assert(recall == 0.8)
    assert(f1Score == 0.8)
    assert(passRate == 75.0)
  }

  test("compute method updates metrics for true positive case") {
    this.truePositives = 0
    this.falsePositives = 0
    this.falseNegatives = 0
    this.trueNegatives = 0
    this.passedTests = 0
    this.failedTests = 0
    this.expected = 0
    this.found = 0
    compute(5, 5, "")
    assert(this.truePositives == 5)
    assert(this.passedTests == 1)
    assert(this.failedTests == 0)
    assert(this.trueNegatives == 0)
  }

  test("compute method updates metrics for true negative case") {
    this.truePositives = 0
    this.falsePositives = 0
    this.falseNegatives = 0
    this.trueNegatives = 0
    this.passedTests = 0
    this.failedTests = 0
    this.expected = 0
    this.found = 0
    compute(0, 0, "")
    assert(this.trueNegatives == 1)
    assert(this.passedTests == 1)
    assert(this.failedTests == 0)
    assert(this.truePositives == 0)
  }

  test("compute method updates metrics for false positive case") {
    this.truePositives = 0
    this.falsePositives = 0
    this.falseNegatives = 0
    this.trueNegatives = 0
    this.passedTests = 0
    this.failedTests = 0
    this.expected = 0
    this.found = 0
    compute(2, 5, "")
    assert(this.falsePositives == 3)
    assert(this.failedTests == 1)
    assert(this.passedTests == 0)
  }

  test("compute method updates metrics for false negative case") {
    this.truePositives = 0
    this.falsePositives = 0
    this.falseNegatives = 0
    this.trueNegatives = 0
    this.passedTests = 0
    this.failedTests = 0
    this.expected = 0
    this.found = 0
    compute(5, 2, "")
    assert(this.falseNegatives == 3)
    assert(this.failedTests == 1)
    assert(this.passedTests == 0)
  }
}
