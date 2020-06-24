package br.unb.cic.soot

import br.unb.cic.soot.graph.{NodeType, SimpleNode, SinkNode, SourceNode}
import org.scalatest.{BeforeAndAfter, FunSuite}
import soot.jimple.{AssignStmt, InvokeExpr, InvokeStmt}

class ArrayTest extends JSVFATest {

  override def getClassName(): String = "samples.ArraySample"
  override def getMainMethod(): String = "main"

  override def analyze(unit: soot.Unit): NodeType = {
    if(unit.isInstanceOf[InvokeStmt]) {
      val invokeStmt = unit.asInstanceOf[InvokeStmt]
      return analyzeInvokeStmt(invokeStmt.getInvokeExpr)
    }
    if(unit.isInstanceOf[soot.jimple.AssignStmt]) {
      val assignStmt = unit.asInstanceOf[AssignStmt]
      if(assignStmt.getRightOp.isInstanceOf[InvokeExpr]) {
        val invokeStmt = assignStmt.getRightOp.asInstanceOf[InvokeExpr]
        return analyzeInvokeStmt(invokeStmt)
      }
    }
    return SimpleNode
  }

  def analyzeInvokeStmt(exp: InvokeExpr) : NodeType =
    exp.getMethod.getName match {
      case "source" => SourceNode
      case "sink"   => SinkNode
      case _        => SimpleNode
    }

}

class ArraySampleTestSuite extends FunSuite with BeforeAndAfter {
  val svfa = new ArrayTest()

  before {
    svfa.buildSparseValueFlowGraph()
  }

  test("we should find exactly three conflicts in this analysis") {
    println(svfa.svgToDotModel())
    assert(svfa.reportConflicts().size == 3)
  }
}
