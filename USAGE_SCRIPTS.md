# SVFA Testing Scripts Usage

## 🎯 Two-Script Approach

### **Script 1: Execute Securibench Tests** 
`./scripts/run-securibench-tests.sh [suite|clean|--help]`

**Purpose**: Run SVFA analysis on specified test suite(s) and save results to disk (no metrics computation).

**What it does**:
- Executes specific test suite or all 12 Securibench test suites (Inter, Basic, Aliasing, Arrays, Collections, Datastructures, Factories, Pred, Reflection, Sanitizers, Session, StrongUpdates)
- Saves results as JSON files in `target/test-results/`
- Shows execution summary and timing
- **Does NOT compute accuracy metrics**

**Get help**: Run `./scripts/run-securibench-tests.sh --help` for detailed usage information.

**Usage**:
```bash
# Execute all tests
./scripts/run-securibench-tests.sh
./scripts/run-securibench-tests.sh all

# Execute specific test suite
./scripts/run-securibench-tests.sh inter
./scripts/run-securibench-tests.sh basic
./scripts/run-securibench-tests.sh session
# ... (all 12 suites supported)

# Clean previous data and execute all tests
./scripts/run-securibench-tests.sh clean
```

**Output**:
```
=== EXECUTING ALL SECURIBENCH TESTS ===
🚀 Starting test execution for all suites...

🔄 Executing Inter tests (securibench.micro.inter)...
=== PHASE 1: EXECUTING TESTS FOR securibench.micro.inter ===
Executing: Inter1
  Inter1: 1/1 conflicts - ✅ PASS (204ms)
Executing: Inter2
  Inter2: 2/2 conflicts - ✅ PASS (414ms)
...
=== EXECUTION COMPLETE: 14 tests executed ===
Results: 9 passed, 5 failed

📊 EXECUTION SUMMARY:
   Total tests: 14
   Passed: 9
   Failed: 5
   Success rate: 64%

ℹ️  Note: SBT 'success' indicates technical execution completed.
   Individual test results show SVFA analysis accuracy.

✅ Inter test execution completed (technical success)
   📊 14 tests executed in 12s
   ℹ️  Individual test results show SVFA analysis accuracy

🏁 ALL TEST EXECUTION COMPLETED
✅ All test suites executed successfully!
📊 Total: 56 tests executed in 33s
```

---

### **Script 2: Compute Securibench Metrics**
`./scripts/compute-securibench-metrics.sh [suite|--help]`

**Purpose**: Compute accuracy metrics for Securibench test suites with **automatic test execution**.

**Default behavior**: Processes **all suites** and creates CSV + summary reports.

**Get help**: Run `./scripts/compute-securibench-metrics.sh --help` for detailed usage information.

**What it does**:
- **Auto-executes missing tests** (no need to run tests separately!)
- Loads saved JSON test results
- Computes TP, FP, FN, Precision, Recall, F-score
- Creates timestamped CSV file with detailed metrics
- Creates summary report with overall statistics
- Displays summary on console

**Usage**:
```bash
# Process all suites (default)
./scripts/compute-securibench-metrics.sh
./scripts/compute-securibench-metrics.sh all

# Process specific suite
./scripts/compute-securibench-metrics.sh inter
./scripts/compute-securibench-metrics.sh basic
./scripts/compute-securibench-metrics.sh session
./scripts/compute-securibench-metrics.sh aliasing
# ... (all 12 suites supported)

# Clean all previous test data and metrics
./scripts/compute-securibench-metrics.sh clean
```

**Output Files**:
- `target/metrics/securibench_metrics_YYYYMMDD_HHMMSS.csv` - Detailed CSV data
- `target/metrics/securibench_summary_YYYYMMDD_HHMMSS.txt` - Summary report

**CSV Format**:
```csv
Suite,Test,Found,Expected,Status,TP,FP,FN,Precision,Recall,F-score,Execution_Time_ms
inter,Inter1,1,1,PASS,1,0,0,1.000,1.000,1.000,196
inter,Inter2,2,2,PASS,2,0,0,1.000,1.000,1.000,303
basic,Basic1,1,1,PASS,1,0,0,1.000,1.000,1.000,203
...
```

---

## 🚀 Typical Workflow

### **Option A: One-Step Approach** (Recommended)
```bash
./scripts/compute-securibench-metrics.sh
```
*Auto-executes missing tests and generates metrics - that's it!*

### **Option B: Two-Step Approach** (For batch execution)
```bash
# Step 1: Execute all tests at once (optional)
./scripts/run-securibench-tests.sh

# Step 2: Compute metrics (fast, uses cached results)
./scripts/compute-securibench-metrics.sh
```

### **Step 3: Analyze Results**
- Open the CSV file in Excel, Google Sheets, or analysis tools
- Use the summary report for quick overview
- Re-run metrics computation anytime (uses cached results when available)

---

## 📊 Sample Results

### **Console Output**:
```
📊 METRICS SUMMARY:

--- Inter Test Suite ---
Tests: 14 total, 9 passed, 5 failed
Success Rate: 64.2%
Vulnerabilities: 12 found, 18 expected
Metrics: TP=12, FP=0, FN=6
Overall Precision: 1.000
Overall Recall: .666

--- Basic Test Suite ---
Tests: 42 total, 38 passed, 4 failed  
Success Rate: 90.4%
Vulnerabilities: 60 found, 60 expected
Metrics: TP=58, FP=2, FN=2
Overall Precision: .966
Overall Recall: .966
```

### **File Locations**:
```
target/
├── test-results/securibench/micro/
│   ├── inter/
│   │   ├── Inter1.json
│   │   ├── Inter2.json
│   │   └── ...
│   └── basic/
│       ├── Basic1.json
│       ├── Basic2.json
│       └── ...
└── metrics/
    ├── securibench_metrics_20251215_214119.csv
    └── securibench_summary_20251215_214119.txt
```

---

## 🔧 Advanced Usage

### **Get Help**
Both scripts include comprehensive help:

```bash
# Detailed help for metrics computation
./scripts/compute-securibench-metrics.sh --help
./scripts/compute-securibench-metrics.sh -h

# Detailed help for test execution  
./scripts/run-securibench-tests.sh --help
./scripts/run-securibench-tests.sh -h
```

**Help includes**:
- Complete usage instructions
- All available options and test suites
- Performance expectations
- Output file locations
- Practical examples

### **Clean Previous Test Data**
Remove all previous test results and metrics for a fresh start:

```bash
# Clean using metrics script
./scripts/compute-securibench-metrics.sh clean

# Clean and execute all tests
./scripts/run-securibench-tests.sh clean
```

**What gets cleaned**:
- All JSON test result files (`target/test-results/`)
- All CSV and summary reports (`target/metrics/`)
- Temporary log files (`/tmp/executor_*.log`, `/tmp/metrics_*.log`)

**When to use clean**:
- Before important analysis runs
- When debugging test issues
- To free up disk space
- To ensure completely fresh results

### **Add New Test Suite**
1. Create executor and metrics classes (see existing Basic/Inter examples)
2. Add suite to both scripts' `SUITE_KEYS` and `SUITE_NAMES` arrays
3. Scripts will automatically include the new suite

### **Custom Analysis**
- Use the CSV file for custom analysis in R, Python, Excel
- Filter by suite, test name, or status
- Create visualizations and trend analysis
- Compare results across different SVFA configurations

### **Integration with CI/CD**
```bash
# In CI pipeline
./scripts/run-securibench-tests.sh
if [ $? -eq 0 ]; then
    ./scripts/compute-securibench-metrics.sh
    # Upload CSV to artifact storage
fi
```

## 🔍 Understanding Test Results

### **Two Types of "Success"**

When running Securibench tests, you'll see **two different success indicators**:

#### **1. Technical Execution Success** ✅
```
✅ Aliasing test execution completed (technical success)
[info] All tests passed.
```
**Meaning**: SBT successfully executed all test cases without crashes or technical errors.

#### **2. SVFA Analysis Results** ✅/❌
```
Executing: Aliasing1
  Aliasing1: 1/1 conflicts - ✅ PASS (269ms)
Executing: Aliasing2  
  Aliasing2: 0/1 conflicts - ❌ FAIL (301ms)
```
**Meaning**: Whether SVFA found the expected number of vulnerabilities in each test case.

### **Why Both Matter**

- **Technical Success**: Ensures the testing infrastructure works correctly
- **Analysis Results**: Shows how well SVFA detects vulnerabilities
- **A suite can be "technically successful" even if many analysis tests fail**

### **Reading the Summary**
```
📊 EXECUTION SUMMARY:
   Total tests: 6
   Passed: 2        ← SVFA analysis accuracy
   Failed: 4        ← SVFA analysis accuracy  
   Success rate: 33% ← SVFA analysis accuracy
```

This approach provides **maximum flexibility** for SVFA analysis and metrics computation! 🎯
