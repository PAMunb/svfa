package br.unb.cic.metrics

trait CustomMetrics {

  var truePositives: Int = 0
  var falsePositives: Int = 0
  var falseNegatives: Int = 0
  var trueNegatives: Int = 0
  var passedTests: Int = 0
  var failedTests: Int = 0
  var expected: Int = 0
  var found: Int = 0

  def reportTruePositives(truePositives: Int): Unit = {
    this.truePositives += truePositives
  }

  def reportFalsePositives(falsePositives: Int): Unit = {
    this.falsePositives += falsePositives
  }

  def reportFalseNegatives(falseNegatives: Int): Unit = {
    this.falseNegatives += falseNegatives
  }

  def reportTrueNegatives(): Unit = {
    this.trueNegatives += 1
  }

  def reportPassedTest(): Unit = {
    this.passedTests += 1
  }

  def reportFailedTest(): Unit = {
    this.failedTests += 1
  }

  def reportExpected(expected: Int): Unit = {
    this.expected += expected
  }

  def reportFound(found: Int): Unit = {
    this.found += found
  }

  def compute(expected: Int, found: Int): Unit = {
    this.reportExpected(expected)
    this.reportFound(found)

    if (expected == found) {
      this.reportPassedTest()
      if (expected == 0) {
        this.reportTrueNegatives()
        return
      }
      this.reportTruePositives(expected)
      return
    }

    this.reportFailedTest()
    if (found > expected) {
      this.reportFalsePositives(found - expected)
      return
    }
    this.reportFalseNegatives(expected - found)
  }

  def precision: Double = {
    val denom = this.truePositives + this.falsePositives
    val value = if (denom == 0) 0.0 else (this.truePositives * 1.0) / denom
    BigDecimal(value).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
  }

  def recall: Double = {
    val denom = this.truePositives + this.falseNegatives
    val value = if (denom == 0) 0.0 else (this.truePositives * 1.0) / denom
    BigDecimal(value).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
  }

  def f1Score: Double = {
    val p = precision
    val r = recall
    val value = if (p + r == 0) 0.0 else 2 * (p * r) / (p + r)
    BigDecimal(value).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
  }

  def passRate: Double = {
    val denom = this.passedTests + this.failedTests
    val value = if (denom == 0) 0.0 else (this.passedTests * 1.0) / denom * 100
    BigDecimal(value).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
  }

  def vulnerabilities: Int = this.expected

  def vulnerabilitiesFound: Int = this.found

  def report(): Unit = {
    println("----------------------------------------------------------------------------------------------------------------")
    println(s"failed = ${this.failedTests}, passed = ${this.passedTests} of = ${this.passedTests + this.failedTests} tests.")
    println(s"Pass Rate = ${this.passRate}%")
    println(s"Expecting ${this.vulnerabilities} of ${this.vulnerabilitiesFound} warnings.")
    println(s"TP = ${this.truePositives} FP = ${this.falsePositives} FN = ${this.falseNegatives} TN = ${this.trueNegatives}")
    println(s"Precision = ${this.precision}% Recall = ${this.recall}% F-score = ${this.f1Score}%")
  }
}
