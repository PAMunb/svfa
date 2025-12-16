#!/usr/bin/env python3
"""
SVFA Securibench Metrics Computer (Python Version)

This script computes accuracy metrics for Securibench test suites with automatic test execution.
Automatically executes missing tests before computing metrics.

Dependencies: Python 3.6+ (standard library only)
"""

import argparse
import subprocess
import sys
import os
import json
import csv
import shutil
from pathlib import Path
from datetime import datetime
from typing import List, Dict, Optional, Tuple, Any

# Import configuration from test runner
from run_securibench_tests import (
    CALL_GRAPH_ALGORITHMS, TEST_SUITES, SUITE_DESCRIPTIONS, CALLGRAPH_DESCRIPTIONS,
    Colors, print_colored, print_header, print_success, print_error, print_warning, print_info,
    clean_test_data, get_suite_name
)


class TestResult:
    """Represents a single test result."""
    
    def __init__(self, test_name: str, expected: int, found: int, passed: bool):
        self.test_name = test_name
        self.expected = expected
        self.found = found
        self.passed = passed
    
    @classmethod
    def from_json(cls, json_data: Dict[str, Any]) -> 'TestResult':
        """Create TestResult from JSON data."""
        return cls(
            test_name=json_data.get('testName', 'Unknown'),
            expected=json_data.get('expectedVulnerabilities', 0),
            found=json_data.get('foundVulnerabilities', 0),
            passed=json_data.get('passed', False)
        )


class SuiteMetrics:
    """Represents metrics for a test suite."""
    
    def __init__(self, suite_name: str):
        self.suite_name = suite_name
        self.results: List[TestResult] = []
        self.tp = 0  # True Positives
        self.fp = 0  # False Positives  
        self.fn = 0  # False Negatives
        self.tn = 0  # True Negatives
    
    def add_result(self, result: TestResult) -> None:
        """Add a test result and update metrics."""
        self.results.append(result)
        
        # Calculate TP, FP, FN based on expected vs found vulnerabilities
        expected = result.expected
        found = result.found
        
        if expected > 0:  # Test case has vulnerabilities
            if found >= expected:
                self.tp += expected
                self.fp += max(0, found - expected)
            else:
                self.tp += found
                self.fn += expected - found
        else:  # Test case has no vulnerabilities
            if found > 0:
                self.fp += found
            else:
                self.tn += 1
    
    @property
    def precision(self) -> float:
        """Calculate precision: TP / (TP + FP)."""
        denominator = self.tp + self.fp
        return self.tp / denominator if denominator > 0 else 0.0
    
    @property
    def recall(self) -> float:
        """Calculate recall: TP / (TP + FN)."""
        denominator = self.tp + self.fn
        return self.tp / denominator if denominator > 0 else 0.0
    
    @property
    def f_score(self) -> float:
        """Calculate F-score: 2 * (precision * recall) / (precision + recall)."""
        p, r = self.precision, self.recall
        return 2 * (p * r) / (p + r) if (p + r) > 0 else 0.0
    
    @property
    def accuracy(self) -> float:
        """Calculate accuracy: (TP + TN) / (TP + TN + FP + FN)."""
        total = self.tp + self.tn + self.fp + self.fn
        return (self.tp + self.tn) / total if total > 0 else 0.0
    
    def print_summary(self) -> None:
        """Print formatted metrics summary."""
        print(f"\n{Colors.BOLD}=== {self.suite_name.upper()} METRICS SUMMARY ==={Colors.RESET}")
        print(f"Tests executed: {len(self.results)}")
        print(f"Passed: {sum(1 for r in self.results if r.passed)}")
        print(f"Failed: {sum(1 for r in self.results if not r.passed)}")
        print()
        print(f"True Positives (TP):  {self.tp}")
        print(f"False Positives (FP): {self.fp}")
        print(f"False Negatives (FN): {self.fn}")
        print(f"True Negatives (TN):  {self.tn}")
        print()
        print(f"Precision: {self.precision:.3f}")
        print(f"Recall:    {self.recall:.3f}")
        print(f"F-score:   {self.f_score:.3f}")
        print(f"Accuracy:  {self.accuracy:.3f}")


def show_help() -> None:
    """Display comprehensive help information."""
    help_text = f"""
{Colors.BOLD}=== SECURIBENCH METRICS COMPUTATION SCRIPT (Python) ==={Colors.RESET}

{Colors.BOLD}DESCRIPTION:{Colors.RESET}
    Compute accuracy metrics for Securibench test suites with automatic test execution.
    Automatically executes missing tests before computing metrics.

{Colors.BOLD}USAGE:{Colors.RESET}
    {sys.argv[0]} [SUITE] [CALLGRAPH] [OPTIONS]

{Colors.BOLD}ARGUMENTS:{Colors.RESET}
    SUITE               Test suite to process (default: all)
    CALLGRAPH           Call graph algorithm (default: spark)

{Colors.BOLD}OPTIONS:{Colors.RESET}
    --help, -h          Show this help message
    --clean             Remove all previous test results and metrics
    --verbose, -v       Enable verbose output
    --csv-only          Only generate CSV report, skip console output

{Colors.BOLD}AVAILABLE TEST SUITES:{Colors.RESET}"""
    
    for suite, desc in SUITE_DESCRIPTIONS.items():
        help_text += f"    {suite:<15} {desc}\n"
    
    help_text += f"\n{Colors.BOLD}CALL GRAPH ALGORITHMS:{Colors.RESET}\n"
    for alg, desc in CALLGRAPH_DESCRIPTIONS.items():
        help_text += f"    {alg:<15} {desc}\n"
    
    help_text += f"""
{Colors.BOLD}EXAMPLES:{Colors.RESET}
    {sys.argv[0]}                           # Process all suites with SPARK (auto-execute missing tests)
    {sys.argv[0]} all                       # Same as above
    {sys.argv[0]} basic                     # Process only Basic suite with SPARK
    {sys.argv[0]} inter cha                 # Process Inter suite with CHA call graph
    {sys.argv[0]} basic rta                 # Process Basic suite with RTA call graph
    {sys.argv[0]} inter vta                 # Process Inter suite with VTA call graph
    {sys.argv[0]} all spark_library         # Process all suites with SPARK_LIBRARY
    {sys.argv[0]} --clean                   # Remove all previous test data
    {sys.argv[0]} --help                    # Show this help

{Colors.BOLD}OUTPUT FILES:{Colors.RESET}
    - CSV report: target/metrics/securibench_metrics_[callgraph]_YYYYMMDD_HHMMSS.csv
    - Summary report: target/metrics/securibench_summary_[callgraph]_YYYYMMDD_HHMMSS.txt

{Colors.BOLD}AUTO-EXECUTION:{Colors.RESET}
    - Automatically detects missing test results
    - Executes missing tests using run_securibench_tests.py
    - Processes results and generates metrics
    - No need to run tests separately!

{Colors.BOLD}PERFORMANCE:{Colors.RESET}
    - First run may take several minutes (executing tests)
    - Subsequent runs are fast (uses cached results)
    - Use '--clean' to ensure fresh results
    - Technical 'success' ≠ SVFA analysis accuracy

For detailed documentation, see: USAGE_SCRIPTS.md
"""
    print(help_text)


def load_test_results(suite_key: str) -> List[TestResult]:
    """Load test results from JSON files."""
    results_dir = Path(f"target/test-results/securibench/micro/{suite_key}")
    results = []
    
    if not results_dir.exists():
        return results
    
    for json_file in results_dir.glob("*.json"):
        try:
            with open(json_file, 'r') as f:
                data = json.load(f)
                result = TestResult.from_json(data)
                results.append(result)
        except (json.JSONDecodeError, KeyError, IOError) as e:
            print_warning(f"Failed to load {json_file}: {e}")
    
    return results


def execute_missing_tests(suite_key: str, callgraph: str, verbose: bool = False) -> bool:
    """Execute tests for a suite if results are missing."""
    results_dir = Path(f"target/test-results/securibench/micro/{suite_key}")
    
    if not results_dir.exists() or not list(results_dir.glob("*.json")):
        suite_name = get_suite_name(suite_key)
        print_warning(f"No test results found for {suite_name}")
        print_info("Auto-executing missing tests...")
        
        # Execute tests using the Python test runner
        cmd = [
            sys.executable,
            'scripts/run_securibench_tests.py',
            suite_key,
            callgraph
        ]
        
        if verbose:
            cmd.append('--verbose')
        
        try:
            result = subprocess.run(cmd, capture_output=not verbose, text=True, timeout=3600)
            
            if result.returncode == 0:
                print_success(f"Test execution completed for {suite_name}")
                return True
            else:
                print_error(f"Failed to execute {suite_name} tests")
                if verbose and result.stderr:
                    print(f"Error output: {result.stderr}")
                return False
        except subprocess.TimeoutExpired:
            print_error(f"Test execution timed out for {suite_name}")
            return False
        except Exception as e:
            print_error(f"Failed to execute tests for {suite_name}: {e}")
            return False
    
    return True


def compute_suite_metrics(suite_key: str, callgraph: str, verbose: bool = False) -> Optional[SuiteMetrics]:
    """Compute metrics for a specific test suite."""
    suite_name = get_suite_name(suite_key)
    
    print_header(f"Processing {suite_name} metrics")
    
    # Check if results exist, execute tests if missing
    if not execute_missing_tests(suite_key, callgraph, verbose):
        return None
    
    # Load test results
    results = load_test_results(suite_key)
    
    if not results:
        print_error(f"No test results found for {suite_name} after execution")
        return None
    
    # Compute metrics
    metrics = SuiteMetrics(suite_name)
    for result in results:
        metrics.add_result(result)
    
    if verbose:
        metrics.print_summary()
    
    print_success(f"Processed {len(results)} test results for {suite_name}")
    return metrics


def create_csv_report(all_metrics: List[SuiteMetrics], callgraph: str, timestamp: str) -> Path:
    """Create CSV report with detailed metrics."""
    output_dir = Path("target/metrics")
    output_dir.mkdir(parents=True, exist_ok=True)
    
    csv_file = output_dir / f"securibench_metrics_{callgraph}_{timestamp}.csv"
    
    with open(csv_file, 'w', newline='') as f:
        writer = csv.writer(f)
        
        # Write header
        writer.writerow([
            'Suite', 'Test_Count', 'Passed', 'Failed', 
            'TP', 'FP', 'FN', 'TN',
            'Precision', 'Recall', 'F_Score', 'Accuracy'
        ])
        
        # Write data for each suite
        for metrics in all_metrics:
            passed_count = sum(1 for r in metrics.results if r.passed)
            failed_count = len(metrics.results) - passed_count
            
            writer.writerow([
                metrics.suite_name,
                len(metrics.results),
                passed_count,
                failed_count,
                metrics.tp,
                metrics.fp,
                metrics.fn,
                metrics.tn,
                f"{metrics.precision:.3f}",
                f"{metrics.recall:.3f}",
                f"{metrics.f_score:.3f}",
                f"{metrics.accuracy:.3f}"
            ])
    
    return csv_file


def create_summary_report(all_metrics: List[SuiteMetrics], callgraph: str, timestamp: str) -> Path:
    """Create text summary report."""
    output_dir = Path("target/metrics")
    output_dir.mkdir(parents=True, exist_ok=True)
    
    summary_file = output_dir / f"securibench_summary_{callgraph}_{timestamp}.txt"
    
    with open(summary_file, 'w') as f:
        f.write(f"SECURIBENCH METRICS SUMMARY - {callgraph.upper()} CALL GRAPH\n")
        f.write(f"Generated: {datetime.now().strftime('%Y-%m-%d %H:%M:%S')}\n")
        f.write("=" * 60 + "\n\n")
        
        total_tests = sum(len(m.results) for m in all_metrics)
        total_passed = sum(sum(1 for r in m.results if r.passed) for m in all_metrics)
        total_failed = total_tests - total_passed
        
        f.write(f"OVERALL SUMMARY:\n")
        f.write(f"Total test suites: {len(all_metrics)}\n")
        f.write(f"Total tests: {total_tests}\n")
        f.write(f"Total passed: {total_passed}\n")
        f.write(f"Total failed: {total_failed}\n\n")
        
        # Aggregate metrics
        total_tp = sum(m.tp for m in all_metrics)
        total_fp = sum(m.fp for m in all_metrics)
        total_fn = sum(m.fn for m in all_metrics)
        total_tn = sum(m.tn for m in all_metrics)
        
        total_precision = total_tp / (total_tp + total_fp) if (total_tp + total_fp) > 0 else 0.0
        total_recall = total_tp / (total_tp + total_fn) if (total_tp + total_fn) > 0 else 0.0
        total_f_score = 2 * (total_precision * total_recall) / (total_precision + total_recall) if (total_precision + total_recall) > 0 else 0.0
        
        f.write(f"AGGREGATE METRICS:\n")
        f.write(f"True Positives: {total_tp}\n")
        f.write(f"False Positives: {total_fp}\n")
        f.write(f"False Negatives: {total_fn}\n")
        f.write(f"True Negatives: {total_tn}\n")
        f.write(f"Precision: {total_precision:.3f}\n")
        f.write(f"Recall: {total_recall:.3f}\n")
        f.write(f"F-Score: {total_f_score:.3f}\n\n")
        
        f.write("PER-SUITE BREAKDOWN:\n")
        f.write("-" * 60 + "\n")
        
        for metrics in all_metrics:
            passed = sum(1 for r in metrics.results if r.passed)
            failed = len(metrics.results) - passed
            
            f.write(f"\n{metrics.suite_name}:\n")
            f.write(f"  Tests: {len(metrics.results)} (Passed: {passed}, Failed: {failed})\n")
            f.write(f"  TP: {metrics.tp}, FP: {metrics.fp}, FN: {metrics.fn}, TN: {metrics.tn}\n")
            f.write(f"  Precision: {metrics.precision:.3f}, Recall: {metrics.recall:.3f}, F-Score: {metrics.f_score:.3f}\n")
    
    return summary_file


def main() -> int:
    """Main entry point."""
    parser = argparse.ArgumentParser(
        description='Compute accuracy metrics for Securibench test suites',
        add_help=False  # We'll handle help ourselves
    )
    
    parser.add_argument('suite', nargs='?', default='all',
                       help='Test suite to process (default: all)')
    parser.add_argument('callgraph', nargs='?', default='spark',
                       help='Call graph algorithm (default: spark)')
    parser.add_argument('--clean', action='store_true',
                       help='Remove all previous test results and metrics')
    parser.add_argument('--verbose', '-v', action='store_true',
                       help='Enable verbose output')
    parser.add_argument('--csv-only', action='store_true',
                       help='Only generate CSV report, skip console output')
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
        return 0
    
    # Display header
    print_colored(f"=== SECURIBENCH METRICS COMPUTATION WITH {args.callgraph.upper()} CALL GRAPH ===", Colors.BOLD)
    print()
    
    # Determine which suites to process
    suites_to_process = TEST_SUITES if args.suite == 'all' else [args.suite]
    
    # Process each suite
    all_metrics = []
    failed_suites = []
    
    for suite_key in suites_to_process:
        metrics = compute_suite_metrics(suite_key, args.callgraph, args.verbose)
        if metrics:
            all_metrics.append(metrics)
        else:
            failed_suites.append(get_suite_name(suite_key))
    
    if not all_metrics:
        print_error("No metrics could be computed")
        return 1
    
    # Generate reports
    timestamp = datetime.now().strftime("%Y%m%d_%H%M%S")
    
    try:
        csv_file = create_csv_report(all_metrics, args.callgraph, timestamp)
        summary_file = create_summary_report(all_metrics, args.callgraph, timestamp)
        
        print()
        print_success(f"CSV report created: {csv_file}")
        print_success(f"Summary report created: {summary_file}")
        
        # Display console summary unless csv-only mode
        if not args.csv_only:
            print()
            for metrics in all_metrics:
                metrics.print_summary()
        
        if failed_suites:
            print()
            print_warning("Some suites could not be processed:")
            for suite in failed_suites:
                print(f"   - {suite}")
        
        return 1 if failed_suites else 0
        
    except Exception as e:
        print_error(f"Failed to generate reports: {e}")
        return 1


if __name__ == '__main__':
    try:
        sys.exit(main())
    except KeyboardInterrupt:
        print_error("\nExecution interrupted by user")
        sys.exit(130)
    except Exception as e:
        print_error(f"Unexpected error: {e}")
        sys.exit(1)
