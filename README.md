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

- JSVFA metrics

| Expected | Actual | TP | FP | Precision | Recall | F-score |
|:--------:|:------:|:--:|:--:|:---------:|:------:|:-------:|
|   121    |  100   | 92 | 9  |   0.91    |  0.76  |  0.83   |

- FLOWDROID metrics from [Paper](https://www.bodden.de/pubs/far+14flowdroid.pdf)

| Expected | Actual | TP  | FP | Precision | Recall | F-score |
|:--------:|:------:|:---:|:--:|:---------:|:------:|:-------:|
|   121    |  126   | 117 | 9  |   0.93    |  0.97  |  0.95   |




|      Test      | Expected | Actual | Status | TP  | FP  | Precision | Recall | F-score |
|:--------------:|:--------:|:------:|:------:|:---:|:---:|:---------:|:------:|:-------:|
|     INTER      |          |        |        |     |     |           |        |         |
|     Inter1     |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
|     Inter2     |    2     |   2    |   ✅    |  2  |  0  |     -     |   -    |    -    |
|     Inter3     |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
|     Inter4     |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
|     Inter5     |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
|     Inter6     |    1     |   0    |   ❌    |  0  |  0  |     -     |   -    |    -    |
|     Inter7     |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
|     Inter8     |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
|     Inter9     |    2     |   1    |   ❌    |  1  |  0  |     -     |   -    |    -    |
|    Inter10     |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
|    Inter11     |    1     |   0    |   ❌    |  0  |  0  |     -     |   -    |    -    |
|    Inter12     |    1     |   0    |   ❌    |  0  |  0  |     -     |   -    |    -    |
|    Inter13     |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
|    Inter14     |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
|     TOTAL      |    16    |   12   | 10/14  | 12  |  0  |   1.00    |  0.75  |  0.86   |

|      Test      | Expected | Actual | Status | TP  | FP  | Precision | Recall | F-score |
|:--------------:|:--------:|:------:|:------:|:---:|:---:|:---------:|:------:|:-------:|
|      PRED      |   N/A    |  N/A   |  N/A   | N/A | N/A |    N/A    |  N/A   |   N/A   |

|      Test      | Expected | Actual | Status | TP  | FP  | Precision | Recall | F-score |
|:--------------:|:--------:|:------:|:------:|:---:|:---:|:---------:|:------:|:-------:|
|   REFLECTION   |   N/A    |  N/A   |  N/A   | N/A | N/A |    N/A    |  N/A   |   N/A   |

|      Test      | Expected | Actual | Status | TP  | FP  | Precision | Recall | F-score |
|:--------------:|:--------:|:------:|:------:|:---:|:---:|:---------:|:------:|:-------:|
|   SANITIZER    |   N/A    |  N/A   |  N/A   | N/A | N/A |    N/A    |  N/A   |   N/A   |

|      Test      | Expected | Actual | Status | TP  | FP  | Precision | Recall | F-score |
|:--------------:|:--------:|:------:|:------:|:---:|:---:|:---------:|:------:|:-------:|
|    Session1    |    1     |   0    |   ❌    |  0  |  0  |     -     |   -    |    -    |
|    Session2    |    1     |   0    |   ❌    |  0  |  0  |     -     |   -    |    -    |
|    Session3    |    1     |   0    |   ❌    |  0  |  0  |     -     |   -    |    -    |
|     TOTAL      |    3     |   0    |  0/3   |  0  |  0  |     0     |   0    |    0    |

|      Test      | Expected | Actual | Status | TP  | FP  | Precision | Recall | F-score |
|:--------------:|:--------:|:------:|:------:|:---:|:---:|:---------:|:------:|:-------:|
| StrongUpdate1  |    0     |   0    |   ✅    |  0  |  0  |     -     |   -    |    -    |
| StrongUpdate2  |    0     |   0    |   ✅    |  0  |  0  |     -     |   -    |    -    |
| StrongUpdate3  |    0     |   1    |   ❌    |  0  |  1  |     -     |   -    |    -    |
| StrongUpdate4  |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
| StrongUpdate5  |    0     |   1    |   ❌    |  0  |  1  |     -     |   -    |    -    |
|     TOTAL      |    1     |   3    |  3/5   |  1  |  2  |   0.33    |  1.00  |  0.50   |

|      Test      | Expected | Actual | Status | TP  | FP  | Precision | Recall | F-score |
|:--------------:|:--------:|:------:|:------:|:---:|:---:|:---------:|:------:|:-------:|
|     TOTAL      |   121    |  100   |   -    | 92  |  9  |   0.91    |  0.76  |  0.83   |

* Flowdroid is not taking in count the TP expected in the test group: STRONG UPDATE

- **AliasingTest** - failed: 0, passed: 4, ignored: 2 of 6 test.

|      Test      | Expected | Actual | Status | TP  | FP  | Precision | Recall | F-score |
|:--------------:|:--------:|:------:|:------:|:---:|:---:|:---------:|:------:|:-------:|
|   Aliasing1    |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
|   Aliasing2    |    0     |   0    |   ✅    |  0  |  0  |     -     |   -    |    -    |
|   Aliasing3    |    0     |   0    |   ✅    |  0  |  0  |     -     |   -    |    -    |
|   Aliasing4    |    2     |   2    |   ✅    |  2  |  0  |     -     |   -    |    -    |
|   Aliasing5    |    1     |   0    |   ❌    |  0  |  0  |     -     |   -    |    -    |
|   Aliasing6    |    7     |   1    |   ❌    |  1  |  0  |     -     |   -    |    -    |
|     TOTAL      |    11    |   4    |  4/6   |  4  |  0  |   1.00    |  0.36  |  0.53   |

- **ArraysTest** - failed: 0, passed: 5, ignored: 5 of 10 test.

|      Test      | Expected | Actual | Status | TP  | FP  | Precision | Recall | F-score |
|:--------------:|:--------:|:------:|:------:|:---:|:---:|:---------:|:------:|:-------:|
|     Array1     |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
|     Array2     |    1     |   3    |   ❌    |  1  |  2  |     -     |   -    |    -    |
|     Array3     |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
|     Array4     |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
|     Array5     |    0     |   1    |   ❌    |  0  |  1  |     -     |   -    |    -    |
|     Array6     |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
|     Array7     |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
|     Array8     |    1     |   2    |   ❌    |  1  |  1  |     -     |   -    |    -    |
|     Array9     |    1     |   0    |   ❌    |  0  |  0  |     -     |   -    |    -    |
|    Array10     |    1     |   0    |   ❌    |  0  |  0  |     -     |   -    |    -    |
|     TOTAL      |    9     |   11   |  5/10  |  7  |  4  |   0.64    |  0.78  |  0.70   |


- **BasicTest** - failed: 0, passed: 38, ignored: 3 of 41 test.

|      Test      | Expected | Actual | Status | TP  | FP  | Precision | Recall | F-score |
|:--------------:|:--------:|:------:|:------:|:---:|:---:|:---------:|:------:|:-------:|
|     Basic1     |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
|     Basic2     |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
|     Basic3     |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
|     Basic4     |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
|     Basic5     |    3     |   3    |   ✅    |  3  |  0  |     -     |   -    |    -    |
|     Basic6     |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
|     Basic7     |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
|     Basic8     |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
|     Basic9     |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
|    Basic10     |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
|    Basic11     |    2     |   2    |   ✅    |  2  |  0  |     -     |   -    |    -    |
|    Basic12     |    2     |   2    |   ✅    |  2  |  0  |     -     |   -    |    -    |
|    Basic13     |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
|    Basic14     |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
|    Basic15     |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
|    Basic16     |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
|    Basic17     |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
|    Basic18     |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
|    Basic19     |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
|    Basic20     |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
|    Basic21     |    4     |   4    |   ✅    |  4  |  0  |     -     |   -    |    -    |
|    Basic22     |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
|    Basic23     |    3     |   3    |   ✅    |  3  |  0  |     -     |   -    |    -    |
|    Basic24     |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
|    Basic25     |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
|    Basic26     |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
|    Basic27     |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
|    Basic28     |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
|    Basic29     |    2     |   2    |   ✅    |  2  |  0  |     -     |   -    |    -    |
|    Basic30     |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
|    Basic31     |    3     |   3    |   ✅    |  3  |  0  |     -     |   -    |    -    |
|    Basic32     |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
|    Basic33     |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
|    Basic34     |    2     |   2    |   ✅    |  2  |  0  |     -     |   -    |    -    |
|    Basic35     |    6     |   6    |   ✅    |  6  |  0  |     -     |   -    |    -    |
|    Basic36     |    1     |   0    |   ❌    |  0  |  0  |     -     |   -    |    -    |
|    Basic37     |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
|    Basic38     |    1     |   2    |   ❌    |  1  |  1  |     -     |   -    |    -    |
|    Basic39     |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
|    Basic40     |    -     |   -    |   -    |  -  |  -  |     -     |   -    |    -    |
|    Basic41     |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
|    Basic42     |    1     |   0    |   ❌    |  0  |  0  |     -     |   -    |    -    |
|     TOTAL      |    59    |   58   | 38/41  | 57  |  1  |   0.98    |  0.97  |  0.97   |


- **CollectionTest** - failed: 0, passed: 4, ignored: 10 of 14 tests.

|      Test      | Expected | Actual | Status | TP  | FP  | Precision | Recall | F-score |
|:--------------:|:--------:|:------:|:------:|:---:|:---:|:---------:|:------:|:-------:|
|  Collection1   |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
|  Collection2   |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
|  Collection3   |    2     |   1    |   ❌    |  1  |  0  |     -     |   -    |    -    |
|  Collection4   |    1     |   0    |   ❌    |  0  |  0  |     -     |   -    |    -    |
|  Collection5   |    1     |   0    |   ❌    |  0  |  0  |     -     |   -    |    -    |
|  Collection6   |    1     |   0    |   ❌    |  0  |  0  |     -     |   -    |    -    |
|  Collection7   |    1     |   0    |   ❌    |  0  |  0  |     -     |   -    |    -    |
|  Collection8   |    1     |   0    |   ❌    |  0  |  0  |     -     |   -    |    -    |
|  Collection9   |    0     |   0    |   ✅    |  0  |  0  |     -     |   -    |    -    |
|  Collection10  |    1     |   0    |   ❌    |  0  |  0  |     -     |   -    |    -    |
|  Collection11  |    1     |   0    |   ❌    |  0  |  0  |     -     |   -    |    -    |
|  Collection12  |    1     |   0    |   ❌    |  0  |  0  |     -     |   -    |    -    |
|  Collection13  |    1     |   0    |   ❌    |  0  |  0  |     -     |   -    |    -    |
|  Collection14  |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
|     TOTAL      |    14    |   4    |  4/14  |  4  |  0  |   1.00    |  0.29  |  0.44   |


- **DataStructureTest** - failed: 0, passed: 4, ignored: 2 of 6 tests.

|      Test      | Expected | Actual | Status | TP  | FP  | Precision | Recall | F-score |
|:--------------:|:--------:|:------:|:------:|:---:|:---:|:---------:|:------:|:-------:|
| DataStructure1 |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
| DataStructure2 |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
| DataStructure3 |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
| DataStructure4 |    0     |   1    |   ❌    |  0  |  1  |     -     |   -    |    -    |
| DataStructure5 |    1     |   0    |   ❌    |  0  |  0  |     -     |   -    |    -    |
| DataStructure6 |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
|     TOTAL      |    5     |   5    |  4/6   |  4  |  1  |   0.80    |  0.80  |  0.80   |


- **FactoryTest** - failed: 0, passed: 2, ignored: 1 of 3 tests.

|      Test      | Expected | Actual | Status | TP  | FP  | Precision | Recall | F-score |
|:--------------:|:--------:|:------:|:------:|:---:|:---:|:---------:|:------:|:-------:|
|    FACTORY     |          |        |        |     |     |           |        |         |
|    Factory1    |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
|    Factory2    |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
|    Factory3    |    1     |   2    |   ❌    |  1  |  1  |     -     |   -    |    -    |
|     TOTAL      |    3     |   4    |  2/3   |  3  |  1  |   0.75    |  1.00  |  0.86   |


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
We have created a file `taintbench.properties` in `src/test/resources` to set the configurations.

> failed: ?, passed: 1, ignored: ? of 39 test (?%)

- [Roidsec]
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