package br.unb.cic.securibench

import java.io.{File, FileWriter, PrintWriter}
import scala.io.Source
import scala.util.Try
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule

/**
 * Data class representing the result of a single test execution
 */
case class TestExecutionResult(
  testName: String,
  packageName: String,
  className: String,
  expectedVulnerabilities: Int,
  foundVulnerabilities: Int,
  executionTimeMs: Long,
  conflicts: List[String], // Serialized conflict information
  timestamp: Long = System.currentTimeMillis()
)

/**
 * Utility for saving and loading test execution results
 */
object TestResultStorage {
  private val mapper = new ObjectMapper()
  mapper.registerModule(DefaultScalaModule)
  
  def getResultsDirectory(packageName: String): File = {
    // Include call graph algorithm in the directory path to avoid overwriting results
    val callGraphAlgorithm = SecuribenchConfig.getCallGraphAlgorithm().name.toLowerCase
    val dir = new File(s"target/test-results/${callGraphAlgorithm}/${packageName.replace('.', '/')}")
    if (!dir.exists()) {
      dir.mkdirs()
    }
    dir
  }
  
  def saveTestResult(result: TestExecutionResult): Unit = {
    val resultsDir = getResultsDirectory(result.packageName)
    val resultFile = new File(resultsDir, s"${result.testName}.json")
    
    Try {
      val writer = new PrintWriter(new FileWriter(resultFile))
      try {
        writer.println(mapper.writeValueAsString(result))
      } finally {
        writer.close()
      }
    }.recover {
      case e: Exception => 
        println(s"Failed to save result for ${result.testName}: ${e.getMessage}")
    }
  }
  
  def loadTestResults(packageName: String): List[TestExecutionResult] = {
    val resultsDir = getResultsDirectory(packageName)
    if (!resultsDir.exists()) {
      return List.empty
    }
    
    resultsDir.listFiles()
      .filter(_.getName.endsWith(".json"))
      .flatMap { file =>
        Try {
          val source = Source.fromFile(file)
          try {
            mapper.readValue(source.mkString, classOf[TestExecutionResult])
          } finally {
            source.close()
          }
        }.toOption
      }
      .toList
  }
  
  def clearResults(packageName: String): Unit = {
    val resultsDir = getResultsDirectory(packageName)
    if (resultsDir.exists()) {
      resultsDir.listFiles().foreach(_.delete())
    }
  }
}
