package br.unb.cic.android

import br.unb.cic.android.specs.RoidSecSpec
import br.unb.cic.soot.svfa.report.ReportFormat
import org.scalatest.FunSuite

class RoidsecTest extends FunSuite with ReportFormat {
  test("This is a test case for the Roidsec benchmark") {
    val svfa = new AndroidTaintBenchTest("roidsec")// with RoidSecSpec
    svfa.buildSparseValueFlowGraph()
    createJsonReport(svfa.conflictPaths() ,"roidsec", "docs-metrics/taintbench/experiment-I/findings")
    assert(svfa.reportConflictsSVG().size == 6)
  }
}
