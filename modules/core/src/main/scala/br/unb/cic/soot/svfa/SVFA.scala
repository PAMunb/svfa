package br.unb.cic.soot.svfa

import br.unb.cic.soot.graph.{GraphNode, SimpleNode, SinkNode, SourceNode, Statement}
import soot._
import br.unb.cic.soot.svfa.configuration.SootConfiguration
import ujson._

import java.io.{BufferedWriter, FileWriter}
import java.nio.file.Paths
import java.time.{LocalDate, LocalTime}

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

  def findConflictingPaths(fileName: String, path: String = "") = {
    var conflicts = List[Obj]()
    svg.findConflictingPaths().foreach(conflict => {

      val source = conflict.head
      val sourceJson = generateJsonFormat(source)

      val sink = conflict.last
      val sinkJson = generateJsonFormat(sink)

      val intermediateFlows = conflict.drop(1).dropRight(1)
      val intermediateFlowsJson = intermediateFlows.zipWithIndex.map { case (node, idx) => generateJsonFormat(node, idx + 1) }

      val conflictDetails = Obj(
          "source" -> sourceJson,
          "sink" -> sinkJson,
          "intermediateFlows" -> Arr(intermediateFlowsJson)
        )

      conflicts = conflicts :+ conflictDetails
    })

    val findings = Obj(
      "fileName" -> Str(s"${fileName}.apk"),
      "day" -> Str(LocalDate.now().toString + "T" + LocalTime.now().toString),
      "findings" -> conflicts
    )

    createFile(write(findings, indent = 4), s"${fileName}_findings.json", path)
  }

  private def generateJsonFormat(node: GraphNode, id: Int = -1) = {

    val stmt = node.value.asInstanceOf[Statement]
    val method = stmt.sootMethod

    node.nodeType match { 
      case SourceNode | SinkNode =>
        Obj(
          "statement" -> Str(""),
          "methodName" -> Str(method.getDeclaration),
          "className" -> Str(stmt.className),
          "lineNo" -> Num(stmt.sootUnit.getJavaSourceStartLineNumber),
          "targetName" -> Str(""),
          "targetNo" -> Num(0),
          "IRs" -> Arr(
            Obj(
              "type" -> Str("Jimple"),
              "IRstatement" -> Str(stmt.stmt)
            )
          )
        )
      case SimpleNode =>
        Obj(
          "statement" -> Str(""),
          "methodName" -> Str(method.getDeclaration),
          "className" -> Str(stmt.className),
          "lineNo" -> Num(stmt.sootUnit.getJavaSourceStartLineNumber),
          "IRstatement" -> Str(stmt.stmt),
          "ID" -> Num(id)
        )
      case _ =>
        throw new IllegalArgumentException("Invalid node type: " + node.nodeType)
    }
  }

  private def createFile(json: String, fileName: String, path: String = "") = {

    val filePath = path match {
      case "" => fileName
      case _  => s"${path}/${fileName}"
    }
    val file = new BufferedWriter(new FileWriter(filePath))
    try {
      file.write(json)
    } finally {
      file.close()
    }
  }
}
