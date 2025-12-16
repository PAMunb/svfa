#!/bin/bash

# Script to compute accuracy metrics for Securibench test suites
# Automatically executes missing tests before computing metrics
# Usage: ./compute-securibench-metrics.sh [suite] [callgraph] [clean]
# Where suite can be: inter, basic, aliasing, arrays, collections, datastructures, 
#                     factories, pred, reflection, sanitizers, session, strong_updates, or omitted for all suites
# Where callgraph can be: spark, cha, spark_library, or omitted for spark (default)
# Special commands:
#   clean - Remove all previous test results and metrics
# Outputs results to CSV file and console

# Function to display help
show_help() {
    cat << EOF
=== SECURIBENCH METRICS COMPUTATION SCRIPT ===

DESCRIPTION:
    Compute accuracy metrics for Securibench test suites with automatic test execution.
    Automatically executes missing tests before computing metrics.

USAGE:
    $0 [SUITE] [CALLGRAPH] [OPTIONS]

ARGUMENTS:
    SUITE               Test suite to process (default: all)
    CALLGRAPH           Call graph algorithm (default: spark)

OPTIONS:
    --help, -h          Show this help message
    clean               Remove all previous test results and metrics
    all                 Process all test suites (default)

AVAILABLE TEST SUITES:
    inter               Interprocedural analysis tests (14 tests)
    basic               Basic taint flow tests (42 tests)
    aliasing            Aliasing and pointer analysis tests (6 tests)
    arrays              Array handling tests (10 tests)
    collections         Java collections tests (14 tests)
    datastructures      Data structure tests (6 tests)
    factories           Factory pattern tests (3 tests)
    pred                Predicate tests (9 tests)
    reflection          Reflection API tests (4 tests)
    sanitizers          Input sanitization tests (6 tests)
    session             HTTP session tests (3 tests)
    strong_updates      Strong update analysis tests (5 tests)

CALL GRAPH ALGORITHMS:
    spark               SPARK points-to analysis (default, most precise)
    cha                 Class Hierarchy Analysis (fastest, least precise)
    spark_library       SPARK with library support (comprehensive coverage)
    rta                 Rapid Type Analysis via SPARK (fast, moderately precise)
    vta                 Variable Type Analysis via SPARK (balanced speed/precision)

EXAMPLES:
    $0                                  # Process all suites with SPARK (auto-execute missing tests)
    $0 all                              # Same as above
    $0 basic                            # Process only Basic suite with SPARK
    $0 inter cha                        # Process Inter suite with CHA call graph
    $0 basic rta                        # Process Basic suite with RTA call graph
    $0 inter vta                        # Process Inter suite with VTA call graph
    $0 all spark_library                # Process all suites with SPARK_LIBRARY
    $0 clean                            # Remove all previous test data
    $0 --help                           # Show this help

OUTPUT:
    - CSV report: target/metrics/securibench_metrics_[callgraph]_YYYYMMDD_HHMMSS.csv
    - Summary report: target/metrics/securibench_summary_[callgraph]_YYYYMMDD_HHMMSS.txt
    - Console summary with TP, FP, FN, Precision, Recall, F-score

FEATURES:
    ✅ Auto-execution: Missing tests are automatically executed
    ✅ Smart caching: Uses existing results when available
    ✅ CSV output: Ready for analysis in Excel, R, Python
    ✅ Comprehensive metrics: TP, FP, FN, Precision, Recall, F-score
    ✅ Clean functionality: Easy removal of previous data

NOTES:
    - First run may take several minutes (executing tests)
    - Subsequent runs are fast (uses cached results)
    - Use 'clean' to ensure fresh results
    - Technical 'success' ≠ SVFA analysis accuracy

For detailed documentation, see: USAGE_SCRIPTS.md
EOF
}

# Handle help option
case "$1" in
    --help|-h|help)
        show_help
        exit 0
        ;;
esac

# Available test suites
SUITE_KEYS=("inter" "basic" "aliasing" "arrays" "collections" "datastructures" "factories" "pred" "reflection" "sanitizers" "session" "strong_updates")
SUITE_NAMES=("Inter" "Basic" "Aliasing" "Arrays" "Collections" "Datastructures" "Factories" "Pred" "Reflection" "Sanitizers" "Session" "StrongUpdates")

# Available call graph algorithms
CALLGRAPH_ALGORITHMS=("spark" "cha" "spark_library" "rta" "vta")

# Parse arguments
parse_arguments() {
    local arg1=$1
    local arg2=$2
    
    # Handle special cases first
    case "$arg1" in
        --help|-h|help)
            show_help
            exit 0
            ;;
        clean)
            CLEAN_FIRST=true
            REQUESTED_SUITE=${arg2:-"all"}
            REQUESTED_CALLGRAPH="spark"
            return
            ;;
    esac
    
    # Parse suite and call graph arguments
    REQUESTED_SUITE=${arg1:-"all"}
    REQUESTED_CALLGRAPH=${arg2:-"spark"}
    
    # Validate call graph algorithm
    if [[ ! " ${CALLGRAPH_ALGORITHMS[*]} " == *" $REQUESTED_CALLGRAPH "* ]]; then
        echo "❌ Unknown call graph algorithm: $REQUESTED_CALLGRAPH"
        echo
        echo "Available call graph algorithms: ${CALLGRAPH_ALGORITHMS[*]}"
        echo "Usage: $0 [suite] [callgraph] [clean|--help]"
        echo
        echo "For detailed help, run: $0 --help"
        exit 1
    fi
}

# Parse command line arguments
parse_arguments "$1" "$2"

SUITE=${REQUESTED_SUITE:-"all"}
CALLGRAPH=${REQUESTED_CALLGRAPH:-"spark"}
OUTPUT_DIR="target/metrics"
TIMESTAMP=$(date +"%Y%m%d_%H%M%S")

# Handle clean option
if [ "$CLEAN_FIRST" == "true" ]; then
    clean_test_data
    # After cleaning, process all suites by default
    SUITE="all"
fi

# Create output directory
mkdir -p "$OUTPUT_DIR"

# Function to clean previous test data
clean_test_data() {
    echo "=== CLEANING SECURIBENCH TEST DATA ==="
    echo
    
    # Check what exists
    test_results_dir="target/test-results"
    metrics_dir="target/metrics"
    
    total_removed=0
    
    if [ -d "$test_results_dir" ]; then
        test_count=$(find "$test_results_dir" -name "*.json" 2>/dev/null | wc -l)
        if [ "$test_count" -gt 0 ]; then
            echo "🗑️  Removing $test_count test result files from $test_results_dir"
            rm -rf "$test_results_dir"
            total_removed=$((total_removed + test_count))
        else
            echo "📁 No test results found in $test_results_dir"
        fi
    else
        echo "📁 No test results directory found"
    fi
    
    if [ -d "$metrics_dir" ]; then
        metrics_count=$(find "$metrics_dir" -name "securibench_*" 2>/dev/null | wc -l)
        if [ "$metrics_count" -gt 0 ]; then
            echo "🗑️  Removing $metrics_count metrics files from $metrics_dir"
            rm -f "$metrics_dir"/securibench_*
            total_removed=$((total_removed + metrics_count))
        else
            echo "📁 No metrics files found in $metrics_dir"
        fi
    else
        echo "📁 No metrics directory found"
    fi
    
    # Clean temporary log files
    temp_logs=$(ls /tmp/executor_*.log /tmp/metrics_*.log 2>/dev/null | wc -l)
    if [ "$temp_logs" -gt 0 ]; then
        echo "🗑️  Removing $temp_logs temporary log files"
        rm -f /tmp/executor_*.log /tmp/metrics_*.log 2>/dev/null
        total_removed=$((total_removed + temp_logs))
    fi
    
    echo
    if [ "$total_removed" -gt 0 ]; then
        echo "✅ Cleanup complete! Removed $total_removed files"
        echo "💡 Next run will execute all tests from scratch"
    else
        echo "✅ No files to clean - workspace is already clean"
    fi
    echo
}

# Function to get suite name by key
get_suite_name() {
    local key=$1
    for i in "${!SUITE_KEYS[@]}"; do
        if [[ "${SUITE_KEYS[$i]}" == "$key" ]]; then
            echo "${SUITE_NAMES[$i]}"
            return 0
        fi
    done
    echo ""
}

# Function to execute tests for a specific suite
execute_suite_tests() {
    local suite_key=$1
    local callgraph=$2
    local suite_name=$(get_suite_name "$suite_key")
    
    echo "🔄 Executing $suite_name tests with $callgraph call graph (missing results detected)..."
    
    # Run the specific test executor in batch mode (no server) with call graph configuration
    sbt -Dsecuribench.callgraph="$callgraph" -batch "project securibench" "testOnly *Securibench${suite_name}Executor" > /tmp/executor_${suite_key}.log 2>&1
    
    if [ $? -ne 0 ]; then
        echo "❌ Failed to execute $suite_name tests"
        echo "   Check /tmp/executor_${suite_key}.log for details"
        return 1
    fi
    
    echo "✅ $suite_name tests executed successfully"
    return 0
}

# Function to compute metrics for a specific suite
compute_suite_metrics() {
    local suite_key=$1
    local suite_name=$(get_suite_name "$suite_key")
    
    if [[ -z "$suite_name" ]]; then
        echo "❌ Unknown test suite: $suite_key"
        return 1
    fi
    
    echo "📊 Computing metrics for $suite_name tests..."
    
    # Check if results exist
    results_dir="target/test-results/securibench/micro/$suite_key"
    if [ ! -d "$results_dir" ] || [ -z "$(ls -A "$results_dir" 2>/dev/null)" ]; then
        echo "⚠️  No test results found for $suite_name in $results_dir"
        echo "🔄 Auto-executing missing tests..."
        
        # Automatically execute the missing tests
        if ! execute_suite_tests "$suite_key" "$CALLGRAPH"; then
            return 1
        fi
        
        # Verify results were created
        if [ ! -d "$results_dir" ] || [ -z "$(ls -A "$results_dir" 2>/dev/null)" ]; then
            echo "❌ Test execution completed but no results found"
            return 1
        fi
        
        echo "✅ Test results now available for $suite_name"
    fi
    
    # Run metrics computation in batch mode (no server)
    sbt -batch "project securibench" "testOnly *Securibench${suite_name}Metrics" > /tmp/metrics_${suite_key}.log 2>&1
    
    if [ $? -ne 0 ]; then
        echo "❌ Failed to compute metrics for $suite_name"
        echo "   Check /tmp/metrics_${suite_key}.log for details"
        return 1
    fi
    
    echo "✅ Metrics computed for $suite_name"
    return 0
}

# Function to extract metrics from SBT output and create CSV
create_csv_report() {
    local output_file="$OUTPUT_DIR/securibench_metrics_${CALLGRAPH}_${TIMESTAMP}.csv"
    
    echo "📄 Creating CSV report: $output_file"
    
    # CSV Header
    echo "Suite,Test,Found,Expected,Status,TP,FP,FN,Precision,Recall,F-score,Execution_Time_ms" > "$output_file"
    
    # Process each suite's results
    for suite_key in "${SUITE_KEYS[@]}"; do
        results_dir="target/test-results/securibench/micro/$suite_key"
        
        if [ -d "$results_dir" ] && [ -n "$(ls -A "$results_dir" 2>/dev/null)" ]; then
            echo "Processing $suite_key results..."
            
            # Read each JSON result file and extract metrics
            for json_file in "$results_dir"/*.json; do
                if [ -f "$json_file" ]; then
                    # Extract data using basic text processing (avoiding jq dependency)
                    test_name=$(basename "$json_file" .json)
                    
                    # Extract values from JSON (simple grep/sed approach)
                    found=$(grep -o '"foundVulnerabilities":[0-9]*' "$json_file" | cut -d: -f2)
                    expected=$(grep -o '"expectedVulnerabilities":[0-9]*' "$json_file" | cut -d: -f2)
                    exec_time=$(grep -o '"executionTimeMs":[0-9]*' "$json_file" | cut -d: -f2)
                    
                    # Calculate metrics
                    if [[ -n "$found" && -n "$expected" ]]; then
                        tp=$((found < expected ? found : expected))
                        fp=$((found > expected ? found - expected : 0))
                        fn=$((expected > found ? expected - found : 0))
                        
                        # Calculate precision, recall, f-score
                        if [ "$found" -gt 0 ]; then
                            precision=$(echo "scale=3; $tp / $found" | bc -l 2>/dev/null || echo "0")
                        else
                            precision="0"
                        fi
                        
                        if [ "$expected" -gt 0 ]; then
                            recall=$(echo "scale=3; $tp / $expected" | bc -l 2>/dev/null || echo "0")
                        else
                            recall="0"
                        fi
                        
                        if [ "$found" -eq "$expected" ]; then
                            status="PASS"
                        else
                            status="FAIL"
                        fi
                        
                        # F-score calculation
                        if (( $(echo "$precision + $recall > 0" | bc -l 2>/dev/null || echo "0") )); then
                            fscore=$(echo "scale=3; 2 * $precision * $recall / ($precision + $recall)" | bc -l 2>/dev/null || echo "0")
                        else
                            fscore="0"
                        fi
                        
                        # Add to CSV
                        echo "$suite_key,$test_name,$found,$expected,$status,$tp,$fp,$fn,$precision,$recall,$fscore,$exec_time" >> "$output_file"
                    fi
                fi
            done
        fi
    done
    
    echo "✅ CSV report created: $output_file"
    
    # Create summary
    create_summary_report "$output_file"
}

# Function to create summary report
create_summary_report() {
    local csv_file=$1
    local summary_file="$OUTPUT_DIR/securibench_summary_${CALLGRAPH}_${TIMESTAMP}.txt"
    
    echo "📋 Creating summary report: $summary_file"
    
    {
        echo "=== SECURIBENCH METRICS SUMMARY ==="
        echo "Generated: $(date)"
        echo "CSV Data: $(basename "$csv_file")"
        echo
        
        for suite_key in "${SUITE_KEYS[@]}"; do
            suite_name=$(get_suite_name "$suite_key")
            
            # Count tests and calculate summary for this suite
            total_tests=$(grep "^$suite_key," "$csv_file" | wc -l)
            passed_tests=$(grep "^$suite_key,.*,PASS," "$csv_file" | wc -l)
            failed_tests=$((total_tests - passed_tests))
            
            if [ "$total_tests" -gt 0 ]; then
                success_rate=$(echo "scale=1; $passed_tests * 100 / $total_tests" | bc -l)
                
                echo "--- $suite_name Test Suite ---"
                echo "Tests: $total_tests total, $passed_tests passed, $failed_tests failed"
                echo "Success Rate: ${success_rate}%"
                
                # Calculate totals
                total_found=$(grep "^$suite_key," "$csv_file" | cut -d, -f3 | awk '{sum+=$1} END {print sum+0}')
                total_expected=$(grep "^$suite_key," "$csv_file" | cut -d, -f4 | awk '{sum+=$1} END {print sum+0}')
                total_tp=$(grep "^$suite_key," "$csv_file" | cut -d, -f6 | awk '{sum+=$1} END {print sum+0}')
                total_fp=$(grep "^$suite_key," "$csv_file" | cut -d, -f7 | awk '{sum+=$1} END {print sum+0}')
                total_fn=$(grep "^$suite_key," "$csv_file" | cut -d, -f8 | awk '{sum+=$1} END {print sum+0}')
                
                echo "Vulnerabilities: $total_found found, $total_expected expected"
                echo "Metrics: TP=$total_tp, FP=$total_fp, FN=$total_fn"
                
                if [ "$total_found" -gt 0 ]; then
                    overall_precision=$(echo "scale=3; $total_tp / $total_found" | bc -l)
                else
                    overall_precision="0"
                fi
                
                if [ "$total_expected" -gt 0 ]; then
                    overall_recall=$(echo "scale=3; $total_tp / $total_expected" | bc -l)
                else
                    overall_recall="0"
                fi
                
                echo "Overall Precision: $overall_precision"
                echo "Overall Recall: $overall_recall"
                echo
            else
                echo "--- $suite_name Test Suite ---"
                echo "No results found"
                echo
            fi
        done
        
    } > "$summary_file"
    
    echo "✅ Summary report created: $summary_file"
    
    # Display summary on console
    echo
    echo "📊 METRICS SUMMARY:"
    cat "$summary_file"
}

# Main script logic
echo "=== SECURIBENCH METRICS COMPUTATION WITH $CALLGRAPH CALL GRAPH ==="
echo "🔍 Checking test results and auto-executing missing tests..."
echo

case "$SUITE" in
    "clean")
        clean_test_data
        exit 0
        ;;
        
    "all")
        echo "🔄 Computing metrics for all test suites..."
        echo
        
        failed_suites=()
        
        for suite_key in "${SUITE_KEYS[@]}"; do
            compute_suite_metrics "$suite_key"
            if [ $? -ne 0 ]; then
                failed_suites+=("$suite_key")
            fi
        done
        
        echo
        if [ ${#failed_suites[@]} -eq 0 ]; then
            echo "✅ All metrics computed successfully!"
            create_csv_report
        else
            echo "⚠️  Some suites had issues: ${failed_suites[*]}"
            echo "Creating partial CSV report..."
            create_csv_report
        fi
        ;;
        
    *)
        # Single suite
        if [[ " ${SUITE_KEYS[*]} " == *" $SUITE "* ]]; then
            compute_suite_metrics "$SUITE"
            if [ $? -eq 0 ]; then
                echo "Creating CSV report for $SUITE..."
                create_csv_report
            fi
        else
            echo "❌ Unknown test suite: $SUITE"
            echo
            echo "Available suites: ${SUITE_KEYS[*]}"
            echo "Special commands: clean"
            echo "Usage: $0 [suite|all|clean|--help]"
            echo
            echo "For detailed help, run: $0 --help"
            exit 1
        fi
        ;;
esac

echo
echo "📁 Reports saved in: $OUTPUT_DIR/"
echo "🔍 Use the CSV file for further analysis or visualization"
