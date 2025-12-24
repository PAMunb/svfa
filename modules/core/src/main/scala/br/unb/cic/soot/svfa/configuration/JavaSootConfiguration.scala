package br.unb.cic.soot.svfa.configuration

import java.io.File
import scala.collection.JavaConverters._

import soot.options.Options
import soot._

sealed trait CG

case object CHA extends CG
case object SPARK_LIBRARY extends CG
case object SPARK extends CG
case object RTA extends CG
case object VTA extends CG

/** Base class for all implementations of SVFA algorithms.
  */
trait JavaSootConfiguration extends SootConfiguration {
  def callGraph(): CG = SPARK

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

  def configureCallGraphPhase() {
    callGraph() match {
      case CHA => Options.v().setPhaseOption("cg.cha", "on")
      case SPARK => {
        Options.v().setPhaseOption("cg.spark", "on")
        // Disable on-demand analysis to ensure complete call graph construction
        Options.v().setPhaseOption("cg.spark", "cs-demand:false")
        Options.v().setPhaseOption("cg.spark", "string-constants:true")
        // Add more aggressive options for better interprocedural coverage
        Options.v().setPhaseOption("cg.spark", "simulate-natives:true")
        Options.v().setPhaseOption("cg.spark", "simple-edges-bidirectional:false")
      }
      case SPARK_LIBRARY => {
        Options.v().setPhaseOption("cg.spark", "on")
        Options.v().setPhaseOption("cg", "library:any-subtype")
      }
      case RTA => {
        Options.v().setPhaseOption("cg.spark", "on")
        // Enable RTA mode in SPARK
        Options.v().setPhaseOption("cg.spark", "rta:true")
        // RTA requires on-fly-cg to be disabled
        Options.v().setPhaseOption("cg.spark", "on-fly-cg:false")
        // RTA-specific optimizations
        Options.v().setPhaseOption("cg.spark", "cs-demand:false")
        Options.v().setPhaseOption("cg.spark", "string-constants:true")
        Options.v().setPhaseOption("cg.spark", "simulate-natives:true")
      }
      case VTA => {
        Options.v().setPhaseOption("cg.spark", "on")
        // Enable VTA mode in SPARK
        Options.v().setPhaseOption("cg.spark", "vta:true")
        // VTA requires on-fly-cg to be disabled (similar to RTA)
        Options.v().setPhaseOption("cg.spark", "on-fly-cg:false")
        // VTA automatically configures internal options, but we add common ones
        Options.v().setPhaseOption("cg.spark", "cs-demand:false")
        Options.v().setPhaseOption("cg.spark", "string-constants:true")
        Options.v().setPhaseOption("cg.spark", "simulate-natives:true")
      }
    }
  }

}
