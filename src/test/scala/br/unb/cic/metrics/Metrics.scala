package br.unb.cic.metrics

trait Metrics {

    def computeMetricsByResults(expected: Int, actual: Int): Unit = {
        var TP = 0.0
        var FP = 0.0
        var P = 0.0

        // Compute expected and unexpected flows
        P = expected
        if (actual > expected) {
            TP = expected
            FP = (actual - expected)
        }
        else {
            TP = actual
        }

        // Compute Metrics
        computeMetricsByCriterions(P,TP, FP)
    }

    def computeMetricsByCriterions(P: Double, TP: Double, FP: Double): Unit = {
        val precision = TP / (TP + FP)
        val recall = TP / P
        val fscore = (2 * precision * recall) / (precision + recall)

        println(s"P: $P")
        println(s"TP: $TP")
        println(s"FP: $FP")

        println(f"precision: $precision%1.2f")
        println(f"recall: $recall%1.2f")
        println(f"fscore $fscore%1.2f")
    }
}
