package br.unb.cic.soot.svfa.configuration

import soot.jimple.infoflow.InfoflowConfiguration
import soot.jimple.infoflow.android.{
  InfoflowAndroidConfiguration,
  SetupApplication
}
import soot._
import soot.options.Options
import soot.jimple.infoflow.android.config.SootConfigForAndroid

sealed trait AndroidCallGraph

case object AndroidCHA extends AndroidCallGraph
case object AndroidRTA extends AndroidCallGraph  
case object AndroidVTA extends AndroidCallGraph
case object AndroidSPARK extends AndroidCallGraph

trait AndroidSootConfiguration extends SootConfiguration {

  def apk: String

  def platform(): String
  
  def callGraphAlgorithm(): AndroidCallGraph = AndroidSPARK
  
  private def mapToInfoflowAlgorithm(androidCG: AndroidCallGraph): InfoflowConfiguration.CallgraphAlgorithm = {
    androidCG match {
      case AndroidCHA => InfoflowConfiguration.CallgraphAlgorithm.CHA
      case AndroidRTA => InfoflowConfiguration.CallgraphAlgorithm.RTA
      case AndroidVTA => InfoflowConfiguration.CallgraphAlgorithm.VTA
      case AndroidSPARK => InfoflowConfiguration.CallgraphAlgorithm.SPARK
    }
  }
  
  override def configureSoot(): scala.Unit = {
    // Set up InfoflowAndroidConfiguration
    val config = new InfoflowAndroidConfiguration
    config.setCallgraphAlgorithm(mapToInfoflowAlgorithm(callGraphAlgorithm()))
    config.setCodeEliminationMode(
      InfoflowConfiguration.CodeEliminationMode.NoCodeElimination
    )
    config.getAnalysisFileConfig.setAndroidPlatformDir(platform)
    config.getAnalysisFileConfig.setTargetAPKFile(apk)
    config.setMergeDexFiles(true)
    config.setTaintAnalysisEnabled(true)
    config.setIgnoreFlowsInSystemPackages(true)
    config.setExcludeSootLibraryClasses(true)

    // Proper Soot initialization for Android
    G.reset()
    Options.v().set_allow_phantom_refs(true)
    Options.v().set_output_format(Options.output_format_none)
    Options.v().set_whole_program(true)
    Options.v().set_src_prec(Options.src_prec_apk_class_jimple)
    Options.v().set_keep_line_number(true)
    Options.v().set_keep_offset(false)
    Options.v().set_throw_analysis(Options.throw_analysis_dalvik)
    Options.v().set_process_multiple_dex(true)
    Options.v().set_ignore_resolution_errors(true)
    // Let FlowDroid compute/patch entry points and classpath via its Soot config
    // but make sure Android platform is known
    Options.v().set_force_android_jar(platform())

    // Bridge FlowDroid -> Soot options
    val sootConfig = new SootConfigForAndroid()
    sootConfig.setSootOptions(Options.v(), config)

    // Create the data flow analyzer with Soot config
    val flowDroid = new SetupApplication(config)
    flowDroid.setSootConfig(sootConfig)

    // Build the callgraph (also runs callback discovery)
    flowDroid.constructCallgraph()
  }

}
