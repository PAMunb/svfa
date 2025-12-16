package br.unb.cic.soot.svfa.configuration

import java.io.File
import scala.collection.JavaConverters._

import soot.options.Options
import soot._
import br.unb.cic.soot.svfa.jimple.{CallGraphAlgorithm, SVFAConfig}

/**
 * Configurable Java Soot Configuration that uses SVFAConfig for call graph settings.
 * 
 * This trait allows the call graph algorithm to be configured through the unified
 * SVFAConfig system rather than being hardcoded.
 */
trait ConfigurableJavaSootConfiguration extends SootConfiguration {
  
  /**
   * Get the SVFA configuration that includes call graph settings.
   * This should be implemented by classes that use this trait.
   */
  def getSVFAConfig: SVFAConfig
  
  /**
   * Legacy method for backward compatibility.
   * Now delegates to the SVFAConfig setting.
   */
  def callGraph(): CG = getSVFAConfig.callGraphAlgorithm match {
    case CallGraphAlgorithm.Spark => SPARK
    case CallGraphAlgorithm.CHA => CHA
    case CallGraphAlgorithm.SparkLibrary => SPARK_LIBRARY
    case CallGraphAlgorithm.RTA => RTA
    case CallGraphAlgorithm.VTA => VTA
  }

  def sootClassPath(): String

  def getEntryPoints(): List[SootMethod]

  def getIncludeList(): List[String]

  def applicationClassPath(): List[String]

  override def configureSoot() {
    G.reset()
    Options.v().set_no_bodies_for_excluded(true)
    Options.v().set_allow_phantom_refs(true)
    Options.v().set_include(getIncludeList().asJava);
    Options.v().set_output_format(Options.output_format_none)
    Options.v().set_whole_program(true)
    Options
      .v()
      .set_soot_classpath(
        sootClassPath() + File.pathSeparator + pathToJCE() + File.pathSeparator + pathToRT()
      )
    Options.v().set_process_dir(applicationClassPath().asJava)
    Options.v().set_full_resolver(true)
    Options.v().set_keep_line_number(true)
    Options.v().set_prepend_classpath(true)
    Options.v().setPhaseOption("jb", "use-original-names:true")
    configureCallGraphPhase()

    Scene.v().loadNecessaryClasses()
    Scene.v().setEntryPoints(getEntryPoints().asJava)
  }

  /**
   * Configure the call graph phase based on the SVFAConfig setting.
   */
  def configureCallGraphPhase() {
    getSVFAConfig.callGraphAlgorithm match {
      case CallGraphAlgorithm.Spark => {
        Options.v().setPhaseOption("cg.spark", "on")
        // Disable on-demand analysis to ensure complete call graph construction
        Options.v().setPhaseOption("cg.spark", "cs-demand:false")
        Options.v().setPhaseOption("cg.spark", "string-constants:true")
        // Add more aggressive options for better interprocedural coverage
        Options.v().setPhaseOption("cg.spark", "simulate-natives:true")
        Options.v().setPhaseOption("cg.spark", "simple-edges-bidirectional:false")
      }
      case CallGraphAlgorithm.CHA => {
        Options.v().setPhaseOption("cg.cha", "on")
      }
      case CallGraphAlgorithm.SparkLibrary => {
        Options.v().setPhaseOption("cg.spark", "on")
        Options.v().setPhaseOption("cg", "library:any-subtype")
        // Use similar SPARK options but with library support
        Options.v().setPhaseOption("cg.spark", "cs-demand:false")
        Options.v().setPhaseOption("cg.spark", "string-constants:true")
      }
      case CallGraphAlgorithm.RTA => {
        Options.v().setPhaseOption("cg.spark", "on")
        // Enable RTA mode in SPARK
        Options.v().setPhaseOption("cg.spark", "rta:true")
        // RTA-specific optimizations
        Options.v().setPhaseOption("cg.spark", "cs-demand:false")
        Options.v().setPhaseOption("cg.spark", "string-constants:true")
        Options.v().setPhaseOption("cg.spark", "simulate-natives:true")
      }
      case CallGraphAlgorithm.VTA => {
        Options.v().setPhaseOption("cg.spark", "on")
        // Enable VTA mode in SPARK
        Options.v().setPhaseOption("cg.spark", "vta:true")
        // VTA automatically sets field-based:true, types-for-sites:true, simplify-sccs:true
        // and on-fly-cg:false internally, but we can be explicit
        Options.v().setPhaseOption("cg.spark", "cs-demand:false")
        Options.v().setPhaseOption("cg.spark", "string-constants:true")
        Options.v().setPhaseOption("cg.spark", "simulate-natives:true")
      }
    }
  }
}
