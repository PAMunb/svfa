package br.unb.cic.android

import org.scalatest.FunSuite

class RoidsecTest extends FunSuite {
  test("This is a test case for the Roidsec benchmark") {
    assume(scala.util.Properties.envOrNone("ANDROID_SDK").isDefined)
    assume(scala.util.Properties.envOrNone("TAINT_BENCH").isDefined)
    val svfa = new AndroidTaintBenchTest("roidsec")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 1)
  }
}

