package br.unb.cic.securibench

import br.unb.cic.soot.JSVFATest
import br.unb.cic.soot.graph._
import br.unb.cic.soot.svfa.jimple.SVFAConfig
import soot.jimple.{AssignStmt, InvokeExpr, InvokeStmt}

/**
 * Securibench test class with configurable SVFA settings.
 * 
 * @param className The fully qualified name of the class to analyze
 * @param mainMethod The name of the main method (usually "doGet")
 * @param config Optional SVFA configuration (defaults to command-line/environment configuration)
 */
class SecuribenchTest(
    var className: String = "", 
    var mainMethod: String = "",
    config: SVFAConfig = SecuribenchConfig.getConfiguration()
) extends JSVFATest
    with SecuribenchSpec {

  // Override the configuration with command-line/environment settings
  override def svfaConfig: SVFAConfig = config
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
