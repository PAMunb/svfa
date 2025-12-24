package br.unb.cic.svfa.conflicts

import br.unb.cic.soot.{LineBasedSVFATest, MethodBasedSVFATest}
import org.scalatest.{BeforeAndAfter, FunSuite}

class SemanticConflictsTestSuite  extends FunSuite with BeforeAndAfter {
  test("we should find conflicts in samples.conflicts.Sample01") {
    val svfa = new LineBasedSVFATest(
      className = "samples.conflicts.Sample01",
      mainMethod = "main",
      sourceLines = Set(7),
      sinkLines = Set(9))

    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().nonEmpty)
  }

  ignore("we should find conflicts in samples.conflicts.Sample02---however, the JIMPLE is optimized, leading to no conflict.") {
    val svfa = new LineBasedSVFATest(
      className = "samples.conflicts.Sample02",
      mainMethod = "execute",
      sourceLines = Set(7),
      sinkLines = Set(9))

    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().nonEmpty)
  }

  test("we should find conflicts in samples.conflicts.Sample03") {
    val svfa = new LineBasedSVFATest(
      className = "samples.conflicts.Sample03",
      mainMethod = "execute",
      sourceLines = Set(7),
      sinkLines = Set(9))

    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().nonEmpty)
  }
}
