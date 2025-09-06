# SVFA (Sparse Value Flow Analysis) implementation based on Soot

This is a scala implementation of a framework that builds a sparse-value flow graph using Soot.

## Status

   * Experimental.

## Usage

   * Clone this repository or download a stable release.
   * Add a GitHub token to your **~/.gitconfig**.
     ```
     [github]
             token = TOKEN
     ```
   * Build this project using sbt (`sbt compile test`)
   * Publish the artifact as a JAR file in your m2 repository (`sbt publish`)
   * Create a dependency to the svfa-scala artifact in your maven project. 

```{xml}
<dependency>	
  <groupId>br.unb.cic</groupId>
  <artifactId>svfa-scala_2.12</artifactId>
  <version>3.0.1-SNAPSHOT</version>
 </dependency>
```

   * Implement a class that extends the `JSVFA class` (see some examples in the scala tests). you must provide implementations to the following methods.
      * `getEntryPoints()` to set up the "main" methods. This implementation must return a list of Soot methods.
      * `sootClassPath()` to set up the soot classpath. This implementation must return a string.
      * `analyze(unit)` to identify the type of a node  (source, sink, simple node) in the graph; given a statement (soot unit).

## Installation

- Install Scala Plugin in IntelliJ IDEA.
- Install Java 8 (Java JDK Path `/usr/lib/jvm/java-8-openjdk-amd64`).
```{bash}
  sudo apt install openjdk-8-jre-headless
  sudo apt install openjdk-8-jdk
```
- Clone the project:
```{bash}
    git clone https://github.com/rbonifacio/svfa-scala
```
- Add GitHub token in `~/.gitconfig`.
- IDE
  - Reload `sbt` .
  - Set Project's settings to work with Java 8.
  - Build Project.
  - Run test.


## Benchmark

This project integrates 2 well-known benchmarks.

### Securibench

This benchmark was integrated because it is also used in the [FlowDroid Project](https://github.com/secure-software-engineering/FlowDroid) 
and the tests cases are in `src/test/java/securibench`.

> failed: 0, passed: 71, ignored: 32 of 103 test (68.93%)

- **AliasingTest** - failed: 0, passed: 4, ignored: 2 of 6 test `(66.7%)`
  - [5]
  - [6]

- **ArraysTest** - failed: 0, passed: 5, ignored: 5 of 10 test `(50%)`
  - [2]
  - [5]
  - [8]
  - [9]
  - [10]

- **BasicTest** - failed: 0, passed: 38, ignored: 3 of 41 test `(92.68%)`; [40] does not exist
  - [36]
  - [38]
  - [42]

- **CollectionTest** - failed: 0, passed: 3, ignored: 12 of 15 test `(20%)`
  - [3]
  - [4]
  - [5]
  - [6]
  - [7]
  - [8]
  - [9]
  - [10]
  - [11] * There are any assertions here, it calls test [11b]
  - [11b]
  - [12]
  - [13]

- **DataStructureTest** - failed: 0, passed: 5, ignored: 1 of 6 test `(83.33%)`
  - [5]

- **FactoryTest** - failed: 0, passed: 2, ignored: 1 of 3 test `(66.67%)`
  - [3]

- **InterTest** - failed: 0, passed:11, ignored: 3 of 14 test `(78.57%)`
  - [6]
  - [11] - flaky
  - [12]

- **SessionTest** - failed: 0, passed: 0, ignored: 3 of 3 test `(0%)`
  - [1]
  - [2]
  - [3]

- **StrongUpdateTest** - failed: 0, passed: 3, ignored: 2 of 5 test `(60%)`
  - [3]
  - [5]

### Taintbench: (WIP) 

[Taintbench](https://github.com/TaintBench/TaintBench/releases/download/TaintBenchSuite/TaintBench.zip) contains a set o Android Apks that are old malware apps.

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

#### Test Results

> failed: ?, passed: 1, ignored: ? of 39 test (?%)

- [Roidsec] ✅
- [ ]
- [ ]


## Tasks
### WIP
- [ ] Finish integration of Taintbench.
- [ ] Add set up project documentation.
- [ ] Integrate Securibench as a submodule.
- [ ] Fix bugs for Securibench in folders
  - [ ] Datastructure
  - [ ] Factory
  - [ ] Session
  - [ ] Strong Update
  - [ ] Aliasing


[//]: # (## License)