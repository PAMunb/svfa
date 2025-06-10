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

> failed: 0, passed: 61, ignored: 42 of 103 tests.

|      Test       |    Σ    |   TP   | FP |
|:---------------:|:-------:|:------:|:--:|
|    Aliasing     |   5/6   | 10/11  | 0  |   
|      Array      |  1/10   |  0/9   | 0  | 
|      Basic      |  35/42  | 56/61  | 2  |  
|   Collection    |  2/14   |  2/14  | 1  |
|  DataStructure  |   4/6   |  5/5   | 2  | 
|     Factory     |   2/3   |  3/3   | 1  |  
|      Inter      |  8/14   | 10/16  | 0  | 
|    ~~Pred~~     | ~~0/9~~ |   -    | -  |
| ~~Reflection~~  | ~~0/4~~ |   -    | -  |
| ~~Sanitizers~~  | ~~0/6~~ |   -    | -  |
|     Session     |   0/3   |  0/3   | 0  | 
|  StrongUpdate   |   4/5   |  0/1   | 0  |
|    **TOTAL**    | 61/103  | 86/123 | 6  |

- **Precision:** 0.93
- **Recall:** 0.70
- **F-score:** 0.80
- **Pass Rate:** 59.22%

To have detailed information about each group of tests run, [see here.](old-metrics) (*computed in in March, 2023.*)

#### JSVFA 2.0 metrics

> failed: 34, passed: 69, ignored: 0 of 103 tests

|      Test       |    Σ    |   TP   | FP |
|:---------------:|:-------:|:------:|:--:|
|    Aliasing     |   4/6   |  4/11  | 0  |    
|      Array      |  5/10   |  7/9   | 4  |   
|      Basic      |  37/42  | 57/61  | 1  |  
|   Collection    |  4/14   |  4/14  | 0  | 
|  DataStructure  |   4/6   |  4/5   | 1  |  
|     Factory     |   2/3   |  3/3   | 1  |  
|      Inter      |  10/14  | 12/16  | 0  |  
|    ~~Pred~~     | ~~0/9~~ |   -    | -  |
| ~~Reflection~~  | ~~0/4~~ |   -    | -  |
| ~~Sanitizers~~  | ~~0/6~~ |   -    | -  |
|     Session     |   0/3   |  0/3   | 0  | 
|  StrongUpdate   |   3/5   |  1/1   | 2  |   
|    **TOTAL**    | 69/103  | 92/123 | 9  |     

- **Precision:** 0.91
- **Recall:** 0.75
- **F-score:** 0.82
- **Pass Rate:** 66.99%

To have detailed information about each group of tests run, [see here.](new-metrics)

#### FLOWDROID metrics from [Paper](https://www.bodden.de/pubs/far+14flowdroid.pdf)

|     Test      |   TP    | FP  | 
|:-------------:|:-------:|:---:|
|   Aliasing    |  11/11  |  0  |   
|     Array     |   9/9   |  4  |   
|     Basic     |  58/60  |  1  |  
|  Collection   |  14/14  |  0  | 
| DataStructure |   5/5   |  1  |  
|    Factory    |   3/3   |  1  |  
|     Inter     |  14/16  |  0  |  
|    Session    |   3/3   |  0  |  
| StrongUpdate  |   0/0   |  2  |   
|   **TOTAL**   | 117/121 |  9  |   

- **Precision:** 0.93
- **Recall:** 0.97
- **F-score:** 0.95
- **Pass Rate:** 87.38%

**OBSERVATIONS**
- Flowdroid is not taking in count the TP expected in StrongUpdate4;
- Test Basic40 is commented in the test suite so the amount of TP differs from the original run by Flowdroid;

#### METRICS SUMMARY

| Frameworks | Precision | Recall | F-score | Pass Rate |
|:----------:|:---------:|:------:|:-------:|----------:|
|   JSVFA    |   0.93    |  0.71  |  0.81   |    61.76% | 
| JSVFA 2.0  |   0.91    |  0.75  |  0.82   |    66.99% | 
| FlowDroid  |   0.93    |  0.97  |  0.95   |    87.38% | 
|   Joana    |   0.86    |  0.74  |  0.79   |    69.90% | 

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