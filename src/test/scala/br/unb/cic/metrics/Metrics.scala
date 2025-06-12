package br.unb.cic.metrics

import java.util.concurrent.TimeUnit


trait Metrics {

  var beforeExecutionTime : Long = 0.toLong

  def computeMetricsByResults(expected: Int, actual: Int): Unit = {
    var TP = 0.0
    var FP = 0.0
    var FN = 0.0

    // Compute expected and unexpected flows
    if (actual > expected) {
      FP = actual - expected
    }
    else if (actual < expected){
      FN = expected - actual
    }
    else {
      TP = expected
    }

    // Compute Metrics
    computeMetricsByCriterions(TP, FP, FN)
  }

  def computeMetricsByCriterions(TP: Double, FP: Double, FN: Double): Unit = {
    val precision = TP / (TP + FP)
    val recall = TP / (TP + FN)
    val fscore = (2 * precision * recall) / (precision + recall)

    println(s"TP: $TP")
    println(s"FP: $FP")
    println(s"FP: $FN")

    println(f"precision: $precision%1.2f")
    println(f"recall: $recall%1.2f")
    println(f"fscore $fscore%1.2f")
  }

  def startExecutionTime(): Unit = beforeExecutionTime = System.nanoTime;

  def endExecutionTime(): Unit = {
    println(s"Execution time: ${TimeUnit.NANOSECONDS.toMillis(System.nanoTime - beforeExecutionTime)} ms.")
  }
}
