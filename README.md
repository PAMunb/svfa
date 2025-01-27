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
~~TTests failed: 34, passed: 64, ignored: 6 of 104 test~~T

Tests failed: 40, passed: 64, ignored: 0 of 104 test
Tests failed: 33, passed: 71, ignored: 0 of 104 test (original)

Tests failed: +17.5%, passed: +9.86, ignored: 0 of 104 test (original)

#### AliasingTest
Tests failed: 0, passed: 5, ignored: 1 of 6 test
#### ArraysTest
Tests failed: 9, passed: 1, ignored: 0 of 10 test
#### BasicTest
Tests failed: 0, passed: 37, ignored: 5 of 42 test

Fails:
17
36 (same)
38
42

#### CollectionTest 
Tests failed: 14, passed: 1, ignored: 0 of 15 test
#### DataStructureTest &#9745;
Tests failed: 1, passed: 5, ignored: 0 of 6 test
#### FactoryTest &#9745;
Tests failed: 1, passed: 2, ignored: 0 of 3 test
#### InterTest
Tests failed: 7, passed: 7, ignored: 0 of 14 test
~~#### PredTest~~
~~Tests failed: 3, passed: 6, ignored: 0 of 9 test~~
~~#### ReflectionTest~~
~~Tests failed: 4, passed: 0, ignored: 0 of 4 test~~
~~#### SanitizerTest~~
~~Tests failed: 2, passed: 4, ignored: 0 of 6 test~~
#### SessionTest &#9745;
Tests failed: 3, passed: 0, ignored: 0 of 3 test
#### StrongUpdateTest &#9745;
Tests failed: 1, passed: 4, ignored: 0 of 5 test

## TEST METRICS

|     Test      |   TP   | FP | 
|:-------------:|:------:|:--:|
|   Aliasing    | 10/11  | 0  |   
|     Array     |  0/9   | 0  |   
|     Basic     | 57/59  | 2  |  
|  Collection   |  2/14  | 1  |
| DataStructure |  5/5   | 2  | 
|    Factory    |  3/3   | 1  |  
|     Inter     | 10/16  | 0  |  
|    Session    |  0/3   | 0  |  
| StrongUpdate  |  0/1   | 0  |   
|   **TOTAL**   | 87/121 | 6  |

- **Precision:** 0.94
- **Recall:** 0.72
- **F-score:** 0.81


In the next tables, there is detailed information about each group of tests run.
- **AliasingTest** - failed: 0, passed: 5, ignored: 1 of 6 tests.

|      Test      | Expected | Actual | Status | TP | FP | Precision | Recall | F-score |
|:--------------:|:--------:|:------:|:------:|:--:|:--:|:---------:|:------:|:-------:|
|   Aliasing1    |    1     |   1    |   ✅    | 1  | 0  |     -     |   -    |    -    |
|   Aliasing2    |    0     |   0    |   ✅    | 0  | 0  |     -     |   -    |    -    |
|   Aliasing3    |    0     |   0    |   ✅    | 0  | 0  |     -     |   -    |    -    |
|   Aliasing4    |    2     |   2    |   ✅    | 2  | 0  |     -     |   -    |    -    |
|   Aliasing5    |    1     |   0    |   ❌    | 0  | 0  |     -     |   -    |    -    |
|   Aliasing6    |    7     |   7    |   ✅    | 7  | 0  |     -     |   -    |    -    |
|     TOTAL      |    11    |   10   |  5/6   | 10 | 0  |     1.00     |   0.91    |    0.95    |

- **ArraysTest** - failed: 0, passed: 1, ignored: 9 of 10 test.

|      Test      | Expected | Actual | Status | TP | FP | Precision | Recall | F-score |
|:--------------:|:--------:|:------:|:------:|:--:|:--:|:---------:|:------:|:-------:|
|     Array1     |    1     |   0    |   ❌    | 0  | 0  |     -     |   -    |    -    |
|     Array2     |    1     |   0    |   ❌    | 0  | 0  |     -     |   -    |    -    |
|     Array3     |    1     |   0    |   ❌    | 0  | 0  |     -     |   -    |    -    |
|     Array4     |    1     |   0    |   ❌    | 0  | 0  |     -     |   -    |    -    |
|     Array5     |    0     |   0    |   ✅    | 0  | 0  |     -     |   -    |    -    |
|     Array6     |    1     |   0    |   ❌    | 0  | 0  |     -     |   -    |    -    |
|     Array7     |    1     |   0    |   ❌    | 0  | 0  |     -     |   -    |    -    |
|     Array8     |    1     |   0    |   ❌    | 0  | 0  |     -     |   -    |    -    |
|     Array9     |    1     |   0    |   ❌    | 0  | 0  |     -     |   -    |    -    |
|    Array10     |    1     |   0    |   ❌    | 0  | 0  |     -     |   -    |    -    |
|     TOTAL      |    9     |   0    |  1/10  | 0  | 0  |     0     |   0    |    0    |

- **BasicTest** - failed: 0, passed: 37, ignored: 4 of 41 test.

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
|    Basic17     |    1     |   2    |   ❌    |  1  |  1  |     -     |   -    |    -    |
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
|    Basic40     |   N/A    |  N/A   |  N/A   | N/A | N/A |    N/A    |  N/A   |   N/A   |
|    Basic41     |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
|    Basic42     |    1     |   0    |   ❌    |  0  |  0  |     -     |   -    |    -    |
|     TOTAL      |    59    |   59   | 37/41  | 57  |  2  |     0.97     |   0.97    |    0.97    |

- **CollectionTest** - failed: 0, passed: 2, ignored: 12 of 14 tests.

|      Test      | Expected | Actual | Status | TP | FP | Precision | Recall | F-score |
|:--------------:|:--------:|:------:|:------:|:--:|:--:|:---------:|:------:|:-------:|
|  Collection1   |    1     |   1    |   ✅    | 1  | 0  |     -     |   -    |    -    |
|  Collection2   |    1     |   2    |   ❌    | 1  | 1  |     -     |   -    |    -    |
|  Collection3   |    2     |   0    |   ❌    | 0  | 0  |     -     |   -    |    -    |
|  Collection4   |    1     |   0    |   ❌    | 0  | 0  |     -     |   -    |    -    |
|  Collection5   |    1     |   0    |   ❌    | 0  | 0  |     -     |   -    |    -    |
|  Collection6   |    1     |   0    |   ❌    | 0  | 0  |     -     |   -    |    -    |
|  Collection7   |    1     |   0    |   ❌    | 0  | 0  |     -     |   -    |    -    |
|  Collection8   |    1     |   0    |   ❌    | 0  | 0  |     -     |   -    |    -    |
|  Collection9   |    0     |   0    |   ✅    | 0  | 0  |     -     |   -    |    -    |
|  Collection10  |    1     |   0    |   ❌    | 0  | 0  |     -     |   -    |    -    |
|  Collection11  |    1     |   0    |   ❌    | 0  | 0  |     -     |   -    |    -    |
|  Collection12  |    1     |   0    |   ❌    | 0  | 0  |     -     |   -    |    -    |
|  Collection13  |    1     |   0    |   ❌    | 0  | 0  |     -     |   -    |    -    |
|  Collection14  |    1     |   0    |   ❌    | 0  | 0  |     -     |   -    |    -    |
|     TOTAL      |    14    |   3    |  2/14  | 2  | 1  |     0.67     |   0.14    |    0.24    |

- **DataStructureTest** - failed: 0, passed: 4, ignored: 2 of 6 tests.

|      Test      | Expected | Actual | Status | TP | FP | Precision | Recall | F-score |
|:--------------:|:--------:|:------:|:------:|:--:|:--:|:---------:|:------:|:-------:|
| DataStructure1 |    1     |   1    |   ✅    | 1  | 0  |     -     |   -    |    -    |
| DataStructure2 |    1     |   2    |   ❌    | 1  | 1  |     -     |   -    |    -    |
| DataStructure3 |    1     |   1    |   ✅    | 1  | 0  |     -     |   -    |    -    |
| DataStructure4 |    0     |   1    |   ❌    | 0  | 1  |     -     |   -    |    -    |
| DataStructure5 |    1     |   1    |   ✅    | 1  | 0  |     -     |   -    |    -    |
| DataStructure6 |    1     |   1    |   ✅    | 1  | 0  |     -     |   -    |    -    |
|     TOTAL      |    5     |   7    |  4/6   | 5  | 2  |    0.71     |   1.00    |   0.83    |


- **FactoryTest** - failed: 0, passed: 2, ignored: 1 of 3 tests.

|      Test      | Expected | Actual | Status | TP  | FP  | Precision | Recall | F-score |
|:--------------:|:--------:|:------:|:------:|:---:|:---:|:---------:|:------:|:-------:|
|    Factory1    |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
|    Factory2    |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
|    Factory3    |    1     |   2    |   ❌    |  1  |  1  |     -     |   -    |    -    |
|     TOTAL      |    3     |   4    |  2/3   |  3  |  1  |   0.75    |  1.00  |  0.86   |

- **InterTest** - failed: 0, passed:8, ignored: 6 of 14 tests

|      Test      | Expected | Actual | Status | TP | FP | Precision | Recall | F-score |
|:--------------:|:--------:|:------:|:------:|:--:|:--:|:---------:|:------:|:-------:|
|     Inter1     |    1     |   1    |   ✅    | 1  | 0  |     -     |   -    |    -    |
|     Inter2     |    2     |   2    |   ✅    | 2  | 0  |     -     |   -    |    -    |
|     Inter3     |    1     |   1    |   ✅    | 1  | 0  |     -     |   -    |    -    |
|     Inter4     |    1     |   0    |   ❌    | 0  | 0  |     -     |   -    |    -    |
|     Inter5     |    1     |   1    |   ✅    | 1  | 0  |     -     |   -    |    -    |
|     Inter6     |    1     |   0    |   ❌    | 0  | 0  |     -     |   -    |    -    |
|     Inter7     |    1     |   0    |   ❌    | 0  | 0  |     -     |   -    |    -    |
|     Inter8     |    1     |   1    |   ✅    | 1  | 0  |     -     |   -    |    -    |
|     Inter9     |    2     |   1    |   ❌    | 1  | 0  |     -     |   -    |    -    |
|    Inter10     |    1     |   1    |   ✅    | 1  | 0  |     -     |   -    |    -    |
|    Inter11     |    1     |   0    |   ❌    | 0  | 0  |     -     |   -    |    -    |
|    Inter12     |    1     |   0    |   ❌    | 0  | 0  |     -     |   -    |    -    |
|    Inter13     |    1     |   1    |   ✅    | 1  | 0  |     -     |   -    |    -    |
|    Inter14     |    1     |   1    |   ✅    | 1  | 0  |     -     |   -    |    -    |
|     TOTAL      |    16    |   10   |  6/14  | 10 | 0  |     1.00     |   0.63    |    0.77    |

- **SessionTest** - failed: 0, passed: 0, ignored: 3 of 3 tests.

|      Test      | Expected | Actual | Status | TP  | FP  | Precision | Recall | F-score |
|:--------------:|:--------:|:------:|:------:|:---:|:---:|:---------:|:------:|:-------:|
|    Session1    |    1     |   0    |   ❌    |  0  |  0  |     -     |   -    |    -    |
|    Session2    |    1     |   0    |   ❌    |  0  |  0  |     -     |   -    |    -    |
|    Session3    |    1     |   0    |   ❌    |  0  |  0  |     -     |   -    |    -    |
|     TOTAL      |    3     |   0    |  0/3   |  0  |  0  |     0     |   0    |    0    |

- **StrongUpdateTest** - failed: 0, passed: 4, ignored: 1 of 5 tests.

|      Test      | Expected | Actual | Status | TP | FP | Precision | Recall | F-score |
|:--------------:|:--------:|:------:|:------:|:--:|:--:|:---------:|:------:|:-------:|
| StrongUpdate1  |    0     |   0    |   ✅    | 0  | 0  |     -     |   -    |    -    |
| StrongUpdate2  |    0     |   0    |   ✅    | 0  | 0  |     -     |   -    |    -    |
| StrongUpdate3  |    0     |   0    |   ✅    | 0  | 0  |     -     |   -    |    -    |
| StrongUpdate4  |    1     |   0    |   ❌    | 0  | 0  |     -     |   -    |    -    |
| StrongUpdate5  |    0     |   0    |   ✅    | 0  | 0  |     -     |   -    |    -    |
|     TOTAL      |    1     |   0    |  4/5   | 0  | 0  |     0     |   0    |    0    |
