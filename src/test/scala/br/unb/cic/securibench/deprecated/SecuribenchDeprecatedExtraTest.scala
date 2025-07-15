package br.unb.cic.securibench.deprecated

import br.unb.cic.securibench.SecuribenchTest
import org.scalatest.FunSuite

class SecuribenchDeprecatedExtraTest extends FunSuite {

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

  /**
   * REFLECTION TESTs
   */
  ignore("in the class Refl1 we should detect 1 conflict(s) of a Reflection test case") {
    val svfa = new SecuribenchTest("securibench.micro.reflection.Refl1", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 1)
  }

  ignore("in the class Refl2 we should detect 1 conflict(s) of a Reflection test case") {
    val svfa = new SecuribenchTest("securibench.micro.reflection.Refl2", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 1)
  }

  ignore("in the class Refl3 we should detect 1 conflict(s) of a Reflection test case") {
    val svfa = new SecuribenchTest("securibench.micro.reflection.Refl3", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 1)
  }

  ignore("in the class Refl4 we should detect 1 conflict(s) of a Reflection test case") {
    val svfa = new SecuribenchTest("securibench.micro.reflection.Refl4", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 1)
  }

  /**
   * SANITIZERS TESTs
   */
  ignore("in the class Sanitizers1 we should detect 1 conflict(s) of a Sanitizers test case") {
    val svfa = new SecuribenchTest("securibench.micro.sanitizers.Sanitizers1", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 1)
  }

  ignore("in the class Sanitizers2 we should detect 1 conflict(s) of a Sanitizers test case") {
    val svfa = new SecuribenchTest("securibench.micro.sanitizers.Sanitizers2", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 1)
  }

  test("in the class Sanitizers3 we should detect 0 conflict(s) of a Sanitizers test case") {
    val svfa = new SecuribenchTest("securibench.micro.sanitizers.Sanitizers3", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 0)
  }

  ignore("in the class Sanitizers4 we should detect 2 conflict(s) of a Sanitizers test case") {
    val svfa = new SecuribenchTest("securibench.micro.sanitizers.Sanitizers4", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 2)
  }

  ignore("in the class Sanitizers5 we should detect 1 conflict(s) of a Sanitizers test case") {
    val svfa = new SecuribenchTest("securibench.micro.sanitizers.Sanitizers5", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 1)
  }

  ignore("in the class Sanitizers6 we should detect 1 conflict(s) of a Sanitizers test case") {
    val svfa = new SecuribenchTest("securibench.micro.sanitizers.Sanitizers6", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 1)
  }
}