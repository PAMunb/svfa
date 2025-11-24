# SVFA (Sparse Value Flow Analysis) implementation based on Soot

This is a scala implementation of a framework that builds a sparse-value flow graph using Soot.

## Status

   * Experimental.

## Architecture

This project follows a **modular architecture** with three focused modules:

- **`core`**: Essential SVFA framework + Android analysis support
- **`securibench`**: Java security vulnerability analysis benchmarks  
- **`taintbench`**: Android malware analysis benchmarks
- **`scripts`**: snippets of code use to automatize metric computation 

## Quick Start

### For Library Users

#### Using svfa-core in Scala Projects

Add to your `build.sbt`:
```scala
resolvers += Resolver.githubPackages("PAMunb", "svfa")
libraryDependencies += "br.unb.cic" %% "svfa-core" % "0.6.1-SNAPSHOT"
```

#### Using svfa-core in Java/Maven Projects

Add to your `pom.xml`:
```xml
<repositories>
  <repository>
    <id>github</id>
    <url>https://maven.pkg.github.com/PAMunb/svfa</url>
  </repository>
</repositories>

<dependency>
  <groupId>br.unb.cic</groupId>
  <artifactId>svfa-core_2.12</artifactId>
  <version>0.6.1-SNAPSHOT</version>
</dependency>
```

#### Using svfa-core in Gradle Projects

Add to your `build.gradle`:
```gradle
repositories {
    maven {
        url = uri("https://maven.pkg.github.com/PAMunb/svfa")
        credentials {
            username = project.findProperty("gpr.user") ?: System.getenv("USERNAME")
            password = project.findProperty("gpr.key") ?: System.getenv("TOKEN")
        }
    }
}

dependencies {
    implementation 'br.unb.cic:svfa-core_2.12:0.6.1-SNAPSHOT'
}
```

### For Developers

#### Installation

- Install Java 8 (required)
- Install sbt (Scala Build Tool)
- Clone this repository:
```bash
git clone https://github.com/PAMunb/svfa
cd svfa
```

#### Building

```bash
# Compile all modules
sbt compileAll

# Run tests for all modules
sbt testCore
sbt testSecuribench
sbt testTaintbench
```

#### Publishing (Maintainers)

1. **Setup GitHub Token**:
   ```bash
   git config --global github.token YOUR_GITHUB_TOKEN
   ```
   or
   ```bash
   export GITHUB_TOKEN="your_personal_access_token"
   ```

2. **Publish Core Module**:
   ```bash
   sbt publishCore
   ```

3. **Publish All Modules Locally**:
   ```bash
   sbt publishAllLocal
   ```

## API Usage

Implement a class that extends the `JSVFA class` (i.e., scala/br/unb/cic/svfa/JSVFATest.scala)

You must provide implementations for:

* `getEntryPoints()` - Set up the "main" methods (returns List of Soot methods)
* `sootClassPath()` - Set up the soot classpath (returns String)  
* `analyze(unit)` - Identify node types (source, sink, simple node) in the graph

The framework implements a flexible approach (`trait`) to store the set of node types (source, sink)
Then, this trait is available to be manipulated in method `analyze(unit)`

```scala
trait SecuribenchSpec {
  val sinkList: Seq[String] = List()

  val sourceList: Seq[String] = List()
```

### Example Usage

```scala
import br.unb.cic.soot.JSVFATest

class MyAnalysis extends JSVFATest {
  override def getEntryPoints(): List[SootMethod] = {
    // Define your entry points
  }
  
  override def sootClassPath(): String = {
    // Return your classpath
  }
  
  override def analyze(unit: Unit): NodeType = {
    // Analyze statements and return Source, Sink, or SimpleNode
  }
}
```

## Available Commands

| Command               | Description                                      |
|-----------------------|--------------------------------------------------|
| `sbt testCore`        | Run core SVFA tests                              |
| `sbt testSecuribench` | Run security vulnerability tests (93 test cases) |
| `sbt testTaintbench`  | Run Android malware tests                        |
| `sbt testRoidsec`     | Run specific Roidsec test                        |
| `sbt compileAll`      | Compile all modules                              |
| `sbt publishCore`     | Publish core module to GitHub Packages           |
| `sbt publishAllLocal` | Publish all modules to local Maven repository    |

## Scripts

Enhanced scripts are available for convenient testing:

```bash
# Core tests (no dependencies)
./scripts/run-core-tests.sh

# Security vulnerability analysis  
./scripts/run-securibench.sh

# Android malware analysis (requires environment setup)
./scripts/run-taintbench.sh --help
./scripts/run-taintbench.sh --check-env
./scripts/run-taintbench.sh roidsec
```

## Installation (Ubuntu/Debian)

For Ubuntu/Debian systems:
```bash
# Install Java 8
sudo apt install openjdk-8-jre-headless openjdk-8-jdk

# Install sbt
echo "deb https://repo.scala-sbt.org/scalasbt/debian all main" | sudo tee /etc/apt/sources.list.d/sbt.list
curl -sL "https://keyserver.ubuntu.com/pks/lookup?op=get&search=0x2EE0EA64E40A89B84B2DF73499E82A75642AC823" | sudo apt-key add
sudo apt-get update && sudo apt-get install sbt

# Clone and build
git clone https://github.com/PAMunb/svfa
cd svfa
sbt compileAll
```


## Benchmark

This project integrates 2 well-known benchmarks.

### Securibench

This benchmark was integrated because it is also used in the [FlowDroid Project](https://github.com/secure-software-engineering/FlowDroid) and this integration
is implemented in `securibench` module.

The result are presented in a table that contains the following information.

- **Expected:** The amount of taint flows presented by TAINTBENCH
- **Actual:** The amount of taint flows detected by JSVFA
- **Status:** If the test PASS OR FAIL
- **TP:** True Positive
- **FP:** False Positive
- **Precision:** TP/(TP + FP)
- **Recall:** TP/P
- **F-score:** (2 x Precision x Recall)/(Precision + Recall)

#### Old metrics (v0.3.0)

> failed: 59, passed: 63 of 122 tests - (51.63%)

|      Test      | Found | Expected | Status | TP | FP | FN | Precision | Recall | F-score | Pass Rate |
|:--------------:|:-----:|:--------:|:------:|:--:|:--:|:---|:---------:|:------:|:-------:|:---------:|
|    aliasing    |  10   |    12    |  2/6   | 8  | 1  | 3  |   0.89    |  0.73  |  0.80   |   33.33   |
|     arrays     |   0   |    9     |  1/10  | 0  | 0  | 9  |   0.00    |  0.00  |  0.00   |    10     |
|     basic      |  60   |    60    | 36/42  | 52 | 3  | 3  |   0.95    |  0.95  |  0.95   |   85.71   |
|  collections   |   3   |    15    |  1/14  | 1  | 1  | 13 |   0.50    |  0.07  |  0.12   |   7.14    |
| datastructures |   7   |    5     |  4/6   | 4  | 2  | 0  |   0.67    |  1.00  |  0.80   |   66.67   |
|   factories    |   4   |    3     |  2/3   | 2  | 1  | 0  |   0.67    |  1.00  |  0.80   |   66.67   |
|     inter      |  10   |    18    |  7/14  | 8  | 0  | 8  |   1.00    |  0.50  |  0.67   |    50     |
|    session     |   0   |    3     |  0/3   | 0  | 0  | 3  |   0.00    |  0.00  |  0.00   |     0     |
| strong_updates |   0   |    1     |  4/5   | 0  | 0  | 1  |   0.00    |  0.00  |  0.00   |    80     |
|      Pred      |   8   |    5     |  6/9   | 5  | 3  | 0  |   0.63    |  1.00  |  0.77   |  66.67%   |
|   Reflection   |   0   |    4     |  0/4   | 0  | 0  | 4  |   0.00    |  0.00  |  0.00   |    0%     |
|   Sanitizers   |   0   |    4     |  0/6   | 0  | 0  | 6  |   0.00    |  0.00  |  0.00   |    0%     |
|     TOTAL      |  102  |   139    | 63/122 | 80 | 11 | 50 |   0.88    |  0.62  |  0.72   |   51.64   | 

To have detailed information about each test category run, [see here.](modules/securibench/src/docs-metrics/jsvfa/jsvfa-metrics-v0.3.0.md) (*computed in June 2023.*)

#### New metrics (v0.6.1)

> failed: 47, passed: 75 of 122 tests - (61.48%)

|      Test      | Found | Expected | Status | TP | FP | FN | Precision | Recall | F-score | Pass Rate |
|:--------------:|:-----:|:--------:|:------:|:--:|:--:|:---|:---------:|:------:|:-------:|:---------:|
|    Aliasing    |  10   |    12    |  2/6   | 8  | 1  | 3  |   0.89    |  0.73  |  0.80   |  33.33%   |
|     Arrays     |  11   |    9     |  5/10  | 5  | 4  | 2  |   0.56    |  0.71  |  0.63   |    50%    |
|     Basic      |  57   |    60    | 38/42  | 55 | 1  | 4  |   0.98    |  0.93  |  0.95   |  90.48%   |
|  Collections   |   8   |    15    |  5/14  | 5  | 1  | 8  |   0.83    |  0.38  |  0.52   |  35.71%   |
| Datastructures |   5   |    5     |  4/6   | 4  | 1  | 1  |   0.80    |  0.80  |  0.80   |  66.67%   |
|   Factories    |   4   |    3     |  2/3   | 2  | 1  | 0  |   0.67    |  1.00  |  0.80   |  66.67%   |
|     Inter      |  12   |    18    |  8/14  | 9  | 0  | 6  |   1.00    |  0.60  |  0.75   |  57.14%   |
|    Session     |   0   |    3     |  0/3   | 0  | 0  | 3  |   0.00    |  0.00  |  0.00   |    0%     |
| StrongUpdates  |   3   |    1     |  3/5   | 1  | 2  | 0  |   0.33    |  1.00  |  0.50   |    60%    |
|      Pred      |   8   |    5     |  6/9   | 5  | 3  | 0  |   0.63    |  1.00  |  0.77   |  66.67%   |
|   Reflection   |   0   |    4     |  0/4   | 0  | 0  | 4  |   0.00    |  0.00  |  0.00   |    0%     |
|   Sanitizers   |   2   |    6     |  2/6   | 1  | 0  | 4  |   1.00    |  0.20  |  0.33   |  33.33%   |
|     TOTAL      |  120  |   141    | 75/122 | 95 | 14 | 35 |   0.87    |  0.73  |  0.79   |  61.48%   |

To have detailed information about each test category run, [see here.](modules/securibench/src/docs-metrics/jsvfa/jsvfa-metrics-v0.6.1.md) (*computed in November 2025.*)

##### Common issues
From the 47 tests, we have categorized nine (9) issues.

[i] **Wrong counting**: Some tests from the Securibench benchmark are incorrectly labeled, leading to wrong expected values.
We have mapped three cases: `(6.38%)`
- Aliasing2
- Aliasing4
- Inter4

[ii] Array Indexes: The actual implementation is unable to recognize tainted in specific indexes from an array. Currently, it marks all the array as tainted.
We have mapped six cases: `(12.77%)`
- Aliasing3
- Arrays2
- Arrays5
- Arrays8
- Arrays9
- Arrays10

[iii] Support Class Missing: Some tests use methods from securibench that are not mocked.
We have mapped seven cases: `(14.89%)`
- Basic31
- Basic36
- Basic38
- Session1
- Session2
- Session3
- Sanitizers5

[iv] Missing Context: The logic for handling context is not entirely flawless, resulting in certain edge cases that lead to bugs such as:
  [a] Nested structures as HashMap, LinkedList, and others,
  [b] Loop statement as "for" or "while",
  [c] Parameters passed in the constructor.
We have mapped 17 cases: `(36.17%)`
- Aliasing5
- Basic42
- Collections3
- Collections5
- Collections6
- Collections7
- Collections8
- Collections9
- Collections10
- Collections12
- Collections13
- Datastructures4
- Datastructures5
- Factories3
- Inter5
- Inter9
- Inter12

[v] Reflection: The current implementation does not address the reflection feature,
We have mapped 5 cases: `(10.64%)`
- Inter6
- Refl1
- Refl2
- Refl3
- Refl4

[vi] Global variables references: There are unaddressed edge cases regarding the handling of the definition of global variables.,
We have mapped two cases: `(4.26%)`
- StrongUpdates3
- StrongUpdates5


[vii] Path for conditional: The current logic always evaluates two paths for a conditional, regardless of whether the condition is set to True or False,
We have mapped three cases: `(6.38%)`
- Pred3
- Pred6
- Pred7

[viii] Sanitizer method: The current implementation fails to deal with the intermediary method utilized by the sanitizer.
We have mapped three cases: `(6.38%)`
- Sanitizers2
- Sanitizers4
- Sanitizers6

[ix] Flaky
We have mapped one cases: `(2.13%)`
- Inter11

#### FLOWDROID 

- failed: 36, passed: 67 of 103 tests. `(65.05%)`

| Test           | Found | Expected | Status | TP | FP | FN | Precision | Recall | F1   | Pass Rate |
|----------------|-------|----------|--------|----|----|----|-----------|--------|------|-----------|
| Aliasing       | 11    | 11       | 4/6    | 9  | 1  | 1  | 0.90      | 0.90   | 0.90 | 66.67%    |
| Arrays         | 14    | 9        | 6/10   | 6  | 5  | 0  | 0.55      | 1.00   | 0.71 | 60%       |
| Basic          | 38    | 61       | 26/42  | 33 | 1  | 24 | 0.97      | 0.58   | 0.73 | 61.90%    |
| Collections    | 14    | 15       | 11/14  | 12 | 1  | 2  | 0.92      | 0.86   | 0.89 | 78.57%    |
| Datastructures | 5     | 5        | 4/6    | 3  | 1  | 1  | 0.75      | 0.75   | 0.75 | 66.67%    |
| Factories      | 1     | 3        | 1/3    | 1  | 0  | 2  | 1.00      | 0.33   | 0.50 | 33.33%    |
| Inter          | 15    | 18       | 11/14  | 13 | 0  | 3  | 1.00      | 0.81   | 0.90 | 78.57%    |
| Session        | 0     | 3        | 0/3    | 0  | 0  | 3  | 0.00      | 0.00   | 0.00 | 0%        |
| StrongUpdates  | 0     | 1        | 4/5    | 0  | 0  | 1  | 0.00      | 0.00   | 0.00 | 80%       |
| Pred           | -     | -        | -      | -  | -  | -  | -         | -      | -    | -         | * NO EXECUTED
| Reflection     | -     | -        | -      | -  | -  | -  | -         | -      | -    | -         | * NO EXECUTED
| Sanitizers     | -     | -        | -      | -  | -  | -  | -         | -      | -    | -         | * NO EXECUTED
| **TOTAL**      | 98    | 126      | 67/103 | 77 | 9  | 37 | 0.90      | 0.68   | 0.77 | 65.05%    |

To have detailed information about each group of tests run, [see here.](modules/securibench/src/docs-metrics/flowdroid/flowdroid-metrics.md)

#### JOANA

> failed: 37, passed: 85 of 122 tests. `(69.67%)`

| Test           | Found | Expected | Status | TP | FP | FN | Precision | Recall | F1    | Pass rate |
|----------------|-------|----------|--------|----|----|----|-----------|--------|-------|-----------|
| Aliasing       | 6     | 11       | 2/6    | 2  | 2  | 7  | 0.50      | 0.22   | 0.31  | 33.33%    |
| Arrays         | 10    | 9        | 9/10   | 9  | 1  | 0  | 0.90      | 1.00   | 0.95  | 90%       |
| Basic          | 45    | 61       | 25/42  | 26 | 6  | 22 | 0.81      | 0.54   | 0.65  | 59.52%    |
| Collections    | 15    | 14       | 13/14  | 14 | 1  | 0  | 0.93      | 1.00   | 0.96  | 92.86%    |
| Datastructures | 6     | 5        | 5/6    | 5  | 1  | 0  | 0.83      | 1.00   | 0.91  | 83.33%    |
| Factories      | 3     | 3        | 3/3    | 3  | 0  | 0  | 1.00      | 1.00   | 1.00  | 100%      |
| Inter          | 13    | 16       | 11/14  | 11 | 0  | 3  | 1.00      | 0.79   | 0.88  | 78.57%    |
| Session        | 3     | 3        | 3/3    | 3  | 0  | 0  | 1.00      | 1.00   | 1.00  | 100%      |
| StrongUpdates  | 5     | 1        | 1/5    | 1  | 4  | 0  | 0.20      | 1.00   | 0.33  | 20%       |
| Pred           | 8     | 5        | 6/9    | 5  | 3  | 0  | 0.63      | 1.00   | 0.77  | 66.67%    |
| Reflection     | 3     | 4        | 3/4    | 3  | 0  | 1  | 1.00      | 0.75   | 0.86  | 75%       |
| Sanitizer      | 6     | 6        | 4/6    | 4  | 1  | 1  | 0.80      | 0.80   | 0.80  | 66.67%    |
| TOTAL          | 123   | 138      | 85/122 | 86 | 19 | 34 | 0.82      | 0.72   | 0.77  | 69.67%    |

To have detailed information about each group of tests run, [see here.](modules/securibench/src/docs-metrics/joana/joana-metrics.md)

### METRICS SUMMARY

|     Test     | Found | Expected | Status | TP | FP | FN | Precision | Recall | F-score | Pass Rate |
|:------------:|:-----:|:--------:|:------:|:--:|:--:|:---|:---------:|:------:|:-------:|----------:|
| JSVFA v0.3.0 |  102  |   139    | 63/122 | 80 | 11 | 50 |   0.88    |  0.62  |  0.72   |    51.64% | 
| JSVFA v0.6.1 |  120  |   141    | 75/122 | 95 | 14 | 35 |   0.87    |  0.73  |  0.79   |    61.48% |
|  Flowdroid   |  98   |   126    | 67/103 | 77 | 9  | 37 |   0.90    |  0.68  |  0.77   |    65.05% |
|    Joana     |  123  |   138    | 85/122 | 86 | 19 | 34 |   0.82    |  0.72  |  0.77   |    69.67% |


### TAINTBENCH:

[TAINTBENCH](https://taintbench.github.io/) is a benchmark that contains a [set of old malware Android Apks](https://github.com/TaintBench/TaintBench/releases/download/TaintBenchSuite/TaintBench.zip) , 
and it is introduced by [Paper: TaintBench: Automatic real-world malware benchmarking of Android taint analyses](https://doi.org/10.1007/s10664-021-10013-5), 
which in its result section presents six experiments to answer one of its RQ: *How effective are taint analysis tools on TaintBench compared to DroidBench*,
where FLOWDROID and AMANDROID as the chosen tools.

In the next sections, we will focus in **Experiment 2** and **Experiment 3** and use the latest version of JSVFA to reproduce them.
Both experiments compare the matches between the set of leaks reported by each APK execution TaintBench paper, referred to as `expected`, 
against the number of leaks identified by JSVFA, which is termed `actual`. A `match` is when the source and sink are the same in both the actual and expected sets.
We have created a file `taintbench.properties` to set the configurations in module `taintbench`.


#### Environment Setup

The tests require environment variables to be set for configuration:

- `ANDROID_SDK`: Path to your Android SDK (e.g., `/home/user/Android/Sdk/`)
- `TAINT_BENCH`: Path to your TaintBench directory (e.g., `/home/user/Documents/taint-bench`)

#### Running Android Tests

You can run Android tests in several ways:

**1. Using the convenience shell script (Recommended):**
```bash
./run-tests.sh --android-sdk /path/to/android/sdk --taint-bench /path/to/taintbench roidsec
./run-tests.sh --android-sdk /path/to/android/sdk --taint-bench /path/to/taintbench android
./run-tests.sh --android-sdk /path/to/android/sdk --taint-bench /path/to/taintbench all
```

**2. Using environment variables:**
```bash
export ANDROID_SDK=/path/to/android/sdk
export TAINT_BENCH=/path/to/taintbench
sbt test
# Or run specific tests:
sbt testRoidsec
sbt testAndroid
```

**3. Using SBT system properties:**
```bash
sbt -Dandroid.sdk=/path/to/android/sdk -Dtaint.bench=/path/to/taintbench testRoidsec
```

**4. Using inline environment variables:**
```bash
ANDROID_SDK=/path/to/android/sdk TAINT_BENCH=/path/to/taintbench sbt testRoidsec
```

**5. Using SBT testOnly command:**
```bash
ANDROID_SDK=/path/to/android/sdk TAINT_BENCH=/path/to/taintbench sbt "testOnly br.unb.cic.android.RoidsecTest"
```


#### EXPERIMENT I

This case emulates **Experiment 2 - TB2** that states:

>All tools are configured with sources and sinks defined in benchmark suite.

The mentioned sources and sinks can be found in [TB_SourcesAndSinks](https://github.com/TaintBench/TaintBench/blob/main/TB_SourcesAndSinks.txt). 
As a result, it finds fewer leaks than the expected, and, it gets only 10 matches,
which means, it has found 76 new leakages.

| Actual Findings | Expected Findings | Matches |
|:---------------:|:-----------------:|:-------:|
|       86        |        216        |   10    |

#### EXPERIMENT II

This case emulates **Experiment 3 - TB3** that configures:

>For each benchmark app, a list of sources and sinks defined in this app is used to 
configure all tools. Each tool analyzes each benchmark app with the associated list 
of sources and sinks 

The mentioned lists can be found in [TB_SourcesAndSinks](https://taintbench.github.io/taintbenchSuite).
As a result, it finds more leaks than the expected, however, it still gets only 10 matches as in the last experiment.
which means, it has found 645 new leakages.

| Actual Findings | Expected Findings | Matches |
|:---------------:|:-----------------:|:-------:|
|       655       |        216        |   10    |


## Tasks

### WIP
- [ ] Add set up project documentation.
- [ ] Fix bugs for Securibench in folders
    - [ ] Datastructure
    - [ ] Factory
    - [ ] Session
    - [ ] Strong Update
    - [ ] Aliasing

## License
This project is licensed under the MIT License.

Copyright (c) 2023-2024 Program Analysis and Manipulation Group at University of Brasília  and contributors

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
