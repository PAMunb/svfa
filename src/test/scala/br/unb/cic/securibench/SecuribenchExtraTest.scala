package br.unb.cic.securibench

import org.scalatest.FunSuite

class SecuribenchExtraTest extends FunSuite {

  /**
   * PRED TESTs
   */

  test("in the class Pred1 we should detect 0 conflict(s) of a Pred test case") {
    val svfa = new SecuribenchTest("securibench.micro.pred.Pred1", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 0)
  }

  test("in the class Pred2 we should detect 1 conflict(s) of a Pred test case") {
    val svfa = new SecuribenchTest("securibench.micro.pred.Pred2", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 1)
  }

  ignore("in the class Pred3 we should detect 0 conflict(s) of a Pred test case") {
    val svfa = new SecuribenchTest("securibench.micro.pred.Pred3", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 0)
  }

  test("in the class Pred4 we should detect 1 conflict(s) of a Pred test case") {
    val svfa = new SecuribenchTest("securibench.micro.pred.Pred4", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 1)
  }

  test("in the class Pred5 we should detect 1 conflict(s) of a Pred test case") {
    val svfa = new SecuribenchTest("securibench.micro.pred.Pred5", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 1)
  }

  ignore("in the class Pred6 we should detect 0 conflict(s) of a Pred test case") {
    val svfa = new SecuribenchTest("securibench.micro.pred.Pred6", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 0)
  }

  ignore("in the class Pred7 we should detect 0 conflict(s) of a Pred test case") {
    val svfa = new SecuribenchTest("securibench.micro.pred.Pred7", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 0)
  }

  test("in the class Pred8 we should detect 1 conflict(s) of a Pred test case") {
    val svfa = new SecuribenchTest("securibench.micro.pred.Pred8", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 1)
  }

  test("in the class Pred9 we should detect 1 conflict(s) of a Pred test case") {
    val svfa = new SecuribenchTest("securibench.micro.pred.Pred9", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 1)
  }
}