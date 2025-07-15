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

  def precision(testName: String = null): Double = {
    val (tp, fp) = Option(testName) match {
      case Some(name) =>
        val m = getOrCreateMetrics(name)
        (m.truePositives, m.falsePositives)
      case None =>
        metricsByTest.values.foldLeft((0, 0)) { case ((accTP, accFP), m) => (accTP + m.truePositives, accFP + m.falsePositives) }
    }
    val denom = tp + fp
    val value = denom match {
      case 0 => 0.0
      case d => (tp * 1.0) / d
    }
    BigDecimal(value).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
  }

  def recall(testName: String = null): Double = {
    val (tp, fn) = Option(testName) match {
      case Some(name) =>
        val m = getOrCreateMetrics(name)
        (m.truePositives, m.falseNegatives)
      case None =>
        metricsByTest.values.foldLeft((0, 0)) { case ((accTP, accFN), m) => (accTP + m.truePositives, accFN + m.falseNegatives) }
    }
    val denom = tp + fn
    val value = denom match {
      case 0 => 0.0
      case d => (tp * 1.0) / d
    }
    BigDecimal(value).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
  }

  def f1Score(testName: String = null): Double = {
    val p = precision(testName)
    val r = recall(testName)
    val value = (p + r) match {
      case 0.0 => 0.0
      case s => 2 * (p * r) / s
    }
    BigDecimal(value).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
  }

  def passRate(testName: String = null): Double = {
    val (passed, failed) = Option(testName) match {
      case Some(name) =>
        val m = getOrCreateMetrics(name)
        (m.passedTests, m.failedTests)
      case None =>
        metricsByTest.values.foldLeft((0, 0)) { case ((accPassed, accFailed), m) => (accPassed + m.passedTests, accFailed + m.failedTests) }
    }
    val denom = passed + failed
    val value = denom match {
      case 0 => 0.0
      case d => (passed * 1.0) / d * 100
    }
    BigDecimal(value).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
  }

  def vulnerabilities(testName: String = null): Int = Option(testName) match {
    case Some(name) => getOrCreateMetrics(name).expected
    case None => metricsByTest.values.map(_.expected).sum
  }

  def vulnerabilitiesFound(testName: String = null): Int = Option(testName) match {
    case Some(name) => getOrCreateMetrics(name).found
    case None => metricsByTest.values.map(_.found).sum
  }

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
    val header = "|      Test      | Found | Expected | Status | TP | FP | FN | Precision | Recall | F-score |"
    val sep    = "|:--------------:|:-----:|:--------:|:------:|:--:|:--:|:---|:---------:|:------:|:-------:|"
    println(header)
    println(sep)
    var totalFound = 0
    var totalExpected = 0
    var totalPassed = 0
    var totalTests = 0
    var totalTP = 0
    var totalFP = 0
    var totalFN = 0
    metricsByTest.foreach { case (testName, m) =>
      val status = s"${m.passedTests}/${m.passedTests + m.failedTests}"
      val prec = precision(testName)
      val rec = recall(testName)
      val f1 = f1Score(testName)
      val shortTestName = testName.split('.').last.padTo(14, ' ')
      println(f"| $shortTestName| ${m.found}%5d | ${m.expected}%8d | ${status}%6s | ${m.truePositives}%2d | ${m.falsePositives}%2d | ${m.falseNegatives}%3d | ${prec}%9.2f | ${rec}%6.2f | ${f1}%7.2f |")
      totalFound += m.found
      totalExpected += m.expected
      totalPassed += m.passedTests
      totalTests += m.passedTests + m.failedTests
      totalTP += m.truePositives
      totalFP += m.falsePositives
      totalFN += m.falseNegatives
    }
    val totalPrec = precision()
    val totalRec = recall()
    val totalF1 = f1Score()
    val totalStatus = s"${totalPassed}/${totalTests}"
    println(f"|     TOTAL      | ${totalFound}%5d | ${totalExpected}%8d | ${totalStatus}%6s | ${totalTP}%2d | ${totalFP}%2d | ${totalFN}%3d | ${totalPrec}%9.2f | ${totalRec}%6.2f | ${totalF1}%7.2f |")
  }
}
