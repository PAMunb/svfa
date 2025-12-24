#!/usr/bin/env python3
"""
SVFA Securibench Test Runner (Python Implementation)

A Python alternative to the bash scripts with enhanced features:
- Better argument parsing and validation
- JSON result processing
- Progress indicators
- Structured error handling
- Easy extensibility

Minimal dependencies: Only Python 3.6+ standard library
"""

import argparse
import json
import os
import subprocess
import sys
import time
from datetime import datetime
from pathlib import Path
from typing import Dict, List, Optional, Tuple


class SVFAConfig:
    """Configuration for SVFA test execution."""
    
    # Test suites configuration
    SUITES = {
        'inter': {'name': 'Inter', 'tests': 14, 'package': 'securibench.micro.inter'},
        'basic': {'name': 'Basic', 'tests': 42, 'package': 'securibench.micro.basic'},
        'aliasing': {'name': 'Aliasing', 'tests': 6, 'package': 'securibench.micro.aliasing'},
        'arrays': {'name': 'Arrays', 'tests': 10, 'package': 'securibench.micro.arrays'},
        'collections': {'name': 'Collections', 'tests': 14, 'package': 'securibench.micro.collections'},
        'datastructures': {'name': 'Datastructures', 'tests': 6, 'package': 'securibench.micro.datastructures'},
        'factories': {'name': 'Factories', 'tests': 3, 'package': 'securibench.micro.factories'},
        'pred': {'name': 'Pred', 'tests': 9, 'package': 'securibench.micro.pred'},
        'reflection': {'name': 'Reflection', 'tests': 4, 'package': 'securibench.micro.reflection'},
        'sanitizers': {'name': 'Sanitizers', 'tests': 6, 'package': 'securibench.micro.sanitizers'},
        'session': {'name': 'Session', 'tests': 3, 'package': 'securibench.micro.session'},
        'strong_updates': {'name': 'StrongUpdates', 'tests': 5, 'package': 'securibench.micro.strongupdates'}
    }
    
    # Call graph algorithms configuration
    CALL_GRAPHS = {
        'spark': {
            'name': 'SPARK',
            'description': 'SPARK points-to analysis (default, most precise)',
            'speed': '⚡⚡',
            'precision': '⭐⭐⭐⭐'
        },
        'cha': {
            'name': 'CHA', 
            'description': 'Class Hierarchy Analysis (fastest, least precise)',
            'speed': '⚡⚡⚡⚡⚡',
            'precision': '⭐'
        },
        'spark_library': {
            'name': 'SPARK_LIBRARY',
            'description': 'SPARK with library support (comprehensive coverage)',
            'speed': '⚡',
            'precision': '⭐⭐⭐⭐⭐'
        },
        'rta': {
            'name': 'RTA',
            'description': 'Rapid Type Analysis via SPARK (fast, moderately precise)',
            'speed': '⚡⚡⚡⚡',
            'precision': '⭐⭐'
        },
        'vta': {
            'name': 'VTA',
            'description': 'Variable Type Analysis via SPARK (balanced speed/precision)',
            'speed': '⚡⚡⚡',
            'precision': '⭐⭐⭐'
        }
    }


class SVFARunner:
    """Main SVFA test runner with enhanced Python features."""
    
    def __init__(self):
        self.config = SVFAConfig()
        self.start_time = time.time()
        
    def create_parser(self) -> argparse.ArgumentParser:
        """Create argument parser with comprehensive options."""
        parser = argparse.ArgumentParser(
            description='SVFA Securibench Test Runner (Python Implementation)',
            formatter_class=argparse.RawDescriptionHelpFormatter,
            epilog=self._get_epilog()
        )
        
        parser.add_argument(
            'suite',
            nargs='?',
            default='all',
            choices=list(self.config.SUITES.keys()) + ['all'],
            help='Test suite to execute (default: all)'
        )
        
        parser.add_argument(
            'callgraph',
            nargs='?', 
            default='spark',
            choices=list(self.config.CALL_GRAPHS.keys()),
            help='Call graph algorithm (default: spark)'
        )
        
        parser.add_argument(
            '--clean',
            action='store_true',
            help='Remove all previous test data before execution'
        )
        
        parser.add_argument(
            '--mode',
            choices=['execute', 'metrics', 'both'],
            default='execute',
            help='Execution mode: execute tests, compute metrics, or both (default: execute)'
        )
        
        parser.add_argument(
            '--verbose', '-v',
            action='store_true',
            help='Enable verbose output'
        )
        
        parser.add_argument(
            '--dry-run',
            action='store_true',
            help='Show what would be executed without running'
        )
        
        parser.add_argument(
            '--list-suites',
            action='store_true',
            help='List all available test suites and exit'
        )
        
        parser.add_argument(
            '--list-callgraphs',
            action='store_true',
            help='List all available call graph algorithms and exit'
        )
        
        return parser
    
    def _get_epilog(self) -> str:
        """Get help epilog with examples and algorithm info."""
        return """
Examples:
  %(prog)s                          # Execute all suites with SPARK
  %(prog)s inter                    # Execute Inter suite with SPARK  
  %(prog)s inter cha                # Execute Inter suite with CHA
  %(prog)s basic rta --verbose      # Execute Basic suite with RTA (verbose)
  %(prog)s --clean                  # Clean and execute all suites
  %(prog)s inter spark --mode both # Execute Inter tests and compute metrics
  %(prog)s --list-suites           # Show available test suites
  %(prog)s --list-callgraphs       # Show available call graph algorithms

Call Graph Algorithms:
  spark        - SPARK points-to analysis (⚡⚡ speed, ⭐⭐⭐⭐ precision)
  cha          - Class Hierarchy Analysis (⚡⚡⚡⚡⚡ speed, ⭐ precision)  
  spark_library- SPARK with library support (⚡ speed, ⭐⭐⭐⭐⭐ precision)
  rta          - Rapid Type Analysis (⚡⚡⚡⚡ speed, ⭐⭐ precision)
  vta          - Variable Type Analysis (⚡⚡⚡ speed, ⭐⭐⭐ precision)

For detailed documentation, see: USAGE_SCRIPTS.md
        """
    
    def print_info(self, message: str, prefix: str = "ℹ️"):
        """Print informational message."""
        print(f"{prefix} {message}")
    
    def print_success(self, message: str):
        """Print success message."""
        print(f"✅ {message}")
    
    def print_error(self, message: str):
        """Print error message."""
        print(f"❌ {message}", file=sys.stderr)
    
    def print_progress(self, message: str):
        """Print progress message."""
        print(f"🔄 {message}")
    
    def list_suites(self):
        """List all available test suites."""
        print("Available Test Suites:")
        print("=" * 50)
        total_tests = 0
        for key, info in self.config.SUITES.items():
            print(f"  {key:<15} {info['name']:<15} ({info['tests']:>2} tests)")
            total_tests += info['tests']
        print("=" * 50)
        print(f"  Total: {len(self.config.SUITES)} suites, {total_tests} tests")
    
    def list_callgraphs(self):
        """List all available call graph algorithms."""
        print("Available Call Graph Algorithms:")
        print("=" * 70)
        for key, info in self.config.CALL_GRAPHS.items():
            speed = info['speed']
            precision = info['precision']
            print(f"  {key:<15} {speed:<8} {precision:<10} {info['description']}")
        print("=" * 70)
        print("Legend: ⚡ = Speed, ⭐ = Precision (more symbols = better)")
    
    def clean_test_data(self, verbose: bool = False):
        """Clean previous test data and metrics."""
        self.print_progress("Cleaning previous test data...")
        
        paths_to_clean = [
            "target/test-results",
            "target/metrics"
        ]
        
        total_removed = 0
        for path in paths_to_clean:
            if os.path.exists(path):
                if verbose:
                    self.print_info(f"Removing {path}")
                try:
                    import shutil
                    shutil.rmtree(path)
                    total_removed += 1
                except Exception as e:
                    self.print_error(f"Failed to remove {path}: {e}")
        
        if total_removed > 0:
            self.print_success(f"Cleanup complete! Removed {total_removed} directories")
        else:
            self.print_success("No files to clean - workspace is already clean")
    
    def execute_sbt_command(self, command: List[str], verbose: bool = False, dry_run: bool = False) -> Tuple[bool, str]:
        """Execute SBT command with proper error handling."""
        cmd_str = ' '.join(command)
        
        if dry_run:
            self.print_info(f"DRY RUN: Would execute: {cmd_str}")
            return True, "Dry run - not executed"
        
        if verbose:
            self.print_info(f"Executing: {cmd_str}")
        
        try:
            result = subprocess.run(
                command,
                capture_output=not verbose,
                text=True,
                timeout=3600  # 1 hour timeout
            )
            
            success = result.returncode == 0
            output = result.stdout if result.stdout else result.stderr
            
            return success, output
            
        except subprocess.TimeoutExpired:
            self.print_error("Command timed out after 1 hour")
            return False, "Timeout"
        except Exception as e:
            self.print_error(f"Command execution failed: {e}")
            return False, str(e)
    
    def execute_suite(self, suite_key: str, callgraph: str, verbose: bool = False, dry_run: bool = False) -> bool:
        """Execute a specific test suite."""
        suite_info = self.config.SUITES[suite_key]
        suite_name = suite_info['name']
        
        self.print_progress(f"Executing {suite_name} tests with {callgraph} call graph...")
        
        start_time = time.time()
        
        command = [
            'sbt',
            f'-Dsecuribench.callgraph={callgraph}',
            'project securibench',
            f'testOnly *Securibench{suite_name}Executor'
        ]
        
        success, output = self.execute_sbt_command(command, verbose, dry_run)
        
        duration = int(time.time() - start_time)
        
        if success:
            self.print_success(f"{suite_name} test execution completed with {callgraph} call graph")
            
            # Count results
            results_dir = Path(f"target/test-results/securibench/micro/{suite_key}")
            if results_dir.exists():
                json_files = list(results_dir.glob("*.json"))
                test_count = len(json_files)
                self.print_info(f"{test_count} tests executed in {duration}s using {callgraph} call graph")
            
            return True
        else:
            self.print_error(f"{suite_name} test execution failed")
            if verbose and output:
                print(output)
            return False
    
    def compute_metrics(self, suite_key: str, callgraph: str, verbose: bool = False, dry_run: bool = False) -> bool:
        """Compute metrics for a specific test suite."""
        suite_info = self.config.SUITES[suite_key]
        suite_name = suite_info['name']
        
        self.print_progress(f"Computing metrics for {suite_name} tests with {callgraph} call graph...")
        
        command = [
            'sbt',
            f'-Dsecuribench.callgraph={callgraph}',
            'project securibench',
            f'testOnly *Securibench{suite_name}Metrics'
        ]
        
        success, output = self.execute_sbt_command(command, verbose, dry_run)
        
        if success:
            self.print_success(f"{suite_name} metrics computation completed")
            return True
        else:
            self.print_error(f"{suite_name} metrics computation failed")
            if verbose and output:
                print(output)
            return False
    
    def run(self, args):
        """Main execution method."""
        # Handle list options
        if args.list_suites:
            self.list_suites()
            return 0
        
        if args.list_callgraphs:
            self.list_callgraphs()
            return 0
        
        # Handle clean option
        if args.clean:
            self.clean_test_data(args.verbose)
        
        # Determine suites to run
        if args.suite == 'all':
            suites_to_run = list(self.config.SUITES.keys())
        else:
            suites_to_run = [args.suite]
        
        self.print_info(f"Running {len(suites_to_run)} suite(s) with {args.callgraph} call graph")
        if args.dry_run:
            self.print_info("DRY RUN MODE - No actual execution")
        
        # Execute suites
        failed_suites = []
        total_tests = 0
        
        for suite_key in suites_to_run:
            suite_info = self.config.SUITES[suite_key]
            
            # Execute tests
            if args.mode in ['execute', 'both']:
                success = self.execute_suite(suite_key, args.callgraph, args.verbose, args.dry_run)
                if not success:
                    failed_suites.append(suite_key)
                    continue
                
                total_tests += suite_info['tests']
            
            # Compute metrics
            if args.mode in ['metrics', 'both']:
                success = self.compute_metrics(suite_key, args.callgraph, args.verbose, args.dry_run)
                if not success:
                    failed_suites.append(suite_key)
        
        # Summary
        total_time = int(time.time() - self.start_time)
        
        if failed_suites:
            self.print_error(f"Some suites failed: {', '.join(failed_suites)}")
            return 1
        else:
            self.print_success(f"All suites completed successfully!")
            self.print_info(f"Total: {total_tests} tests executed in {total_time}s using {args.callgraph} call graph")
            return 0


def main():
    """Main entry point."""
    runner = SVFARunner()
    parser = runner.create_parser()
    args = parser.parse_args()
    
    try:
        return runner.run(args)
    except KeyboardInterrupt:
        runner.print_error("Interrupted by user")
        return 130
    except Exception as e:
        runner.print_error(f"Unexpected error: {e}")
        return 1


if __name__ == '__main__':
    sys.exit(main())
