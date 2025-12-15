package br.unb.cic.soot.graph

/**
 * Represents different types of edges in the program dependence graph.
 * Used for control flow and data flow analysis in SVFA.
 */
sealed trait EdgeType

/** Standard control flow edge */
case object SimpleEdge extends EdgeType

/** Control flow edge for true branch of conditional */
case object TrueEdge extends EdgeType

/** Control flow edge for false branch of conditional */
case object FalseEdge extends EdgeType

/** Control flow edge representing loop back-edges */
case object LoopEdge extends EdgeType

/** Data dependence edge for definition-use relationships */
case object DefEdge extends EdgeType

object EdgeType {
  /**
   * Converts a string representation to the corresponding EdgeType.
   * Defaults to SimpleEdge for unrecognized strings.
   */
  def convert(edge: String): EdgeType = edge match {
    case "TrueEdge"   => TrueEdge
    case "FalseEdge"  => FalseEdge
    case "LoopEdge"   => LoopEdge
    case "DefEdge"    => DefEdge
    case _            => SimpleEdge
  }
}

/**
 * Program Dependence Graph label types for edge classification.
 */
sealed trait PDGType extends LabelType

/** Label for loop-related edges */
case object LoopLabel extends PDGType

/** Label for true branch edges */
case object TrueLabel extends PDGType

/** Label for false branch edges */
case object FalseLabel extends PDGType

/** Label for definition edges */
case object DefLabel extends PDGType

case class TrueLabelType(labelT: PDGType) extends EdgeLabel {
  override type T = PDGType
  override var value = labelT
  override val labelType: LabelType = TrueLabel
}

case class FalseLabelType(labelT: PDGType) extends EdgeLabel {
  override type T = PDGType
  override var value = labelT
  override val labelType: LabelType = FalseLabel
}

case class DefLabelType(labelT: PDGType) extends EdgeLabel {
  override type T = PDGType
  override var value = labelT
  override val labelType: LabelType = DefLabel
}




