package br.unb.cic.soot.scripts

import java.io.{File, PrintWriter}
import scala.io.Source
import ujson._

import scala.collection.mutable

object CompareFindings extends App {

  if (args.isEmpty) {
      println("No parameters provided.")
      System.exit(1)
  }

  val actualPathFindings = args(0)
  // val expectedPathFindings = args(1)
  // val resultPathFindings = args(2)

  val actualFindings = new File(actualPathFindings)
  // val expectedFindings = new File(expectedPathFindings)

  val actualJsonFiles = actualFindings.listFiles().filter(_.getName.endsWith("_findings.json"))
  val actualConflicts = new mutable.HashSet[(String, Set[(String, String)])]()

  actualJsonFiles.map(file => {
      val jsonString = Source.fromFile(file).getLines().mkString("\n")

      // Parse the JSON string into a ujson.Value
      val jsonData: Value = read(jsonString)

      val fileName = jsonData("fileName").str
      // Accessing findings
      val conflicts = getConflicts(jsonData)

      actualConflicts.add((fileName, conflicts))
  })

  // generate a csv file with the conflicts
  val csvFile = new File("conflicts.csv")
  val writer = new PrintWriter(csvFile)
  writer.write("fileName,actual findings\n")
  actualConflicts.foreach(conflict => {
    writer.write(conflict._1 + "," + conflict._2.size + "\n")
  })
  writer.close()

  System.exit(0)

  // methods
  private def getSourceIR(jsonData: Value): String = {
    getSourceOrSinkIR(jsonData, "source")
  }

  private def getSinkIR(jsonData: Value): String = {
    getSourceOrSinkIR(jsonData, "sink")
  }

  private def getSourceOrSinkIR(jsonData: Value, sourceOrSink: String): String = {
    jsonData(sourceOrSink)("IRs")(0)("IRstatement").toString()
  }

  private def getConflicts(jsonData: Value): Set[(String, String)] = {
    jsonData("findings").arr.map(finding => {
      (getSourceIR(finding), getSinkIR(finding))
    }).toSet
  }
}
