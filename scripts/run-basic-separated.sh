#!/bin/bash

# Quick script to run Basic tests with separated phases

echo "🚀 Running Basic tests (separated phases)..."
echo

# Phase 1: Execute
echo "📋 Phase 1: Executing tests..."
sbt "project securibench" "testOnly *SecuribenchBasicExecutor"

# Phase 2: Metrics
echo "📊 Phase 2: Computing metrics..."
sbt "project securibench" "testOnly *SecuribenchBasicMetrics"

echo "✅ Basic tests complete!"
