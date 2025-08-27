#!/bin/bash

# Local test script to verify the GitHub Actions workflow commands
echo "Running local tests to verify GitHub Actions workflow..."

echo "1. Running tests from SecuriBench..."
sbt "testOnly br.unb.cic.securibench.deprecated.SecuribenchTestSuite"

# echo "2. Running tests from TaintBench..."
# sbt "testOnly br.unb.cic.android.AndroidTaintBenchSuiteExperiment1Test"
# sbt "testOnly br.unb.cic.android.AndroidTaintBenchSuiteExperiment2Test"

echo "All tests completed!"