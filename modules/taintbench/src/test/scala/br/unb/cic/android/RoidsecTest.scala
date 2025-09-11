package br.unb.cic.android

import br.unb.cic.android.specs.RoidSecSpec
import org.scalatest.FunSuite

class RoidsecTest extends FunSuite {
  test("This is a test case for the Roidsec benchmark") {
    val svfa = new AndroidTaintBenchTest("roidsec") with RoidSecSpec
    svfa.buildSparseValueFlowGraph()
    svfa.findConflictingPaths("roidsec", "docs-metrics/taintbench/experiment-I/findings")
    assert(svfa.reportConflictsSVG().size == 6)
  }
}
