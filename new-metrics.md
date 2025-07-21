#### JSVFA metrics

> Metrics for Pass Rate

|     Test      | Expected | Passed | Failed | Pass Rate |
|:-------------:|:--------:|:------:|:------:|:---------:|
|   Aliasing    |    6     |   4    |   2    |  66.67%   |    
|     Array     |    10    |   5    |   5    |    50%    |   
|     Basic     |    42    |   37   |   5    |  88.10%   |      
|  Collection   |    14    |   4    |   10   |  28.57%   |   
| DataStructure |    6     |   4    |   2    |  66.67%   |    
|    Factory    |    3     |   2    |   1    |  66.67%   |    
|     Inter     |    14    |   10   |   4    |  71.43%   |     
|    Session    |    3     |   0    |   3    |    0%     |      
| StrongUpdate  |    5     |   3    |   2    |    60%    |       
|   **TOTAL**   |   103    |   69   |   34   |  66.99%   |

> Pass Rate metric for additional (unused) tests.

|    Test    | Expected | Passed | Failed | Pass Rate |
|:----------:|:--------:|:------:|:------:|:---------:| 
|    Pred    |    9     |   6    |   3    |  66.67%   |   
| Reflection |    4     |   0    |   4    |    0%     |   
| Sanitizer  |    6     |   2    |   4    |  33.33%   |

> Metrics for Precision, Recall and F-score.

|     Test      | Expected | Actual | TP | FP | Precision | Recall | F-score |
|:-------------:|:--------:|:-------|:--:|:--:|:---------:|:------:|:-------:|
|   Aliasing    |    11    | 4      | 4  | 0  |   1.00    |  0.36  |  0.53   |
|     Array     |    9     | 11     | 7  | 4  |   0.64    |  0.78  |  0.70   |
|     Basic     |    61    | 58     | 57 | 1  |   0.98    |  0.97  |  0.97   |
|  Collection   |    14    | 4      | 4  | 0  |   1.00    |  0.29  |  0.44   |
| DataStructure |    5     | 5      | 4  | 1  |   0.80    |  0.80  |  0.80   |
|    Factory    |    3     | 4      | 3  | 1  |   0.75    |  1.00  |  0.86   |
|     Inter     |    16    | 12     | 12 | 0  |   1.00    |  0.75  |  0.86   |
|    Session    |    3     | 0      | 0  | 0  |     0     |   0    |    0    |
| StrongUpdate  |    1     | 3      | 1  | 2  |   0.33    |  1.00  |  0.50   |
|   **TOTAL**   |   123    | 101    | 92 | 9  |   0.91    |  0.75  |  0.82   |

> Details

- **AliasingTest** - failed: 5, passed: 1 of 6 tests - (16.67%)

|   Test    | Found | Expected | Status | TP | FP | FN | Precision | Recall | F-score |
|:---------:|:-----:|:--------:|:------:|:--:|:--:|:---|:---------:|:------:|:-------:|
| Aliasing1 |   1   |    1     |   ✅    | 1  | 0  | 0  |   1.00    |  1.00  |  1.00   |
| Aliasing2 |   0   |    1     |   ❌    | 0  | 0  | 1  |   0.00    |  0.00  |  0.00   |
| Aliasing3 |   0   |    1     |   ❌    | 0  | 0  | 1  |   0.00    |  0.00  |  0.00   |
| Aliasing4 |   2   |    1     |   ❌    | 0  | 1  | 0  |   0.00    |  0.00  |  0.00   |
| Aliasing5 |   0   |    1     |   ❌    | 0  | 0  | 1  |   0.00    |  0.00  |  0.00   |
| Aliasing6 |   1   |    7     |   ❌    | 0  | 0  | 6  |   0.00    |  0.00  |  0.00   |
|   TOTAL   |   4   |    12    |  1/6   | 1  | 1  | 9  |   0.50    |  0.10  |  0.17   |


- **ArraysTest** - failed: 5, passed: 5, ignored: 0 of 10 test.

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


- **BasicTest** - failed: 5, passed: 37, ignored: 0 of 42 test.

|      Test      | Expected | Actual | Status | TP | FP  | Precision | Recall | F-score |
|:--------------:|:--------:|:------:|:------:|:--:|:---:|:---------:|:------:|:-------:|
|     Basic1     |    1     |   1    |   ✅    | 1  |  0  |     -     |   -    |    -    |
|     Basic2     |    1     |   1    |   ✅    | 1  |  0  |     -     |   -    |    -    |
|     Basic3     |    1     |   1    |   ✅    | 1  |  0  |     -     |   -    |    -    |
|     Basic4     |    1     |   1    |   ✅    | 1  |  0  |     -     |   -    |    -    |
|     Basic5     |    3     |   3    |   ✅    | 3  |  0  |     -     |   -    |    -    |
|     Basic6     |    1     |   1    |   ✅    | 1  |  0  |     -     |   -    |    -    |
|     Basic7     |    1     |   1    |   ✅    | 1  |  0  |     -     |   -    |    -    |
|     Basic8     |    1     |   1    |   ✅    | 1  |  0  |     -     |   -    |    -    |
|     Basic9     |    1     |   1    |   ✅    | 1  |  0  |     -     |   -    |    -    |
|    Basic10     |    1     |   1    |   ✅    | 1  |  0  |     -     |   -    |    -    |
|    Basic11     |    2     |   2    |   ✅    | 2  |  0  |     -     |   -    |    -    |
|    Basic12     |    2     |   2    |   ✅    | 2  |  0  |     -     |   -    |    -    |
|    Basic13     |    1     |   1    |   ✅    | 1  |  0  |     -     |   -    |    -    |
|    Basic14     |    1     |   1    |   ✅    | 1  |  0  |     -     |   -    |    -    |
|    Basic15     |    1     |   1    |   ✅    | 1  |  0  |     -     |   -    |    -    |
|    Basic16     |    1     |   1    |   ✅    | 1  |  0  |     -     |   -    |    -    |
|    Basic17     |    1     |   1    |   ✅    | 1  |  0  |     -     |   -    |    -    |
|    Basic18     |    1     |   1    |   ✅    | 1  |  0  |     -     |   -    |    -    |
|    Basic19     |    1     |   1    |   ✅    | 1  |  0  |     -     |   -    |    -    |
|    Basic20     |    1     |   1    |   ✅    | 1  |  0  |     -     |   -    |    -    |
|    Basic21     |    4     |   4    |   ✅    | 4  |  0  |     -     |   -    |    -    |
|    Basic22     |    1     |   1    |   ✅    | 1  |  0  |     -     |   -    |    -    |
|    Basic23     |    3     |   3    |   ✅    | 3  |  0  |     -     |   -    |    -    |
|    Basic24     |    1     |   1    |   ✅    | 1  |  0  |     -     |   -    |    -    |
|    Basic25     |    1     |   1    |   ✅    | 1  |  0  |     -     |   -    |    -    |
|    Basic26     |    1     |   1    |   ✅    | 1  |  0  |     -     |   -    |    -    |
|    Basic27     |    1     |   1    |   ✅    | 1  |  0  |     -     |   -    |    -    |
|    Basic28     |    2     |   1    |   ❌    | 1  |  0  |     -     |   -    |    -    |
|    Basic29     |    2     |   2    |   ✅    | 2  |  0  |     -     |   -    |    -    |
|    Basic30     |    1     |   1    |   ✅    | 1  |  0  |     -     |   -    |    -    |
|    Basic31     |    3     |   3    |   ✅    | 3  |  0  |     -     |   -    |    -    |
|    Basic32     |    1     |   1    |   ✅    | 1  |  0  |     -     |   -    |    -    |
|    Basic33     |    1     |   1    |   ✅    | 1  |  0  |     -     |   -    |    -    |
|    Basic34     |    2     |   2    |   ✅    | 2  |  0  |     -     |   -    |    -    |
|    Basic35     |    6     |   6    |   ✅    | 6  |  0  |     -     |   -    |    -    |
|    Basic36     |    1     |   0    |   ❌    | 0  |  0  |     -     |   -    |    -    |
|    Basic37     |    1     |   1    |   ✅    | 1  |  0  |     -     |   -    |    -    |
|    Basic38     |    1     |   2    |   ❌    | 1  |  1  |     -     |   -    |    -    |
|    Basic39     |    1     |   1    |   ✅    | 1  |  0  |     -     |   -    |    -    |
|    Basic40     |    1     |   0    |   ❌    | 0  |  0  |     -     |   -    |    -    |
|    Basic41     |    1     |   1    |   ✅    | 1  |  0  |     -     |   -    |    -    |
|    Basic42     |    1     |   0    |   ❌    | 0  |  0  |     -     |   -    |    -    |
|     TOTAL      |    61    |   58   | 37/42  | 57 |  1  |   0.98    |  0.97  |  0.97   |


- **CollectionTest** - failed: 10, passed: 4, ignored: 0 of 14 tests.

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


- **DataStructureTest** - failed: 2, passed: 4, ignored: 0 of 6 tests.

|      Test      | Expected | Actual | Status | TP  | FP  | Precision | Recall | F-score |
|:--------------:|:--------:|:------:|:------:|:---:|:---:|:---------:|:------:|:-------:|
| DataStructure1 |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
| DataStructure2 |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
| DataStructure3 |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
| DataStructure4 |    0     |   1    |   ❌    |  0  |  1  |     -     |   -    |    -    |
| DataStructure5 |    1     |   0    |   ❌    |  0  |  0  |     -     |   -    |    -    |
| DataStructure6 |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
|     TOTAL      |    5     |   5    |  4/6   |  4  |  1  |   0.80    |  0.80  |  0.80   |


- **FactoryTest** - failed: 1, passed: 2, ignored: 0 of 3 tests.

|      Test      | Expected | Actual | Status | TP  | FP  | Precision | Recall | F-score |
|:--------------:|:--------:|:------:|:------:|:---:|:---:|:---------:|:------:|:-------:|
|    Factory1    |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
|    Factory2    |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
|    Factory3    |    1     |   2    |   ❌    |  1  |  1  |     -     |   -    |    -    |
|     TOTAL      |    3     |   4    |  2/3   |  3  |  1  |   0.75    |  1.00  |  0.86   |


- **InterTest** - failed: 4, passed:10, ignored: 0 of 14 tests

|      Test      | Expected | Actual | Status | TP  | FP  | Precision | Recall | F-score |
|:--------------:|:--------:|:------:|:------:|:---:|:---:|:---------:|:------:|:-------:|
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


- **SessionTest** - failed: 3, passed: 0, ignored: 0 of 3 tests.

|      Test      | Expected | Actual | Status | TP  | FP  | Precision | Recall | F-score |
|:--------------:|:--------:|:------:|:------:|:---:|:---:|:---------:|:------:|:-------:|
|    Session1    |    1     |   0    |   ❌    |  0  |  0  |     -     |   -    |    -    |
|    Session2    |    1     |   0    |   ❌    |  0  |  0  |     -     |   -    |    -    |
|    Session3    |    1     |   0    |   ❌    |  0  |  0  |     -     |   -    |    -    |
|     TOTAL      |    3     |   0    |  0/3   |  0  |  0  |     0     |   0    |    0    |


- **StrongUpdateTest** - failed: 2, passed: 3, ignored: 0 of 5 tests.

|      Test      | Expected | Actual | Status | TP  | FP  | Precision | Recall | F-score |
|:--------------:|:--------:|:------:|:------:|:---:|:---:|:---------:|:------:|:-------:|
| StrongUpdate1  |    0     |   0    |   ✅    |  0  |  0  |     -     |   -    |    -    |
| StrongUpdate2  |    0     |   0    |   ✅    |  0  |  0  |     -     |   -    |    -    |
| StrongUpdate3  |    0     |   1    |   ❌    |  0  |  1  |     -     |   -    |    -    |
| StrongUpdate4  |    1     |   1    |   ✅    |  1  |  0  |     -     |   -    |    -    |
| StrongUpdate5  |    0     |   1    |   ❌    |  0  |  1  |     -     |   -    |    -    |
|     TOTAL      |    1     |   3    |  3/5   |  1  |  2  |   0.33    |  1.00  |  0.50   |