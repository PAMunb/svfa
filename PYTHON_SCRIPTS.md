# Python Scripts for SVFA

## 🐍 Overview

SVFA now provides Python alternatives to the bash scripts for better maintainability, cross-platform compatibility, and enhanced features.

## 📋 Available Scripts

### 1. **Test Execution**: `run_securibench_tests.py`
- **Purpose**: Execute Securibench test suites and save results to disk
- **Replaces**: `run-securibench-tests.sh`
- **Dependencies**: Python 3.6+ (standard library only)

### 2. **Metrics Computation**: `compute_securibench_metrics.py`
- **Purpose**: Compute accuracy metrics with automatic test execution
- **Replaces**: `compute-securibench-metrics.sh`
- **Dependencies**: Python 3.6+ (standard library only)

## 🚀 Usage

### Basic Usage (Same as Bash Scripts)

```bash
# Execute tests
./scripts/run_securibench_tests.py inter rta
./scripts/run_securibench_tests.py all cha

# Compute metrics
./scripts/compute_securibench_metrics.py inter rta
./scripts/compute_securibench_metrics.py all
```

### Enhanced Python Features

```bash
# Verbose output with detailed progress
./scripts/run_securibench_tests.py inter spark --verbose

# CSV-only mode (no console output)
./scripts/compute_securibench_metrics.py all spark --csv-only

# Clean and execute in one command
./scripts/run_securibench_tests.py all --clean --verbose
```

## ✨ Advantages of Python Scripts

### **Maintainability**
- **Structured Code**: Clear class hierarchies and function organization
- **Type Hints**: Optional type annotations for better code quality
- **Error Handling**: Proper exception handling vs bash error codes
- **Testing**: Easy to unit test individual functions

### **Enhanced Features**
- **Colored Output**: Better visual feedback with ANSI colors
- **Progress Tracking**: Clear progress indicators and timing
- **Better Argument Parsing**: Robust argument validation with `argparse`
- **JSON Processing**: Native JSON handling for test results
- **CSV Generation**: Built-in CSV creation with proper formatting

### **Cross-Platform Compatibility**
- **Windows Support**: Works identically on Windows, macOS, Linux
- **Path Handling**: Proper path handling with `pathlib`
- **Process Management**: Reliable subprocess execution

### **Developer Experience**
- **IDE Support**: Full autocomplete, debugging, refactoring support
- **Linting**: Can use pylint, flake8, mypy for code quality
- **Documentation**: Built-in help with rich formatting

## 📊 Feature Comparison

| Feature | Bash Scripts | Python Scripts |
|---------|-------------|----------------|
| **Execution Speed** | ⚡⚡⚡ | ⚡⚡ |
| **Maintainability** | ⭐⭐ | ⭐⭐⭐⭐⭐ |
| **Error Handling** | ⭐⭐ | ⭐⭐⭐⭐⭐ |
| **Cross-Platform** | ⭐⭐ | ⭐⭐⭐⭐⭐ |
| **Dependencies** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐⭐ |
| **Features** | ⭐⭐⭐ | ⭐⭐⭐⭐⭐ |
| **Testing** | ⭐⭐ | ⭐⭐⭐⭐⭐ |
| **IDE Support** | ⭐⭐ | ⭐⭐⭐⭐⭐ |

## 🔧 Technical Implementation

### Minimal Dependencies Approach
```python
# Only standard library imports
import argparse
import subprocess
import json
import csv
from pathlib import Path
from datetime import datetime
from typing import List, Dict, Optional
```

### Structured Error Handling
```python
try:
    result = subprocess.run(cmd, capture_output=True, text=True, timeout=3600)
    if result.returncode == 0:
        print_success("Test execution completed")
        return True
    else:
        print_error("Test execution failed")
        return False
except subprocess.TimeoutExpired:
    print_error("Test execution timed out")
    return False
except Exception as e:
    print_error(f"Unexpected error: {e}")
    return False
```

### Rich Output Formatting
```python
class Colors:
    RESET = '\033[0m'
    BOLD = '\033[1m'
    RED = '\033[91m'
    GREEN = '\033[92m'
    YELLOW = '\033[93m'

def print_success(message: str) -> None:
    print(f"{Colors.GREEN}✅ {message}{Colors.RESET}")
```

## 🧪 Testing and Validation

### Unit Testing (Future Enhancement)
```python
import unittest
from unittest.mock import patch, MagicMock

class TestSecuribenchRunner(unittest.TestCase):
    def test_suite_validation(self):
        # Test suite name validation
        pass
    
    def test_callgraph_validation(self):
        # Test call graph algorithm validation
        pass
    
    @patch('subprocess.run')
    def test_sbt_execution(self, mock_run):
        # Test SBT command execution
        pass
```

### Integration Testing
```bash
# Test basic functionality
python3 -m pytest scripts/test_python_scripts.py

# Test with different Python versions
python3.6 scripts/run_securibench_tests.py --help
python3.8 scripts/run_securibench_tests.py --help
python3.10 scripts/run_securibench_tests.py --help
```

## 🔄 Migration Strategy

### Phase 1: Parallel Deployment (Current)
- ✅ Both bash and Python scripts available
- ✅ Same command-line interface
- ✅ Same output format and file locations
- ✅ Users can choose preferred version

### Phase 2: Enhanced Features (Future)
- 🔄 Add progress bars with `tqdm` (optional dependency)
- 🔄 Add configuration file support
- 🔄 Add parallel test execution
- 🔄 Add test result caching and incremental runs

### Phase 3: Deprecation (Future)
- 🔄 Update documentation to prefer Python scripts
- 🔄 Add deprecation warnings to bash scripts
- 🔄 Eventually remove bash scripts

## 📝 Usage Examples

### Development Workflow
```bash
# Quick development cycle with verbose output
./scripts/run_securibench_tests.py basic rta --verbose
./scripts/compute_securibench_metrics.py basic rta --verbose

# Clean slate for fresh analysis
./scripts/run_securibench_tests.py all --clean
./scripts/compute_securibench_metrics.py all
```

### Research Workflow
```bash
# Compare different call graph algorithms
for cg in spark cha rta vta spark_library; do
    ./scripts/run_securibench_tests.py inter $cg
    ./scripts/compute_securibench_metrics.py inter $cg --csv-only
done

# Analyze results
ls target/metrics/securibench_metrics_*_*.csv
```

### CI/CD Integration
```bash
#!/bin/bash
# CI script using Python versions
set -e

# Run tests with timeout protection
timeout 3600 ./scripts/run_securibench_tests.py all spark || exit 1

# Generate metrics
./scripts/compute_securibench_metrics.py all spark --csv-only || exit 1

# Upload results
aws s3 cp target/metrics/ s3://results-bucket/ --recursive
```

## 🐛 Troubleshooting

### Common Issues

**1. Python Version Compatibility**
```bash
# Check Python version
python3 --version  # Should be 3.6+

# Use specific Python version
python3.8 scripts/run_securibench_tests.py --help
```

**2. Import Errors**
```bash
# Ensure scripts are in the correct directory
ls -la scripts/run_securibench_tests.py
ls -la scripts/compute_securibench_metrics.py

# Check file permissions
chmod +x scripts/*.py
```

**3. SBT Integration**
```bash
# Test SBT availability
sbt --version

# Check Java version
java -version  # Should be Java 8
```

## 🔮 Future Enhancements

### Planned Features
- **Progress Bars**: Visual progress indication with `tqdm`
- **Parallel Execution**: Run multiple suites simultaneously
- **Configuration Files**: YAML/JSON configuration support
- **Result Caching**: Incremental analysis and smart caching
- **Web Dashboard**: Optional web interface for results
- **Docker Support**: Containerized execution environment

### Extensibility
```python
# Plugin architecture for custom analyzers
class CustomAnalyzer:
    def analyze(self, results: List[TestResult]) -> Dict[str, Any]:
        # Custom analysis logic
        pass

# Register custom analyzer
register_analyzer('custom', CustomAnalyzer())
```

---

## 📚 Related Documentation

- [USAGE_SCRIPTS.md](USAGE_SCRIPTS.md) - Comprehensive script usage guide
- [CALL_GRAPH_ALGORITHMS.md](CALL_GRAPH_ALGORITHMS.md) - Call graph algorithm details
- [README.md](README.md) - Main project documentation

For questions or issues with Python scripts, please create an issue with the `python-scripts` label.
