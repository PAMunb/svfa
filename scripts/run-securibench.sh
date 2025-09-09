#!/bin/bash
# Run Securibench tests (Java security vulnerability analysis)

set -e

# Colors for output
GREEN='\033[0;32m'
BLUE='\033[0;34m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo -e "${BLUE}SecuriBench Security Test Runner${NC}"
echo ""

# Ensure Java 8 is available
if command -v sdkman-init.sh >/dev/null 2>&1; then
    echo -e "${YELLOW}Setting up Java 8 environment...${NC}"
    source ~/.sdkman/bin/sdkman-init.sh
    sdk use java 8.0.422-amzn
fi

echo -e "${GREEN}Running SecuriBench security vulnerability tests...${NC}"
echo "This will analyze 93 Java security test cases for vulnerabilities like:"
echo "  • XSS (Cross-Site Scripting)"
echo "  • SQL Injection"
echo "  • Buffer Overflows"
echo "  • Data Structure Vulnerabilities"
echo ""

# Use SBT command alias for consistency
sbt "testOnly br.unb.cic.securibench.deprecated.SecuribenchTestSuite"

echo ""
echo -e "${GREEN}SecuriBench tests completed!${NC}"
echo "Check the output above for vulnerability analysis results."
