# SVFA Configuration Modernization

This document describes the modernization of SVFA's configuration system to support both traditional trait-based configuration and the new flexible attribute-based configuration.

## Overview

The SVFA test infrastructure has been enhanced to support multiple configuration approaches:

1. **Traditional Trait-Based Configuration** (backward compatible)
2. **New Attribute-Based Configuration** (flexible and runtime-configurable)
3. **Hybrid Approach** (mix of both)

## Key Benefits

### ✅ **100% Backward Compatibility**
- All existing test code continues to work unchanged
- No breaking changes to existing APIs
- Existing trait mixins still function as before

### ✅ **Runtime Flexibility** 
- Configure analysis settings at runtime
- Compare different configurations easily
- Support configuration from external sources (CLI, config files, etc.)

### ✅ **Performance Optimization**
- Predefined configurations for common scenarios
- Easy switching between fast and precise analysis modes
- Performance benchmarking across configurations

### ✅ **Research-Friendly**
- Compare analysis results across different settings
- Systematic evaluation of configuration impact
- Easy A/B testing of analysis approaches

## Configuration Options

### Predefined Configurations

```scala
// Fast analysis (intraprocedural, field-insensitive, no taint propagation)
SVFAConfig.Fast

// Default analysis (interprocedural, field-sensitive, with taint propagation)  
SVFAConfig.Default

// Precise analysis (interprocedural, field-sensitive, with taint propagation)
SVFAConfig.Precise

// Custom configuration
SVFAConfig(
  interprocedural = true,
  fieldSensitive = false, 
  propagateObjectTaint = true
)
```

### Configuration Parameters

- **`interprocedural`**: Enable/disable interprocedural analysis
- **`fieldSensitive`**: Enable/disable field-sensitive analysis  
- **`propagateObjectTaint`**: Enable/disable object taint propagation

## Updated Test Infrastructure

### Core Module Tests

#### Enhanced Base Classes

1. **`JSVFATest`** - Enhanced to support both approaches
   ```scala
   // Traditional approach (unchanged)
   class MyTest extends JSVFATest { ... }
   
   // New approach with custom configuration
   class MyTest extends JSVFATest {
     override def svfaConfig = SVFAConfig.Fast
   }
   ```

2. **`ConfigurableJSVFATest`** - Pure configuration-based approach
   ```scala
   // Constructor-based configuration
   class MyTest extends ConfigurableJSVFATest(SVFAConfig.Precise) { ... }
   ```

3. **`LineBasedSVFATest`** & **`MethodBasedSVFATest`** - Enhanced with config parameter
   ```scala
   // With custom configuration
   val svfa = new MethodBasedSVFATest(
     className = "samples.ArraySample",
     sourceMethods = Set("source"),
     sinkMethods = Set("sink"),
     config = SVFAConfig.Fast
   )
   ```

#### New Test Suites

1. **`ConfigurationTestSuite`** - Comprehensive configuration comparison tests
   - Performance benchmarking across configurations
   - Result comparison between different analysis modes
   - Validation of configuration application

### Securibench Module Tests

#### Enhanced Classes

1. **`SecuribenchTest`** - Now accepts configuration parameter
   ```scala
   val svfa = new SecuribenchTest(className, entryPoint, SVFAConfig.Fast)
   ```

2. **`ConfigurableSecuribenchTest`** - Configuration-aware test runner
   - Supports different SVFA configurations
   - Provides configuration-specific reporting
   - Includes predefined configuration variants

3. **`SecuribenchConfigurationComparison`** - Comprehensive comparison suite
   - Runs same tests with different configurations
   - Performance and accuracy comparison
   - Detailed analysis reporting

### TaintBench Module Tests

#### Enhanced Classes

1. **`AndroidTaintBenchTest`** - Now supports configuration parameter
   ```scala
   val test = new AndroidTaintBenchTest(apkName, SVFAConfig.Precise)
   ```

## Usage Examples

### Basic Configuration Usage

```scala
// Traditional approach (unchanged)
class TraditionalTest extends JSVFA 
    with Interprocedural 
    with FieldSensitive 
    with PropagateTaint

// New approach with predefined config
class FastTest extends ConfigurableJSVFATest(SVFAConfig.Fast)

// New approach with custom config
class CustomTest extends ConfigurableJSVFATest(
  SVFAConfig(
    interprocedural = false,
    fieldSensitive = true,
    propagateObjectTaint = false
  )
)
```

### Configuration Comparison

```scala
val configurations = Map(
  "Fast" -> SVFAConfig.Fast,
  "Default" -> SVFAConfig.Default,
  "Precise" -> SVFAConfig.Precise
)

configurations.foreach { case (name, config) =>
  val svfa = new MethodBasedSVFATest(
    className = "samples.ArraySample",
    sourceMethods = Set("source"),
    sinkMethods = Set("sink"),
    config = config
  )
  svfa.buildSparseValueFlowGraph()
  val conflicts = svfa.reportConflictsSVG()
  println(s"$name: ${conflicts.size} conflicts")
}
```

### Performance Benchmarking

```scala
val testCases = List("samples.ArraySample", "samples.CC16", "samples.StringBuilderSample")
val configs = List(SVFAConfig.Fast, SVFAConfig.Default, SVFAConfig.Precise)

testCases.foreach { className =>
  configs.foreach { config =>
    val startTime = System.currentTimeMillis()
    val svfa = new MethodBasedSVFATest(className, config = config)
    svfa.buildSparseValueFlowGraph()
    val conflicts = svfa.reportConflictsSVG()
    val endTime = System.currentTimeMillis()
    
    println(s"$className [${config.name}]: ${conflicts.size} conflicts, ${endTime - startTime}ms")
  }
}
```

## Migration Guide

### For Existing Code

**No changes required!** All existing test code continues to work exactly as before.

### For New Code

1. **Use predefined configurations** for common scenarios:
   ```scala
   // Fast analysis for performance testing
   new MethodBasedSVFATest(..., config = SVFAConfig.Fast)
   
   // Precise analysis for accuracy testing  
   new MethodBasedSVFATest(..., config = SVFAConfig.Precise)
   ```

2. **Create custom configurations** for specialized needs:
   ```scala
   val customConfig = SVFAConfig(
     interprocedural = true,
     fieldSensitive = false,
     propagateObjectTaint = true
   )
   new MethodBasedSVFATest(..., config = customConfig)
   ```

3. **Use configuration comparison** for research:
   ```scala
   class MyConfigurationComparison extends FunSuite {
     test("Compare configurations") {
       val configs = List(SVFAConfig.Fast, SVFAConfig.Default, SVFAConfig.Precise)
       configs.foreach { config =>
         // Run same test with different configurations
         val results = runTestWithConfig(config)
         println(s"Config ${config.name}: $results")
       }
     }
   }
   ```

### For Gradual Migration

You can gradually migrate existing tests by adding configuration support:

```scala
// Step 1: Keep existing traits, add ConfigurableAnalysis
class MigratingTest extends JSVFA 
    with Interprocedural      // Keep existing
    with FieldSensitive       // Keep existing  
    with ConfigurableAnalysis // Add configurability

// Step 2: Override specific settings as needed
class MigratingTest extends JSVFA 
    with Interprocedural
    with FieldSensitive
    with ConfigurableAnalysis {
  
  // Make object propagation configurable while keeping others fixed
  def configureForExperiment(propagateTaint: Boolean): Unit = {
    setObjectPropagation(if (propagateTaint) ObjectPropagationMode.Propagate else ObjectPropagationMode.DontPropagate)
  }
}

// Step 3: Eventually migrate to pure configuration-based approach
class FullyMigratedTest extends ConfigurableJSVFATest(SVFAConfig.Default)
```

## Testing the New System

### Run Configuration Tests

```bash
# Test core configuration system
sbt "project core" "testOnly br.unb.cic.soot.ConfigurationTestSuite"

# Test Securibench configuration comparison
sbt "project securibench" "testOnly br.unb.cic.securibench.suite.SecuribenchConfigurationComparison"

# Run all tests to ensure backward compatibility
sbt test
```

### Verify Backward Compatibility

```bash
# All existing tests should pass unchanged
sbt "project core" "testOnly br.unb.cic.soot.TestSuite"
sbt "project securibench" testExecutors
```

## Future Enhancements

### Planned Features

1. **CLI Configuration Support**
   ```bash
   sbt "testOnly MyTest --config=fast"
   sbt "testOnly MyTest --interprocedural=false --field-sensitive=true"
   ```

2. **Configuration File Support**
   ```yaml
   # svfa-config.yml
   analysis:
     interprocedural: true
     fieldSensitive: false
     propagateObjectTaint: true
   ```

3. **Environment Variable Support**
   ```bash
   SVFA_CONFIG=precise sbt test
   SVFA_INTERPROCEDURAL=false sbt test
   ```

4. **Configuration Profiles**
   ```scala
   SVFAConfig.profiles("research")    // Research-optimized settings
   SVFAConfig.profiles("production")  // Production-optimized settings
   SVFAConfig.profiles("debugging")   // Debug-friendly settings
   ```

## Architecture Benefits

### Clean Separation of Concerns
- Configuration logic separated from analysis logic
- Test infrastructure independent of analysis configuration
- Easy to add new configuration options

### Extensibility
- New configuration options can be added without breaking existing code
- Configuration can be extended with additional parameters
- Support for plugin-based configuration extensions

### Maintainability  
- Single source of truth for configuration options
- Consistent configuration API across all test types
- Reduced code duplication in test setup

## Conclusion

The SVFA configuration modernization provides:

- **100% backward compatibility** - no existing code needs to change
- **Flexible runtime configuration** - easy to compare different analysis settings
- **Performance optimization** - predefined configurations for common scenarios
- **Research-friendly** - systematic comparison of analysis approaches
- **Future-proof architecture** - extensible configuration system

This modernization makes SVFA more flexible, easier to use, and better suited for both research and production use cases while maintaining complete compatibility with existing code.
