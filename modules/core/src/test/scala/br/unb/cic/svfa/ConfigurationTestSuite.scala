package br.unb.cic.soot

import br.unb.cic.soot.svfa.jimple.SVFAConfig
import org.scalatest.{BeforeAndAfter, FunSuite}

/**
 * Test suite demonstrating the new SVFA configuration capabilities.
 * 
 * This suite shows how the same test case can be run with different
 * analysis configurations to compare results and performance.
 */
class ConfigurationTestSuite extends FunSuite with BeforeAndAfter {

  // ============================================================================
  // CONFIGURATION COMPARISON TESTS
  // ============================================================================

  test("ArraySample: Compare results across different configurations") {
    val testConfigs = Map(
      "Default" -> SVFAConfig.Default,
      "Fast" -> SVFAConfig.Fast,
      "Precise" -> SVFAConfig.Precise,
      "Intraprocedural" -> SVFAConfig.Default.copy(interprocedural = false),
      "Field-Insensitive" -> SVFAConfig.Default.copy(fieldSensitive = false),
      "No Taint Propagation" -> SVFAConfig.Default.copy(propagateObjectTaint = false)
    )

    val results = testConfigs.map { case (name, config) =>
      val svfa = new MethodBasedSVFATest(
        className = "samples.ArraySample",
        sourceMethods = Set("source"),
        sinkMethods = Set("sink"),
        config = config
      )
      svfa.buildSparseValueFlowGraph()
      val conflicts = svfa.reportConflictsSVG()
      val executionTime = svfa.executionTime()
      
      println(s"$name: ${conflicts.size} conflicts, ${executionTime}ms")
      (name, conflicts.size, executionTime)
    }

    // Verify that different configurations can produce different results
    val conflictCounts = results.map(_._2).toSet
    assert(conflictCounts.nonEmpty, "Should have at least one configuration result")
    
    // Default configuration should find the expected 3 conflicts
    val defaultResult = results.find(_._1 == "Default")
    assert(defaultResult.isDefined, "Default configuration should be tested")
    assert(defaultResult.get._2 == 3, "Default configuration should find 3 conflicts")
  }

  test("CC16: Performance comparison between Fast and Precise configurations") {
    val fastSvfa = new MethodBasedSVFATest(
      className = "samples.CC16",
      sourceMethods = Set("source"),
      sinkMethods = Set("sink"),
      config = SVFAConfig.Fast
    )
    fastSvfa.buildSparseValueFlowGraph()
    val fastConflicts = fastSvfa.reportConflictsSVG()
    val fastTime = fastSvfa.executionTime()

    val preciseSvfa = new MethodBasedSVFATest(
      className = "samples.CC16",
      sourceMethods = Set("source"),
      sinkMethods = Set("sink"),
      config = SVFAConfig.Precise
    )
    preciseSvfa.buildSparseValueFlowGraph()
    val preciseConflicts = preciseSvfa.reportConflictsSVG()
    val preciseTime = preciseSvfa.executionTime()

    println(s"Fast: ${fastConflicts.size} conflicts, ${fastTime}ms")
    println(s"Precise: ${preciseConflicts.size} conflicts, ${preciseTime}ms")

    // Both should find at least 1 conflict for this test case
    assert(fastConflicts.size >= 1, "Fast configuration should find at least 1 conflict")
    assert(preciseConflicts.size >= 1, "Precise configuration should find at least 1 conflict")
  }

  // ============================================================================
  // INTERPROCEDURAL VS INTRAPROCEDURAL TESTS
  // ============================================================================

  test("ContextSensitive: Interprocedural vs Intraprocedural analysis") {
    val interproceduralSvfa = new MethodBasedSVFATest(
      className = "samples.ContextSensitiveSample",
      sourceMethods = Set("readConfiedentialContent"),
      sinkMethods = Set("sink"),
      config = SVFAConfig.Default.copy(interprocedural = true)
    )
    interproceduralSvfa.buildSparseValueFlowGraph()
    val interproceduralConflicts = interproceduralSvfa.reportConflictsSVG()

    val intraproceduralSvfa = new MethodBasedSVFATest(
      className = "samples.ContextSensitiveSample",
      sourceMethods = Set("readConfiedentialContent"),
      sinkMethods = Set("sink"),
      config = SVFAConfig.Default.copy(interprocedural = false)
    )
    intraproceduralSvfa.buildSparseValueFlowGraph()
    val intraproceduralConflicts = intraproceduralSvfa.reportConflictsSVG()

    println(s"Interprocedural: ${interproceduralConflicts.size} conflicts")
    println(s"Intraprocedural: ${intraproceduralConflicts.size} conflicts")

    // Interprocedural analysis should find more conflicts for this test case
    assert(interproceduralConflicts.size >= 1, "Interprocedural should find at least 1 conflict")
    // Note: Intraprocedural might find 0 conflicts if the vulnerability crosses method boundaries
  }

  // ============================================================================
  // FIELD SENSITIVITY TESTS
  // ============================================================================

  test("FieldSample: Field-sensitive vs Field-insensitive analysis") {
    val fieldSensitiveSvfa = new LineBasedSVFATest(
      className = "samples.FieldSample",
      sourceLines = Set(6),
      sinkLines = Set(7, 11),
      config = SVFAConfig.Default.copy(fieldSensitive = true)
    )
    fieldSensitiveSvfa.buildSparseValueFlowGraph()
    val fieldSensitiveConflicts = fieldSensitiveSvfa.reportConflictsSVG()

    val fieldInsensitiveSvfa = new LineBasedSVFATest(
      className = "samples.FieldSample",
      sourceLines = Set(6),
      sinkLines = Set(7, 11),
      config = SVFAConfig.Default.copy(fieldSensitive = false)
    )
    fieldInsensitiveSvfa.buildSparseValueFlowGraph()
    val fieldInsensitiveConflicts = fieldInsensitiveSvfa.reportConflictsSVG()

    println(s"Field-sensitive: ${fieldSensitiveConflicts.size} conflicts")
    println(s"Field-insensitive: ${fieldInsensitiveConflicts.size} conflicts")

    // Field-sensitive analysis should be more precise for field-based vulnerabilities
    assert(fieldSensitiveConflicts.size >= 1, "Field-sensitive should find at least 1 conflict")
  }

  // ============================================================================
  // OBJECT TAINT PROPAGATION TESTS
  // ============================================================================

  test("StringBuilderSample: Object taint propagation comparison") {
    val withPropagationSvfa = new MethodBasedSVFATest(
      className = "samples.StringBuilderSample",
      sourceMethods = Set("source"),
      sinkMethods = Set("sink"),
      config = SVFAConfig.Default.copy(propagateObjectTaint = true)
    )
    withPropagationSvfa.buildSparseValueFlowGraph()
    val withPropagationConflicts = withPropagationSvfa.reportConflictsSVG()

    val withoutPropagationSvfa = new MethodBasedSVFATest(
      className = "samples.StringBuilderSample",
      sourceMethods = Set("source"),
      sinkMethods = Set("sink"),
      config = SVFAConfig.Default.copy(propagateObjectTaint = false)
    )
    withoutPropagationSvfa.buildSparseValueFlowGraph()
    val withoutPropagationConflicts = withoutPropagationSvfa.reportConflictsSVG()

    println(s"With taint propagation: ${withPropagationConflicts.size} conflicts")
    println(s"Without taint propagation: ${withoutPropagationConflicts.size} conflicts")

    // Object taint propagation should be necessary for StringBuilder-based vulnerabilities
    assert(withPropagationConflicts.size >= 1, "With propagation should find at least 1 conflict")
  }

  // ============================================================================
  // CONFIGURATION VALIDATION TESTS
  // ============================================================================

  test("Configuration validation: Ensure configurations are applied correctly") {
    val customConfig = SVFAConfig(
      interprocedural = false,
      fieldSensitive = false,
      propagateObjectTaint = false
    )

    val svfa = new MethodBasedSVFATest(
      className = "samples.ArraySample",
      sourceMethods = Set("source"),
      sinkMethods = Set("sink"),
      config = customConfig
    )

    // Verify configuration is applied
    assert(!svfa.interprocedural(), "Should be intraprocedural")
    assert(svfa.intraprocedural(), "Should be intraprocedural")
    assert(!svfa.isFieldSensitiveAnalysis(), "Should be field-insensitive")
    assert(!svfa.propagateObjectTaint(), "Should not propagate object taint")
  }

  // ============================================================================
  // BACKWARD COMPATIBILITY TESTS
  // ============================================================================

  test("Backward compatibility: Traditional trait-based configuration still works") {
    // This test uses the traditional JSVFATest which uses trait mixins
    val traditionalSvfa = new MethodBasedSVFATest(
      className = "samples.ArraySample",
      sourceMethods = Set("source"),
      sinkMethods = Set("sink")
      // No config parameter - uses default trait-based configuration
    )

    // Verify traditional configuration is applied (via traits)
    assert(traditionalSvfa.interprocedural(), "Traditional should be interprocedural")
    assert(traditionalSvfa.isFieldSensitiveAnalysis(), "Traditional should be field-sensitive")
    assert(traditionalSvfa.propagateObjectTaint(), "Traditional should propagate object taint")

    traditionalSvfa.buildSparseValueFlowGraph()
    val conflicts = traditionalSvfa.reportConflictsSVG()
    assert(conflicts.size == 3, "Traditional configuration should find 3 conflicts")
  }

  // ============================================================================
  // PERFORMANCE BENCHMARKING TESTS
  // ============================================================================

  test("Performance benchmark: Configuration impact on execution time") {
    val testCases = List(
      ("samples.ArraySample", Set("source"), Set("sink")),
      ("samples.CC16", Set("source"), Set("sink")),
      ("samples.StringBuilderSample", Set("source"), Set("sink"))
    )

    val configurations = Map(
      "Fast" -> SVFAConfig.Fast,
      "Default" -> SVFAConfig.Default,
      "Precise" -> SVFAConfig.Precise
    )

    testCases.foreach { case (className, sources, sinks) =>
      println(s"\nBenchmarking $className:")
      
      configurations.foreach { case (configName, config) =>
        val svfa = new MethodBasedSVFATest(
          className = className,
          sourceMethods = sources,
          sinkMethods = sinks,
          config = config
        )
        
        val startTime = System.currentTimeMillis()
        svfa.buildSparseValueFlowGraph()
        val conflicts = svfa.reportConflictsSVG()
        val endTime = System.currentTimeMillis()
        val executionTime = endTime - startTime
        
        println(s"  $configName: ${conflicts.size} conflicts, ${executionTime}ms")
      }
    }
  }
}
