// Securibench module - includes Java security benchmarks
name := "svfa-securibench"

libraryDependencies ++= Seq(
  "javax.servlet" % "javax.servlet-api" % "3.0.1" % Provided,
  // Common dependencies that securibench tests need
  "org.typelevel" %% "cats-core" % "1.6.0",
  "org.soot-oss" % "soot" % "4.5.0",
  "org.scala-graph" %% "graph-core" % "1.13.0",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
  "org.scalatest" %% "scalatest" % "3.0.8" % Test,
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2"
)

// Securibench doesn't need environment variables
Test / envVars := Map.empty[String, String]

// Java sources are now local to this module (securibench test classes)
Test / javaSource := baseDirectory.value / "src" / "test" / "java"


