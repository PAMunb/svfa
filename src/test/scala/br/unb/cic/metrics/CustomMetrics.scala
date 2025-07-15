package br.unb.cic.metrics
import scala.collection.mutable

trait CustomMetrics {

  private val metricsByTest = mutable.Map[String, Metrics]()

  private def getOrCreateMetrics(testName: String): Metrics =
    metricsByTest.getOrElseUpdate(testName, Metrics())

  def reportTruePositives(testName: String, truePositives: Int): Unit = {
    getOrCreateMetrics(testName).truePositives += truePositives
  }

  def reportFalsePositives(testName: String, falsePositives: Int): Unit = {
    getOrCreateMetrics(testName).falsePositives += falsePositives
  }

  def reportFalseNegatives(testName: String, falseNegatives: Int): Unit = {
    getOrCreateMetrics(testName).falseNegatives += falseNegatives
  }

  def reportTrueNegatives(testName: String): Unit = {
    getOrCreateMetrics(testName).trueNegatives += 1
  }

  def reportPassedTest(testName: String): Unit = {
    getOrCreateMetrics(testName).passedTests += 1
  }

  def reportFailedTest(testName: String): Unit = {
    getOrCreateMetrics(testName).failedTests += 1
  }

  def reportExpected(testName: String, expected: Int): Unit = {
    getOrCreateMetrics(testName).expected += expected
  }

  def reportFound(testName: String, found: Int): Unit = {
    getOrCreateMetrics(testName).found += found
  }

  def compute(expected: Int, found: Int, testName: String): Unit = {
    reportExpected(testName, expected)
    reportFound(testName, found)
    (expected, found) match {
      case (e, f) if e == f && e == 0 =>
        reportPassedTest(testName)
        reportTrueNegatives(testName)
      case (e, f) if e == f =>
        reportPassedTest(testName)
        reportTruePositives(testName, e)
      case (e, f) if f > e =>
        reportFailedTest(testName)
        reportFalsePositives(testName, f - e)
      case (e, f) if e > f =>
        reportFailedTest(testName)
        reportFalseNegatives(testName, e - f)
    }
  }

  def precision(testName: String): Double = {
    val m = getOrCreateMetrics(testName)
    val denom = m.truePositives + m.falsePositives
    val value = denom match {
      case 0 => 0.0
      case d => (m.truePositives * 1.0) / d
    }
    BigDecimal(value).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
  }

  def recall(testName: String): Double = {
    val m = getOrCreateMetrics(testName)
    val denom = m.truePositives + m.falseNegatives
    val value = denom match {
      case 0 => 0.0
      case d => (m.truePositives * 1.0) / d
    }
    BigDecimal(value).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
  }

  def f1Score(testName: String): Double = {
    val p = precision(testName)
    val r = recall(testName)
    val value = (p + r) match {
      case 0.0 => 0.0
      case s => 2 * (p * r) / s
    }
    BigDecimal(value).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
  }

  def passRate(testName: String): Double = {
    val m = getOrCreateMetrics(testName)
    val denom = m.passedTests + m.failedTests
    val value = denom match {
      case 0 => 0.0
      case d => (m.passedTests * 1.0) / d * 100
    }
    BigDecimal(value).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
  }

  def vulnerabilities(testName: String): Int = getOrCreateMetrics(testName).expected
  def vulnerabilitiesFound(testName: String): Int = getOrCreateMetrics(testName).found

  def metricsFor(testName: String): Metrics = getOrCreateMetrics(testName)

  def report(testName: String): Unit = {
    val m = getOrCreateMetrics(testName)
    println("----------------------------------------------------------------------------------------------------------------")
    println(s"Metrics for test: $testName")
    println(s"failed = ${m.failedTests}, passed = ${m.passedTests} of = ${m.passedTests + m.failedTests} tests.")
    println(s"Pass Rate = ${passRate(testName)}%")
    println(s"Expecting ${vulnerabilities(testName)} of ${vulnerabilitiesFound(testName)} warnings.")
    println(s"TP = ${m.truePositives} FP = ${m.falsePositives} FN = ${m.falseNegatives} TN = ${m.trueNegatives}")
    println(s"Precision = ${precision(testName)}% Recall = ${recall(testName)}% F-score = ${f1Score(testName)}%")
  }

  def reportAll(): Unit = {
    metricsByTest.keys.foreach(report)
  }

  def reportSummary(): Unit = {
    val total = metricsByTest.values.foldLeft(Metrics()) { (acc, m) =>
      acc.truePositives += m.truePositives
      acc.falsePositives += m.falsePositives
      acc.falseNegatives += m.falseNegatives
      acc.trueNegatives += m.trueNegatives
      acc.passedTests += m.passedTests
      acc.failedTests += m.failedTests
      acc.expected += m.expected
      acc.found += m.found
      acc
    }
    val denomPrecision = total.truePositives + total.falsePositives
    val precision = denomPrecision match {
      case 0 => 0.0
      case d => (total.truePositives * 1.0) / d
    }
    val denomRecall = total.truePositives + total.falseNegatives
    val recall = denomRecall match {
      case 0 => 0.0
      case d => (total.truePositives * 1.0) / d
    }
    val f1 = (precision + recall) match {
      case 0.0 => 0.0
      case s => 2 * (precision * recall) / s
    }
    val denomPassRate = total.passedTests + total.failedTests
    val passRate = denomPassRate match {
      case 0 => 0.0
      case d => (total.passedTests * 1.0) / d * 100
    }
    println("================================================================================================================")
    println("Summary of all metrics:")
    println(s"Total failed = ${total.failedTests}, passed = ${total.passedTests} of = ${total.passedTests + total.failedTests} tests.")
    println(f"Total Pass Rate = ${BigDecimal(passRate).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble}%2.2f%%")
    println(s"Total Expecting ${total.expected} of ${total.found} warnings.")
    println(s"Total TP = ${total.truePositives} FP = ${total.falsePositives} FN = ${total.falseNegatives} TN = ${total.trueNegatives}")
    println(f"Total Precision = ${BigDecimal(precision).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble}%2.2f Recall = ${BigDecimal(recall).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble}%2.2f F-score = ${BigDecimal(f1).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble}%2.2f")
    println("================================================================================================================")
  }
}
