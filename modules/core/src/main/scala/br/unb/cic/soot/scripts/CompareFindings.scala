package br.unb.cic.soot.scripts

import java.io.{File, PrintWriter}
import scala.io.Source
import ujson._

import scala.collection.mutable

/**
  * Usage:
  *   sbt "runMain br.unb.cic.soot.scripts.CompareFindings 
            <actual-path-findings> 
            <expected-path-findings>
            <result-path-findings>
          "
  *
  * Example:
  *   sbt "core/runMain br.unb.cic.soot.scripts.CompareFindings 
            docs-metrics/taintbench/experiment-I/findings 
            docs-metrics/taintbench/source/findings
            docs-metrics/taintbench/experiment-I"
  *
  * Example:
  *   sbt "core/runMain br.unb.cic.soot.scripts.CompareFindings 
            docs-metrics/taintbench/experiment-II/findings 
            docs-metrics/taintbench/source/findings
            docs-metrics/taintbench/experiment-II"
  */

object CompareFindings extends App {

  if (args.isEmpty) {
      println("No parameters provided.")
      System.exit(1)
  }

  val actualPathFindings = args(0)
  val expectedPathFindings = args(1)
  val resultPathFindings = args(2)

  val actualFindings = new File(actualPathFindings)
  val expectedFindings = new File(expectedPathFindings)

  val actualJsonFiles = actualFindings.listFiles().filter(_.getName.endsWith("_findings.json"))
  val actualConflicts = getConflicts(actualJsonFiles)

  val expectedJsonFiles = expectedFindings.listFiles().filter(_.getName.endsWith("_findings.json"))
  val expectedConflicts = getConflicts(expectedJsonFiles)

  // generate a csv file with the conflicts

  createCSVFile(actualConflicts, expectedConflicts, resultPathFindings)

  System.exit(0)

  // methods
  private def getSourceIR(jsonData: Value): String = {
    getSourceOrSinkIR(jsonData, "source")
  }

  private def getSinkIR(jsonData: Value): String = {
    getSourceOrSinkIR(jsonData, "sink")
  }

  private def getSourceOrSinkIR(jsonData: Value, sourceOrSink: String): String = {
    try {
      val irs = jsonData.obj.get(sourceOrSink).flatMap(_.obj.get("IRs"))
      irs match {
        case Some(arr) if arr.arr.nonEmpty =>
          arr(0).obj.get("IRstatement") match {
            case Some(irStatement) => irStatement.toString()
            case None => ""
          }
        case _ => ""
      }
    } catch {
      case _: Throwable => ""
    }
  }

  private def getConflicts(jsonData: Value): Set[(String, String)] = {
    jsonData("findings").arr.map(finding => {
      (getSourceIR(finding), getSinkIR(finding))
    }).toSet
  }

  private def getConflicts(jsonFiles: Array[File]): mutable.HashMap[String, Set[(String, String)]] = {
    val actualConflicts = new mutable.HashMap[String, Set[(String, String)]]()

    jsonFiles.map(file => {
      val jsonString = Source.fromFile(file).getLines().mkString("\n")

      // Parse the JSON string into a ujson.Value
      val jsonData: Value = read(jsonString)

      val fileName = jsonData("fileName").str
      // Accessing findings
      val conflicts = getConflicts(jsonData)

      actualConflicts.put(fileName, conflicts)
    })
    actualConflicts
  }

  private def compareConflicts(actualConflicts: Set[(String, String)], expectedConflicts: Set[(String, String)]): Set[(String, String)] = {
    actualConflicts.size match {
      case 0 => Set.empty
      case _ => actualConflicts.intersect(expectedConflicts)
    }
  }

  private def createCSVFile(actualConflicts: mutable.HashMap[String, Set[(String, String)]], expectedConflicts: mutable.HashMap[String, Set[(String, String)]], resultPathFindings: String): Unit = {
    val csvFile = new File(s"$resultPathFindings/conflicts.csv")
    val writer = new PrintWriter(csvFile)
    writer.write("APK,actual-findings,expected-findings, matches\n")
    actualConflicts.foreach { case (fileName, actualConflictsByFile) =>
      val expectedConflictsByFile = expectedConflicts.getOrElse(fileName, Set.empty)
      writer.write(fileName + "," + actualConflictsByFile.size + "," + expectedConflictsByFile.size + "," + compareConflicts(actualConflictsByFile, expectedConflictsByFile).size + "\n")
    }
    writer.close()
  }
}
