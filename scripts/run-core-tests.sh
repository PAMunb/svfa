#!/bin/bash
# Run core SVFA tests (fast, no external dependencies)

set -e

# Colors for output
GREEN='\033[0;32m'
BLUE='\033[0;34m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo -e "${BLUE}SVFA Core Module Test Runner${NC}"
echo ""

# Ensure Java 8 is available
if command -v sdkman-init.sh >/dev/null 2>&1; then
    echo -e "${YELLOW}Setting up Java 8 environment...${NC}"
    source ~/.sdkman/bin/sdkman-init.sh
    sdk use java 8.0.422-amzn
fi

echo -e "${GREEN}Running core SVFA tests (no external dependencies required)...${NC}"
echo ""

# Use SBT command alias for consistency
sbt testCore

echo ""
echo -e "${GREEN}Core tests completed!${NC}"
