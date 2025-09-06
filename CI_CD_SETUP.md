# CI/CD Setup for SVFA Project

This document describes the Continuous Integration and Continuous Deployment setup for the SVFA project.

## Overview

The project uses GitHub Actions to automatically run tests on pull requests to the `master` and `develop` branches. This ensures code quality and prevents broken code from being merged.

## Workflow Configuration

### GitHub Actions Workflow

The workflow is defined in `.github/workflows/test_suite.yml` and includes:

- **Triggers**: Pull requests to `master` or `develop` branches
- **Environment**: Ubuntu latest with Java 8
- **Build Tool**: SBT (Scala Build Tool)
- **Caching**: Dependencies are cached for faster builds
- **Tests**: Runs specific test suites

### Test Suites

The workflow runs the following test suites:
TO-DO
1. **All tests**: `sbt test`
2. **SecuribenchAliasingTest**: Tests for aliasing scenarios
3. **SecuribenchArraysTest**: Tests for array-related scenarios

## Branch Protection

To enforce that tests must pass before merging:

1. **Configure branch protection rules** in GitHub:
   - Go to Settings > Branches
   - Add protection rules for `master` and `develop`
   - Enable "Require status checks to pass before merging"
   - Select the "Test" status check

2. **Required settings**:
   - ✅ Require pull request before merging
   - ✅ Require status checks to pass before merging
   - ✅ Require branches to be up to date before merging
   - ✅ Restrict pushes that create files that override the above settings

## Local Testing

Before pushing changes, you can run the same tests locally:

```bash
# Run specific test suites
sbt "testOnly br.unb.cic.securibench.deprecated.SecuribenchTestSuite"
sbt "testOnly br.unb.cic.android.AndroidTaintBenchSuiteExperimentXTest"

# Or use the provided script
./scripts/test-local.sh
```

## Workflow Steps

1. **Checkout**: Clones the repository
2. **Setup Java**: Installs Java 8 (Temurin distribution)
3. **Cache Dependencies**: Caches SBT dependencies for faster builds
4. **Run Tests**: Executes specific test suites
5. **Status Check**: Reports pass/fail status to GitHub

## Troubleshooting

### Common Issues

1. **Tests failing locally but passing in CI**:
   - Ensure you're using Java 8
   - Clear SBT cache: `sbt clean`
   - Update dependencies: `sbt update`

2. **Tests passing locally but failing in CI**:
   - Check for environment-specific dependencies
   - Verify test data files are committed
   - Check for absolute paths in tests

3. **Workflow not triggering**:
   - Ensure the workflow file is in `.github/workflows/`
   - Check that the branch names match exactly (`master`, `develop`)
   - Verify the pull request is targeting the correct branches

### Debugging

- Check the Actions tab in GitHub for detailed logs
- Use the local test script to verify commands work locally
- Review the workflow file for syntax errors

## Maintenance

- **Update dependencies**: Regularly update SBT and Scala versions
- **Monitor test performance**: Watch for slow tests that might timeout
- **Review workflow**: Periodically review and optimize the workflow

## Security Considerations

- The workflow runs on GitHub-hosted runners
- No sensitive data should be committed to the repository
- Use GitHub Secrets for any required API keys or credentials 