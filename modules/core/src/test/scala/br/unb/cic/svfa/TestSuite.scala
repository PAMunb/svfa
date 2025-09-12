package br.unb.cic.soot

import org.scalatest.{BeforeAndAfter, FunSuite}

class TestSuite extends FunSuite with BeforeAndAfter {

  test("we should find exactly three conflicts in the ArraySample test") {
    val svfa = new MethodBasedSVFATest(
      className = "samples.ArraySample",
      sourceMethods = Set("source"),
      sinkMethods = Set("sink")
    )
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 3)
  }

  test("we should not find any conflict in the BlackBoardTest test") {
    val svfa = new MethodBasedSVFATest(
      className = "samples.BlackBoard",
      sourceMethods = Set("source"),
      sinkMethods = Set("sink")
    )
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 0)
  }

  test("we should find exactly one conflict in the CC16Test test") {
    val svfa = new MethodBasedSVFATest(
      className = "samples.CC16",
      sourceMethods = Set("source"),
      sinkMethods = Set("sink")
    )
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 1)
  }


  test("we should find exactly one conflict in the IfElseTest test") {
    val svfa = new MethodBasedSVFATest(
      className = "samples.IfElseScenario",
      sourceMethods = Set("source"),
      sinkMethods = Set("sink")
    )
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 1)
  }

  test("we should find two conflicts in the LogbackSampleTest test") {
    val svfa = new LineBasedSVFATest(
      className = "samples.LogbackSample",
      sourceLines = Set(24),
      sinkLines = Set(32, 33)
    )
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 3)
  }

  test("we should find exactly one conflict in the StringBuggerTest test") {
    val svfa = new MethodBasedSVFATest(
      className = "samples.StringBufferSample",
      sourceMethods = Set("source"),
      sinkMethods = Set("sink")
    )
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 1)
  }

  test("we should find exactly one conflict in the StringBuilderTest test") {
    val svfa = new MethodBasedSVFATest(
      className = "samples.StringBuilderSample",
      sourceMethods = Set("source"),
      sinkMethods = Set("sink")
    )
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 1)
  }

  test("we should find exactly one conflict in the StringBuilderComplexTest test") {
    val svfa = new MethodBasedSVFATest(
      className = "samples.StringBuilderComplexSample",
      sourceMethods = Set("source"),
      sinkMethods = Set("sink")
    )
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 1)
  }

  test(
    "we should find exactly one conflict in the InitStringBufferTest test"
  ) {
    val svfa = new MethodBasedSVFATest(
      className = "samples.InitStringBufferSample",
      sourceMethods = Set("source"),
      sinkMethods = Set("sink")
    )
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 1)
  }

  ignore(
    "we should find exactly one conflict in the StringConcatTest test"
  ) {
    val svfa = new MethodBasedSVFATest(
      className = "samples.StringConcatSample",
      sourceMethods = Set("source"),
      sinkMethods = Set("sink")
    )
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 6)
  }

  ignore("we should find exactly one conflict in the StringGetCharsTest test") {
    val svfa = new MethodBasedSVFATest(
      className = "samples.StringGetChars",
      sourceMethods = Set("source"),
      sinkMethods = Set("sink")
    )
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 1)
  }

  test(
    "we should find exactly one conflict in the StringToStringTest test"
  ) {
    val svfa = new MethodBasedSVFATest(
      className = "samples.StringToStringSample",
      sourceMethods = Set("source"),
      sinkMethods = Set("sink")
    )
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 1)
  }

  test(
    "we should find exactly one conflict in the ContextSensitive Sample  test"
  ) {
    val svfa = new MethodBasedSVFATest(
      className = "samples.ContextSensitiveSample",
      sourceMethods = Set("readConfiedentialContent"),
      sinkMethods = Set("sink")
    )
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 1)
  }

  test("we should find exactly one conflict in the FieldSample test") {
    val svfa = new LineBasedSVFATest(
      className = "samples.FieldSample",
      sourceLines = Set(6),
      sinkLines = Set(7, 11)
    )
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 2)
  }

  // This is the case with fields that the source method
  // changes the field that is subsequently used by a sink line
  ignore(
    "we should find exactly one conflict in the MethodFieldTest test"
  ) {
    val svfa = new LineBasedSVFATest(
      className = "samples.MethodFieldSample",
      mainMethod = "m",
      sourceLines = Set(7, 10, 11, 12),
      sinkLines = Set(8)
    )
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size >= 1)
  }

  // This is a simple case that the with a local variable would be detected
  // but with the field variable it isn't detected
  ignore(
    "we should find exactly one conflict in the InvokeInstanceMethodOnFieldTest analysis"
  ) {
    val svfa = new LineBasedSVFATest(
      className = "samples.InvokeInstanceMethodOnFieldSample",
      mainMethod = "m",
      sourceLines = Set(16),
      sinkLines = Set(18)
    )
    svfa.buildSparseValueFlowGraph()
    // System.out.println(svfa.svgToDotModel())
    assert(svfa.reportConflictsSVG().size >= 1)
  }

  // This case is representative of the problem with abstract classes and interfaces
  // that causes the analysis to not be able to infer the concrete implementation of the
  // methods.
  ignore("we should find exactly one conflict in the HashmapTest test") {
    val svfa = new HashmapTest()
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size >= 1)
  }

  test("[Confluence01] We should find exactly one conflict in the Confluence01 test") {
    val svfa = new MethodBasedSVFATest(
      className = "samples.fields.Confluence01",
      sourceMethods = Set("readPassword"),
      sinkMethods = Set("share")
    )
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 1)
  }

  test("[Confluence02] We should find exactly one conflict in the Confluence02 test") {
    val svfa = new MethodBasedSVFATest(
      className = "samples.fields.Confluence02",
      sourceMethods = Set("readPassword"),
      sinkMethods = Set("share")
    )
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 1)
  }

  ignore("[Confluence03] We should find exactly one conflict in the Confluence03 test") {
    val svfa = new LineBasedSVFATest(
      className = "samples.fields.Confluence03",
      sourceLines = Set(26),
      sinkLines = Set(14)
    )
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 1)
  }

  test("[Confluence04] We should find exactly one conflict in the Confluence04 test") {
    val svfa = new LineBasedSVFATest(
      className = "samples.fields.Confluence04",
      sourceLines = Set(15),
      sinkLines = Set(11)
    )
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size > 0)
  }

  // ============================================================================
  // CALL SITE MATCH TESTS - Integrated from CallSiteMatchTestSuite
  // ============================================================================

  test("in the class CallSiteMatch1 we should detect 1 conflict of a unopened callsite test case") {
    val svfa = new MethodBasedSVFATest(
      className = "samples.callSiteMatch.CallSiteMatch1",
      sourceMethods = Set("source"),
      sinkMethods = Set("sink")
    )
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 1)
  }

  test("in the class CallSiteMatch2 we should detect 1 conflict of a unclosed callsite test case") {
    val svfa = new MethodBasedSVFATest(
      className = "samples.callSiteMatch.CallSiteMatch2",
      sourceMethods = Set("source"),
      sinkMethods = Set("sink")
    )
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 1)
  }

  test("in the class CallSiteMatch3 we should detect 1 conflict of a unclosed and unopened callsite test case") {
    val svfa = new MethodBasedSVFATest(
      className = "samples.callSiteMatch.CallSiteMatch3",
      sourceMethods = Set("source"),
      sinkMethods = Set("sink")
    )
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 1)
  }

  ignore("in the class CallSiteMatch4 we should detect 2 conflict of a unclosed and unopened callsite with a common method in between test case") {
    val svfa = new MethodBasedSVFATest(
      className = "samples.callSiteMatch.CallSiteMatch4",
      sourceMethods = Set("source"),
      sinkMethods = Set("sink")
    )
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 2)
  }

  test("in the class CallSiteMatch5 we should detect 2 conflict of a balanced callsite test case") {
    val svfa = new MethodBasedSVFATest(
      className = "samples.callSiteMatch.CallSiteMatch5",
      sourceMethods = Set("source"),
      sinkMethods = Set("sink")
    )
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 2)
  }

  // ============================================================================
  // FIELD SAMPLES TESTS - Integrated from FieldSamplesTestSuite
  // ============================================================================

  test("in the class FieldSample01 we should detect 1 conflict in a direct sink of a tainted field") {
    val svfa = new MethodBasedSVFATest(
      className = "samples.fields.FieldSample01",
      sourceMethods = Set("source"),
      sinkMethods = Set("sink")
    )
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 1)
  }

  ignore("in the class FieldSample02 we should not detect any conflict because the tainted field was override") {
    val svfa = new MethodBasedSVFATest(
      className = "samples.fields.FieldSample02",
      sourceMethods = Set("source"),
      sinkMethods = Set("sink")
    )
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 0)
  }

  test("in the class FieldSample03 we should detect 1 conflict in a direct sink of a tainted field of a contained object") {
    val svfa = new MethodBasedSVFATest(
      className = "samples.fields.FieldSample03",
      sourceMethods = Set("source"),
      sinkMethods = Set("sink")
    )
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 1)
  }

  ignore("in the class FieldSample04 we should not detect any conflict because the contained tainted object and the tainted field was override") {
    val svfa = new MethodBasedSVFATest(
      className = "samples.fields.FieldSample04",
      sourceMethods = Set("source"),
      sinkMethods = Set("sink")
    )
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 1)
  }

  ignore("in the class FieldSample05 we should detect 1 conflict in a direct sink of a object with a tainted field") {
    val svfa = new MethodBasedSVFATest(
      className = "samples.fields.FieldSample05",
      sourceMethods = Set("source"),
      sinkMethods = Set("sink")
    )
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 1)
  }

  ignore("in the class FieldSample06 we should not detect any conflict because the contained tainted object and the tainted field was override") {
    val svfa = new MethodBasedSVFATest(
      className = "samples.fields.FieldSample06",
      sourceMethods = Set("source"),
      sinkMethods = Set("sink")
    )
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 1)
  }

  ignore("in the class FieldSample07 we should detect 1 conflict in a direct sink of a object with a tainted field") {
    val svfa = new MethodBasedSVFATest(
      className = "samples.fields.FieldSample07",
      sourceMethods = Set("source"),
      sinkMethods = Set("sink")
    )
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 1)
  }

  ignore("in the class FieldSample08 we should not detect any conflict because the contained tainted object and the tainted field was override") {
    val svfa = new MethodBasedSVFATest(
      className = "samples.fields.FieldSample08",
      sourceMethods = Set("source"),
      sinkMethods = Set("sink")
    )
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 0)
  }

  ignore("in the class FieldSample09 we should not detect any conflict because the contained tainted object and the tainted field was override") {
    val svfa = new MethodBasedSVFATest(
      className = "samples.fields.FieldSample09",
      sourceMethods = Set("source"),
      sinkMethods = Set("sink")
    )
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 1)
  }

  ignore("in the class FieldSample10 we should detect 1 conflict in a direct sink of a tainted field") {
    val svfa = new MethodBasedSVFATest(
      className = "samples.fields.FieldSample10",
      sourceMethods = Set("source"),
      sinkMethods = Set("sink")
    )
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 1)
  }

  ignore("in the class FieldSample11 we should not detect any conflict because the tainted field was override") {
    val svfa = new MethodBasedSVFATest(
      className = "samples.fields.FieldSample11",
      sourceMethods = Set("source"),
      sinkMethods = Set("sink")
    )
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 0)
  }

  ignore("in the class FieldSample12 we should detect 1 conflict in a direct sink of a tainted field of a contained object") {
    val svfa = new MethodBasedSVFATest(
      className = "samples.fields.FieldSample12",
      sourceMethods = Set("source"),
      sinkMethods = Set("sink")
    )
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 1)
  }

  ignore("in the class FieldSample13 we should not detect any conflict because the contained tainted object and the tainted field was override") {
    val svfa = new MethodBasedSVFATest(
      className = "samples.fields.FieldSample13",
      sourceMethods = Set("source"),
      sinkMethods = Set("sink")
    )
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 0)
  }

  ignore("in the class FieldSample14 we should detect 1 conflict in a direct sink of a object with a tainted field") {
    val svfa = new MethodBasedSVFATest(
      className = "samples.fields.FieldSample14",
      sourceMethods = Set("source"),
      sinkMethods = Set("sink")
    )
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 1)
  }

  ignore("in the class FieldSample15 we should not detect any conflict because the contained tainted object and the tainted field was override") {
    val svfa = new MethodBasedSVFATest(
      className = "samples.fields.FieldSample15",
      sourceMethods = Set("source"),
      sinkMethods = Set("sink")
    )
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 1)
  }
}
