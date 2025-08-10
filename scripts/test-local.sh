#!/bin/bash

# Local test script to verify the GitHub Actions workflow commands
echo "Running local tests to verify GitHub Actions workflow..."

echo "1. Running all tests..."
sbt test

echo "2. Running SecuribenchAliasingTest specifically..."
sbt "testOnly br.unb.cic.securibench.suite.SecuribenchAliasingTest"

echo "3. Running SecuribenchArraysTest specifically..."
sbt "testOnly br.unb.cic.securibench.suite.SecuribenchArraysTest"

echo "All tests completed!" 