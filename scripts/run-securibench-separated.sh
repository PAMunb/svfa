#!/bin/bash

# Script for separated test execution and metrics computation
# Usage: ./run-securibench-separated.sh [suite]
# Where suite can be: inter, basic, all, or omitted for interactive selection

SUITE=${1:-""}

# Available test suites (using simple arrays for compatibility)
SUITE_KEYS=("inter" "basic")
SUITE_NAMES=("Inter" "Basic")

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

# Function to run a specific test suite
run_suite() {
    local suite_key=$1
    local suite_name=$(get_suite_name "$suite_key")
    
    if [[ -z "$suite_name" ]]; then
        echo "❌ Unknown test suite: $suite_key"
        echo "Available suites: ${SUITE_KEYS[*]}"
        exit 1
    fi
    
    echo "=== RUNNING $suite_name TEST SUITE ==="
    echo
    
    # Phase 1: Execute tests
    echo "🚀 PHASE 1: Executing $suite_name tests..."
    sbt "project securibench" "testOnly *Securibench${suite_name}Executor"
    
    if [ $? -ne 0 ]; then
        echo "❌ Phase 1 failed for $suite_name tests"
        return 1
    fi
    
    echo
    echo "⏳ Waiting 2 seconds..."
    sleep 2
    
    # Phase 2: Compute metrics  
    echo "📊 PHASE 2: Computing metrics for $suite_name tests..."
    sbt "project securibench" "testOnly *Securibench${suite_name}Metrics"
    
    if [ $? -ne 0 ]; then
        echo "❌ Phase 2 failed for $suite_name tests"
        return 1
    fi
    
    echo
    echo "✅ $suite_name TEST SUITE COMPLETE!"
    echo "📁 Results saved in: target/test-results/securibench/micro/${suite_key}/"
    echo
}

# Function to run all test suites
run_all_suites() {
    echo "=== RUNNING ALL SECURIBENCH TEST SUITES ==="
    echo
    
    local failed_suites=()
    
    for suite_key in "${SUITE_KEYS[@]}"; do
        local suite_name=$(get_suite_name "$suite_key")
        echo "🔄 Starting $suite_name test suite..."
        run_suite "$suite_key"
        
        if [ $? -ne 0 ]; then
            failed_suites+=("$suite_name")
        fi
        
        echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
        echo
    done
    
    echo "🏁 ALL TEST SUITES COMPLETED!"
    echo
    
    if [ ${#failed_suites[@]} -eq 0 ]; then
        echo "✅ All test suites completed successfully!"
    else
        echo "⚠️  Some test suites had issues:"
        for failed in "${failed_suites[@]}"; do
            echo "   - $failed"
        done
    fi
    
    echo
    echo "📁 All results saved in: target/test-results/securibench/micro/"
    echo "🔍 You can inspect individual result files or re-run metrics computation anytime"
}

# Function to show interactive menu
show_menu() {
    echo "=== SECURIBENCH SEPARATED TESTING ==="
    echo
    echo "Please select a test suite to run:"
    echo
    
    local i=1
    
    for suite_key in "${SUITE_KEYS[@]}"; do
        local suite_name=$(get_suite_name "$suite_key")
        echo "  $i) $suite_name (securibench.micro.$suite_key)"
        ((i++))
    done
    
    echo "  $i) All test suites"
    echo "  0) Exit"
    echo
    
    read -p "Enter your choice (0-$i): " choice
    
    if [[ "$choice" == "0" ]]; then
        echo "Goodbye! 👋"
        exit 0
    elif [[ "$choice" == "$i" ]]; then
        run_all_suites
    elif [[ "$choice" =~ ^[1-9][0-9]*$ ]] && [ "$choice" -le "${#SUITE_KEYS[@]}" ]; then
        local selected_suite=${SUITE_KEYS[$((choice-1))]}
        run_suite "$selected_suite"
    else
        echo "❌ Invalid choice. Please try again."
        echo
        show_menu
    fi
}

# Main script logic
case "$SUITE" in
    "all")
        run_all_suites
        ;;
    "")
        show_menu
        ;;
    *)
        run_suite "$SUITE"
        ;;
esac
