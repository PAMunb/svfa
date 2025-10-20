// TaintBench module - Android malware analysis benchmarks
name := "svfa-taintbench"

libraryDependencies ++= Seq(
  // Android/FlowDroid dependencies
  "de.fraunhofer.sit.sse.flowdroid" % "soot-infoflow" % "2.13.0",
  "de.fraunhofer.sit.sse.flowdroid" % "soot-infoflow-cmd" % "2.13.0", 
  "de.fraunhofer.sit.sse.flowdroid" % "soot-infoflow-android" % "2.13.0",
  "com.google.guava" % "guava" % "27.1-jre",
  // Common dependencies that taintbench tests need
  "org.typelevel" %% "cats-core" % "1.6.0",
  "org.soot-oss" % "soot" % "4.5.0",
  "org.scala-graph" %% "graph-core" % "1.13.0",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
  "org.scalatest" %% "scalatest" % "3.0.8" % Test,
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2"
)

// TaintBench requires Android environment variables
Test / envVars := Map(
  "ANDROID_SDK" -> sys.env.get("ANDROID_SDK").orElse(sys.props.get("android.sdk")),
  "TAINT_BENCH" -> sys.env.get("TAINT_BENCH").orElse(sys.props.get("taint.bench"))
).collect { case (k, Some(v)) => (k, v) }

// Custom commands for Android testing
addCommandAlias("testRoidsec", "testOnly br.unb.cic.android.RoidsecTest")
addCommandAlias("testAndroid", "testOnly br.unb.cic.android.*")
addCommandAlias("AndroidTaintBenchSuiteExperiment1Test", "testOnly br.unb.cic.android.AndroidTaintBenchSuiteExperiment1Test")
addCommandAlias("AndroidTaintBenchSuiteExperiment2Test", "testOnly br.unb.cic.android.AndroidTaintBenchSuiteExperiment2Test")



