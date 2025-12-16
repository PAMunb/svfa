# Securibench Separated Testing

This document explains the new **two-phase testing approach** for Securibench tests that separates test execution from metrics computation.

## 🎯 Why Separate Testing?

**Benefits:**
- ✅ **Faster iteration**: Compute metrics multiple times without re-running expensive tests
- ✅ **Better debugging**: Inspect individual test results and analyze failures
- ✅ **Flexible analysis**: Compare results across different runs or configurations
- ✅ **Persistent results**: Test results are saved to disk for later analysis
- ✅ **Parallel execution**: Run different test suites independently

## 📋 Two-Phase Architecture

### **Phase 1: Test Execution** 
Runs SVFA analysis and saves results to JSON files.

### **Phase 2: Metrics Computation**
Loads saved results and computes accuracy metrics (TP, FP, FN, Precision, Recall, F-score).

## 🚀 Usage

### **Option 1: Run Individual Phases**

#### Phase 1: Execute Tests
```bash
# Execute Inter tests
sbt "project securibench" "testOnly *SecuribenchInterExecutor"

# Execute Basic tests  
sbt "project securibench" "testOnly *SecuribenchBasicExecutor"

# Execute other test suites...
```

#### Phase 2: Compute Metrics
```bash
# Compute metrics for Inter tests
sbt "project securibench" "testOnly *SecuribenchInterMetrics"

# Compute metrics for Basic tests
sbt "project securibench" "testOnly *SecuribenchBasicMetrics"
```

### **Option 2: Use Comprehensive Script**

#### **Interactive Menu** (No arguments)
```bash
./scripts/run-securibench-separated.sh
```
Shows a menu to select which test suite to run.

#### **Command Line Arguments**
```bash
# Run specific test suite
./scripts/run-securibench-separated.sh inter
./scripts/run-securibench-separated.sh basic

# Run all test suites
./scripts/run-securibench-separated.sh all
```

#### **Individual Suite Scripts**
```bash
# Quick scripts for specific suites
./scripts/run-inter-separated.sh
./scripts/run-basic-separated.sh
```

### **Option 3: Traditional Combined Approach** (Still Available)
```bash
# Original approach - runs both phases together
sbt "project securibench" "testOnly *SecuribenchInterTest"
```

## 📁 File Structure

### **Test Results Storage**
Results are saved in: `target/test-results/{package-name}/`

Example for Inter tests:
```
target/test-results/securibench/micro/inter/
├── Inter1.json
├── Inter2.json  
├── Inter3.json
└── ...
```

### **JSON Result Format**
```json
{
  "testName": "Inter1",
  "packageName": "securibench.micro.inter", 
  "className": "securibench.micro.inter.Inter1",
  "expectedVulnerabilities": 1,
  "foundVulnerabilities": 1,
  "executionTimeMs": 1250,
  "conflicts": ["conflict details..."],
  "timestamp": 1703548800000
}
```

## 🏗️ Implementation Classes

### **Base Classes**
- `SecuribenchTestExecutor` - Phase 1 base class
- `SecuribenchMetricsComputer` - Phase 2 base class  
- `TestResultStorage` - JSON serialization utilities

### **Concrete Implementations**
| Test Suite | Executor | Metrics Computer |
|------------|----------|------------------|
| Inter | `SecuribenchInterExecutor` | `SecuribenchInterMetrics` |
| Basic | `SecuribenchBasicExecutor` | `SecuribenchBasicMetrics` |
| Session | `SecuribenchSessionExecutor` | `SecuribenchSessionMetrics` |
| ... | ... | ... |

## 📊 Sample Output

### **Phase 1: Execution**
```
=== PHASE 1: EXECUTING TESTS FOR securibench.micro.inter ===
Executing: Inter1
  Inter1: 1/1 conflicts - ✅ PASS (1250ms)
Executing: Inter2  
  Inter2: 2/2 conflicts - ✅ PASS (890ms)
...
=== EXECUTION COMPLETE: 14 tests executed ===
Results saved to: target/test-results/securibench/micro/inter
```

### **Phase 2: Metrics**
```
=== PHASE 2: COMPUTING METRICS FOR securibench.micro.inter ===
Loaded 14 test results

- **inter** - failed: 5, passed: 9 of 14 tests - (64.3%)
|      Test      | Found | Expected | Status | TP | FP | FN | Precision | Recall | F-score |
|:--------------:|:-----:|:--------:|:------:|:--:|:--:|:---|:---------:|:------:|:-------:|
| Inter1         |     1 |        1 |      ✅ |  1 |  0 |   0 |      1.00 |   1.00 |    1.00 |
| Inter2         |     2 |        2 |      ✅ |  2 |  0 |   0 |      1.00 |   1.00 |    1.00 |
...
| TOTAL          |    15 |       18 |   9/14 | 15 |  0 |   3 |      1.00 |   0.83 |    0.91 |

=== OVERALL STATISTICS ===
Tests: 14 total, 9 passed, 5 failed
Success Rate: 64.3%
Vulnerabilities: 15 found, 18 expected
Execution Time: 12450ms total, 889.3ms average

Slowest Tests:
  Inter9: 2100ms
  Inter4: 1800ms  
  Inter12: 1650ms
```

## 🔧 Advanced Usage

### **Re-run Metrics Only**
After fixing analysis issues, re-compute metrics without re-running tests:
```bash
sbt "project securibench" "testOnly *SecuribenchInterMetrics"
```

### **Clear Results**
```scala
// In Scala code
TestResultStorage.clearResults("securibench.micro.inter")
```

### **Inspect Individual Results**
```bash
# View specific test result
cat target/test-results/securibench/micro/inter/Inter1.json | jq .
```

### **Compare Runs**
Save results from different configurations and compare:
```bash
# Run with different Spark settings
cp -r target/test-results target/test-results-spark-cs-demand-false

# Change configuration and run again  
# Compare results...
```

## 🎛️ Configuration

### **Add New Test Suite**
1. Create executor: `SecuribenchXxxExecutor extends SecuribenchTestExecutor`
2. Create metrics: `SecuribenchXxxMetrics extends SecuribenchMetricsComputer`  
3. Implement `basePackage()` and `entryPointMethod()`

### **Customize Metrics**
Override methods in `SecuribenchMetricsComputer`:
- `printSummaryTable()` - Customize table format
- `printOverallStatistics()` - Add custom statistics
- Test assertion logic in the `test()` method

## 🔍 Troubleshooting

### **No Results Found**
```
❌ No test results found for securibench.micro.inter
   Please run the test executor first!
```
**Solution**: Run the executor phase first: `testOnly *SecuribenchInterExecutor`

### **JSON Parsing Errors**
**Solution**: Clear corrupted results: `TestResultStorage.clearResults(packageName)`

### **Missing Dependencies**
Ensure Jackson dependencies are in `build.sbt`:
```scala
"com.fasterxml.jackson.core" % "jackson-databind" % "2.13.0",
"com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.13.0"
```

## 📋 Quick Reference

### **Available Scripts**
| Script | Purpose | Usage |
|--------|---------|-------|
| `run-securibench-separated.sh` | Main script with menu/args | `./scripts/run-securibench-separated.sh [inter\|basic\|all]` |
| `run-inter-separated.sh` | Inter tests only | `./scripts/run-inter-separated.sh` |
| `run-basic-separated.sh` | Basic tests only | `./scripts/run-basic-separated.sh` |

### **Available Test Suites**
| Suite | Package | Executor | Metrics | Status |
|-------|---------|----------|---------|--------|
| Inter | `securibench.micro.inter` | `SecuribenchInterExecutor` | `SecuribenchInterMetrics` | ✅ Ready |
| Basic | `securibench.micro.basic` | `SecuribenchBasicExecutor` | `SecuribenchBasicMetrics` | ✅ Ready |
| Session | `securibench.micro.session` | `SecuribenchSessionExecutor` | `SecuribenchSessionMetrics` | 🚧 Can be added |
| Aliasing | `securibench.micro.aliasing` | `SecuribenchAliasingExecutor` | `SecuribenchAliasingMetrics` | 🚧 Can be added |

### **Recent Results Summary**
- **Inter Tests**: 9/14 passing (64.3%) - Interprocedural analysis working well
- **Basic Tests**: 38/42 passing (90.5%) - Excellent coverage for basic taint flows
- **Performance**: ~400ms average per test, good for iterative development

This separated approach provides much more flexibility for analyzing and debugging Securibench test results! 🚀
