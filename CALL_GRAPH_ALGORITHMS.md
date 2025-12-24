# Call Graph Algorithms in SVFA

This document describes the call graph construction algorithms supported by SVFA and their characteristics.

## 🎯 Overview

SVFA supports five different call graph construction algorithms, each with different precision and performance trade-offs:

| Algorithm | Speed | Precision | Memory Usage | Best Use Case |
|-----------|-------|-----------|--------------|---------------|
| **CHA** | ⚡⚡⚡⚡⚡ | ⭐ | 💾 | Quick prototyping, large codebases |
| **RTA** | ⚡⚡⚡⚡ | ⭐⭐ | 💾💾 | Development, moderate precision needed |
| **VTA** | ⚡⚡⚡ | ⭐⭐⭐ | 💾💾💾 | Balanced analysis, production use |
| **SPARK** | ⚡⚡ | ⭐⭐⭐⭐ | 💾💾💾💾 | Research, high precision required |
| **SPARK_LIBRARY** | ⚡ | ⭐⭐⭐⭐⭐ | 💾💾💾💾💾 | Comprehensive analysis with libraries |

## 📋 Algorithm Details

### 1. CHA (Class Hierarchy Analysis)
- **Implementation**: Native Soot CHA
- **Configuration**: `cg.cha:on`
- **Characteristics**:
  - Fastest algorithm
  - Uses only class hierarchy information
  - No flow sensitivity
  - High over-approximation (many false positives)
- **When to use**: Initial analysis, very large codebases, performance-critical scenarios

### 2. RTA (Rapid Type Analysis)
- **Implementation**: SPARK with `rta:true`
- **Configuration**: `cg.spark:on`, `rta:true`
- **Characteristics**:
  - Fast analysis with moderate precision
  - Uses single points-to set for all variables
  - Considers instantiated types only
  - Better than CHA, faster than full SPARK
- **When to use**: Development phase, moderate precision requirements

### 3. VTA (Variable Type Analysis)
- **Implementation**: SPARK with `vta:true`
- **Configuration**: `cg.spark:on`, `vta:true`
- **Characteristics**:
  - Balanced speed and precision
  - Field-based analysis
  - Type-based points-to sets
  - Good compromise between RTA and SPARK
- **When to use**: Production analysis, balanced requirements

### 4. SPARK (Standard)
- **Implementation**: Full SPARK points-to analysis
- **Configuration**: `cg.spark:on` with full options
- **Characteristics**:
  - High precision analysis
  - Context-sensitive options available
  - Flow-sensitive analysis
  - Comprehensive but slower
- **When to use**: Research, high-precision requirements, final analysis

### 5. SPARK_LIBRARY
- **Implementation**: SPARK with library support
- **Configuration**: `cg.spark:on`, `library:any-subtype`
- **Characteristics**:
  - Most comprehensive analysis
  - Includes library code analysis
  - Highest precision and recall
  - Slowest and most memory-intensive
- **When to use**: Complete system analysis, library interaction analysis

## 🚀 Usage Examples

### Command Line Usage

```bash
# Execute tests with different call graph algorithms
./scripts/run-securibench-tests.sh inter cha          # CHA - fastest
./scripts/run-securibench-tests.sh inter rta          # RTA - fast, moderate precision
./scripts/run-securibench-tests.sh inter vta          # VTA - balanced
./scripts/run-securibench-tests.sh inter spark        # SPARK - high precision
./scripts/run-securibench-tests.sh inter spark_library # SPARK_LIBRARY - comprehensive

# Compute metrics with matching algorithms
./scripts/compute-securibench-metrics.sh inter cha
./scripts/compute-securibench-metrics.sh inter rta
./scripts/compute-securibench-metrics.sh inter vta
./scripts/compute-securibench-metrics.sh inter spark
./scripts/compute-securibench-metrics.sh inter spark_library
```

### Programmatic Usage

```scala
import br.unb.cic.soot.svfa.jimple.{CallGraphAlgorithm, SVFAConfig}

// Create configurations for different algorithms
val chaConfig = SVFAConfig.Default.withCallGraph(CallGraphAlgorithm.CHA)
val rtaConfig = SVFAConfig.Default.withCallGraph(CallGraphAlgorithm.RTA)
val vtaConfig = SVFAConfig.Default.withCallGraph(CallGraphAlgorithm.VTA)
val sparkConfig = SVFAConfig.Default.withCallGraph(CallGraphAlgorithm.Spark)
val libraryConfig = SVFAConfig.Default.withCallGraph(CallGraphAlgorithm.SparkLibrary)

// Use in test classes
class MyTest extends JSVFATest {
  override def svfaConfig: SVFAConfig = SVFAConfig.Default.withCallGraph(CallGraphAlgorithm.RTA)
}
```

## 📊 Performance Characteristics

### Typical Execution Times (Inter Test Suite)
- **CHA**: ~30 seconds
- **RTA**: ~45 seconds  
- **VTA**: ~60 seconds
- **SPARK**: ~90 seconds
- **SPARK_LIBRARY**: ~120+ seconds

*Note: Times vary significantly based on code size and complexity*

### Memory Usage (Approximate)
- **CHA**: 512MB - 1GB
- **RTA**: 1GB - 2GB
- **VTA**: 2GB - 4GB  
- **SPARK**: 4GB - 8GB
- **SPARK_LIBRARY**: 8GB+ 

## 🔬 Research Considerations

### Precision vs. Performance Trade-offs

1. **For Development/Debugging**: Use RTA or VTA for faster iteration
2. **For Performance Evaluation**: Compare multiple algorithms to understand precision impact
3. **For Research Publications**: Use SPARK or SPARK_LIBRARY for highest precision
4. **For Large-Scale Analysis**: Consider CHA or RTA for feasibility

### Algorithm Selection Guidelines

```
Choose CHA when:
✓ Analyzing very large codebases (>100K LOC)
✓ Need quick feedback during development
✓ Memory is severely constrained
✓ False positives are acceptable

Choose RTA when:
✓ Need moderate precision with good performance
✓ Analyzing medium-sized applications
✓ Development phase analysis
✓ Want better precision than CHA

Choose VTA when:
✓ Need balanced precision and performance
✓ Production-quality analysis required
✓ Field-sensitive analysis important
✓ Good compromise solution needed

Choose SPARK when:
✓ High precision is critical
✓ Research or final analysis phase
✓ Context sensitivity may be needed
✓ Performance is secondary to accuracy

Choose SPARK_LIBRARY when:
✓ Need comprehensive library analysis
✓ Analyzing framework-heavy applications
✓ Maximum precision and recall required
✓ Resources are not constrained
```

## 🛠️ Technical Implementation

### Soot Configuration Details

Each algorithm configures Soot's call graph phase differently:

```scala
// CHA Configuration
Options.v().setPhaseOption("cg.cha", "on")

// RTA Configuration  
Options.v().setPhaseOption("cg.spark", "on")
Options.v().setPhaseOption("cg.spark", "rta:true")

// VTA Configuration
Options.v().setPhaseOption("cg.spark", "on") 
Options.v().setPhaseOption("cg.spark", "vta:true")

// SPARK Configuration
Options.v().setPhaseOption("cg.spark", "on")
Options.v().setPhaseOption("cg.spark", "cs-demand:false")
Options.v().setPhaseOption("cg.spark", "string-constants:true")

// SPARK_LIBRARY Configuration
Options.v().setPhaseOption("cg.spark", "on")
Options.v().setPhaseOption("cg", "library:any-subtype")
```

### Output File Naming

Results are automatically tagged with the algorithm name:
- `securibench_metrics_cha_20251216_083045.csv`
- `securibench_metrics_rta_20251216_083045.csv`
- `securibench_metrics_vta_20251216_083045.csv`
- `securibench_metrics_spark_20251216_083045.csv`
- `securibench_metrics_spark_library_20251216_083045.csv`

## 📚 References

1. [Soot Framework Documentation](https://soot-oss.github.io/soot/)
2. [SPARK: A Flexible Points-to Analysis Framework](https://plg.uwaterloo.ca/~olhotak/pubs/cc05.pdf)
3. [Class Hierarchy Analysis](https://dl.acm.org/doi/10.1145/236337.236371)
4. [Rapid Type Analysis for C++](https://dl.acm.org/doi/10.1145/237721.237727)

---

For more information on SVFA usage, see [USAGE_SCRIPTS.md](USAGE_SCRIPTS.md).
