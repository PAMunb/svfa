# CustomMetrics Trait Documentation

## Overview

The `CustomMetrics` trait provides a comprehensive framework for collecting, storing, and reporting test-related metrics. It is especially useful for evaluating the effectiveness of tools or algorithms in terms of detection (e.g., vulnerabilities, warnings, or test results).

---

## Internal Data Structure

- **metricsByTest**:  
  A mutable map (`mutable.Map[String, Metrics]`) that associates each test (by its name) with a `Metrics` object. This object holds all the relevant counters for that test.

- **getOrCreateMetrics(testName: String): Metrics**:  
  Helper method to fetch the `Metrics` object for a test, or create a new one if it doesn't exist.

---

## Metric Reporting Methods

These methods incrementally update the metrics for a given test:

- **reportTruePositives(testName, truePositives)**:  
  Adds to the count of true positives for the test.

- **reportFalsePositives(testName, falsePositives)**:  
  Adds to the count of false positives.

- **reportFalseNegatives(testName, falseNegatives)**:  
  Adds to the count of false negatives.

- **reportTrueNegatives(testName)**:  
  Increments the count of true negatives by 1.

- **reportPassedTest(testName)**:  
  Increments the count of passed tests by 1.

- **reportFailedTest(testName)**:  
  Increments the count of failed tests by 1.

- **reportExpected(testName, expected)**:  
  Adds to the count of expected findings (e.g., expected vulnerabilities).

- **reportFound(testName, found)**:  
  Adds to the count of found findings (e.g., detected vulnerabilities).

---

## Automated Metric Computation

- **compute(expected, found, testName)**:  
  Given the expected and found counts for a test, this method:
  - Updates the expected and found counts.
  - Determines the test outcome:
    - If both are zero: test passed, increment true negatives.
    - If both are equal and nonzero: test passed, increment true positives.
    - If found > expected: test failed, increment false positives.
    - If expected > found: test failed, increment false negatives.

---

## Metric Calculation Methods

These methods compute standard evaluation metrics, either for a specific test or aggregated across all tests:

- **precision(testName: String = null): Double**  
  \( \text{Precision} = \frac{TP}{TP + FP} \)  
  Returns the precision for a test or overall (rounded to 2 decimal places).

- **recall(testName: String = null): Double**  
  \( \text{Recall} = \frac{TP}{TP + FN} \)  
  Returns the recall for a test or overall (rounded to 2 decimal places).

- **f1Score(testName: String = null): Double**  
  \( F1 = 2 \times \frac{\text{Precision} \times \text{Recall}}{\text{Precision} + \text{Recall}} \)  
  Returns the F1 score for a test or overall (rounded to 2 decimal places).

- **passRate(testName: String = null): Double**  
  \( \text{Pass Rate} = \frac{\text{Passed}}{\text{Passed} + \text{Failed}} \times 100 \)  
  Returns the pass rate as a percentage for a test or overall.

- **vulnerabilities(testName: String = null): Int**  
  Returns the expected vulnerabilities for a test or the sum for all tests.

- **vulnerabilitiesFound(testName: String = null): Int**  
  Returns the found vulnerabilities for a test or the sum for all tests.

---

## Access and Reporting

- **metricsFor(testName: String): Metrics**  
  Returns the `Metrics` object for a given test.

- **report(testName: String): Unit**  
  Prints a detailed report for a specific test, including:
  - Number of failed/passed tests
  - Pass rate
  - Expected vs. found warnings
  - TP, FP, FN, TN counts
  - Precision, recall, F1 score

- **reportAll(): Unit**  
  Prints a report for every test in the map.

- **reportSummary(reportName: String): Unit**  
  Prints a Markdown-style summary table for all tests, including:
  - Test name, found/expected counts, status (pass/fail), TP, FP, FN, precision, recall, F1 score
  - Totals and overall metrics at the end

---

## Design and Usage

- The trait is designed to be mixed into test suites or analysis tools that need to track and report on detection metrics.
- It supports both per-test and aggregate reporting.
- All metrics are rounded to two decimal places for readability.
- The reporting methods are designed for console output, with Markdown-style tables for easy copy-pasting into reports.

---

## Extensibility

- The trait relies on a `Metrics` class (not shown here) to store the actual counters.
- The design is modular, so you can extend or override methods for custom reporting or additional metrics.

---

## Summary

`CustomMetrics` is a reusable trait for tracking, computing, and reporting detailed test metrics (TP, FP, FN, TN, precision, recall, F1, pass rate, etc.) on a per-test and aggregate basis, with built-in support for both programmatic access and human-readable reporting. 