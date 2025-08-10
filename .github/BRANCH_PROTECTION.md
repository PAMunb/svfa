# Branch Protection Rules

To ensure that tests pass before merging pull requests, configure the following branch protection rules in your GitHub repository:

## Required Settings

1. **Go to Settings > Branches** in your GitHub repository
2. **Add rule** for both `master` and `develop` branches
3. Configure the following settings:

### For `master` branch:
- ✅ **Require a pull request before merging**
- ✅ **Require status checks to pass before merging**
  - Select the "Test" status check (from the GitHub Actions workflow)
- ✅ **Require branches to be up to date before merging**
- ✅ **Restrict pushes that create files that override the above settings**

### For `develop` branch:
- ✅ **Require a pull request before merging**
- ✅ **Require status checks to pass before merging**
  - Select the "Test" status check (from the GitHub Actions workflow)
- ✅ **Require branches to be up to date before merging**
- ✅ **Restrict pushes that create files that override the above settings**

## Workflow Details

The GitHub Actions workflow (`.github/workflows/test.yml`) will:

1. Trigger on pull requests to `master` or `develop` branches
2. Set up Java 8 and SBT
3. Cache dependencies for faster builds
4. Run all tests with `sbt test`
5. Run specific test suites:
   - `SecuribenchAliasingTest`
   - `SecuribenchArraysTest`

## Manual Configuration Steps

1. Navigate to your repository on GitHub
2. Go to **Settings** > **Branches**
3. Click **Add rule** for each protected branch
4. Enter the branch name (`master` or `develop`)
5. Configure the protection rules as described above
6. Click **Create** or **Save changes**

This ensures that no code can be merged without passing the required tests. 