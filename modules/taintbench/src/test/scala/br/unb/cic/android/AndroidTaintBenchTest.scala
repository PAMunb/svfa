package br.unb.cic.android

import br.unb.cic.soot.svfa.jimple.JSVFA
import br.unb.cic.soot.svfa.configuration.AndroidSootConfiguration
import br.unb.cic.soot.svfa.jimple.{
  ConfigurableAnalysis,
  FieldSensitive,
  Interprocedural,
  JSVFA,
  PropagateTaint,
  SVFAConfig
}

import scala.io.Source
import java.util.Properties
import java.io.File
import java.io.FileInputStream
import soot._
import soot.jimple._
import br.unb.cic.soot.graph._

import java.nio.file.Paths
import br.unb.cic.soot.svfa.configuration.AndroidSootConfiguration

/**
 * Android TaintBench test with configurable SVFA settings.
 * 
 * @param apk The name of the APK file to analyze (without .apk extension)
 * @param config Optional SVFA configuration (defaults to SVFAConfig.Default)
 */
class AndroidTaintBenchTest(
    apk: String, 
    config: SVFAConfig = SVFAConfig.Default
) extends JSVFA
    with TaintBenchSpec
    with AndroidSootConfiguration
    with Interprocedural
    with FieldSensitive
    with PropagateTaint
    with ConfigurableAnalysis {

  // Set the configuration
  setConfig(config)
  def getApkPath(): String =
    readEnvironmentVariable("TAINT_BENCH") + (s"/$apk.apk")

  def apk(): String = getApkPath()

  def platform(): String =
    readEnvironmentVariable("ANDROID_SDK") + "/platforms/"

  private def readEnvironmentVariable(key: String): String = {
    scala.util.Properties.envOrNone(key) match {
      case Some(value) => value
      case None =>
        throw new RuntimeException(
          s"Environment variable $key is not set. Please set it before executing the test cases." +
            s"\n\nYou can set it by:\n" +
            s"1. Export it: export $key=/path/to/$key\n" +
            s"2. Use SBT custom tasks: sbt 'testRoidsec --android-sdk=/path/to/sdk --taint-bench=/path/to/taintbench'\n" +
            s"3. Pass directly: $key=/path/to/$key sbt 'testOnly br.unb.cic.android.RoidsecTest'"
        )
    }
  }

  override def analyze(unit: soot.Unit): NodeType = {
    unit match {
      case invokeStmt: InvokeStmt =>
        return analyzeInvokeStmt(invokeStmt.getInvokeExpr)
      case assignStmt: AssignStmt =>
        assignStmt.getRightOp match {
          case invokeStmt: InvokeExpr =>
            return analyzeInvokeStmt(invokeStmt)
          case _ =>
            return SimpleNode
        }
      case _ => return SimpleNode
    }
  }

  def analyzeInvokeStmt(exp: InvokeExpr): NodeType = {
    if (sourceList.contains(exp.getMethod.getSignature)) {
      return SourceNode;
    } else if (sinkList.contains(exp.getMethod.getSignature)) {
      return SinkNode;
    }
    return SimpleNode;
  }
}
