// Core SVFA module - minimal dependencies, fast builds
name := "svfa-core"

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % "1.6.0",
  "org.soot-oss" % "soot" % "4.5.0", 
  "org.scala-graph" %% "graph-core" % "1.13.0",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
  "org.scalatest" %% "scalatest" % "3.0.8" % Test,
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2",
  // Android/FlowDroid dependencies for basic Android analysis support
  "de.fraunhofer.sit.sse.flowdroid" % "soot-infoflow" % "2.13.0",
  "de.fraunhofer.sit.sse.flowdroid" % "soot-infoflow-cmd" % "2.13.0",
  "de.fraunhofer.sit.sse.flowdroid" % "soot-infoflow-android" % "2.13.0",
  "com.google.guava" % "guava" % "27.1-jre"
)

// Core doesn't need environment variables
Test / envVars := Map.empty[String, String]

