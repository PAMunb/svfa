package br.unb.cic.soot

import br.unb.cic.soot.graph.{NodeType, SimpleNode, SinkNode, SourceNode}

/**
 * Generic SVFA test class that identifies sources and sinks based on source code line numbers.
 * 
 * @param className The fully qualified name of the class to analyze
 * @param mainMethod The name of the main method (usually "main")
 * @param sourceLines Set of line numbers that should be considered as sources
 * @param sinkLines Set of line numbers that should be considered as sinks
 */
class LineBasedSVFATest(
  className: String,
  mainMethod: String = "main",
  sourceLines: Set[Int],
  sinkLines: Set[Int]
) extends JSVFATest {

  override def getClassName(): String = className
  override def getMainMethod(): String = mainMethod

  override def analyze(unit: soot.Unit): NodeType = {
    val lineNumber = unit.getJavaSourceStartLineNumber
    if (sourceLines.contains(lineNumber)) {
      SourceNode
    } else if (sinkLines.contains(lineNumber)) {
      SinkNode
    } else {
      SimpleNode
    }
  }
}

