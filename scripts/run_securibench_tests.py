#!/usr/bin/env python3
"""
SVFA Securibench Test Runner (Python Version)

This script executes Securibench test suites and saves results to disk.
Runs expensive SVFA analysis on specified suite(s) or all 12 test suites.

Dependencies: Python 3.6+ (standard library only)
"""

import argparse
import json
import subprocess
import sys
import os
import shutil
import time
from pathlib import Path
from datetime import datetime
from typing import List, Optional, Tuple

# Configuration constants
CALL_GRAPH_ALGORITHMS = ['spark', 'cha', 'spark_library', 'rta', 'vta']
TEST_SUITES = [
    'inter', 'basic', 'aliasing', 'arrays', 'collections', 
    'datastructures', 'factories', 'pred', 'reflection', 
    'sanitizers', 'session', 'strong_updates'
]

SUITE_DESCRIPTIONS = {
    'inter': 'Interprocedural analysis tests (14 tests)',
    'basic': 'Basic taint flow tests (42 tests)',
    'aliasing': 'Aliasing and pointer analysis tests (6 tests)',
    'arrays': 'Array handling tests (10 tests)',
    'collections': 'Java collections tests (14 tests)',
    'datastructures': 'Data structure tests (6 tests)',
    'factories': 'Factory pattern tests (3 tests)',
    'pred': 'Predicate tests (9 tests)',
    'reflection': 'Reflection API tests (4 tests)',
    'sanitizers': 'Input sanitization tests (6 tests)',
    'session': 'HTTP session tests (3 tests)',
    'strong_updates': 'Strong update analysis tests (5 tests)'
}

CALLGRAPH_DESCRIPTIONS = {
    'spark': 'SPARK points-to analysis (default, most precise)',
    'cha': 'Class Hierarchy Analysis (fastest, least precise)',
    'spark_library': 'SPARK with library support (comprehensive coverage)',
    'rta': 'Rapid Type Analysis via SPARK (fast, moderately precise)',
    'vta': 'Variable Type Analysis via SPARK (balanced speed/precision)'
}


class Colors:
    """ANSI color codes for terminal output."""
    RESET = '\033[0m'
    BOLD = '\033[1m'
    RED = '\033[91m'
    GREEN = '\033[92m'
    YELLOW = '\033[93m'
    BLUE = '\033[94m'
    PURPLE = '\033[95m'
    CYAN = '\033[96m'


def print_colored(message: str, color: str = Colors.RESET) -> None:
    """Print colored message to terminal."""
    print(f"{color}{message}{Colors.RESET}")


def print_header(title: str) -> None:
    """Print a formatted header."""
    separator = "━" * 50
    print_colored(f"\n{separator}", Colors.CYAN)
    print_colored(f"🔄 {title}", Colors.CYAN)
    print_colored(separator, Colors.CYAN)


def print_success(message: str) -> None:
    """Print success message."""
    print_colored(f"✅ {message}", Colors.GREEN)


def print_error(message: str) -> None:
    """Print error message."""
    print_colored(f"❌ {message}", Colors.RED)


def print_warning(message: str) -> None:
    """Print warning message."""
    print_colored(f"⚠️  {message}", Colors.YELLOW)


def print_info(message: str) -> None:
    """Print info message."""
    print_colored(f"ℹ️  {message}", Colors.BLUE)


def show_help() -> None:
    """Display comprehensive help information."""
    help_text = f"""
{Colors.BOLD}=== SECURIBENCH TEST EXECUTION SCRIPT (Python) ==={Colors.RESET}

{Colors.BOLD}DESCRIPTION:{Colors.RESET}
    Execute Securibench test suites and save results to disk.
    Runs expensive SVFA analysis on specified suite(s) or all 12 test suites (122 total tests).

{Colors.BOLD}USAGE:{Colors.RESET}
    {sys.argv[0]} [SUITE] [CALLGRAPH] [OPTIONS]

{Colors.BOLD}ARGUMENTS:{Colors.RESET}
    SUITE               Test suite to execute (default: all)
    CALLGRAPH           Call graph algorithm (default: spark)

{Colors.BOLD}OPTIONS:{Colors.RESET}
    --help, -h          Show this help message
    --clean             Remove all previous test data before execution
    --verbose, -v       Enable verbose output

{Colors.BOLD}AVAILABLE TEST SUITES:{Colors.RESET}"""
    
    for suite, desc in SUITE_DESCRIPTIONS.items():
        help_text += f"    {suite:<15} {desc}\n"
    
    help_text += f"\n{Colors.BOLD}CALL GRAPH ALGORITHMS:{Colors.RESET}\n"
    for alg, desc in CALLGRAPH_DESCRIPTIONS.items():
        help_text += f"    {alg:<15} {desc}\n"
    
    help_text += f"""
{Colors.BOLD}EXAMPLES:{Colors.RESET}
    {sys.argv[0]}                           # Execute all test suites with SPARK
    {sys.argv[0]} all                       # Same as above
    {sys.argv[0]} inter                     # Execute Inter suite with SPARK
    {sys.argv[0]} inter cha                 # Execute Inter suite with CHA call graph
    {sys.argv[0]} basic rta                 # Execute Basic suite with RTA call graph
    {sys.argv[0]} inter vta                 # Execute Inter suite with VTA call graph
    {sys.argv[0]} all cha                   # Execute all suites with CHA call graph
    {sys.argv[0]} --clean                   # Clean previous data and execute all tests
    {sys.argv[0]} --help                    # Show this help

{Colors.BOLD}OUTPUT:{Colors.RESET}
    - Test results: target/test-results/securibench/micro/[suite]/
    - JSON files: One per test case with detailed results
    - Execution summary: Console output with timing and pass/fail counts

{Colors.BOLD}PERFORMANCE:{Colors.RESET}
    - Total execution time: ~3-5 minutes for all 122 tests
    - Memory usage: High (Soot framework + call graph construction)
    - Disk usage: ~122 JSON files (~1-2MB total)

{Colors.BOLD}NEXT STEPS:{Colors.RESET}
    After execution, use compute_securibench_metrics.py to generate:
    - Accuracy metrics (TP, FP, FN, Precision, Recall, F-score)
    - CSV reports for analysis
    - Summary statistics

{Colors.BOLD}NOTES:{Colors.RESET}
    - This is the expensive phase (SVFA analysis)
    - Results are cached for fast metrics computation
    - Technical 'success' ≠ SVFA analysis accuracy
    - Individual test results show vulnerability detection accuracy

For detailed documentation, see: USAGE_SCRIPTS.md
"""
    print(help_text)


def clean_test_data(verbose: bool = False) -> int:
    """Clean previous test data and metrics."""
    print_header("CLEANING SECURIBENCH TEST DATA")
    
    test_results_dir = Path("target/test-results")
    metrics_dir = Path("target/metrics")
    temp_logs = Path("/tmp").glob("*executor_*.log")
    
    total_removed = 0
    
    # Clean test results
    if test_results_dir.exists():
        json_files = list(test_results_dir.rglob("*.json"))
        if json_files:
            count = len(json_files)
            if verbose:
                print(f"🗑️  Removing {count} test result files from {test_results_dir}")
            shutil.rmtree(test_results_dir)
            total_removed += count
    
    # Clean metrics
    if metrics_dir.exists():
        metrics_files = list(metrics_dir.glob("securibench_*"))
        if metrics_files:
            count = len(metrics_files)
            if verbose:
                print(f"🗑️  Removing {count} metrics files from {metrics_dir}")
            for file in metrics_files:
                file.unlink()
            total_removed += count
    
    # Clean temporary logs
    temp_log_files = list(temp_logs)
    if temp_log_files:
        count = len(temp_log_files)
        if verbose:
            print(f"🗑️  Removing {count} temporary log files")
        for file in temp_log_files:
            try:
                file.unlink()
            except (OSError, PermissionError):
                pass  # Ignore permission errors for temp files
        total_removed += count
    
    if total_removed > 0:
        print_success(f"Cleanup complete! Removed {total_removed} files")
        print_info("Proceeding with fresh test execution...")
    else:
        print_success("No files to clean - workspace is already clean")
        print_info("Proceeding with test execution...")
    
    return total_removed


def get_suite_name(suite_key: str) -> str:
    """Convert suite key to proper case name for SBT."""
    return suite_key.title().replace('_', '')


def count_test_results(results_dir: Path) -> Tuple[int, int, int]:
    """Count total, passed, and failed tests from JSON result files."""
    if not results_dir.exists():
        return 0, 0, 0
    
    json_files = list(results_dir.glob("*.json"))
    total_count = len(json_files)
    passed_count = 0
    failed_count = 0
    
    for json_file in json_files:
        try:
            with open(json_file, 'r') as f:
                data = json.load(f)
                expected = data.get('expectedVulnerabilities', 0)
                found = data.get('foundVulnerabilities', 0)
                
                # Test passes when expected vulnerabilities equals found vulnerabilities
                if expected == found:
                    passed_count += 1
                else:
                    failed_count += 1
        except (json.JSONDecodeError, IOError):
            # If we can't read the file, count it as failed
            failed_count += 1
    
    return total_count, passed_count, failed_count


def execute_suite(suite_key: str, callgraph: str, verbose: bool = False) -> Tuple[bool, int]:
    """Execute a specific test suite with given call graph algorithm."""
    suite_name = get_suite_name(suite_key)
    
    print_header(f"Executing {suite_name} tests (securibench.micro.{suite_key}) with {callgraph} call graph")
    
    start_time = time.time()
    
    # Build SBT command
    cmd = [
        'sbt',
        f'-Dsecuribench.callgraph={callgraph}',
        'project securibench',
        f'testOnly *Securibench{suite_name}Executor'
    ]
    
    if verbose:
        print_info(f"Executing: {' '.join(cmd)}")
    
    try:
        # Execute SBT command
        result = subprocess.run(
            cmd,
            capture_output=not verbose,
            text=True,
            timeout=3600  # 1 hour timeout
        )
        
        end_time = time.time()
        duration = int(end_time - start_time)
        
        if result.returncode == 0:
            print_success(f"{suite_name} test execution completed (technical success) with {callgraph} call graph")
            
            # Count test results
            results_dir = Path(f"target/test-results/securibench/micro/{suite_key}")
            total_count, passed_count, failed_count = count_test_results(results_dir)
            
            if total_count > 0:
                print_info(f"{total_count} tests executed in {duration}s using {callgraph} call graph ({passed_count} passed, {failed_count} failed)")
                print_info("Individual test results show SVFA analysis accuracy")
                return True, total_count
            else:
                print_warning("No test results found")
                return True, 0
        else:
            print_error(f"{suite_name} test execution failed (technical error)")
            if verbose and result.stderr:
                print(f"Error output: {result.stderr}")
            return False, 0
            
    except subprocess.TimeoutExpired:
        print_error(f"{suite_name} test execution timed out after 1 hour")
        return False, 0
    except Exception as e:
        print_error(f"Failed to execute {suite_name} tests: {e}")
        return False, 0


def main() -> int:
    """Main entry point."""
    parser = argparse.ArgumentParser(
        description='Execute Securibench test suites and save results to disk',
        add_help=False  # We'll handle help ourselves
    )
    
    parser.add_argument('suite', nargs='?', default='all',
                       help='Test suite to execute (default: all)')
    parser.add_argument('callgraph', nargs='?', default='spark',
                       help='Call graph algorithm (default: spark)')
    parser.add_argument('--clean', action='store_true',
                       help='Remove all previous test data before execution')
    parser.add_argument('--verbose', '-v', action='store_true',
                       help='Enable verbose output')
    parser.add_argument('--help', '-h', action='store_true',
                       help='Show this help message')
    
    args = parser.parse_args()
    
    # Handle help
    if args.help:
        show_help()
        return 0
    
    # Validate arguments
    if args.suite not in ['all'] + TEST_SUITES:
        print_error(f"Unknown test suite: {args.suite}")
        print()
        print(f"Available suites: {', '.join(['all'] + TEST_SUITES)}")
        print(f"Usage: {sys.argv[0]} [suite] [callgraph] [--clean|--help]")
        print()
        print(f"For detailed help, run: {sys.argv[0]} --help")
        return 1
    
    if args.callgraph not in CALL_GRAPH_ALGORITHMS:
        print_error(f"Unknown call graph algorithm: {args.callgraph}")
        print()
        print(f"Available call graph algorithms: {', '.join(CALL_GRAPH_ALGORITHMS)}")
        print(f"Usage: {sys.argv[0]} [suite] [callgraph] [--clean|--help]")
        print()
        print(f"For detailed help, run: {sys.argv[0]} --help")
        return 1
    
    # Handle clean option
    if args.clean:
        clean_test_data(args.verbose)
        print()
    
    # Display execution header
    if args.suite == 'all':
        print_colored(f"=== EXECUTING ALL SECURIBENCH TESTS WITH {args.callgraph.upper()} CALL GRAPH ===", Colors.BOLD)
        print(f"This will run SVFA analysis on all test suites using {args.callgraph} call graph and save results to disk.")
    else:
        suite_name = get_suite_name(args.suite)
        print_colored(f"=== EXECUTING {suite_name.upper()} TEST SUITE WITH {args.callgraph.upper()} CALL GRAPH ===", Colors.BOLD)
        print(f"This will run SVFA analysis on the {suite_name} suite using {args.callgraph} call graph and save results to disk.")
    
    print("Use compute_securibench_metrics.py afterwards to generate accuracy metrics.")
    print()
    
    # Execute tests
    start_time = time.time()
    failed_suites = []
    total_tests = 0
    
    if args.suite == 'all':
        print_info("Starting test execution for all suites...")
        print()
        
        for suite_key in TEST_SUITES:
            success, test_count = execute_suite(suite_key, args.callgraph, args.verbose)
            if success:
                total_tests += test_count
            else:
                failed_suites.append(get_suite_name(suite_key))
            print()
    else:
        print_info(f"Starting test execution for {get_suite_name(args.suite)} suite...")
        print()
        
        success, test_count = execute_suite(args.suite, args.callgraph, args.verbose)
        if success:
            total_tests = test_count
        else:
            failed_suites.append(get_suite_name(args.suite))
    
    # Final summary
    end_time = time.time()
    total_duration = int(end_time - start_time)
    
    print_header(f"{'ALL TEST' if args.suite == 'all' else get_suite_name(args.suite).upper() + ' TEST'} EXECUTION COMPLETED WITH {args.callgraph.upper()} CALL GRAPH")
    
    if not failed_suites:
        if args.suite == 'all':
            print_success(f"All test suites executed successfully with {args.callgraph} call graph!")
        else:
            print_success(f"{get_suite_name(args.suite)} test suite executed successfully with {args.callgraph} call graph!")
        print_info(f"Total: {total_tests} tests executed in {total_duration}s using {args.callgraph} call graph")
    else:
        print_warning("Some test suites had execution issues:")
        for failed in failed_suites:
            print(f"   - {failed}")
        print_info(f"Partial: {total_tests} tests executed in {total_duration}s using {args.callgraph} call graph")
    
    print()
    print_info("Test results saved in: target/test-results/securibench/micro/")
    if args.suite == 'all':
        print_info("Next step: Run ./scripts/compute_securibench_metrics.py to generate accuracy metrics")
    else:
        print_info(f"Next step: Run ./scripts/compute_securibench_metrics.py {args.suite} to generate accuracy metrics")
    
    # Return appropriate exit code
    return 1 if failed_suites else 0


if __name__ == '__main__':
    try:
        sys.exit(main())
    except KeyboardInterrupt:
        print_error("\nExecution interrupted by user")
        sys.exit(130)
    except Exception as e:
        print_error(f"Unexpected error: {e}")
        sys.exit(1)
