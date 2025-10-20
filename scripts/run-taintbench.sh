#!/bin/bash
# Run TaintBench tests (Android malware analysis)

set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

usage() {
    echo -e "${BLUE}TaintBench Android Malware Test Runner${NC}"
    echo ""
    echo "This script runs SVFA analysis on Android malware samples from TaintBench."
    echo ""
    echo "Usage: $0 [OPTIONS] [TEST_NAME]"
    echo ""
    echo "Environment Variables (required for actual testing):"
    echo "  ANDROID_SDK          Path to Android SDK"
    echo "  TAINT_BENCH          Path to TaintBench APK dataset"
    echo ""
    echo "Options:"
    echo "  --help               Show this help message"
    echo "  --check-env          Only check environment setup"
    echo ""
    echo "Test Names:"
    echo "  roidsec              Run RoidsecTest specifically"
    echo "  android              Run all Android malware tests"
    echo "  all                  Run all TaintBench tests (default)"
    echo ""
    echo "Examples:"
    echo "  export ANDROID_SDK=/Users/username/Library/Android/sdk"
    echo "  export TAINT_BENCH=/Users/username/taintbench"
    echo "  $0 roidsec"
    echo ""
}

check_environment() {
    echo -e "${BLUE}Checking TaintBench environment...${NC}"
    echo ""
    
    # Check Java 8
    if command -v sdkman-init.sh >/dev/null 2>&1; then
        echo -e "${YELLOW}Setting up Java 8 environment...${NC}"
        source ~/.sdkman/bin/sdkman-init.sh
        sdk use java 8.0.422-amzn
    fi
    
    java_version=$(java -version 2>&1 | head -n 1)
    echo "Java version: $java_version"
    
    # Check environment variables
    if [[ -z "$ANDROID_SDK" ]]; then
        echo -e "${YELLOW}Warning: ANDROID_SDK environment variable not set${NC}"
        echo "Tests will be skipped. Set with: export ANDROID_SDK=/path/to/android/sdk"
        ENV_MISSING=true
    else
        echo -e "${GREEN}ANDROID_SDK: $ANDROID_SDK${NC}"
        if [[ ! -d "$ANDROID_SDK" ]]; then
            echo -e "${RED}Warning: ANDROID_SDK directory does not exist${NC}"
        fi
    fi
    
    if [[ -z "$TAINT_BENCH" ]]; then
        echo -e "${YELLOW}Warning: TAINT_BENCH environment variable not set${NC}"
        echo "Tests will be skipped. Set with: export TAINT_BENCH=/path/to/taintbench"
        ENV_MISSING=true
    else
        echo -e "${GREEN}TAINT_BENCH: $TAINT_BENCH${NC}"
        if [[ ! -d "$TAINT_BENCH" ]]; then
            echo -e "${RED}Warning: TAINT_BENCH directory does not exist${NC}"
        fi
    fi
    
    echo ""
    if [[ "$ENV_MISSING" == "true" ]]; then
        echo -e "${YELLOW}Note: Tests will run but Android analysis will be skipped due to missing environment variables.${NC}"
    else
        echo -e "${GREEN}Environment setup complete!${NC}"
    fi
    echo ""
}

run_tests() {
    local test_name="$1"
    
    case "$test_name" in
        "roidsec")
            echo -e "${GREEN}Running RoidsecTest specifically...${NC}"
            sbt testRoidsec
            ;;
        "AndroidTaintBenchSuiteExperiment1")
            echo -e "${GREEN}Running AndroidTaintBenchSuiteExperiment1Test specifically...${NC}"
            sbt AndroidTaintBenchSuiteExperiment1Test
            ;;
        "AndroidTaintBenchSuiteExperiment2")
            echo -e "${GREEN}Running AndroidTaintBenchSuiteExperiment2Test specifically...${NC}"
            sbt AndroidTaintBenchSuiteExperiment2Test
            ;;
        "android")
            echo -e "${GREEN}Running all Android malware tests...${NC}"
            sbt testAndroid
            ;;
        "all"|"")
            echo -e "${GREEN}Running all TaintBench tests...${NC}"
            sbt testTaintbench
            ;;
        *)
            echo -e "${RED}Unknown test name: $test_name${NC}"
            usage
            exit 1
            ;;
    esac
}

# Parse arguments
case "$1" in
    "--help")
        usage
        exit 0
        ;;
    "--check-env")
        check_environment
        exit 0
        ;;
    *)
        check_environment
        if [[ "$1" != "--check-env" ]]; then
            run_tests "$1"
            echo ""
            echo -e "${GREEN}TaintBench tests completed!${NC}"
        fi
        ;;
esac
