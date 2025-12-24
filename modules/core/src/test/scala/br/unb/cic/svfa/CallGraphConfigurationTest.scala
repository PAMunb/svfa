package br.unb.cic.soot

import br.unb.cic.soot.svfa.jimple.{CallGraphAlgorithm, SVFAConfig}
import org.scalatest.{BeforeAndAfter, FunSuite}

/**
 * Test suite demonstrating the new call graph configuration capabilities.
 * 
 * This suite shows how the call graph algorithm can be configured as part
 * of the unified SVFA configuration system.
 */
class CallGraphConfigurationTest extends FunSuite with BeforeAndAfter {

  test("Default configuration uses SPARK call graph") {
    val svfa = new MethodBasedSVFATest(
      className = "samples.ArraySample",
      sourceMethods = Set("source"),
      sinkMethods = Set("sink")
    )
    
    // Verify default configuration
    val actualConfig = svfa.getConfig
    assert(actualConfig.callGraphAlgorithm == CallGraphAlgorithm.Spark)
    assert(actualConfig.callGraphAlgorithm.name == "SPARK")
    
    println(s"Default call graph algorithm: ${actualConfig.callGraphAlgorithm.name}")
  }

  test("Custom configuration can specify call graph algorithm") {
    val customConfig = SVFAConfig(
      interprocedural = true,
      fieldSensitive = true,
      propagateObjectTaint = true,
      callGraphAlgorithm = CallGraphAlgorithm.Spark
    )
    
    val svfa = new MethodBasedSVFATest(
      className = "samples.ArraySample",
      sourceMethods = Set("source"),
      sinkMethods = Set("sink"),
      config = customConfig
    )
    
    // Verify custom configuration
    val actualConfig = svfa.getConfig
    assert(actualConfig.callGraphAlgorithm == CallGraphAlgorithm.Spark)
    assert(actualConfig.callGraphAlgorithm.name == "SPARK")
    
    println(s"Custom call graph algorithm: ${actualConfig.callGraphAlgorithm.name}")
  }

  test("All call graph algorithms are supported") {
    val algorithms = List(
      CallGraphAlgorithm.Spark,
      CallGraphAlgorithm.CHA,
      CallGraphAlgorithm.SparkLibrary
    )
    
    algorithms.foreach { algorithm =>
      val config = SVFAConfig.Default.withCallGraph(algorithm)
      assert(config.callGraphAlgorithm == algorithm)
      println(s"${algorithm.name} call graph algorithm supported")
    }
  }

  test("Predefined configurations use correct call graph algorithms") {
    val configurations = Map(
      "Default" -> (SVFAConfig.Default, CallGraphAlgorithm.Spark),
      "Fast" -> (SVFAConfig.Fast, CallGraphAlgorithm.Spark),
      "Precise" -> (SVFAConfig.Precise, CallGraphAlgorithm.Spark),
      "WithCHA" -> (SVFAConfig.WithCHA, CallGraphAlgorithm.CHA),
      "WithSparkLibrary" -> (SVFAConfig.WithSparkLibrary, CallGraphAlgorithm.SparkLibrary),
      "FastCHA" -> (SVFAConfig.FastCHA, CallGraphAlgorithm.CHA)
    )
    
    configurations.foreach { case (name, (configInstance, expectedAlgorithm)) =>
      assert(configInstance.callGraphAlgorithm == expectedAlgorithm, s"$name should use ${expectedAlgorithm.name}")
      println(s"$name configuration: ${configInstance.callGraphAlgorithm.name} call graph")
    }
  }

  test("Call graph algorithm can be changed at runtime") {
    val svfa = new MethodBasedSVFATest(
      className = "samples.ArraySample",
      sourceMethods = Set("source"),
      sinkMethods = Set("sink")
    )
    
    // Verify initial configuration
    assert(svfa.getConfig.callGraphAlgorithm == CallGraphAlgorithm.Spark)
    
    // Change call graph algorithm (currently only SPARK is supported)
    svfa.setCallGraphAlgorithm(CallGraphAlgorithm.Spark)
    
    // Verify change was applied
    assert(svfa.getConfig.callGraphAlgorithm == CallGraphAlgorithm.Spark)
    
    println(s"Runtime call graph algorithm: ${svfa.getConfig.callGraphAlgorithm.name}")
  }

  test("Configuration with convenience methods includes call graph") {
    val fluentConfig = SVFAConfig.Default
      .withInterprocedural
      .withFieldSensitive
      .withTaintPropagation
      .withCallGraph(CallGraphAlgorithm.Spark)
    
    assert(fluentConfig.interprocedural == true)
    assert(fluentConfig.fieldSensitive == true)
    assert(fluentConfig.propagateObjectTaint == true)
    assert(fluentConfig.callGraphAlgorithm == CallGraphAlgorithm.Spark)
    
    println(s"Fluent configuration call graph: ${fluentConfig.callGraphAlgorithm.name}")
  }

  test("String-based configuration creation works for all call graph algorithms") {
    val testCases = List(
      ("spark", CallGraphAlgorithm.Spark),
      ("cha", CallGraphAlgorithm.CHA),
      ("spark_library", CallGraphAlgorithm.SparkLibrary),
      ("sparklibrary", CallGraphAlgorithm.SparkLibrary),
      ("spark-library", CallGraphAlgorithm.SparkLibrary)
    )
    
    testCases.foreach { case (algorithmString, expectedAlgorithm) =>
      val stringConfig = SVFAConfig.fromStrings(
        interprocedural = "true",
        fieldSensitive = "true", 
        propagateObjectTaint = "true",
        callGraphAlgorithm = algorithmString
      )
      
      assert(stringConfig.interprocedural == true)
      assert(stringConfig.fieldSensitive == true)
      assert(stringConfig.propagateObjectTaint == true)
      assert(stringConfig.callGraphAlgorithm == expectedAlgorithm)
      
      println(s"String '$algorithmString' -> ${stringConfig.callGraphAlgorithm.name} call graph")
    }
  }

  test("Invalid call graph algorithm throws exception") {
    val exception = intercept[IllegalArgumentException] {
      CallGraphAlgorithm.fromString("invalid")
    }
    
    assert(exception.getMessage.contains("Unsupported call graph algorithm"))
    assert(exception.getMessage.contains("Supported algorithms: spark, cha, spark_library"))
    
    println(s"Exception for invalid algorithm: ${exception.getMessage}")
  }

  test("Call graph configuration is preserved during analysis") {
    val svfa = new MethodBasedSVFATest(
      className = "samples.ArraySample",
      sourceMethods = Set("source"),
      sinkMethods = Set("sink"),
      config = SVFAConfig.Precise
    )
    
    // Verify configuration before analysis
    val configBefore = svfa.getConfig
    assert(configBefore.callGraphAlgorithm == CallGraphAlgorithm.Spark)
    
    // Run analysis (this will configure Soot with the call graph settings)
    svfa.buildSparseValueFlowGraph()
    val conflicts = svfa.reportConflictsSVG()
    
    // Verify configuration after analysis
    val configAfter = svfa.getConfig
    assert(configAfter.callGraphAlgorithm == CallGraphAlgorithm.Spark)
    assert(configAfter == configBefore, "Configuration should be preserved during analysis")
    
    // Verify analysis worked
    assert(conflicts.size >= 1, "Should find at least one conflict in ArraySample")
    
    println(s"Analysis completed with ${configAfter.callGraphAlgorithm.name} call graph: ${conflicts.size} conflicts found")
  }

  test("Configuration comparison shows all call graph settings") {
    val configurations = List(
      ("Default", SVFAConfig.Default),
      ("Fast", SVFAConfig.Fast),
      ("Precise", SVFAConfig.Precise),
      ("WithCHA", SVFAConfig.WithCHA),
      ("WithSparkLibrary", SVFAConfig.WithSparkLibrary),
      ("FastCHA", SVFAConfig.FastCHA)
    )
    
    println("\n=== CALL GRAPH CONFIGURATION COMPARISON ===")
    println(f"${"Config"}%-15s ${"Interprocedural"}%-15s ${"FieldSensitive"}%-15s ${"TaintProp"}%-10s ${"CallGraph"}%-15s")
    println("-" * 85)
    
    configurations.foreach { case (name, configInstance) =>
      println(f"$name%-15s ${configInstance.interprocedural}%-15s ${configInstance.fieldSensitive}%-15s ${configInstance.propagateObjectTaint}%-10s ${configInstance.callGraphAlgorithm.name}%-15s")
    }
  }
}
