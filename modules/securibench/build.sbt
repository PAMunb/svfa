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
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2",
  // JSON serialization for test result storage
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.13.0",
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.13.0"
)

// Securibench doesn't need environment variables
Test / envVars := Map.empty[String, String]

// Java sources are now local to this module (securibench test classes)
Test / javaSource := baseDirectory.value / "src" / "test" / "java"

// Custom test configurations for separating execution from metrics
lazy val testExecutors = taskKey[Unit]("Run only test executors (SVFA analysis)")
lazy val testMetrics = taskKey[Unit]("Run only metrics computation")

testExecutors := {
  println("=== RUNNING ONLY TEST EXECUTORS (SVFA ANALYSIS) ===")
  println("This runs SVFA analysis and saves results to disk.")
  println("Use 'testMetrics' afterwards to compute accuracy metrics.")
  println()
  (Test / testOnly).toTask(" *Executor").value
}

testMetrics := {
  println("=== RUNNING ONLY METRICS COMPUTATION ===")
  println("This computes accuracy metrics from saved test results.")
  println("Run 'testExecutors' first if no results exist.")
  println()
  (Test / testOnly).toTask(" *Metrics").value
}


