package br.unb.cic.soot

import br.unb.cic.soot.svfa.configuration.{ConfigurableJavaSootConfiguration, JavaSootConfiguration}
import br.unb.cic.soot.svfa.jimple.{
  ConfigurableAnalysis,
  FieldSensitive,
  Interprocedural,
  JSVFA,
  PropagateTaint,
  SVFAConfig
}
import soot.{Scene, SootMethod}

/**
 * Base test class for SVFA tests.
 * 
 * This class now supports both traditional trait-based configuration and 
 * the new flexible SVFAConfig-based configuration.
 * 
 * For backward compatibility, it defaults to the traditional approach:
 * - Interprocedural analysis
 * - Field-sensitive analysis  
 * - Taint propagation enabled
 * 
 * Subclasses can override `svfaConfig` to use different configurations.
 */
abstract class JSVFATest
    extends JSVFA
    with ConfigurableJavaSootConfiguration
    with Interprocedural
    with FieldSensitive
    with PropagateTaint
    with ConfigurableAnalysis {

  /**
   * Override this method to customize SVFA configuration.
   * Default configuration matches the traditional trait-based approach.
   */
  def svfaConfig: SVFAConfig = SVFAConfig.Default

  // Initialize configuration on construction
  setConfig(svfaConfig)
  
  // Implement ConfigurableJavaSootConfiguration interface
  override def getSVFAConfig: SVFAConfig = svfaConfig
  def getClassName(): String
  def getMainMethod(): String

  override def sootClassPath(): String = ""

  override def applicationClassPath(): List[String] =
    List("modules/core/target/scala-2.12/test-classes", "lib/javax.servlet-api-3.0.1.jar")

  override def getEntryPoints(): List[SootMethod] = {
    val sootClass = Scene.v().getSootClass(getClassName())
    List(sootClass.getMethodByName(getMainMethod()))
  }

  override def getIncludeList(): List[String] = List(
    "java.lang.*",
    "java.util.*"
  )
}
