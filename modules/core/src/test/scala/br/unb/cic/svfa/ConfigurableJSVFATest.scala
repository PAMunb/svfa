package br.unb.cic.soot

import br.unb.cic.soot.svfa.configuration.ConfigurableJavaSootConfiguration
import br.unb.cic.soot.svfa.jimple.{ConfigurableAnalysis, JSVFA, SVFAConfig}
import soot.{Scene, SootMethod}

/**
 * Modern SVFA test class that uses only the new configuration approach.
 * 
 * This class demonstrates how to create tests with runtime-configurable SVFA settings
 * without relying on trait mixins for analysis configuration.
 */
abstract class ConfigurableJSVFATest(config: SVFAConfig = SVFAConfig.Default)
    extends JSVFA
    with ConfigurableJavaSootConfiguration
    with ConfigurableAnalysis {

  // Set configuration at construction time
  setConfig(config)
  
  // Implement ConfigurableJavaSootConfiguration interface
  override def getSVFAConfig: SVFAConfig = config

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

/**
 * Fast analysis configuration for performance-critical tests.
 */
abstract class FastJSVFATest
    extends ConfigurableJSVFATest(SVFAConfig.Fast)

/**
 * Precise analysis configuration for accuracy-critical tests.
 */
abstract class PreciseJSVFATest
    extends ConfigurableJSVFATest(SVFAConfig.Precise)

/**
 * Custom analysis configuration for specialized tests.
 */
abstract class CustomJSVFATest(
    interprocedural: Boolean = true,
    fieldSensitive: Boolean = true,
    propagateObjectTaint: Boolean = true
) extends ConfigurableJSVFATest(
    SVFAConfig(
      interprocedural = interprocedural,
      fieldSensitive = fieldSensitive,
      propagateObjectTaint = propagateObjectTaint
    )
)
