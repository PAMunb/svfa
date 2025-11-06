package br.unb.cic.securibench.deprecated

import br.unb.cic.securibench.SecuribenchTest
import org.scalatest.FunSuite

class SecuribenchTestSuite extends FunSuite {

  /**
   ALIASING TESTs
   */

  test(
    "in the class Aliasing1 we should detect 1 conflict of a simple aliasing test case"
  ) {
    val testName = "Aliasing1"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.aliasing.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()

    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  ignore(
    "in the class Aliasing2 we should detect 1 conflict in this false positive test case"
  ) {
    val testName = "Aliasing2"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.aliasing.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  ignore(
    "in the class Aliasing3 we should detect 1 conflict, but in Flowdroid this test case was not conclusive"
  ) {
    val testName = "Aliasing3"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.aliasing.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  ignore("in the class Aliasing4 we should detect 1 conflict") {
    val testName = "Aliasing4"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.aliasing.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  ignore("in the class Aliasing5 we should detect 1 conflict") {
    val testName = "Aliasing5"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.aliasing.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("in the class Aliasing6 we should detect 7 conflicts") {
    val testName = "Aliasing6"
    val expectedConflicts = 7

    val svfa =
      new SecuribenchTest(s"securibench.micro.aliasing.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  ignore("in the class Aliasing7 we should detect 0 conflicts") {
    val testName = "Aliasing7"
    val expectedConflicts = 0

    val svfa = new SecuribenchTest(s"securibench.micro.aliasing.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()

    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  ignore("in the class Aliasing8 we should detect 7 conflicts") {
    val testName = "Aliasing8"
    val expectedConflicts = 7

    val svfa = new SecuribenchTest(s"securibench.micro.aliasing.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()

    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  ignore("in the class Aliasing9 we should detect 7 conflicts") {
    val testName = "Aliasing9"
    val expectedConflicts = 7

    val svfa = new SecuribenchTest(s"securibench.micro.aliasing.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()

    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  /** ARRAY TESTs
    */

  test(
    "in the class Arrays1 we should detect 1 conflict of a simple array test case"
  ) {
    val testName = "Arrays1"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.arrays.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  ignore(
    "in the class Arrays2 we should detect 1 conflict of a simple array test case"
  ) {
    val testName = "Arrays2"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.arrays.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test(
    "in the class Arrays3 we should detect 1 conflict of a simple array test case"
  ) {
    val testName = "Arrays3"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.arrays.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test(
    "in the class Arrays4 we should detect 1 conflict of a simple array test case"
  ) {
    val testName = "Arrays4"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.arrays.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  ignore(
    "in the class Arrays5 we should detect 0 conflict of a simple array test case"
  ) {
    val testName = "Arrays5"
    val expectedConflicts = 0

    val svfa =
      new SecuribenchTest(s"securibench.micro.arrays.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test(
    "in the class Arrays6 we should detect 1 conflict of a simple array test case"
  ) {
    val testName = "Arrays6"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.arrays.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test(
    "in the class Arrays7 we should detect 1 conflict of a simple array test case"
  ) {
    val testName = "Arrays7"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.arrays.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  ignore(
    "in the class Arrays8 we should detect 1 conflict of a simple array test case"
  ) {
    val testName = "Arrays8"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.arrays.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  ignore(
    "in the class Arrays9 we should detect 1 conflict of a simple array test case"
  ) {
    val testName = "Arrays9"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.arrays.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  ignore(
    "in the class Arrays10 we should detect 0 conflict of a simple array test case"
  ) {
    val testName = "Arrays10"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.arrays.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  /** BASIC TESTs
    */
  test(
    "in the class Basic0 we should detect 1 conflict of a simple XSS test case"
  ) {
    val testName = "Basic0"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test(
    "in the class Basic1 we should detect 1 conflict of a simple XSS test case"
  ) {
    val testName = "Basic1"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test(
    "in the class Basic2 we should detect 1 conflict of a XSS combined with a simple conditional test case"
  ) {
    val testName = "Basic2"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test(
    "in the class Basic3 we should detect 1 conflict of a simple derived string test, very similar to Basic0"
  ) {
    val testName = "Basic3"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test(
    "in the class Basic4 we should detect 1 conflict of a sensitive path test case"
  ) {
    val testName = "Basic4"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test(
    "in the class Basic5 we should detect 3 conflicts of a moderately complex derived string test"
  ) {
    val testName = "Basic5"
    val expectedConflicts = 3

    val svfa =
      new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test(
    "in the class Basic6 we should detect 1 conflict of a complex derived string test"
  ) {
    val testName = "Basic6"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test(
    "in the class Basic7 we should detect 1 conflict of a complex derived string with buffers test"
  ) {
    val testName = "Basic7"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test(
    "in the class Basic8 we should detect 1 conflict of a complex conditional test case"
  ) {
    val testName = "Basic8"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test(
    "in the class Basic9 we should detect 1 conflict of a chain of assignments test case"
  ) {
    val testName = "Basic9"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test(
    "in the class Basic10 we should detect 1 conflict of a chain of assignments and buffers test case"
  ) {
    val testName = "Basic10"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test(
    "in the class Basic11 we should detect 2 conflicts of a simple derived string test with a false positive"
  ) {
    val testName = "Basic11"
    val expectedConflicts = 2

    val svfa =
      new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test(
    "in the class Basic12 we should detect 2 conflicts of a simple conditional test case where both sides have sinks"
  ) {
    val testName = "Basic12"
    val expectedConflicts = 2

    val svfa =
      new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test(
    "in the class Basic13 we should detect 1 conflict of a simple test case, the source method was modified to getInitParameterInstead"
  ) {
    val testName = "Basic13"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test(
    "in the class Basic14 we should detect 1 conflict of a servlet context and casts test case"
  ) {
    val testName = "Basic14"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test(
    "in the class Basic15 we should detect 1 conflict of a casts more exhaustively test case"
  ) {
    val testName = "Basic15"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test(
    "in the class Basic16 we should detect 1 conflict of a store statement in heap-allocated data structures test case"
  ) {
    val testName = "Basic16"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    // println(svfa.svgToDotModel())
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test(
    "in the class Basic17 we should detect 1 conflict of a store statement in heap-allocated data structures and a false positive test case"
  ) {
    val testName = "Basic17"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    // println(svfa.svgToDotModel())
    assert(
      svfa.reportConflictsSVG().size == expectedConflicts
    ) // the search should be context sensitive
  }

  test(
    "in the class Basic18 we should detect 1 conflict of a simple loop unrolling test case"
  ) {
    val testName = "Basic18"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test(
    "in the class Basic19 we should detect 1 conflict of a simple SQL injection with prepared statements test case"
  ) {
    val testName = "Basic19"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test(
    "in the class Basic20 we should detect 1 conflict of a simple SQL injection test case"
  ) {
    val testName = "Basic20"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test(
    "in the class Basic21 we should detect 4 conflicts in a SQL injection with less commonly used methods test case"
  ) {
    val testName = "Basic21"
    val expectedConflicts = 4

    val svfa =
      new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test(
    "in the class Basic22 we should detect 1 conflict in a basic path traversal test case"
  ) {
    val testName = "Basic22"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test(
    "in the class Basic23 we should detect 3 conflicts in a path traversal test case"
  ) {
    val testName = "Basic23"
    val expectedConflicts = 3

    val svfa =
      new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test(
    "in the class Basic24 we should detect 1 conflict in a unsafe redirect test case"
  ) {
    val testName = "Basic24"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test(
    "in the class Basic25 we should detect 1 conflict in a test getParameterValues test case"
  ) {
    val testName = "Basic25"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test(
    "in the class Basic26 we should detect 1 conflict in a getParameterMap test case"
  ) {
    val testName = "Basic26"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test(
    "in the class Basic27 we should detect 1 conflict in a getParameterMap test case"
  ) {
    val testName = "Basic27"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  ignore(
    "in the class Basic28 we should detect 2 conflicts in a complicated control flow test case"
  ) {
    val testName = "Basic28"
    val expectedConflicts = 2

    val svfa =
      new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test(
    "in the class Basic29 we should detect 2 conflicts in a recursive data structures test case"
  ) {
    val testName = "Basic29"
    val expectedConflicts = 2

    val svfa =
      new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test(
    "in the class Basic30 we should detect 1 conflict in a field sensitivity test case"
  ) {
    val testName = "Basic30"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  ignore(
    "in the class Basic31 we should detect 2 conflicts in a values obtained from cookies test case"
  ) {
    val testName = "Basic31"
    val expectedConflicts = 2

    val svfa =
      new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test(
    "in the class Basic32 we should detect 1 conflict in a values obtained from headers test case"
  ) {
    val testName = "Basic32"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test(
    "in the class Basic33 we should detect 1 conflict in a values obtained from headers test case"
  ) {
    val testName = "Basic33"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test(
    "in the class Basic34 we should detect 2 conflicts in a values obtained from headers test case"
  ) {
    val testName = "Basic34"
    val expectedConflicts = 2

    val svfa =
      new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test(
    "in the class Basic35 we should detect 6 conflicts in a values obtained from HttpServletRequest test case"
  ) {
    val testName = "Basic35"
    val expectedConflicts = 6

    val svfa =
      new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  ignore(
    "in the class Basic36 we should detect 1 conflict in a values obtained from HttpServletRequest input stream test case"
  ) {
    val testName = "Basic36"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test(
    "in the class Basic37 we should detect 1 conflict in a StringTokenizer test case"
  ) {
    val testName = "Basic37"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  ignore(
    "in the class Basic38 we should detect 1 conflict in a StringTokenizer with a false positive test case"
  ) {
    val testName = "Basic38"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test(
    "in the class Basic39 we should detect 1 conflict in a StringTokenizer test case"
  ) {
    val testName = "Basic39"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test(
    "in the class Basic41 we should detect 1 conflict in a use getInitParameter instead test case"
  ) {
    val testName = "Basic41"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  ignore(
    "in the class Basic42 we should detect 1 conflict in a use getInitParameterNames test case"
  ) {
    val testName = "Basic42"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  /** COLLECTION TESTs
    */

  test(
    "in the class Collections1 we should detect 1 conflict of a simple collection test case"
  ) {
    val testName = "Collections1"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.collections.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test(
    "in the class Collections2 we should detect 1 conflict of a simple collection test case"
  ) {
    val testName = "Collections2"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.collections.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  ignore(
    "in the class Collections3 we should detect 2 conflicts of a simple collection test case"
  ) {
    val testName = "Collections3"
    val expectedConflicts = 2

    val svfa =
      new SecuribenchTest(s"securibench.micro.collections.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test(
    "in the class Collections4 we should detect 1 conflict of a simple collection test case"
  ) {
    val testName = "Collections4"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.collections.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  ignore(
    "in the class Collections5 we should detect 1 conflict of a simple collection test case"
  ) {
    val testName = "Collections5"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.collections.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  ignore(
    "in the class Collections6 we should detect 1 conflict of a simple collection test case"
  ) {
    val testName = "Collections6"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.collections.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  ignore(
    "in the class Collections7 we should detect 1 conflict of a simple collection test case"
  ) {
    val testName = "Collections7"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.collections.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  ignore(
    "in the class Collections8 we should detect 1 conflict of a simple collection test case"
  ) {
    val testName = "Collections8"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.collections.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  ignore(
    "in the class Collections9 we should detect 1 conflict of a simple collection test case"
  ) {
    val testName = "Collections9"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.collections.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  ignore(
    "in the class Collections10 we should detect 1 conflict of a simple collection test case"
  ) {
    val testName = "Collections10"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.collections.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test(
    "in the class Collections11 we should detect 1 conflict of a simple collection test case"
  ) {
    val testName = "Collections11"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.collections.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  ignore(
    "in the class Collections12 we should detect 1 conflict of a simple collection test case"
  ) {
    val testName = "Collections12"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.collections.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  ignore(
    "in the class Collections13 we should detect 1 conflict of a simple collection test case"
  ) {
    val testName = "Collections13"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.collections.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test(
    "in the class Collections14 we should detect 1 conflict of a simple collection test case"
  ) {
    val testName = "Collections14"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.collections.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  /** DATASTRUCTURE TESTs
    */

  test(
    "in the class Datastructures1 we should detect 1 conflict of a simple data structure test case"
  ) {
    val testName = "Datastructures1"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(
      s"securibench.micro.datastructures.$testName",
      "doGet"
    )
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test(
    "in the class Datastructures2 we should detect 1 conflict of a simple data structure test case"
  ) {
    val testName = "Datastructures2"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(
      s"securibench.micro.datastructures.$testName",
      "doGet"
    )
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test(
    "in the class Datastructures3 we should detect 1 conflict of a simple data structure test case"
  ) {
    val testName = "Datastructures3"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(
      s"securibench.micro.datastructures.$testName",
      "doGet"
    )
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  ignore(
    "in the class Datastructures4 we should detect 0 conflict of a simple data structure test case"
  ) {
    val testName = "Datastructures4"
    val expectedConflicts = 0

    val svfa = new SecuribenchTest(
      s"securibench.micro.datastructures.$testName",
      "doGet"
    )
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  ignore(
    "in the class Datastructures5 we should detect 1 conflict of a simple data structure test case"
  ) {
    val testName = "Datastructures5"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(
      s"securibench.micro.datastructures.$testName",
      "doGet"
    )
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test(
    "in the class Datastructures6 we should detect 1 conflict of a simple data structure test case"
  ) {
    val testName = "Datastructures6"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(
      s"securibench.micro.datastructures.$testName",
      "doGet"
    )
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  /** FACTORY TESTs
    */

  test(
    "in the class Factories1 we should detect 1 conflict of a simple factory test case"
  ) {
    val testName = "Factories1"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.factories.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test(
    "in the class Factories2 we should detect 1 conflict of a simple factory test case"
  ) {
    val testName = "Factories2"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.factories.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  ignore(
    "in the class Factories3 we should detect 1 conflict of a simple factory test case"
  ) {
    val testName = "Factories3"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.factories.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  /** INTER TESTs
    */

  test(
    "in the class Inter1 we should detect 1 conflict of a simple inter test case"
  ) {
    val testName = "Inter1"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.inter.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test(
    "in the class Inter2 we should detect 2 conflicts of a simple inter test case"
  ) {
    val testName = "Inter2"
    val expectedConflicts = 2

    val svfa =
      new SecuribenchTest(s"securibench.micro.inter.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test(
    "in the class Inter3 we should detect 1 conflict of a simple inter test case"
  ) {
    val testName = "Inter3"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.inter.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  ignore(
    "in the class Inter4 we should detect 2 conflicts of a simple inter test case"
  ) {
    val testName = "Inter4"
    val expectedConflicts = 2

    val svfa =
      new SecuribenchTest(s"securibench.micro.inter.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
//    println(svfa.svgToDotModel())
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  ignore(
    "in the class Inter5 we should detect 2 conflicts of a simple inter test case"
  ) {
    val testName = "Inter5"
    val expectedConflicts = 2

    val svfa =
      new SecuribenchTest(s"securibench.micro.inter.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  ignore(
    "in the class Inter6 we should detect 1 conflict of a simple inter test case"
  ) {
    val testName = "Inter6"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.inter.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test(
    "in the class Inter7 we should detect 1 conflict of a simple inter test case"
  ) {
    val testName = "Inter7"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.inter.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test(
    "in the class Inter8 we should detect 1 conflict of a simple inter test case"
  ) {
    val testName = "Inter8"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.inter.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  ignore(
    "in the class Inter9 we should detect 2 conflicts of a simple inter test case"
  ) {
    val testName = "Inter9"
    val expectedConflicts = 2

    val svfa =
      new SecuribenchTest(s"securibench.micro.inter.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test(
    "in the class Inter10 we should detect 1 conflict of a simple inter test case"
  ) {
    val testName = "Inter10"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.inter.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  // FLAKY: It only fails in the Github action pipeline
  ignore(
    "in the class Inter11 we should detect 1 conflict of a simple inter test case"
  ) {
    val testName = "Inter11"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.inter.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  ignore(
    "in the class Inter12 we should detect 1 conflict of a simple inter test case"
  ) {
    val testName = "Inter12"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.inter.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test(
    "in the class Inter13 we should detect 1 conflict of a simple inter test case"
  ) {
    val testName = "Inter13"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.inter.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test(
    "in the class Inter14 we should detect 1 conflict of a simple inter test case"
  ) {
    val testName = "Inter14"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.inter.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  /** SESSION TESTs
    */

  test(
    "in the class Session1 we should detect 1 conflict of a simple session test case"
  ) {
    val testName = "Session1"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.session.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
//    println(svfa.svgToDotModel())
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  /**
   * WHY IS IT FAILING?
   *
   * Here is a section of the jimple from file: "Session1.java"
   *
   * ---------------------------------------------------------------------------------------------------------------------------------
   * s1: name = interfaceinvoke req.<javax.servlet.http.HttpServletRequest: java.lang.String getParameter(java.lang.String)>("name");
   * s2: session = interfaceinvoke req.<javax.servlet.http.HttpServletRequest: javax.servlet.http.HttpSession getSession()>();
   * s3: interfaceinvoke session.<javax.servlet.http.HttpSession: void setAttribute(java.lang.String,java.lang.Object)>("name", name);
   * s4: $stack7 = interfaceinvoke session.<javax.servlet.http.HttpSession: java.lang.Object getAttribute(java.lang.String)>("name");
   * s5: s2 = (java.lang.String) $stack7;
   * s6: writer = interfaceinvoke resp.<javax.servlet.http.HttpServletResponse: java.io.PrintWriter getWriter()>();
   * s7: virtualinvoke writer.<java.io.PrintWriter: void println(java.lang.String)>(s2);
   * ---------------------------------------------------------------------------------------------------------------------------------
   *
   * Currently, statements (s1, s2, s3, s4, s6) of type "interfaceinvoke" are not processed totally by the program
   * (not edges are created at least they are "source/sink" nodes). I found 2 cases to improve
   *
   * #1: If the expression is local as (s3/s4), it must be created an edge FROM its declaration TO the current stmt.
   * #2: If the argument(s) passed in the expression call are locals as (s3), it must be created an edge FROM the argument(s) declaration
   * TO the current stmt.
   *
   * Moreover, I found that these expression falls under rules conditions in (JSVFA.scala@invokeRule:449).
   * We need to validate if this logic is right.
   *
   * PD: "Session2.java" and "Session3.java" have similar issues.
   */

  ignore(
    "in the class Session2 we should detect 1 conflict of a simple session test case"
  ) {
    val testName = "Session2"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.session.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  ignore(
    "in the class Session3 we should detect 1 conflict of a simple session test case"
  ) {
    val testName = "Session3"
    val expectedConflicts = 1

    val svfa =
      new SecuribenchTest(s"securibench.micro.session.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  /** STRONG UPDATE TESTs
    */

  test(
    "in the class StrongUpdates1 we should detect 0 conflict of a simple strong update test case"
  ) {
    val testName = "StrongUpdates1"
    val expectedConflicts = 0

    val svfa = new SecuribenchTest(
      s"securibench.micro.strong_updates.$testName",
      "doGet"
    )
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test(
    "in the class StrongUpdates2 we should detect 0 conflict of a simple strong update test case"
  ) {
    val testName = "StrongUpdates2"
    val expectedConflicts = 0

    val svfa = new SecuribenchTest(
      s"securibench.micro.strong_updates.$testName",
      "doGet"
    )
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  ignore(
    "in the class StrongUpdates3 we should detect 0 conflict of a simple strong update test case"
  ) {
    val testName = "StrongUpdates3"
    val expectedConflicts = 0

    val svfa = new SecuribenchTest(
      s"securibench.micro.strong_updates.$testName",
      "doGet"
    )
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  // FLAKY: It only fails in the Github action pipeline
  ignore(
    "in the class StrongUpdates4 we should detect 1 conflict of a simple strong update test case"
  ) {
    val testName = "StrongUpdates4"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(
      s"securibench.micro.strong_updates.$testName",
      "doGet"
    )
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  ignore(
    "in the class StrongUpdates5 we should detect 0 conflict of a simple strong update test case"
  ) {
    val testName = "StrongUpdates5"
    val expectedConflicts = 0

    val svfa = new SecuribenchTest(
      s"securibench.micro.strong_updates.$testName",
      "doGet"
    )
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  /**
   *
   * EXTRA TESTs
   *
   */

  /** PRED TESTs
   */

  test(
    "in the class Pred1 we should detect 0 conflict(s) of a Pred test case"
  ) {
    val svfa = new SecuribenchTest("securibench.micro.pred.Pred1", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 0)
  }

  test(
    "in the class Pred2 we should detect 1 conflict(s) of a Pred test case"
  ) {
    val svfa = new SecuribenchTest("securibench.micro.pred.Pred2", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 1)
  }

  ignore(
    "in the class Pred3 we should detect 0 conflict(s) of a Pred test case"
  ) {
    val svfa = new SecuribenchTest("securibench.micro.pred.Pred3", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 0)
  }

  test(
    "in the class Pred4 we should detect 1 conflict(s) of a Pred test case"
  ) {
    val svfa = new SecuribenchTest("securibench.micro.pred.Pred4", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 1)
  }

  test(
    "in the class Pred5 we should detect 1 conflict(s) of a Pred test case"
  ) {
    val svfa = new SecuribenchTest("securibench.micro.pred.Pred5", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 1)
  }

  ignore(
    "in the class Pred6 we should detect 0 conflict(s) of a Pred test case"
  ) {
    val svfa = new SecuribenchTest("securibench.micro.pred.Pred6", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 0)
  }

  ignore(
    "in the class Pred7 we should detect 0 conflict(s) of a Pred test case"
  ) {
    val svfa = new SecuribenchTest("securibench.micro.pred.Pred7", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 0)
  }

  test(
    "in the class Pred8 we should detect 1 conflict(s) of a Pred test case"
  ) {
    val svfa = new SecuribenchTest("securibench.micro.pred.Pred8", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 1)
  }

  test(
    "in the class Pred9 we should detect 1 conflict(s) of a Pred test case"
  ) {
    val svfa = new SecuribenchTest("securibench.micro.pred.Pred9", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 1)
  }

  /** REFLECTION TESTs
   */
  ignore(
    "in the class Refl1 we should detect 1 conflict(s) of a Reflection test case"
  ) {
    val svfa =
      new SecuribenchTest("securibench.micro.reflection.Refl1", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 1)
  }

  ignore(
    "in the class Refl2 we should detect 1 conflict(s) of a Reflection test case"
  ) {
    val svfa =
      new SecuribenchTest("securibench.micro.reflection.Refl2", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 1)
  }

  ignore(
    "in the class Refl3 we should detect 1 conflict(s) of a Reflection test case"
  ) {
    val svfa =
      new SecuribenchTest("securibench.micro.reflection.Refl3", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 1)
  }

  ignore(
    "in the class Refl4 we should detect 1 conflict(s) of a Reflection test case"
  ) {
    val svfa =
      new SecuribenchTest("securibench.micro.reflection.Refl4", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 1)
  }

  /** SANITIZERS TESTs
   */
  test(
    "in the class Sanitizers1 we should detect 1 conflict(s) of a Sanitizers test case"
  ) {
    val svfa =
      new SecuribenchTest("securibench.micro.sanitizers.Sanitizers1", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 1)
  }

  ignore(
    "in the class Sanitizers2 we should detect 1 conflict(s) of a Sanitizers test case"
  ) {
    val svfa =
      new SecuribenchTest("securibench.micro.sanitizers.Sanitizers2", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 1)
  }

  test(
    "in the class Sanitizers3 we should detect 0 conflict(s) of a Sanitizers test case"
  ) {
    val svfa =
      new SecuribenchTest("securibench.micro.sanitizers.Sanitizers3", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 0)
  }

  ignore(
    "in the class Sanitizers4 we should detect 2 conflict(s) of a Sanitizers test case"
  ) {
    val svfa =
      new SecuribenchTest("securibench.micro.sanitizers.Sanitizers4", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 2)
  }

  ignore(
    "in the class Sanitizers5 we should detect 1 conflict(s) of a Sanitizers test case"
  ) {
    val svfa =
      new SecuribenchTest("securibench.micro.sanitizers.Sanitizers5", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 1)
  }

  ignore(
    "in the class Sanitizers6 we should detect 1 conflict(s) of a Sanitizers test case"
  ) {
    val svfa =
      new SecuribenchTest("securibench.micro.sanitizers.Sanitizers6", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == 1)
  }
}
