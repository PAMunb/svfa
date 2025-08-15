package br.unb.cic.soot.svfa.configuration

import soot._
import soot.jimple.infoflow.InfoflowConfiguration
import soot.jimple.infoflow.android.config.SootConfigForAndroid
import soot.jimple.infoflow.android.{InfoflowAndroidConfiguration, SetupApplication}
import soot.options.Options

import java.io.File
import java.util.Collections

trait AndroidSootConfiguration2 extends SootConfiguration {

  def apk: String

  def platform(): String

  def callGraph(): CG = SPARK

  def applicationClassPath(): List[String] = List("target/scala-2.12/test-classes", System.getProperty("user.home")+"/.m2/repository/javax/servlet/javax.servlet-api/3.0.1/javax.servlet-api-3.0.1.jar")

  def sootClassPath(): String = ""

  override def configureSoot() {
    // Do InfoflowAndroidConfiguration
    val config = new InfoflowAndroidConfiguration
    config.setCallgraphAlgorithm(InfoflowConfiguration.CallgraphAlgorithm.SPARK)
    config.setCodeEliminationMode(
      InfoflowConfiguration.CodeEliminationMode.NoCodeElimination
    )
    config.getAnalysisFileConfig.setAndroidPlatformDir(platform)
    config.getAnalysisFileConfig.setTargetAPKFile(apk)

    // Do Soot Configuration
    G.reset()
    soot.options.Options.v().set_allow_phantom_refs(true);
    soot.options.Options.v().set_output_format(soot.options.Options.output_format_none);
//    soot.options.Options.v().set_process_dir(Collections.singletonList(apk));
//    soot.options.Options.v().set_force_android_jar(platform());
    soot.options.Options.v().set_src_prec(soot.options.Options.src_prec_apk_class_jimple);
    soot.options.Options.v().set_keep_line_number(false);
    soot.options.Options.v().set_keep_offset(false);
    soot.options.Options.v().set_throw_analysis(soot.options.Options.throw_analysis_dalvik);
    soot.options.Options.v().set_process_multiple_dex(true);
    soot.options.Options.v().set_whole_program(true);

    // Soot config for Android
    val sootConfig = new SootConfigForAndroid();
    sootConfig.setSootOptions(soot.options.Options.v(), config);

    // Create the data flow analyzer
    val analyzer = new SetupApplication(config);
    analyzer.setSootConfig(sootConfig);
    analyzer.constructCallgraph();
  }
  def configureSoot2() {
//    val config = new InfoflowAndroidConfiguration
//    config.setCallgraphAlgorithm(InfoflowConfiguration.CallgraphAlgorithm.SPARK)
//    config.setCodeEliminationMode(
//      InfoflowConfiguration.CodeEliminationMode.NoCodeElimination
//    )
//    config.getAnalysisFileConfig.setAndroidPlatformDir(platform)
//    config.getAnalysisFileConfig.setTargetAPKFile(apk)
//
//    val flowDroid = new SetupApplication(config)
//
//    flowDroid.constructCallgraph()

//    val androidJar = config.getAnalysisFileConfig.getAndroidPlatformDir
//    val apkFileLocation = config.getAnalysisFileConfig.getTargetAPKFile

    // Clean up any old Soot instance we may have
//    val config = new InfoflowAndroidConfiguration
    G.reset

    Options.v.set_no_bodies_for_excluded(true)
    Options.v.set_allow_phantom_refs(true)
    Options.v.set_output_format(Options.output_format_none)
    Options.v.set_whole_program(true)
    Options.v.set_process_dir(Collections.singletonList(apk))
    Options.v.set_force_android_jar(platform())
    Options.v.set_src_prec(Options.src_prec_apk_class_jimple)
    Options.v.set_keep_offset(false)
    Options.v.set_keep_line_number(true)
    Options.v.set_throw_analysis(Options.throw_analysis_dalvik)
    Options.v.set_process_multiple_dex(true)
    Options.v.set_ignore_resolution_errors(true)

    // Set soot phase option if original names should be used
    Options.v.setPhaseOption("jb", "use-original-names:true")

    // Set the Soot configuration options. Note that this will needs to be
    // done before we compute the classpath.

    Options
      .v()
      .set_soot_classpath(
        sootClassPath() + File.pathSeparator + pathToJCE() + File.pathSeparator + pathToRT()
      )
    Main.v.autoSetOptions()
    configureCallGraphPhase()

    // Load whatever we need
    Scene.v.loadNecessaryClasses()

    // Make sure that we have valid Jimple bodies
    PackManager.v.getPack("wjpp").apply()

    // Patch the callgraph to support additional edges. We do this now,
    // because during callback discovery, the context-insensitive callgraph
    // algorithm would flood us with invalid edges.
//    val configuration = new InfoflowAndroidConfiguration();
//    configuration.setSootIntegrationMode(SootIntegrationMode.UseExistingInstance);
//    val flowDroid = new SetupApplication(configuration)
//    flowDroid.constructCallgraph()
  }


  def configureCallGraphPhase() {
    callGraph() match {
      case CHA => Options.v().setPhaseOption("cg.cha", "on")
      case SPARK => {
        Options.v().setPhaseOption("cg.spark", "on")
//        Options.v().setPhaseOption("cg.spark", "cs-demand:true")
//        Options.v().setPhaseOption("cg.spark", "string-constants:true")
        Options.v.setPhaseOption("cg.spark", "string-constants:true")
      }
      case SPARK_LIBRARY => {
        Options.v().setPhaseOption("cg.spark", "on")
        Options.v().setPhaseOption("cg", "library:any-subtype")
      }
    }
  }
}
