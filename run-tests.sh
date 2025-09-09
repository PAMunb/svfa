#!/bin/bash

# SVFA Test Runner
# This script provides convenient ways to run tests with environment variables

set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Function to print usage
usage() {
    echo -e "${BLUE}SVFA Test Runner${NC}"
    echo ""
    echo "Usage: $0 [OPTIONS] [TEST_NAME]"
    echo ""
    echo "Options:"
    echo "  --android-sdk PATH    Path to Android SDK (required)"
    echo "  --taint-bench PATH    Path to TaintBench dataset (required)"
    echo "  --help               Show this help message"
    echo ""
    echo "Test Names:"
    echo "  roidsec              Run RoidsecTest"
    echo "  android              Run all Android tests"
    echo "  all                  Run all tests"
    echo ""
    echo "Examples:"
    echo "  $0 --android-sdk /opt/android-sdk --taint-bench /opt/taintbench roidsec"
    echo "  $0 --android-sdk \$ANDROID_HOME --taint-bench \$HOME/taintbench android"
    echo ""
    echo "Environment Variables (alternative to command line options):"
    echo "  ANDROID_SDK          Path to Android SDK"
    echo "  TAINT_BENCH          Path to TaintBench dataset"
}

# Default values
ANDROID_SDK=""
TAINT_BENCH=""
TEST_NAME=""

# Parse command line arguments
while [[ $# -gt 0 ]]; do
    case $1 in
        --android-sdk)
            ANDROID_SDK="$2"
            shift 2
            ;;
        --taint-bench)
            TAINT_BENCH="$2"
            shift 2
            ;;
        --help)
            usage
            exit 0
            ;;
        roidsec|AndroidTaintBenchSuiteExperiment1|AndroidTaintBenchSuiteExperiment2|android|all)
            TEST_NAME="$1"
            shift
            ;;
        *)
            echo -e "${RED}Unknown option: $1${NC}"
            usage
            exit 1
            ;;
    esac
done

# Use environment variables as fallback
if [[ -z "$ANDROID_SDK" && -n "$ANDROID_SDK_ENV" ]]; then
    ANDROID_SDK="$ANDROID_SDK_ENV"
fi

if [[ -z "$TAINT_BENCH" && -n "$TAINT_BENCH_ENV" ]]; then
    TAINT_BENCH="$TAINT_BENCH_ENV"
fi

# Check if required parameters are provided
if [[ -z "$ANDROID_SDK" ]]; then
    echo -e "${RED}Error: Android SDK path is required${NC}"
    echo "Use --android-sdk /path/to/sdk or set ANDROID_SDK environment variable"
    exit 1
fi

if [[ -z "$TAINT_BENCH" ]]; then
    echo -e "${RED}Error: TaintBench path is required${NC}"
    echo "Use --taint-bench /path/to/taintbench or set TAINT_BENCH environment variable"
    exit 1
fi

if [[ -z "$TEST_NAME" ]]; then
    echo -e "${RED}Error: Test name is required${NC}"
    usage
    exit 1
fi

# Validate paths
if [[ ! -d "$ANDROID_SDK" ]]; then
    echo -e "${RED}Error: Android SDK path does not exist: $ANDROID_SDK${NC}"
    exit 1
fi

if [[ ! -d "$TAINT_BENCH" ]]; then
    echo -e "${RED}Error: TaintBench path does not exist: $TAINT_BENCH${NC}"
    exit 1
fi

# Export environment variables
export ANDROID_SDK="$ANDROID_SDK"
export TAINT_BENCH="$TAINT_BENCH"

echo -e "${GREEN}Running SVFA tests with:${NC}"
echo -e "  ${BLUE}Android SDK:${NC} $ANDROID_SDK"
echo -e "  ${BLUE}TaintBench:${NC} $TAINT_BENCH"
echo -e "  ${BLUE}Test:${NC} $TEST_NAME"
echo ""

# Run the appropriate test
case $TEST_NAME in
    roidsec)
        echo -e "${YELLOW}Running RoidsecTest...${NC}"
        sbt "taintbench/testOnly br.unb.cic.android.RoidsecTest"
        ;;
    AndroidTaintBenchSuiteExperiment1)
        echo -e "${GREEN}Running AndroidTaintBenchSuiteExperiment1Test specifically...${NC}"
        sbt AndroidTaintBenchSuiteExperiment1Test
        ;;
    AndroidTaintBenchSuiteExperiment2)
        echo -e "${GREEN}Running AndroidTaintBenchSuiteExperiment2Test specifically...${NC}"
        sbt AndroidTaintBenchSuiteExperiment2Test
        ;;
    android)
        echo -e "${YELLOW}Running all Android tests...${NC}"
        sbt "taintbench/testOnly br.unb.cic.android.*"
        ;;
    all)
        echo -e "${YELLOW}Running all tests...${NC}"
        sbt taintbench/test
        ;;
    *)
        echo -e "${RED}Unknown test name: $TEST_NAME${NC}"
        usage
        exit 1
        ;;
esac

echo -e "${GREEN}Tests completed successfully!${NC}"






