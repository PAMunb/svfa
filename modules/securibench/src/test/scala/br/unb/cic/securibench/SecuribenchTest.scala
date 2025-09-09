package br.unb.cic.securibench

import br.unb.cic.soot.JSVFATest
import br.unb.cic.soot.graph._
import soot.jimple.{AssignStmt, InvokeExpr, InvokeStmt}

class SecuribenchTest(var className: String = "", var mainMethod: String = "")
    extends JSVFATest
    with SecuribenchSpec {
  override def getClassName(): String = className

  override def getMainMethod(): String = mainMethod

  override def applicationClassPath(): List[String] =
    List("modules/securibench/target/scala-2.12/test-classes")

  override def analyze(unit: soot.Unit): NodeType = {
    if (unit.isInstanceOf[InvokeStmt]) {
      val invokeStmt = unit.asInstanceOf[InvokeStmt]
      return analyzeInvokeExpr(invokeStmt.getInvokeExpr)
    }
    if (unit.isInstanceOf[soot.jimple.AssignStmt]) {
      val assignStmt = unit.asInstanceOf[AssignStmt]
      if (assignStmt.getRightOp.isInstanceOf[InvokeExpr]) {
        val invokeExpr = assignStmt.getRightOp.asInstanceOf[InvokeExpr]
        return analyzeInvokeExpr(invokeExpr)
      }
    }
    SimpleNode
  }

  def analyzeInvokeExpr(exp: InvokeExpr): NodeType = {
    if (sourceList.contains(exp.getMethod.getSignature)) {
      return SourceNode
    } else if (sinkList.contains(exp.getMethod.getSignature)) {
      return SinkNode
    }
    SimpleNode
  }

  override def getIncludeList(): List[String] = List(
    "java.lang.*",
    "javax.servlet.*",
    "java.util.*",
    "java.io.*"
  )
}
