package br.unb.cic.soot.svfa

import br.unb.cic.soot.graph.{GraphNode, Statement, StatementNode}
import soot._
import br.unb.cic.soot.svfa.configuration.SootConfiguration
import ujson._
import java.io.{FileWriter, BufferedWriter}
import scala.util.parsing.json.JSONObject

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

  def reportConflictsSVG(
      useUniquePaths: Boolean = false
  ): collection.Set[String] = {
    svg.reportConflicts(useUniquePaths)
  }

  def executionTime(): Double = {
    BigDecimal((endTime - startTime) / 1000000)
      .setScale(2, BigDecimal.RoundingMode.HALF_UP)
      .toDouble
  }

  def findConflictingPaths() = {
    svg.findConflictingPaths().foreach(conflict => {

      val source = conflict.head
      val sourceJson = generateJsonFormat(source)

      // Convert the parent object to a JSON string
      val jsonString = write(sourceJson, indent = 4)

      createFile(jsonString, "source.json")
    })
  }

  private def generateJsonFormat(node: GraphNode) = {

    val stmt = node.value.asInstanceOf[Statement]
    val method = stmt.sootMethod

    Obj(
      "statement" -> Str(""),
      "methodName" -> Str(method.getDeclaration),
      "className" -> Str(stmt.className),
      "lineNo" -> Num(stmt.sootUnit.getJavaSourceStartLineNumber),
      "targetName" -> Str(""),
      "targetNo" -> Num(0),
      "IRs" -> Obj(
        "type" -> Str("Jimple"),
        "IRstatement" -> Str(stmt.stmt)
      )
    )
  }

  private def createFile(json: String, fileName: String) = {
    val file = new BufferedWriter(new FileWriter(fileName))
    try {
      file.write(json)
    } finally {
      file.close()
    }
  }
}
