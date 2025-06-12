package br.unb.cic.metrics

import br.unb.cic.android.AndroidTaintBenchTest
import org.scalatest.FunSuite


class MetricsTest extends FunSuite with Metrics {

    test("metrics") {
      val expected = 7
      val actual = 1

      this.computeMetricsByResults(expected, actual)
    }

    test("computeMetricsByCriterions") {
      val P = 0
      val TP = 2
      val FP = 2
      val FN = 7

      this.computeMetricsByCriterions(TP, FP, FN)
    }
}
