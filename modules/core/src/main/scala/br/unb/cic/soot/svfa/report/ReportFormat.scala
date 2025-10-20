package br.unb.cic.soot.svfa.report

import br.unb.cic.soot.graph.{GraphNode, SimpleNode, SinkNode, SourceNode, Statement}
import ujson.{Arr, Num, Obj, Str, write}

import java.io.{BufferedWriter, FileWriter}
import java.time.{LocalDate, LocalTime}

trait ReportFormat {

  def createJsonReport(conflictPaths: scala.collection.Set[List[GraphNode]], fileName: String, path: String = ""): Unit = {
    findConflictingPaths(conflictPaths, fileName, path)
  }

  private def findConflictingPaths(conflictPaths: scala.collection.Set[List[GraphNode]], fileName: String, path: String = ""): Unit = {
    var conflicts = List[Obj]()
    conflictPaths.foreach(conflict => {

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