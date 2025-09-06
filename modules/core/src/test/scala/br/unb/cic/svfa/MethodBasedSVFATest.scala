package br.unb.cic.soot

import br.unb.cic.soot.graph.{NodeType, SimpleNode, SinkNode, SourceNode}
import soot.jimple.{AssignStmt, InvokeExpr, InvokeStmt}

/**
 * Generic SVFA test class that identifies sources and sinks based on method names.
 * 
 * @param className The fully qualified name of the class to analyze
 * @param mainMethod The name of the main method (usually "main")
 * @param sourceMethods Set of method names that should be considered as sources
 * @param sinkMethods Set of method names that should be considered as sinks
 */
class MethodBasedSVFATest(
  className: String,
  mainMethod: String = "main",
  sourceMethods: Set[String],
  sinkMethods: Set[String]
) extends JSVFATest {

  override def getClassName(): String = className
  override def getMainMethod(): String = mainMethod

  override def analyze(unit: soot.Unit): NodeType = {
    if (unit.isInstanceOf[InvokeStmt]) {
      val invokeStmt = unit.asInstanceOf[InvokeStmt]
      return analyzeInvokeStmt(invokeStmt.getInvokeExpr)
    }
    if (unit.isInstanceOf[AssignStmt]) {
      val assignStmt = unit.asInstanceOf[AssignStmt]
      if (assignStmt.getRightOp.isInstanceOf[InvokeExpr]) {
        val invokeStmt = assignStmt.getRightOp.asInstanceOf[InvokeExpr]
        return analyzeInvokeStmt(invokeStmt)
      }
    }
    SimpleNode
  }

  private def analyzeInvokeStmt(exp: InvokeExpr): NodeType = {
    val methodName = exp.getMethod.getName
    if (sourceMethods.contains(methodName)) {
      SourceNode
    } else if (sinkMethods.contains(methodName)) {
      SinkNode
    } else {
      SimpleNode
    }
  }
}

