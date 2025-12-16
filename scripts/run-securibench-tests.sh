#!/bin/bash

# Script to execute Securibench tests without computing metrics
# This runs the expensive SVFA analysis and saves results for later metrics computation
# Usage: ./run-securibench-tests.sh [suite] [callgraph] [clean|--help]
# Where suite can be: inter, basic, aliasing, arrays, collections, datastructures,
#                     factories, pred, reflection, sanitizers, session, strong_updates, or omitted for all suites
# Where callgraph can be: spark, cha, spark_library, or omitted for spark (default)
# Special commands:
#   clean - Remove all previous test results before execution

# Function to display help
show_help() {
    cat << EOF
=== SECURIBENCH TEST EXECUTION SCRIPT ===

DESCRIPTION:
    Execute Securibench test suites and save results to disk.
    Runs expensive SVFA analysis on specified suite(s) or all 12 test suites (122 total tests).

USAGE:
    $0 [SUITE] [CALLGRAPH] [OPTIONS]

ARGUMENTS:
    SUITE               Test suite to execute (default: all)
    CALLGRAPH           Call graph algorithm (default: spark)

OPTIONS:
    --help, -h          Show this help message
    clean               Remove all previous test data before execution
    all                 Execute all test suites (default)

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
    cha                 Class Hierarchy Analysis (faster, less precise)
    spark_library       SPARK with library support (comprehensive)

EXAMPLES:
    $0                                  # Execute all test suites with SPARK
    $0 all                              # Same as above
    $0 inter                            # Execute Inter suite with SPARK
    $0 inter cha                        # Execute Inter suite with CHA call graph
    $0 basic spark_library              # Execute Basic suite with SPARK_LIBRARY
    $0 all cha                          # Execute all suites with CHA call graph
    $0 clean                            # Clean previous data and execute all tests
    $0 --help                           # Show this help

OUTPUT:
    - Test results: target/test-results/securibench/micro/[suite]/
    - JSON files: One per test case with detailed results
    - Execution summary: Console output with timing and pass/fail counts

PERFORMANCE:
    - Total execution time: ~3-5 minutes for all 122 tests
    - Memory usage: High (Soot framework + call graph construction)
    - Disk usage: ~122 JSON files (~1-2MB total)

NEXT STEPS:
    After execution, use compute-securibench-metrics.sh to generate:
    - Accuracy metrics (TP, FP, FN, Precision, Recall, F-score)
    - CSV reports for analysis
    - Summary statistics

NOTES:
    - This is the expensive phase (SVFA analysis)
    - Results are cached for fast metrics computation
    - Technical 'success' ≠ SVFA analysis accuracy
    - Individual test results show vulnerability detection accuracy

For detailed documentation, see: USAGE_SCRIPTS.md
EOF
}

# Handle clean option
if [ "$CLEAN_FIRST" == "true" ]; then
    echo "=== CLEANING SECURIBENCH TEST DATA ==="
    echo
    
    test_results_dir="target/test-results"
    metrics_dir="target/metrics"
    
    total_removed=0
    
    if [ -d "$test_results_dir" ]; then
        test_count=$(find "$test_results_dir" -name "*.json" 2>/dev/null | wc -l)
        if [ "$test_count" -gt 0 ]; then
            echo "🗑️  Removing $test_count test result files from $test_results_dir"
            rm -rf "$test_results_dir"
            total_removed=$((total_removed + test_count))
        fi
    fi
    
    if [ -d "$metrics_dir" ]; then
        metrics_count=$(find "$metrics_dir" -name "securibench_*" 2>/dev/null | wc -l)
        if [ "$metrics_count" -gt 0 ]; then
            echo "🗑️  Removing $metrics_count metrics files from $metrics_dir"
            rm -f "$metrics_dir"/securibench_*
            total_removed=$((total_removed + metrics_count))
        fi
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
        echo "💡 Proceeding with fresh test execution..."
    else
        echo "✅ No files to clean - workspace is already clean"
        echo "💡 Proceeding with test execution..."
    fi
    echo
fi

# Available test suites
SUITE_KEYS=("inter" "basic" "aliasing" "arrays" "collections" "datastructures" "factories" "pred" "reflection" "sanitizers" "session" "strong_updates")
SUITE_NAMES=("Inter" "Basic" "Aliasing" "Arrays" "Collections" "Datastructures" "Factories" "Pred" "Reflection" "Sanitizers" "Session" "StrongUpdates")

# Available call graph algorithms
CALLGRAPH_ALGORITHMS=("spark" "cha" "spark_library")

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

# Function to execute a specific suite
execute_suite() {
    local suite_key=$1
    local callgraph=$2
    local suite_name=$(get_suite_name "$suite_key")
    
    if [[ -z "$suite_name" ]]; then
        echo "❌ Unknown test suite: $suite_key"
        echo
        echo "Available suites: ${SUITE_KEYS[*]}"
        echo "Usage: $0 [suite] [callgraph] [clean|--help]"
        echo
        echo "For detailed help, run: $0 --help"
        exit 1
    fi
    
    echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
    echo "🔄 Executing $suite_name tests (securibench.micro.$suite_key) with $callgraph call graph..."
    echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
    
    start_time=$(date +%s)
    
    # Execute with call graph configuration
    sbt -Dsecuribench.callgraph="$callgraph" "project securibench" "testOnly *Securibench${suite_name}Executor"
    exit_code=$?
    
    end_time=$(date +%s)
    duration=$((end_time - start_time))
    
    if [ $exit_code -ne 0 ]; then
        echo "❌ $suite_name test execution failed (technical error)"
        return 1
    else
        echo "✅ $suite_name test execution completed (technical success) with $callgraph call graph"
        
        # Count tests from results directory
        results_dir="target/test-results/securibench/micro/$suite_key"
        if [ -d "$results_dir" ]; then
            test_count=$(ls "$results_dir"/*.json 2>/dev/null | wc -l)
            echo "   📊 $test_count tests executed in ${duration}s using $callgraph call graph"
            echo "   ℹ️  Individual test results show SVFA analysis accuracy"
        fi
        return 0
    fi
}

# Determine execution mode and display header
case "$REQUESTED_SUITE" in
    "all"|"")
        echo "=== EXECUTING ALL SECURIBENCH TESTS WITH $REQUESTED_CALLGRAPH CALL GRAPH ==="
        echo "This will run SVFA analysis on all test suites using $REQUESTED_CALLGRAPH call graph and save results to disk."
        echo "Use compute-securibench-metrics.sh afterwards to generate accuracy metrics."
        echo
        ;;
    *)
        # Check if it's a valid suite
        if [[ " ${SUITE_KEYS[*]} " == *" $REQUESTED_SUITE "* ]]; then
            suite_name=$(get_suite_name "$REQUESTED_SUITE")
            echo "=== EXECUTING $suite_name TEST SUITE WITH $REQUESTED_CALLGRAPH CALL GRAPH ==="
            echo "This will run SVFA analysis on the $suite_name suite using $REQUESTED_CALLGRAPH call graph and save results to disk."
            echo "Use compute-securibench-metrics.sh afterwards to generate accuracy metrics."
            echo
        else
            echo "❌ Unknown test suite: $REQUESTED_SUITE"
            echo
            echo "Available suites: ${SUITE_KEYS[*]}"
            echo "Available call graphs: ${CALLGRAPH_ALGORITHMS[*]}"
            echo "Usage: $0 [suite] [callgraph] [clean|--help]"
            echo
            echo "For detailed help, run: $0 --help"
            exit 1
        fi
        ;;
esac

# Execute the requested suite(s)
if [ "$REQUESTED_SUITE" == "all" ]; then
    # Execute all suites
    failed_suites=()
    total_tests=0
    total_time=0

    echo "🚀 Starting test execution for all suites..."
    echo

    for i in "${!SUITE_KEYS[@]}"; do
        suite_key="${SUITE_KEYS[$i]}"
        
        start_time=$(date +%s)
        
        if execute_suite "$suite_key" "$REQUESTED_CALLGRAPH"; then
            # Count tests from results directory
            results_dir="target/test-results/securibench/micro/$suite_key"
            if [ -d "$results_dir" ]; then
                test_count=$(ls "$results_dir"/*.json 2>/dev/null | wc -l)
                total_tests=$((total_tests + test_count))
            fi
        else
            suite_name=$(get_suite_name "$suite_key")
            failed_suites+=("$suite_name")
        fi
        
        end_time=$(date +%s)
        duration=$((end_time - start_time))
        total_time=$((total_time + duration))
        
        echo
    done
else
    # Execute single suite
    echo "🚀 Starting test execution for $(get_suite_name "$REQUESTED_SUITE") suite..."
    echo
    
    start_time=$(date +%s)
    
    if execute_suite "$REQUESTED_SUITE" "$REQUESTED_CALLGRAPH"; then
        results_dir="target/test-results/securibench/micro/$REQUESTED_SUITE"
        if [ -d "$results_dir" ]; then
            total_tests=$(ls "$results_dir"/*.json 2>/dev/null | wc -l)
        fi
        failed_suites=()
    else
        suite_name=$(get_suite_name "$REQUESTED_SUITE")
        failed_suites=("$suite_name")
        total_tests=0
    fi
    
    end_time=$(date +%s)
    total_time=$((end_time - start_time))
    
    echo
fi

echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
if [ "$REQUESTED_SUITE" == "all" ]; then
    echo "🏁 ALL TEST EXECUTION COMPLETED WITH $REQUESTED_CALLGRAPH CALL GRAPH"
else
    suite_name=$(get_suite_name "$REQUESTED_SUITE")
    echo "🏁 $suite_name TEST EXECUTION COMPLETED WITH $REQUESTED_CALLGRAPH CALL GRAPH"
fi
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"

if [ ${#failed_suites[@]} -eq 0 ]; then
    if [ "$REQUESTED_SUITE" == "all" ]; then
        echo "✅ All test suites executed successfully with $REQUESTED_CALLGRAPH call graph!"
    else
        suite_name=$(get_suite_name "$REQUESTED_SUITE")
        echo "✅ $suite_name test suite executed successfully with $REQUESTED_CALLGRAPH call graph!"
    fi
    echo "📊 Total: $total_tests tests executed in ${total_time}s using $REQUESTED_CALLGRAPH call graph"
else
    echo "⚠️  Some test suites had execution issues:"
    for failed in "${failed_suites[@]}"; do
        echo "   - $failed"
    done
    echo "📊 Partial: $total_tests tests executed in ${total_time}s using $REQUESTED_CALLGRAPH call graph"
fi

echo
echo "📁 Test results saved in: target/test-results/securibench/micro/"
if [ "$REQUESTED_SUITE" == "all" ]; then
    echo "🔍 Next step: Run ./scripts/compute-securibench-metrics.sh to generate accuracy metrics"
else
    echo "🔍 Next step: Run ./scripts/compute-securibench-metrics.sh $REQUESTED_SUITE to generate accuracy metrics"
fi
echo
echo "💡 Suite results:"
if [ "$REQUESTED_SUITE" == "all" ]; then
    for i in "${!SUITE_KEYS[@]}"; do
        suite_key="${SUITE_KEYS[$i]}"
        suite_name="${SUITE_NAMES[$i]}"
        results_dir="target/test-results/securibench/micro/$suite_key"
        if [ -d "$results_dir" ]; then
            test_count=$(ls "$results_dir"/*.json 2>/dev/null | wc -l)
            echo "   - $suite_name: $test_count tests in $results_dir"
        fi
    done
else
    suite_name=$(get_suite_name "$REQUESTED_SUITE")
    results_dir="target/test-results/securibench/micro/$REQUESTED_SUITE"
    if [ -d "$results_dir" ]; then
        test_count=$(ls "$results_dir"/*.json 2>/dev/null | wc -l)
        echo "   - $suite_name: $test_count tests in $results_dir"
    fi
fi
