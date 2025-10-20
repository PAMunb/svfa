// Multi-module SBT configuration for SVFA project
// This separates core SVFA functionality from heavy benchmark dependencies

ThisBuild / scalaVersion := "2.12.20"
ThisBuild / organization := "br.unb.cic"
ThisBuild / version := "0.6.0"

// Global settings
ThisBuild / publishConfiguration := publishConfiguration.value.withOverwrite(true)
ThisBuild / publishLocalConfiguration := publishLocalConfiguration.value.withOverwrite(true)
ThisBuild / Test / parallelExecution := false

// Global resolvers
ThisBuild / resolvers ++= Seq(
  "Local maven repository" at s"file://${Path.userHome.absolutePath}/.m2/repository",
  Classpaths.typesafeReleases
)

// Common dependencies
lazy val commonDependencies = Seq(
  "org.typelevel" %% "cats-core" % "1.6.0",
  "org.soot-oss" % "soot" % "4.5.0",
  "org.scala-graph" %% "graph-core" % "1.13.0",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
  "org.scalatest" %% "scalatest" % "3.0.8" % Test,
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2",
  "com.lihaoyi" %% "upickle" % "3.1.0"
)

// Android-specific dependencies
lazy val androidDependencies = Seq(
  "de.fraunhofer.sit.sse.flowdroid" % "soot-infoflow" % "2.13.0",
  "de.fraunhofer.sit.sse.flowdroid" % "soot-infoflow-cmd" % "2.13.0",
  "de.fraunhofer.sit.sse.flowdroid" % "soot-infoflow-android" % "2.13.0",
  "com.google.guava" % "guava" % "27.1-jre"
)

// Servlet dependencies
lazy val servletDependencies = Seq(
  "javax.servlet" % "javax.servlet-api" % "3.0.1" % Provided
)

// ROOT PROJECT (Aggregate)
lazy val root = (project in file("."))
  .aggregate(core, script, securibench, taintbench)
  .settings(
    name := "svfa-scala",
    publish / skip := true, // Don't publish the aggregate
    // Commands that work on all modules
    addCommandAlias("testCore", "core/test"),
    addCommandAlias("testSecuribench", "securibench/test"),
    addCommandAlias("testTaintbench", "taintbench/test"),
    addCommandAlias("testAll", "test")
  )

// CORE MODULE - Essential SVFA functionality
lazy val core = (project in file("modules/core"))
  .settings(
    name := "svfa-core",
    libraryDependencies ++= commonDependencies,
    
    // Core doesn't need environment variables
    Test / envVars := Map.empty[String, String],
    
    // Source directories for core module
    Compile / scalaSource := baseDirectory.value / "src" / "main" / "scala",
    Test / scalaSource := baseDirectory.value / "src" / "test" / "scala",
    Compile / resourceDirectory := baseDirectory.value / "src" / "main" / "resources",
    Test / resourceDirectory := baseDirectory.value / "src" / "test" / "resources",
    
    // Publishing configuration - only when GitHub token is available
    publishTo := {
      val githubToken = sys.env.get("GITHUB_TOKEN")
      if (githubToken.isDefined) {
        Some("GitHub PAMunb Apache Maven Packages" at "https://maven.pkg.github.com/PAMunb/svfa")
      } else {
        Some(Resolver.mavenLocal)
      }
    },
    
    // Credentials only when GitHub token is available
    credentials := {
      val githubToken = sys.env.get("GITHUB_TOKEN")
      if (githubToken.isDefined) {
        Seq(Credentials("GitHub Package Registry", "maven.pkg.github.com", "PAMunb", githubToken.get))
      } else {
        Seq.empty
      }
    }
  )

// SECURIBENCH MODULE - Java-based security benchmarks
lazy val securibench = (project in file("modules/securibench"))
  .dependsOn(core % "compile->compile;test->test")
  .settings(
    name := "svfa-securibench", 
    libraryDependencies ++= servletDependencies,
    
    // Securibench doesn't need environment variables
    Test / envVars := Map.empty[String, String],
    
    // Include Java sources for securibench test cases
    Test / javaSource := baseDirectory.value / "src" / "test" / "java",
    Test / scalaSource := baseDirectory.value / "src" / "test" / "scala",
    Test / resourceDirectory := baseDirectory.value / "src" / "test" / "resources"
  )

// TAINTBENCH MODULE - Android-based malware analysis benchmarks  
lazy val taintbench = (project in file("modules/taintbench"))
  .dependsOn(core % "compile->compile;test->test")
  .settings(
    name := "svfa-taintbench",
    libraryDependencies ++= androidDependencies,
    
    // TaintBench requires environment variables
    Test / envVars := Map(
      "ANDROID_SDK" -> sys.env.get("ANDROID_SDK").orElse(sys.props.get("android.sdk")),
      "TAINT_BENCH" -> sys.env.get("TAINT_BENCH").orElse(sys.props.get("taint.bench"))
    ).collect { case (k, Some(v)) => (k, v) },
    
    // Custom commands for Android testing
    addCommandAlias("testRoidsec", "testOnly br.unb.cic.android.RoidsecTest"),
    addCommandAlias("testAndroid", "testOnly br.unb.cic.android.*"),
    
    Test / scalaSource := baseDirectory.value / "src" / "test" / "scala",
    Test / resourceDirectory := baseDirectory.value / "src" / "test" / "resources"
  )

// SCRIPT MODULE - Android-based malware analysis benchmarks
lazy val script = (project in file("modules/script"))
  .dependsOn(core % "compile->compile;test->test")
  .settings(
    name := "svfa-script",
    libraryDependencies ++= commonDependencies,

    // Custom commands for Android testing
//    addCommandAlias("testRoidsec", "testOnly br.unb.cic.android.RoidsecTest"),
//    addCommandAlias("testAndroid", "testOnly br.unb.cic.android.*"),

    // Source directories for core module
    Compile / scalaSource := baseDirectory.value / "src" / "main" / "scala",
//    Test / scalaSource := baseDirectory.value / "src" / "test" / "scala",
//    Test / resourceDirectory := baseDirectory.value / "src" / "test" / "resources"
  )

// Global commands that work across modules
addCommandAlias("compileAll", "all core/compile securibench/compile taintbench/compile")
addCommandAlias("publishAllLocal", "all core/publishLocal securibench/publishLocal taintbench/publishLocal")
addCommandAlias("publishCore", "core/publish")
