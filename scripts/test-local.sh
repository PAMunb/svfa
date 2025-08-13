#!/bin/bash

# Local test script to verify the GitHub Actions workflow commands
echo "Running local tests to verify GitHub Actions workflow..."

echo "1. Running br.unb.cic.securibench.deprecated.SecuribenchTestSuite specifically..."
sbt "testOnly br.unb.cic.securibench.deprecated.SecuribenchTestSuite"

echo "All tests completed!"