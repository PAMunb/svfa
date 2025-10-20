// script module - helpers methods
name := "svfa-script"

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % "1.6.0",
  "org.scala-graph" %% "graph-core" % "1.13.0",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
  "org.scalatest" %% "scalatest" % "3.0.8" % Test,
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2",
  "com.lihaoyi" %% "upickle" % "3.1.0",
)

// Core doesn't need environment variables
Test / envVars := Map.empty[String, String]