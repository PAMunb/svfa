# SVFA (Sparse Value Flow Analysis) implementation based on Soot

This is a scala implementation of a framework that builds a sparse-value flow graph using Soot.

## Status

   * experimental

## Usage

   * clone this repository or download an stable release
   * you will need to add a github token to your **~/.gitconfig**.
     ```
     [github]
             token = TOKEN
     ```
   * build this project using sbt (`sbt compile test`)
   * publish the artifact as a JAR file in your m2 repository (`sbt publish`)
   * create a dependency to the svfa-scala artifact in your maven project. 

```{xml}
<dependency>	
  <groupId>br.unb.cic</groupId>
  <artifactId>svfa-scala_2.12</artifactId>
  <version>0.0.2-SNAPSHOT</version>
 </dependency>
```

   * implement a class that extends the `JSVFA class` (see some examples in the scala tests). you must provide implementations to the following methods
      * `getEntryPoints()` to set up the "main" methods. This implementation must return a list of Soot methods
      * `sootClassPath()` to set up the soot classpath. This implementation must return a string
      * `analyze(unit)` to identify the type of a node  (source, sink, simple node) in the graph; given a statement (soot unit)


## Dependencies

This project use some of the [FlowDroid](https://github.com/secure-software-engineering/FlowDroid) test cases. The FlowDroid test cases in `src/test/java/securibench` are under [LGPL-2.1](https://github.com/secure-software-engineering/FlowDroid/blob/develop/LICENSE) license.


## Installation

- Install Scala Plugin in IntelliJ IDEA
- Install Java 8 (Java JDK Path `/usr/lib/jvm/java-8-openjdk-amd64`)
```{bash}
  sudo apt install openjdk-8-jre-headless
  sudo apt install openjdk-8-jdk
```
- Clone the project:
```{bash}
    git clone https://github.com/rbonifacio/svfa-scala
```
- Add dependency: 
     - Download [servlet-api-2.5.jar](https://repo1.maven.org/maven2/javax/servlet/servlet-api/2.5/servlet-api-2.5.jar) and move to `.m2/repository/javax/servlet/servlet-api/2.5/`
- Add GitHub token in `~/.gitconfig`
- IDE
  - Reload `sbt` 
  - Set Project's settings to work with Java 8
  - Build Project
  - Run test


### Flowdroid

## TEST METRICS

> failed: 0, passed: 63, ignored: 39 of 102 tests.

|     Test      |   TP   | FP | 
|:-------------:|:------:|:--:|
|   Aliasing    | 10/11  | 0  |   
|     Array     |  0/9   | 0  |   
|     Basic     | 56/59  | 2  |  
|  Collection   |  2/14  | 1  |
| DataStructure |  5/5   | 2  | 
|    Factory    |  3/3   | 1  |  
|     Inter     | 10/16  | 0  |  
|    Session    |  0/3   | 0  |  
| StrongUpdate  |  0/1   | 0  |   
|   **TOTAL**   | 86/121 | 6  |

- **Precision:** 0.93
- **Recall:** 0.71
- **F-score:** 0.81

To have detailed information about each group of tests run, [see here.](old-metrics)

**OBSERVATIONS**
- Flowdroid is not taking in count the TP expected in StrongUpdate4;
- Test Basic40 is commented in the test suite so the amount of TP differs from the original run by Flowdroid; 
- There are two flaky tests: Basic6 and Inter11.