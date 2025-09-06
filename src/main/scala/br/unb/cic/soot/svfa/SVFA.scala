package br.unb.cic.soot.svfa

import soot._
import br.unb.cic.soot.svfa.configuration.SootConfiguration

/** Base class for all implementations of SVFA algorithms.
  */
abstract class SVFA extends SootConfiguration {

  var svg = new br.unb.cic.soot.graph.Graph()
  private var startTime = 0.0
  private var endTime = 0.0

  def buildSparseValueFlowGraph() {
    startTime = System.nanoTime()
    configureSoot()
    beforeGraphConstruction()
    val (pack, t) = createSceneTransform()
    PackManager.v().getPack(pack).add(t)
    configurePackages().foreach(p => PackManager.v().getPack(p).apply())
    afterGraphConstruction()
    endTime = System.nanoTime()
  }

  def svgToDotModel(): String = {
    svg.toDotModel()
  }

  def reportConflictsSVG(useUniquePaths: Boolean = false): collection.Set[String] = {
    svg.reportConflicts(useUniquePaths)
  }

  def executionTime(): Double = {
    BigDecimal((endTime - startTime) / 1000000).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
  }
}
