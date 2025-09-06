package br.unb.cic.soot.svfa.configuration

import soot.jimple.infoflow.InfoflowConfiguration
import soot.jimple.infoflow.android.{
  InfoflowAndroidConfiguration,
  SetupApplication
}

sealed trait AndroidCallGraph

case object AndroidCHA extends AndroidCallGraph
case object AndroidRTA extends AndroidCallGraph  
case object AndroidVTA extends AndroidCallGraph
case object AndroidSPARK extends AndroidCallGraph

trait AndroidSootConfiguration extends SootConfiguration {

  def apk: String

  def platform(): String
  
  def callGraphAlgorithm(): AndroidCallGraph = AndroidCHA
  
  private def mapToInfoflowAlgorithm(androidCG: AndroidCallGraph): InfoflowConfiguration.CallgraphAlgorithm = {
    androidCG match {
      case AndroidCHA => InfoflowConfiguration.CallgraphAlgorithm.CHA
      case AndroidRTA => InfoflowConfiguration.CallgraphAlgorithm.RTA
      case AndroidVTA => InfoflowConfiguration.CallgraphAlgorithm.VTA
      case AndroidSPARK => InfoflowConfiguration.CallgraphAlgorithm.SPARK
    }
  }
  
  override def configureSoot(): Unit = {
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

    val flowDroid = new SetupApplication(config)

    flowDroid.constructCallgraph()
  }

}
