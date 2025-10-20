# SVFA (Sparse Value Flow Analysis) implementation based on Soot

This is a scala implementation of a framework that builds a sparse-value flow graph using Soot.

## Status

   * Experimental.

## Architecture

This project follows a **modular architecture** with three focused modules:

- **`core`**: Essential SVFA framework + Android analysis support
- **`securibench`**: Java security vulnerability analysis benchmarks  
- **`taintbench`**: Android malware analysis benchmarks

## Quick Start

### For Library Users

#### Using svfa-core in Scala Projects

Add to your `build.sbt`:
```scala
resolvers += Resolver.githubPackages("PAMunb", "svfa")
libraryDependencies += "br.unb.cic" %% "svfa-core" % "0.5.0"
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
  <version>0.5.0</version>
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
    implementation 'br.unb.cic:svfa-core_2.12:0.5.0'
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

Implement a class that extends the `JSVFA class` (see examples in the tests). You must provide implementations for:

* `getEntryPoints()` - Set up the "main" methods (returns List of Soot methods)
* `sootClassPath()` - Set up the soot classpath (returns String)  
* `analyze(unit)` - Identify node types (source, sink, simple node) in the graph

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

| Command | Description |
|---------|-------------|
| `sbt testCore` | Run core SVFA tests |
| `sbt testSecuribench` | Run security vulnerability tests (93 test cases) |
| `sbt testTaintbench` | Run Android malware tests |
| `sbt testRoidsec` | Run specific Roidsec test |
| `sbt compileAll` | Compile all modules |
| `sbt publishCore` | Publish core module to GitHub Packages |
| `sbt publishAllLocal` | Publish all modules to local Maven repository |

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

This benchmark was integrated because it is also used in the [FlowDroid Project](https://github.com/secure-software-engineering/FlowDroid) and tests cases are in `src/test/java/securibench`.

#### JSVFA metrics (old)

> failed: 46, passed: 57 of 103 tests - (55.34%)

|      Test      | Found | Expected | Status | TP | FP | FN | Precision | Recall | F-score |
|:--------------:|:-----:|:--------:|:------:|:--:|:--:|:---|:---------:|:------:|:-------:|
|    aliasing    |  10   |    12    |  2/6   | 8  | 1  | 3  |   0.89    |  0.73  |  0.80   |
|     arrays     |   0   |    9     |  1/10  | 0  | 0  | 9  |   0.00    |  0.00  |  0.00   | 
|     basic      |  60   |    60    | 36/42  | 52 | 3  | 3  |   0.95    |  0.95  |  0.95   |
|  collections   |   3   |    15    |  1/14  | 1  | 1  | 13 |   0.50    |  0.07  |  0.12   |
| datastructures |   7   |    5     |  4/6   | 4  | 2  | 0  |   0.67    |  1.00  |  0.80   |
|   factories    |   4   |    3     |  2/3   | 2  | 1  | 0  |   0.67    |  1.00  |  0.80   |
|     inter      |  10   |    18    |  7/14  | 8  | 0  | 8  |   1.00    |  0.50  |  0.67   |
|    session     |   0   |    3     |  0/3   | 0  | 0  | 3  |   0.00    |  0.00  |  0.00   |
| strong_updates |   0   |    1     |  4/5   | 0  | 0  | 1  |   0.00    |  0.00  |  0.00   |
|     TOTAL      |  94   |   126    | 57/103 | 75 | 8  | 40 |   0.90    |  0.65  |  0.75   |

To have detailed information about each group of tests run, [see here.](docs-metrics/securibench/jsvfa/jsvfa-metrics-v0.3.0.md) (*computed in March 2023.*)

#### JSVFA 2.0 metrics (v0.3.4)

> failed: 38, passed: 65 of 103 tests - (63.11%)

|      Test      | Found | Expected | Status | TP | FP | FN | Precision | Recall | F-score |
|:--------------:|:-----:|:--------:|:------:|:--:|:--:|:---|:---------:|:------:|:-------:|
|    Aliasing    |   4   |    12    |  1/6   | 1  | 1  | 9  |   0.50    |  0.10  |  0.17   |
|     Arrays     |  11   |    9     |  5/10  | 5  | 4  | 2  |   0.56    |  0.71  |  0.63   |
|     Basic      |  59   |    60    | 37/42  | 53 | 2  | 3  |   0.96    |  0.95  |  0.95   |
|  Collections   |   8   |    15    |  5/14  | 5  | 1  | 8  |   0.83    |  0.38  |  0.52   |
| Datastructures |   5   |    5     |  4/6   | 4  | 1  | 1  |   0.80    |  0.80  |  0.80   |
|   Factories    |   4   |    3     |  2/3   | 2  | 1  | 0  |   0.67    |  1.00  |  0.80   |
|     Inter      |  12   |    18    |  8/14  | 9  | 0  | 6  |   1.00    |  0.60  |  0.75   |
|    Session     |   0   |    3     |  0/3   | 0  | 0  | 3  |   0.00    |  0.00  |  0.00   |
| StrongUpdates  |   3   |    1     |  3/5   | 1  | 2  | 0  |   0.33    |  1.00  |  0.50   |
|     TOTAL      |  106  |   126    | 65/103 | 80 | 12 | 32 |   0.87    |  0.71  |  0.78   |

To have detailed information about each group of tests run, [see here.](docs-metrics/securibench/jsvfa/jsvfa-metrics-v0.3.4.md) (*computed in September 2025.*)

#### FLOWDROID 

- failed: 36, passed: 67 of 103 tests. `(65.05%)`

| Test           | Found | Expected | Status | TP | FP | FN | Precision | Recall | F1   |
|----------------|-------|----------|--------|----|----|----|-----------|--------|------|
| Aliasing       | 11    | 11       | 4/6    | 9  | 1  | 1  | 0.90      | 0.90   | 0.90 |
| Arrays         | 14    | 9        | 6/10   | 6  | 5  | 0  | 0.55      | 1.00   | 0.71 |
| Basic          | 38    | 61       | 26/42  | 33 | 1  | 24 | 0.97      | 0.58   | 0.73 |
| Collections    | 14    | 14       | 12/14  | 12 | 1  | 1  | 0.92      | 0.92   | 0.92 |
| Datastructures | 5     | 5        | 4/6    | 3  | 1  | 1  | 0.75      | 0.75   | 0.75 |
| Factories      | 1     | 3        | 1/3    | 1  | 0  | 2  | 1.00      | 0.33   | 0.50 |
| Inter          | 15    | 16       | 13/14  | 15 | 0  | 1  | 1.00      | 0.94   | 0.97 |
| Session        | 0     | 3        | 0/3    | 0  | 0  | 3  | 0.00      | 0.00   | 0.00 |
| StrongUpdates  | 0     | 1        | 4/5    | 0  | 0  | 1  | 0.00      | 0.00   | 0.00 |
| TOTAL          | 98    | 126      | 67/103 | 77 | 9  | 37 | 0.90      | 0.68   | 0.77 |

To have detailed information about each group of tests run, [see here.](docs-metrics/securibench/flowdroid/flowdroid-metrics.md)

#### JOANA

> failed: 32, passed: 71 of 103 tests. `(68.93%)`

| Test           | Found | Expected | Status | TP | FP | FN | Precision | Recall | F1   |
|----------------|-------|----------|--------|----|----|----|-----------|--------|------|
| Aliasing       | 6     | 11       | 2/6    | 2  | 2  | 7  | 0.50      | 0.22   | 0.31 |
| Arrays         | 10    | 9        | 9/10   | 9  | 1  | 0  | 0.90      | 1.00   | 0.95 |
| Basic          | 45    | 61       | 25/42  | 26 | 6  | 22 | 0.81      | 0.54   | 0.65 |
| Collections    | 15    | 15       | 14/14  | 15 | 0  | 0  | 1.00      | 1.00   | 1.00 |
| Datastructures | 6     | 5        | 5/6    | 5  | 1  | 0  | 0.83      | 1.00   | 0.91 |
| Factories      | 3     | 3        | 3/3    | 3  | 0  | 0  | 1.00      | 1.00   | 1.00 |
| Inter          | 13    | 18       | 9/14   | 9  | 0  | 5  | 1.00      | 0.64   | 0.78 |
| Session        | 3     | 3        | 3/3    | 3  | 0  | 0  | 1.00      | 1.00   | 1.00 |
| StrongUpdates  | 5     | 1        | 1/5    | 1  | 4  | 0  | 0.20      | 1.00   | 0.33 |
| TOTAL          | 106   | 126      | 71/103 | 73 | 14 | 34 | 0.84      | 0.68   | 0.75 |

To have detailed information about each group of tests run, [see here.](docs-metrics/securibench/joana/joana-metrics.md)

#### METRICS SUMMARY

|   Test    | Found | Expected | Status | TP | FP | FN | Precision | Recall | F-score | Pass Rate |
|:---------:|:-----:|:--------:|:------:|:--:|:--:|:---|:---------:|:------:|:-------:|----------:|
|   JSVFA   |  94   |   126    | 57/103 | 75 | 8  | 40 |   0.90    |  0.65  |  0.75   |    55.34% |
| JSVFA 2.0 |  106  |   126    | 65/103 | 80 | 12 | 32 |   0.87    |  0.71  |  0.78   |    63.11% |
| Flowdroid |  98   |   126    | 67/103 | 77 | 9  | 37 |   0.90    |  0.68  |  0.77   |    65.05% |
|   Joana   |  106  |   126    | 71/103 | 73 | 14 | 34 |   0.84    |  0.68  |  0.75   |    68.93% |


### TAINTBENCH:

[TAINTBENCH](https://taintbench.github.io/) is a benchmark that contains a [set of old malware Android Apks](https://github.com/TaintBench/TaintBench/releases/download/TaintBenchSuite/TaintBench.zip) , 
and it is introduced by [Paper: TaintBench: Automatic real-world malware benchmarking of Android taint analyses](https://doi.org/10.1007/s10664-021-10013-5), 
which in its result section presents six experiments to answer one of its RQ: *How effective are taint analysis tools on TaintBench compared to DroidBench*,
where FLOWDROID and AMANDROID as the chosen tools.

In the next sections, we will focus in **Experiment 2** and **Experiment 3** and use our tool (JSVFA) to reproduce them. 
After that, we will compare the already computed results for FLOWDROID to our results.

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

- The result for each APK tested using JSVFA are presented in a table that contains the following information.
  - **Expected:** The amount of taint flows presented by TAINTBENCH
  - **Actual:** The amount of taint flows detected by JSVFA
  - **Status:** If the test PASS OR FAIL
  - **TP:** True Positive
  - **FP:** False Positive
  - **Precision:** TP/(TP + FP)
  - **Recall:** TP/P
  - **F-score:** (2 x Precision x Recall)/(Precision + Recall)
- We have created a file `taintbench.properties` in `src/test/resources` to set the configurations

**Disclaimer**: Although TAINTBENCH contains 203 expected [taint flows](https://taintbench.github.io/taintbenchSuite/),
we have decided to use only 186 expected cases because the mentioned paper, uses as a reference, works with those amounts.

#### EXPERIMENT I

This case emulates **Experiment 2 - TB2** that states:

>All tools are configured with sources and sinks defined in benchmark suite.

The mentioned sources and sinks can be found in [TB_SourcesAndSinks](https://github.com/TaintBench/TaintBench/blob/main/TB_SourcesAndSinks.txt), 
and we have stored them in `src/test/scala/br/unb/cic/android/TaintBenchSpec.scala`.

As a result, we got `36, passed: 3 of 39 tests` and comparing to FLOWDROID we computed the next metrics:
~~a better `precision(0.82)`, the same `recall(0.22)` value and a slightly better `F-score(0.35)`.~~

- JSVFA metrics, to have detailed information about each group of tests run, [see here.](modules/taintbench/src/docs-metrics/taintbench/taintbench-experiment-I.md)

| Found | Expected | Status | TP | FP | FN  | Precision | Recall | F-score | Pass Rate |
|:-----:|:--------:|:------:|:--:|:--:|:---:|:---------:|:------:|:-------:|:---------:| 
|  60   |   203    |  3/39  | 4  | 12 | 155 |   0.25    |  0.03  |  0.05   |   7.69%   |

- FLOWDROID metrics from [Paper](https://doi.org/10.1007/s10664-021-10013-5)

| Found | Expected | Status | TP | FP | FN | Precision | Recall | F-score | Pass Rate |
|:-----:|:--------:|:------:|:--:|:--:|:--:|:---------:|:------:|:-------:|:---------:| 
|  55   |   186    |   ?    | 41 | 14 | ?  |   0.75    |  0.22  |  0.34   |     ?     |

##### Observation
- From the 37 failing tests, 28 of them reported zero flows.


#### EXPERIMENT II

This case emulates **Experiment 3 - TB3** that configures:

>For each benchmark app, a list of sources and sinks defined in this app is used to 
configure all tools. Each tool analyzes each benchmark app with the associated list 
of sources and sinks 

The mentioned lists can be found in https://taintbench.github.io/taintbenchSuite/, and we have stored them by individual
files in `src/test/scala/br/unb/cic/android/specs`.

As a result, we got `failed: 35, passed: 4 of 39 tests` and comparing to FLOWDROID
~~awe detect a several better amount of `TP(135)` but also a several amount of `FP(318)` and about metric, we got a significant less `precision(0.30)` due to the high amount of FP; however,
a good `recall(0.73)` value and a better `F-score(0.42)`.~~

- JSVFA metrics, to have detailed information about each group of tests run, [see here.](modules/taintbench/src/docs-metrics/taintbench/taintbench-experiment-II.md)

| Found | Expected | Status | TP | FP  | FN | Precision | Recall | F-score | Pass Rate |
|:-----:|:--------:|:------:|:--:|:---:|:--:|:---------:|:------:|:-------:|:---------:|
|  620  |   203    |  4/39  | 29 | 463 | 46 |   0.06    |  0.39  |  0.10   |  10.26%   |

- FLOWDROID metrics from Paper https://doi.org/10.1007/s10664-021-10013-5

| Found | Expected | Status | TP | FP | FN | Precision | Recall | F-score | Pass Rate |
|:-----:|:--------:|:------:|:--:|:--:|:--:|:---------:|:------:|:-------:|:---------:|
|  57   |   186    |   ?    | 43 | 14 | ?  |   0.75    |  0.23  |  0.35   |     ?     |

##### Observation
- We got a big amount of FP.

## Tasks
### WIP
- [ ] Add set up project documentation.
- [ ] Fix bugs for Securibench in folders
    - [ ] Datastructure
    - [ ] Factory
    - [ ] Session
    - [ ] Strong Update
    - [ ] Aliasing

### TO-DO
-

### DONE
- [X] Create Git Action flow.
- [X] Integrate Taintbench.

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
