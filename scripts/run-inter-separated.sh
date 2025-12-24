#!/bin/bash

# Quick script to run Inter tests with separated phases

echo "🚀 Running Inter tests (separated phases)..."
echo

# Phase 1: Execute
echo "📋 Phase 1: Executing tests..."
sbt "project securibench" "testOnly *SecuribenchInterExecutor"

# Phase 2: Metrics
echo "📊 Phase 2: Computing metrics..."
sbt "project securibench" "testOnly *SecuribenchInterMetrics"

echo "✅ Inter tests complete!"
