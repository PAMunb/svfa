package br.unb.cic.securibench.deprecated

import br.unb.cic.securibench.SecuribenchTest
import org.scalatest.FunSuite

class SecuribenchTestSuite extends FunSuite {

  /**
   * ALIASING TESTs
   */

  test("in the class Aliasing1 we should detect 1 conflict of a simple aliasing test case") {
    val testName = "Aliasing1"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.aliasing.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("in the class Aliasing2 we should not detect any conflict in this false positive test case") {
    val testName = "Aliasing2"
    val expectedConflicts = 0

    val svfa = new SecuribenchTest(s"securibench.micro.aliasing.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
 }

  test("in the class Aliasing3 we should not detect any conflict, but in Flowdroid this test case was not conclusive") {
    val testName = "Aliasing3"
    val expectedConflicts = 0

    val svfa = new SecuribenchTest(s"securibench.micro.aliasing.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("in the class Aliasing4 we should detect 2 conflict") {
    val testName = "Aliasing4"
    val expectedConflicts = 2

    val svfa = new SecuribenchTest(s"securibench.micro.aliasing.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("in the class Aliasing5 we should detect 1 conflict") {
    val testName = "Aliasing5"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.aliasing.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("in the class Aliasing6 we should detect 7 conflicts") {
    val testName = "Aliasing6"
    val expectedConflicts = 7

    val svfa = new SecuribenchTest(s"securibench.micro.aliasing.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  /**
   * ARRAY TESTs
   */

  test("description: Array1") {
    val testName = "Arrays1"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.arrays.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("description: Array2") {
    val testName = "Arrays2"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.arrays.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("description: Array3") {
    val testName = "Arrays3"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.arrays.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("description: Array4") {
    val testName = "Arrays4"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.arrays.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("description: Array5") {
    val testName = "Arrays5"
    val expectedConflicts = 0

    val svfa = new SecuribenchTest(s"securibench.micro.arrays.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("description: Array6") {
    val testName = "Arrays6"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.arrays.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("description: Array7") {
    val testName = "Arrays7"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.arrays.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("description: Array8") {
    val testName = "Arrays8"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.arrays.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("description: Array9") {
    val testName = "Arrays9"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.arrays.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("description: Array10") {
    val testName = "Arrays10"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.arrays.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  /**
   * BASIC TESTs
   */

  test("in the class Basic1 we should detect 1 conflict of a simple XSS test case") {
    val testName = "Basic1"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("in the class Basic2 we should detect 1 conflict of a XSS combined with a simple conditional test case") {
    val testName = "Basic2"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("in the class Basic3 we should detect 1 conflict of a simple derived string test, very similar to Basic0") {
    val testName = "Basic3"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("in the class Basic4 we should detect 1 conflict of a sensitive path test case") {
    val testName = "Basic4"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("in the class Basic5 we should detect 3 conflicts of a moderately complex derived string test") {
    val testName = "Basic5"
    val expectedConflicts = 3

    val svfa = new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  //FLAKY
  test("in the class Basic6 we should detect 1 conflict of a complex derived string test") {
    val testName = "Basic6"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("in the class Basic7 we should detect 1 conflict of a complex derived string with buffers test") {
    val testName = "Basic7"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("in the class Basic8 we should detect 1 conflict of a complex conditional test case") {
    val testName = "Basic8"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("in the class Basic9 we should detect 1 conflict of a chain of assignments test case") {
    val testName = "Basic9"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("in the class Basic10 we should detect 1 conflict of a chain of assignments and buffers test case") {
    val testName = "Basic10"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("in the class Basic11 we should detect 2 conflicts of a simple derived string test with a false positive") {
    val testName = "Basic11"
    val expectedConflicts = 2

    val svfa = new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("in the class Basic12 we should detect 2 conflicts of a simple conditional test case where both sides have sinks") {
    val testName = "Basic12"
    val expectedConflicts = 2

    val svfa = new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("in the class Basic13 we should detect 1 conflict of a simple test case, the source method was modified to getInitParameterInstead") {
    val testName = "Basic13"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("in the class Basic14 we should detect 1 conflict of a servlet context and casts test case") {
    val testName = "Basic14"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("in the class Basic15 we should detect 1 conflict of a casts more exhaustively test case") {
    val testName = "Basic15"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("in the class Basic16 we should detect 1 conflict of a store statement in heap-allocated data structures test case") {
    val testName = "Basic16"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    // println(svfa.svgToDotModel())
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("in the class Basic17 we should detect 1 conflict of a store statement in heap-allocated data structures and a false positive test case") {
    val testName = "Basic17"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    // println(svfa.svgToDotModel())
    assert(svfa.reportConflictsSVG().size == expectedConflicts) // the search should be context sensitive
  }

  test("in the class Basic18 we should detect 1 conflict of a simple loop unrolling test case") {
    val testName = "Basic18"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("in the class Basic19 we should detect 1 conflict of a simple SQL injection with prepared statements test case") {
    val testName = "Basic19"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("in the class Basic20 we should detect 1 conflict of a simple SQL injection test case") {
    val testName = "Basic20"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("in the class Basic21 we should detect 4 conflicts in a SQL injection with less commonly used methods test case") {
    val testName = "Basic21"
    val expectedConflicts = 4

    val svfa = new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("in the class Basic22 we should detect 1 conflict in a basic path traversal test case") {
    val testName = "Basic22"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("in the class Basic23 we should detect 3 conflicts in a path traversal test case") {
    val testName = "Basic23"
    val expectedConflicts = 3

    val svfa = new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("in the class Basic24 we should detect 1 conflict in a unsafe redirect test case") {
    val testName = "Basic24"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("in the class Basic25 we should detect 1 conflict in a test getParameterValues test case") {
    val testName = "Basic25"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("in the class Basic26 we should detect 1 conflict in a getParameterMap test case") {
    val testName = "Basic26"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("in the class Basic27 we should detect 1 conflict in a getParameterMap test case") {
    val testName = "Basic27"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("in the class Basic28 we should detect 2 conflicts in a complicated control flow test case") {
    val testName = "Basic28"
    val expectedConflicts = 2

    val svfa = new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("in the class Basic29 we should detect 2 conflicts in a recursive data structures test case") {
    val testName = "Basic29"
    val expectedConflicts = 2

    val svfa = new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("in the class Basic30 we should detect 1 conflict in a field sensitivity test case") {
    val testName = "Basic30"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("in the class Basic31 we should detect 3 conflicts in a values obtained from cookies test case") {
    val testName = "Basic31"
    val expectedConflicts = 3

    val svfa = new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("in the class Basic32 we should detect 1 conflict in a values obtained from headers test case") {
    val testName = "Basic32"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("in the class Basic33 we should detect 1 conflict in a values obtained from headers test case") {
    val testName = "Basic33"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("in the class Basic34 we should detect 2 conflicts in a values obtained from headers test case") {
    val testName = "Basic34"
    val expectedConflicts = 2

    val svfa = new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("in the class Basic35 we should detect 6 conflicts in a values obtained from HttpServletRequest test case") {
    val testName = "Basic35"
    val expectedConflicts = 6

    val svfa = new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("in the class Basic36 we should detect 1 conflict in a values obtained from HttpServletRequest input stream test case") {
    val testName = "Basic36"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("in the class Basic37 we should detect 1 conflict in a StringTokenizer test case") {
    val testName = "Basic37"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("in the class Basic38 we should detect 1 conflict in a StringTokenizer with a false positive test case") {
    val testName = "Basic38"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("in the class Basic39 we should detect 1 conflict in a StringTokenizer test case") {
    val testName = "Basic39"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("in the class Basic40 we should detect 1 conflict in a use getInitParameter instead test case") {
    val testName = "Basic40"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("in the class Basic41 we should detect 1 conflict in a use getInitParameter instead test case") {
    val testName = "Basic41"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("in the class Basic42 we should detect 1 conflict in a use getInitParameterNames test case") {
    val testName = "Basic42"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.basic.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  /**
   * COLLECTION TESTs
   */

  test("description: Collection1") {
    val testName = "Collections1"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.collections.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("description: Collection2") {
    val testName = "Collections2"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.collections.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("description: Collection3") {
    val testName = "Collections3"
    val expectedConflicts = 2

    val svfa = new SecuribenchTest(s"securibench.micro.collections.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("description: Collection4") {
    val testName = "Collections4"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.collections.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("description: Collection5") {
    val testName = "Collections5"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.collections.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("description: Collection6") {
    val testName = "Collections6"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.collections.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("description: Collection7") {
    val testName = "Collections7"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.collections.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("description: Collection8") {
    val testName = "Collections8"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.collections.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("description: Collection9") {
    val testName = "Collections9"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.collections.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("description: Collection10") {
    val testName = "Collections10"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.collections.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("description: Collection11") {
    val testName = "Collections11"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.collections.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("description: Collection12") {
    val testName = "Collections12"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.collections.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("description: Collection13") {
    val testName = "Collections13"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.collections.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("description: Collection14") {
    val testName = "Collections14"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.collections.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  /**
   * DATASTRUCTURE TESTs
   */

  test("description: DataStructure1") {
    val testName = "Datastructures1"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.datastructures.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("description: DataStructure2") {
    val testName = "Datastructures2"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.datastructures.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("description: DataStructure3") {
    val testName = "Datastructures3"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.datastructures.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("description: DataStructure4") {
    val testName = "Datastructures4"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.datastructures.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("description: DataStructure5") {
    val testName = "Datastructures5"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.datastructures.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("description: DataStructure6") {
    val testName = "Datastructures6"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.datastructures.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  /**
   * FACTORY TESTs
   */

  test("description: Factory1") {
    val testName = "Factories1"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.factories.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("description: Factory2") {
    val testName = "Factories2"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.factories.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("description: Factory3") {
    val testName = "Factories3"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.factories.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  /**
   * INTER TESTs
   */
  
  test("description: Inter1") {
    val testName = "Inter1"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.inter.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("description: Inter2") {
    val testName = "Inter2"
    val expectedConflicts = 2

    val svfa = new SecuribenchTest(s"securibench.micro.inter.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("description: Inter3") {
    val testName = "Inter3"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.inter.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("description: Inter4") {
    val testName = "Inter4"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.inter.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
//    println(svfa.svgToDotModel())
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("description: Inter5") {
    val testName = "Inter5"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.inter.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("description: Inter6") {
    val testName = "Inter6"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.inter.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("description: Inter7") {
    val testName = "Inter7"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.inter.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("description: Inter8") {
    val testName = "Inter8"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.inter.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("description: Inter9") {
    val testName = "Inter9"
    val expectedConflicts = 2

    val svfa = new SecuribenchTest(s"securibench.micro.inter.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("description: Inter10") {
    val testName = "Inter10"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.inter.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

//  FLAKY
  test("description: Inter11") {
    val testName = "Inter11"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.inter.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("description: Inter12") {
    val testName = "Inter12"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.inter.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }


  test("description: Inter13") {
    val testName = "Inter13"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.inter.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }


  test("description: Inter14") {
    val testName = "Inter14"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.inter.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  /**
   * SESSION TESTs
   */

  test("description: Session1") {
    val testName = "Session1"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.session.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("description: Session2") {
    val testName = "Session2"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.session.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("description: Session3") {
    val testName = "Session3"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.session.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  /**
   * STRONG UPDATE TESTs
   */

  test("description: StrongUpdate1") {
    val testName = "StrongUpdates1"
    val expectedConflicts = 0

    val svfa = new SecuribenchTest(s"securibench.micro.strong_updates.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("description: StrongUpdate2") {
    val testName = "StrongUpdates2"
    val expectedConflicts = 0

    val svfa = new SecuribenchTest(s"securibench.micro.strong_updates.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("description: StrongUpdate3") {
    val testName = "StrongUpdates3"
    val expectedConflicts = 0

    val svfa = new SecuribenchTest(s"securibench.micro.strong_updates.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("description: StrongUpdate4") {
    val testName = "StrongUpdates4"
    val expectedConflicts = 1

    val svfa = new SecuribenchTest(s"securibench.micro.strong_updates.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }

  test("description: StrongUpdate5") {
    val testName = "StrongUpdates5"
    val expectedConflicts = 0

    val svfa = new SecuribenchTest(s"securibench.micro.strong_updates.$testName", "doGet")
    svfa.buildSparseValueFlowGraph()
    assert(svfa.reportConflictsSVG().size == expectedConflicts)
  }
}
