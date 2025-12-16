package br.unb.cic.securibench

import br.unb.cic.soot.svfa.jimple.{CallGraphAlgorithm, SVFAConfig}

/**
 * Configuration parser for Securibench tests.
 * 
 * Supports command-line arguments and system properties for configuring
 * call graph algorithms and other SVFA settings.
 */
object SecuribenchConfig {
  
  /**
   * Parse call graph algorithm from system properties or environment variables.
   * 
   * Checks in order:
   * 1. System property: -Dsecuribench.callgraph=spark
   * 2. Environment variable: SECURIBENCH_CALLGRAPH=spark
   * 3. Default: SPARK
   */
  def getCallGraphAlgorithm(): CallGraphAlgorithm = {
    val callGraphName = Option(System.getProperty("securibench.callgraph"))
      .orElse(Option(System.getenv("SECURIBENCH_CALLGRAPH")))
      .getOrElse("spark")
      .toLowerCase
    
    try {
      CallGraphAlgorithm.fromString(callGraphName)
    } catch {
      case e: IllegalArgumentException =>
        println(s"Warning: ${e.getMessage}")
        println(s"Available algorithms: ${CallGraphAlgorithm.availableNames.mkString(", ")}")
        println("Falling back to SPARK")
        CallGraphAlgorithm.Spark
    }
  }
  
  /**
   * Parse complete SVFA configuration from system properties.
   * 
   * Supported properties:
   * - securibench.callgraph: spark|cha|spark_library
   * - securibench.interprocedural: true|false
   * - securibench.fieldsensitive: true|false
   * - securibench.propagatetaint: true|false
   */
  def getConfiguration(): SVFAConfig = {
    val callGraph = getCallGraphAlgorithm()
    
    val interprocedural = Option(System.getProperty("securibench.interprocedural"))
      .orElse(Option(System.getenv("SECURIBENCH_INTERPROCEDURAL")))
      .map(_.toLowerCase == "true")
      .getOrElse(true)
    
    val fieldSensitive = Option(System.getProperty("securibench.fieldsensitive"))
      .orElse(Option(System.getenv("SECURIBENCH_FIELDSENSITIVE")))
      .map(_.toLowerCase == "true")
      .getOrElse(true)
    
    val propagateTaint = Option(System.getProperty("securibench.propagatetaint"))
      .orElse(Option(System.getenv("SECURIBENCH_PROPAGATETAINT")))
      .map(_.toLowerCase == "true")
      .getOrElse(true)
    
    SVFAConfig(
      interprocedural = interprocedural,
      fieldSensitive = fieldSensitive,
      propagateObjectTaint = propagateTaint,
      callGraphAlgorithm = callGraph
    )
  }
  
  /**
   * Get a predefined configuration by name.
   * 
   * Supported names:
   * - default: SVFAConfig.Default (SPARK)
   * - fast: SVFAConfig.Fast (SPARK)
   * - precise: SVFAConfig.Precise (SPARK)
   * - cha: SVFAConfig.WithCHA (CHA)
   * - spark_library: SVFAConfig.WithSparkLibrary (SPARK_LIBRARY)
   * - fast_cha: SVFAConfig.FastCHA (CHA)
   */
  def getConfigurationByName(name: String): SVFAConfig = {
    name.toLowerCase match {
      case "default" => SVFAConfig.Default
      case "fast" => SVFAConfig.Fast
      case "precise" => SVFAConfig.Precise
      case "cha" => SVFAConfig.WithCHA
      case "spark_library" | "sparklibrary" => SVFAConfig.WithSparkLibrary
      case "fast_cha" | "fastcha" => SVFAConfig.FastCHA
      case _ => 
        println(s"Warning: Unknown configuration name '$name'. Using default.")
        println("Available configurations: default, fast, precise, cha, spark_library, fast_cha")
        SVFAConfig.Default
    }
  }
  
  /**
   * Print current configuration for debugging.
   */
  def printConfiguration(config: SVFAConfig): Unit = {
    println("=== SECURIBENCH CONFIGURATION ===")
    println(s"Call Graph Algorithm: ${config.callGraphAlgorithm.name}")
    println(s"Interprocedural: ${config.interprocedural}")
    println(s"Field Sensitive: ${config.fieldSensitive}")
    println(s"Propagate Object Taint: ${config.propagateObjectTaint}")
    println("=" * 35)
  }
  
  /**
   * Print usage information for command-line configuration.
   */
  def printUsage(): Unit = {
    println("""
=== SECURIBENCH CONFIGURATION USAGE ===

Command-line configuration via system properties:
  sbt -Dsecuribench.callgraph=cha "project securibench" test
  sbt -Dsecuribench.interprocedural=false "project securibench" test
  
Environment variables:
  export SECURIBENCH_CALLGRAPH=spark_library
  sbt "project securibench" test
  
Available call graph algorithms:
  - spark (default): SPARK points-to analysis
  - cha: Class Hierarchy Analysis (faster, less precise)
  - spark_library: SPARK with library support
  
Available predefined configurations:
  - default: Interprocedural, field-sensitive, SPARK
  - fast: Intraprocedural, field-insensitive, SPARK
  - precise: Interprocedural, field-sensitive, SPARK
  - cha: Interprocedural, field-sensitive, CHA
  - spark_library: Interprocedural, field-sensitive, SPARK_LIBRARY
  - fast_cha: Intraprocedural, field-insensitive, CHA
  
Examples:
  # Use CHA call graph
  sbt -Dsecuribench.callgraph=cha "project securibench" test
  
  # Use SPARK_LIBRARY with intraprocedural analysis
  sbt -Dsecuribench.callgraph=spark_library -Dsecuribench.interprocedural=false "project securibench" test
  
  # Use environment variables
  export SECURIBENCH_CALLGRAPH=cha
  sbt "project securibench" test
""")
  }
}
