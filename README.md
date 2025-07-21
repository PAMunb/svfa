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

To have detailed information about each group of tests run, [see here.](old-metrics) (*computed in in March, 2023.*)

#### JSVFA 2.0 metrics

> failed: 40, passed: 63 of 103 tests - (61.17%)

|      Test      | Found | Expected | Status | TP | FP | FN | Precision | Recall | F-score |
|:--------------:|:-----:|:--------:|:------:|:--:|:--:|:---|:---------:|:------:|:-------:|
|    Aliasing    |   4   |    12    |  1/6   | 1  | 1  | 9  |   0.50    |  0.10  |  0.17   |
|     Arrays     |  11   |    9     |  5/10  | 5  | 4  | 2  |   0.56    |  0.71  |  0.63   |
|     Basic      |  59   |    60    | 37/42  | 53 | 2  | 3  |   0.96    |  0.95  |  0.95   |
|  Collections   |   4   |    15    |  3/14  | 3  | 0  | 11 |   1.00    |  0.21  |  0.35   |
| Datastructures |   5   |    5     |  4/6   | 4  | 1  | 1  |   0.80    |  0.80  |  0.80   |
|   Factories    |   4   |    3     |  2/3   | 2  | 1  | 0  |   0.67    |  1.00  |  0.80   |
|     Inter      |  13   |    18    |  9/14  | 10 | 0  | 5  |   1.00    |  0.67  |  0.80   |
|    Session     |   0   |    3     |  0/3   | 0  | 0  | 3  |   0.00    |  0.00  |  0.00   |
| StrongUpdates  |   3   |    1     |  3/5   | 1  | 2  | 0  |   0.33    |  1.00  |  0.50   |
|     TOTAL      |  102  |   126    | 63/103 | 78 | 11 | 35 |   0.88    |  0.69  |  0.77   |

To have detailed information about each group of tests run, [see here.](new-metrics)

#### FLOWDROID 

- failed: 33, passed: 70 of 103 tests. `(67.96%)`

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
| TOTAL          | 98    | 123      | 70/103 | 79 | 9  | 34 | 0.90      | 0.70   | 0.79 |

#### JOANA

> failed: 31, passed: 72 of 103 tests. `(69.90%)`

| Test           | Found | Expected | Status | TP | FP | FN | Precision | Recall | F1   |
|----------------|-------|----------|--------|----|----|----|-----------|--------|------|
| Aliasing       | 6     | 11       | 2/6    | 2  | 2  | 7  | 0.50      | 0.22   | 0.31 |
| Arrays         | 10    | 9        | 9/10   | 9  | 1  | 0  | 0.90      | 1.00   | 0.95 |
| Basic          | 45    | 61       | 25/42  | 26 | 6  | 22 | 0.81      | 0.54   | 0.65 |
| Collections    | 15    | 14       | 13/14  | 14 | 1  | 0  | 0.93      | 1.00   | 0.96 |
| Datastructures | 6     | 5        | 5/6    | 5  | 1  | 0  | 0.83      | 1.00   | 0.91 |
| Factories      | 3     | 3        | 3/3    | 3  | 0  | 0  | 1.00      | 1.00   | 1.00 |
| Inter          | 13    | 16       | 11/14  | 11 | 0  | 3  | 1.00      | 0.79   | 0.88 |
| Session        | 3     | 3        | 3/3    | 3  | 0  | 0  | 1.00      | 1.00   | 1.00 |
| StrongUpdates  | 5     | 1        | 1/5    | 1  | 4  | 0  | 0.20      | 1.00   | 0.33 |
| TOTAL          | 106   | 123      | 72/103 | 74 | 15 | 32 | 0.83      | 0.70   | 0.76 |


#### METRICS SUMMARY

|   Test    | Found | Expected | Status | TP | FP | FN | Precision | Recall | F-score | Pass Rate |
|:---------:|:-----:|:--------:|:------:|:--:|:--:|:---|:---------:|:------:|:-------:|----------:|
|   JSVFA   |  94   |   126    | 57/103 | 75 | 8  | 40 |   0.90    |  0.65  |  0.75   |    55.34% |
| JSVFA 2.0 |  102  |   126    | 63/103 | 78 | 11 | 35 |   0.88    |  0.69  |  0.77   |    61.17% |
| Flowdroid |  98   |   123    | 70/103 | 79 | 9  | 34 |   0.90    |  0.70  |  0.79   |    67.96% |
|   Joana   |  106  |   123    | 72/103 | 74 | 15 | 32 |   0.83    |  0.70  |  0.76   |    69.90% |


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
- [ ] Check if each test in Securibench has the right expected values.
- [ ] Add set up project documentation.
- [ ] Integrate Securibench as a submodule.
- [ ] Fix bugs for Securibench in folders
  - [ ] Datastructure
  - [ ] Factory
  - [ ] Session
  - [ ] Strong Update
  - [ ] Aliasing


[//]: # (## License)