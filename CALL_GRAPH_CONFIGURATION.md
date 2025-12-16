# Call Graph Configuration Guide

This document describes how to configure call graph algorithms in SVFA, including the new command-line configuration capabilities for Securibench tests.

## Overview

SVFA now supports three call graph algorithms:

- **SPARK** (default): Precise points-to analysis with context sensitivity
- **CHA**: Class Hierarchy Analysis - faster but less precise
- **SPARK_LIBRARY**: SPARK with library support for better coverage

## Module-Specific Configuration

### Core Module
- **Fixed to SPARK**: All core tests use SPARK call graph algorithm
- **Reason**: Maintains consistency and precision for core functionality tests
- **Configuration**: Cannot be changed via command line

### TaintBench Module  
- **Fixed to SPARK**: All Android tests use SPARK call graph algorithm
- **Reason**: Android analysis requires precise call graph construction
- **Configuration**: Uses AndroidSootConfiguration (separate from Java call graph)

### Securibench Module
- **Configurable**: Supports all three call graph algorithms
- **Default**: SPARK (maintains backward compatibility)
- **Configuration**: Command-line and environment variable support

## Securibench Configuration

### Command-Line Configuration

#### Basic Usage
```bash
# Use CHA call graph
sbt -Dsecuribench.callgraph=cha "project securibench" test

# Use SPARK_LIBRARY call graph
sbt -Dsecuribench.callgraph=spark_library "project securibench" test

# Use default SPARK call graph (no parameter needed)
sbt "project securibench" test
```

#### Advanced Configuration
```bash
# Combine call graph with other settings
sbt -Dsecuribench.callgraph=cha \
    -Dsecuribench.interprocedural=false \
    -Dsecuribench.fieldsensitive=true \
    "project securibench" test

# Use environment variables
export SECURIBENCH_CALLGRAPH=spark_library
export SECURIBENCH_INTERPROCEDURAL=true
sbt "project securibench" test
```

### Environment Variables

| Variable | Description | Values | Default |
|----------|-------------|---------|---------|
| `SECURIBENCH_CALLGRAPH` | Call graph algorithm | `spark`, `cha`, `spark_library` | `spark` |
| `SECURIBENCH_INTERPROCEDURAL` | Interprocedural analysis | `true`, `false` | `true` |
| `SECURIBENCH_FIELDSENSITIVE` | Field-sensitive analysis | `true`, `false` | `true` |
| `SECURIBENCH_PROPAGATETAINT` | Object taint propagation | `true`, `false` | `true` |

### System Properties

| Property | Description | Values | Default |
|----------|-------------|---------|---------|
| `securibench.callgraph` | Call graph algorithm | `spark`, `cha`, `spark_library` | `spark` |
| `securibench.interprocedural` | Interprocedural analysis | `true`, `false` | `true` |
| `securibench.fieldsensitive` | Field-sensitive analysis | `true`, `false` | `true` |
| `securibench.propagatetaint` | Object taint propagation | `true`, `false` | `true` |

## Call Graph Algorithm Details

### SPARK (Default)
- **Type**: Points-to analysis with context sensitivity
- **Precision**: High - most accurate call graph construction
- **Performance**: Slower - comprehensive analysis takes time
- **Use Case**: Default choice for accurate vulnerability detection
- **Configuration**:
  ```
  cs-demand: false (eager construction)
  string-constants: true
  simulate-natives: true
  simple-edges-bidirectional: false
  ```

### CHA (Class Hierarchy Analysis)
- **Type**: Static analysis based on class hierarchy
- **Precision**: Lower - may include infeasible call edges
- **Performance**: Faster - quick call graph construction
- **Use Case**: Performance testing, initial analysis, large codebases
- **Configuration**:
  ```
  cg.cha: on
  ```

### SPARK_LIBRARY
- **Type**: SPARK with library support
- **Precision**: High - similar to SPARK with better library coverage
- **Performance**: Slower - comprehensive analysis with library support
- **Use Case**: Analysis involving extensive library interactions
- **Configuration**:
  ```
  cg.spark: on
  library: any-subtype
  cs-demand: false
  string-constants: true
  ```

## Predefined Configurations

### Core Configurations (All Modules)
```scala
SVFAConfig.Default      // SPARK, interprocedural, field-sensitive
SVFAConfig.Fast         // SPARK, intraprocedural, field-insensitive  
SVFAConfig.Precise      // SPARK, interprocedural, field-sensitive
```

### Call Graph Specific Configurations (Securibench)
```scala
SVFAConfig.WithCHA           // CHA, interprocedural, field-sensitive
SVFAConfig.WithSparkLibrary  // SPARK_LIBRARY, interprocedural, field-sensitive
SVFAConfig.FastCHA          // CHA, intraprocedural, field-insensitive
```

## Usage Examples

### Performance Comparison
```bash
# Compare call graph algorithms for performance
echo "=== SPARK (Default) ==="
time sbt "project securibench" "testOnly br.unb.cic.securibench.suite.SecuribenchInterExecutor"

echo "=== CHA (Faster) ==="
time sbt -Dsecuribench.callgraph=cha "project securibench" "testOnly br.unb.cic.securibench.suite.SecuribenchInterExecutor"

echo "=== SPARK_LIBRARY (Comprehensive) ==="
time sbt -Dsecuribench.callgraph=spark_library "project securibench" "testOnly br.unb.cic.securibench.suite.SecuribenchInterExecutor"
```

### Accuracy Comparison
```bash
# Compare call graph algorithms for accuracy
echo "=== Testing with SPARK ==="
sbt "project securibench" "testOnly br.unb.cic.securibench.suite.SecuribenchInterMetrics"

echo "=== Testing with CHA ==="
sbt -Dsecuribench.callgraph=cha "project securibench" "testOnly br.unb.cic.securibench.suite.SecuribenchInterMetrics"

echo "=== Testing with SPARK_LIBRARY ==="
sbt -Dsecuribench.callgraph=spark_library "project securibench" "testOnly br.unb.cic.securibench.suite.SecuribenchInterMetrics"
```

### Script Integration
```bash
#!/bin/bash
# run-securibench-callgraph-comparison.sh

ALGORITHMS=("spark" "cha" "spark_library")
SUITE="inter"

for algorithm in "${ALGORITHMS[@]}"; do
    echo "=== Running $SUITE tests with $algorithm call graph ==="
    sbt -Dsecuribench.callgraph=$algorithm \
        "project securibench" \
        "testOnly br.unb.cic.securibench.suite.Securibench${SUITE^}Executor"
    
    echo "=== Computing metrics for $algorithm ==="
    sbt -Dsecuribench.callgraph=$algorithm \
        "project securibench" \
        "testOnly br.unb.cic.securibench.suite.Securibench${SUITE^}Metrics"
done
```

## Programmatic Configuration

### In Test Code
```scala
// Use specific call graph algorithm
val chaConfig = SVFAConfig.WithCHA
val test = new SecuribenchTest("Inter1", "doGet", chaConfig)

// Create custom configuration
val customConfig = SVFAConfig(
  interprocedural = true,
  fieldSensitive = false,
  propagateObjectTaint = true,
  callGraphAlgorithm = CallGraphAlgorithm.SparkLibrary
)
val customTest = new SecuribenchTest("Inter1", "doGet", customConfig)
```

### Configuration Validation
```scala
// Check current configuration
val config = SecuribenchConfig.getConfiguration()
SecuribenchConfig.printConfiguration(config)

// Output:
// === SECURIBENCH CONFIGURATION ===
// Call Graph Algorithm: CHA
// Interprocedural: true
// Field Sensitive: true
// Propagate Object Taint: true
// ===================================
```

## Troubleshooting

### Common Issues

#### Invalid Call Graph Algorithm
```
Error: Unsupported call graph algorithm: invalid
Solution: Use one of: spark, cha, spark_library
```

#### Configuration Not Applied
```
Issue: Tests still use SPARK despite setting CHA
Solution: Check system property spelling and restart SBT
```

#### Performance Issues
```
Issue: CHA is slower than expected
Solution: CHA should be faster than SPARK; check for configuration conflicts
```

### Debug Configuration
```bash
# Print current configuration
sbt -Dsecuribench.callgraph=cha \
    "project securibench" \
    "runMain br.unb.cic.securibench.SecuribenchConfig.printUsage"
```

## Migration Guide

### From Fixed SPARK to Configurable
```scala
// Before (fixed SPARK)
val test = new SecuribenchTest("Inter1", "doGet")

// After (configurable, defaults to command-line setting)
val test = new SecuribenchTest("Inter1", "doGet") // Uses SecuribenchConfig.getConfiguration()

// After (explicit configuration)
val test = new SecuribenchTest("Inter1", "doGet", SVFAConfig.WithCHA)
```

### Backward Compatibility
- **All existing code works unchanged**
- **Default behavior is identical** (SPARK call graph)
- **No breaking changes** to APIs
- **Command-line configuration is optional**

## Performance Guidelines

### When to Use Each Algorithm

#### SPARK (Default)
- **Use for**: Production analysis, accuracy-critical tests, research
- **Avoid for**: Large codebases with time constraints, initial exploration

#### CHA
- **Use for**: Performance testing, large codebases, initial analysis
- **Avoid for**: Precision-critical analysis, small codebases where SPARK is fast enough

#### SPARK_LIBRARY  
- **Use for**: Library-heavy applications, comprehensive coverage needs
- **Avoid for**: Simple applications, performance-critical scenarios

### Expected Performance Impact
- **CHA**: ~50-80% faster than SPARK, ~10-30% less precise
- **SPARK**: Baseline performance and precision
- **SPARK_LIBRARY**: ~10-20% slower than SPARK, ~5-10% more comprehensive

## Future Enhancements

### Planned Features
- **RTA (Rapid Type Analysis)**: Additional fast call graph algorithm
- **Configuration profiles**: Named configuration sets for common scenarios
- **Automatic algorithm selection**: Based on codebase characteristics
- **Performance benchmarking**: Built-in timing and comparison tools

### Extension Points
```scala
// Future algorithms can be added easily
object CallGraphAlgorithm {
  case object RTA extends CallGraphAlgorithm { ... }        // Future
  case object FlowSensitive extends CallGraphAlgorithm { ... } // Future
}
```

This flexible architecture makes it easy to add new call graph algorithms and configuration options as SVFA evolves.
