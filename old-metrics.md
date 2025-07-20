#### JSVFA old metrics

> Pass Rate metric for additional (unused) tests.

|    Test    | Expected | Passed | Failed | Pass Rate |
|:----------:|:--------:|:------:|:------:|:---------:| 
|    Pred    |    9     |   6    |   3    |  66.67%   |   
| Reflection |    4     |   0    |   4    |    0%     |   
| Sanitizer  |    6     |   1    |   5    |  16.67%   |

> Metrics
- **securibench.micro** - failed: 44, passed: 59 of 103 tests - (57.28%)

|      Test      | Found | Expected | Status | TP | FP | FN | Precision | Recall | F-score |
|:--------------:|:-----:|:--------:|:------:|:--:|:--:|:---|:---------:|:------:|:-------:|
|    aliasing    |  10   |    12    |  2/6   | 8  | 1  | 3  |   0.89    |  0.73  |  0.80   |
|     arrays     |   0   |    9     |  1/10  | 0  | 0  | 9  |   0.00    |  0.00  |  0.00   | 
|     basic      |  60   |    60    | 36/42  | 52 | 3  | 3  |   0.95    |  0.95  |  0.95   |
|  collections   |   3   |    14    |  2/14  | 1  | 1  | 12 |   0.50    |  0.08  |  0.14   |
| datastructures |   7   |    5     |  4/6   | 4  | 2  | 0  |   0.67    |  1.00  |  0.80   |
|   factories    |   4   |    3     |  2/3   | 2  | 1  | 0  |   0.67    |  1.00  |  0.80   |
|     inter      |  10   |    16    |  8/14  | 9  | 0  | 6  |   1.00    |  0.60  |  0.75   |
|    session     |   0   |    3     |  0/3   | 0  | 0  | 3  |   0.00    |  0.00  |  0.00   |
| strong_updates |   0   |    1     |  4/5   | 0  | 0  | 1  |   0.00    |  0.00  |  0.00   |
|     TOTAL      |  94   |   123    | 59/103 | 76 | 8  | 37 |   0.90    |  0.67  |  0.77   |


> Details

- **securibench.micro.aliasing** - failed: 4, passed: 2 of 6 tests - (33.33%)

|   Test    | Found | Expected | Status | TP | FP | FN | Precision | Recall | F-score |
|:---------:|:-----:|:--------:|:------:|:--:|:--:|:---|:---------:|:------:|:-------:|
| Aliasing1 |   1   |    1     |   ✅    | 1  | 0  | 0  |   1.00    |  1.00  |  1.00   |
| Aliasing2 |   0   |    1     |   ❌    | 0  | 0  | 1  |   0.00    |  0.00  |  0.00   |
| Aliasing3 |   0   |    1     |   ❌    | 0  | 0  | 1  |   0.00    |  0.00  |  0.00   |
| Aliasing4 |   2   |    1     |   ❌    | 0  | 1  | 0  |   0.00    |  0.00  |  0.00   |
| Aliasing5 |   0   |    1     |   ❌    | 0  | 0  | 1  |   0.00    |  0.00  |  0.00   |
| Aliasing6 |   7   |    7     |   ✅    | 7  | 0  | 0  |   1.00    |  1.00  |  1.00   |
|   TOTAL   |  10   |    12    |  2/6   | 8  | 1  | 3  |   0.89    |  0.73  |  0.80   |

- **securibench.micro.arrays** - failed: 9, passed: 1 of 10 tests - (10.0%)

|   Test   | Found | Expected | Status | TP | FP | FN | Precision | Recall | F-score |
|:--------:|:-----:|:--------:|:------:|:--:|:--:|:---|:---------:|:------:|:-------:|
| Arrays1  |   0   |    1     |   ❌    | 0  | 0  | 1  |   0.00    |  0.00  |  0.00   |
| Arrays10 |   0   |    1     |   ❌    | 0  | 0  | 1  |   0.00    |  0.00  |  0.00   |
| Arrays2  |   0   |    1     |   ❌    | 0  | 0  | 1  |   0.00    |  0.00  |  0.00   |
| Arrays3  |   0   |    1     |   ❌    | 0  | 0  | 1  |   0.00    |  0.00  |  0.00   |
| Arrays4  |   0   |    1     |   ❌    | 0  | 0  | 1  |   0.00    |  0.00  |  0.00   |
| Arrays5  |   0   |    0     |   ✅    | 0  | 0  | 0  |   0.00    |  0.00  |  0.00   |
| Arrays6  |   0   |    1     |   ❌    | 0  | 0  | 1  |   0.00    |  0.00  |  0.00   |
| Arrays7  |   0   |    1     |   ❌    | 0  | 0  | 1  |   0.00    |  0.00  |  0.00   |
| Arrays8  |   0   |    1     |   ❌    | 0  | 0  | 1  |   0.00    |  0.00  |  0.00   |
| Arrays9  |   0   |    1     |   ❌    | 0  | 0  | 1  |   0.00    |  0.00  |  0.00   |
|  TOTAL   |   0   |    9     |  1/10  | 0  | 0  | 9  |   0.00    |  0.00  |  0.00   |


- **securibench.micro.basic** - failed: 6, passed: 36 of 42 tests - (85.71%)

|  Test   | Found | Expected | Status | TP | FP | FN | Precision | Recall | F-score |
|:-------:|:-----:|:--------:|:------:|:--:|:--:|:---|:---------:|:------:|:-------:|
| Basic0  |   1   |    1     |   ✅    | 1  | 0  | 0  |   1.00    |  1.00  |  1.00   |
| Basic1  |   1   |    1     |   ✅    | 1  | 0  | 0  |   1.00    |  1.00  |  1.00   |
| Basic10 |   1   |    1     |   ✅    | 1  | 0  | 0  |   1.00    |  1.00  |  1.00   |
| Basic11 |   2   |    2     |   ✅    | 2  | 0  | 0  |   1.00    |  1.00  |  1.00   |
| Basic12 |   2   |    2     |   ✅    | 2  | 0  | 0  |   1.00    |  1.00  |  1.00   |
| Basic13 |   1   |    1     |   ✅    | 1  | 0  | 0  |   1.00    |  1.00  |  1.00   |
| Basic14 |   1   |    1     |   ✅    | 1  | 0  | 0  |   1.00    |  1.00  |  1.00   |
| Basic15 |   1   |    1     |   ✅    | 1  | 0  | 0  |   1.00    |  1.00  |  1.00   |
| Basic16 |   1   |    1     |   ✅    | 1  | 0  | 0  |   1.00    |  1.00  |  1.00   |
| Basic17 |   2   |    1     |   ❌    | 0  | 1  | 0  |   0.00    |  0.00  |  0.00   |
| Basic18 |   1   |    1     |   ✅    | 1  | 0  | 0  |   1.00    |  1.00  |  1.00   |
| Basic19 |   1   |    1     |   ✅    | 1  | 0  | 0  |   1.00    |  1.00  |  1.00   |
| Basic2  |   1   |    1     |   ✅    | 1  | 0  | 0  |   1.00    |  1.00  |  1.00   |
| Basic20 |   1   |    1     |   ✅    | 1  | 0  | 0  |   1.00    |  1.00  |  1.00   |
| Basic21 |   4   |    4     |   ✅    | 4  | 0  | 0  |   1.00    |  1.00  |  1.00   |
| Basic22 |   1   |    1     |   ✅    | 1  | 0  | 0  |   1.00    |  1.00  |  1.00   |
| Basic23 |   3   |    3     |   ✅    | 3  | 0  | 0  |   1.00    |  1.00  |  1.00   |
| Basic24 |   1   |    1     |   ✅    | 1  | 0  | 0  |   1.00    |  1.00  |  1.00   |
| Basic25 |   1   |    1     |   ✅    | 1  | 0  | 0  |   1.00    |  1.00  |  1.00   |
| Basic26 |   1   |    1     |   ✅    | 1  | 0  | 0  |   1.00    |  1.00  |  1.00   |
| Basic27 |   1   |    1     |   ✅    | 1  | 0  | 0  |   1.00    |  1.00  |  1.00   |
| Basic28 |   1   |    2     |   ❌    | 0  | 0  | 1  |   0.00    |  0.00  |  0.00   |
| Basic29 |   2   |    2     |   ✅    | 2  | 0  | 0  |   1.00    |  1.00  |  1.00   |
| Basic3  |   1   |    1     |   ✅    | 1  | 0  | 0  |   1.00    |  1.00  |  1.00   |
| Basic30 |   1   |    1     |   ✅    | 1  | 0  | 0  |   1.00    |  1.00  |  1.00   |
| Basic31 |   3   |    2     |   ❌    | 0  | 1  | 0  |   0.00    |  0.00  |  0.00   |
| Basic32 |   1   |    1     |   ✅    | 1  | 0  | 0  |   1.00    |  1.00  |  1.00   |
| Basic33 |   1   |    1     |   ✅    | 1  | 0  | 0  |   1.00    |  1.00  |  1.00   |
| Basic34 |   2   |    2     |   ✅    | 2  | 0  | 0  |   1.00    |  1.00  |  1.00   |
| Basic35 |   6   |    6     |   ✅    | 6  | 0  | 0  |   1.00    |  1.00  |  1.00   |
| Basic36 |   0   |    1     |   ❌    | 0  | 0  | 1  |   0.00    |  0.00  |  0.00   |
| Basic37 |   1   |    1     |   ✅    | 1  | 0  | 0  |   1.00    |  1.00  |  1.00   |
| Basic38 |   2   |    1     |   ❌    | 0  | 1  | 0  |   0.00    |  0.00  |  0.00   |
| Basic39 |   1   |    1     |   ✅    | 1  | 0  | 0  |   1.00    |  1.00  |  1.00   |
| Basic4  |   1   |    1     |   ✅    | 1  | 0  | 0  |   1.00    |  1.00  |  1.00   |
| Basic41 |   1   |    1     |   ✅    | 1  | 0  | 0  |   1.00    |  1.00  |  1.00   |
| Basic42 |   0   |    1     |   ❌    | 0  | 0  | 1  |   0.00    |  0.00  |  0.00   |
| Basic5  |   3   |    3     |   ✅    | 3  | 0  | 0  |   1.00    |  1.00  |  1.00   |
| Basic6  |   1   |    1     |   ✅    | 1  | 0  | 0  |   1.00    |  1.00  |  1.00   |
| Basic7  |   1   |    1     |   ✅    | 1  | 0  | 0  |   1.00    |  1.00  |  1.00   |
| Basic8  |   1   |    1     |   ✅    | 1  | 0  | 0  |   1.00    |  1.00  |  1.00   |
| Basic9  |   1   |    1     |   ✅    | 1  | 0  | 0  |   1.00    |  1.00  |  1.00   |
|  TOTAL  |  60   |    60    | 36/42  | 52 | 3  | 3  |   0.95    |  0.95  |  0.95   |


- **securibench.micro.collections** - failed: 12, passed: 2 of 14 tests - (14.29%)

|     Test      | Found | Expected | Status | TP | FP | FN | Precision | Recall | F-score |
|:-------------:|:-----:|:--------:|:------:|:--:|:--:|:---|:---------:|:------:|:-------:|
| Collections1  |   1   |    1     |   ✅    | 1  | 0  | 0  |   1.00    |  1.00  |  1.00   |
| Collections10 |   0   |    1     |   ❌    | 0  | 0  | 1  |   0.00    |  0.00  |  0.00   |
| Collections11 |   0   |    1     |   ❌    | 0  | 0  | 1  |   0.00    |  0.00  |  0.00   |
| Collections12 |   0   |    1     |   ❌    | 0  | 0  | 1  |   0.00    |  0.00  |  0.00   |
| Collections13 |   0   |    1     |   ❌    | 0  | 0  | 1  |   0.00    |  0.00  |  0.00   |
| Collections14 |   0   |    1     |   ❌    | 0  | 0  | 1  |   0.00    |  0.00  |  0.00   |
| Collections2  |   2   |    1     |   ❌    | 0  | 1  | 0  |   0.00    |  0.00  |  0.00   |
| Collections3  |   0   |    2     |   ❌    | 0  | 0  | 2  |   0.00    |  0.00  |  0.00   |
| Collections4  |   0   |    1     |   ❌    | 0  | 0  | 1  |   0.00    |  0.00  |  0.00   |
| Collections5  |   0   |    1     |   ❌    | 0  | 0  | 1  |   0.00    |  0.00  |  0.00   |
| Collections6  |   0   |    1     |   ❌    | 0  | 0  | 1  |   0.00    |  0.00  |  0.00   |
| Collections7  |   0   |    1     |   ❌    | 0  | 0  | 1  |   0.00    |  0.00  |  0.00   |
| Collections8  |   0   |    1     |   ❌    | 0  | 0  | 1  |   0.00    |  0.00  |  0.00   |
| Collections9  |   0   |    0     |   ✅    | 0  | 0  | 0  |   0.00    |  0.00  |  0.00   |
|     TOTAL     |   3   |    14    |  2/14  | 1  | 1  | 12 |   0.50    |  0.08  |  0.14   |


- **securibench.micro.datastructures** - failed: 2, passed: 4 of 6 tests - (66.67%)

|      Test       | Found | Expected | Status | TP | FP | FN | Precision | Recall | F-score |
|:---------------:|:-----:|:--------:|:------:|:--:|:--:|:---|:---------:|:------:|:-------:|
| Datastructures1 |   1   |    1     |   ✅    | 1  | 0  | 0  |   1.00    |  1.00  |  1.00   |
| Datastructures2 |   2   |    1     |   ❌    | 0  | 1  | 0  |   0.00    |  0.00  |  0.00   |
| Datastructures3 |   1   |    1     |   ✅    | 1  | 0  | 0  |   1.00    |  1.00  |  1.00   |
| Datastructures4 |   1   |    0     |   ❌    | 0  | 1  | 0  |   0.00    |  0.00  |  0.00   |
| Datastructures5 |   1   |    1     |   ✅    | 1  | 0  | 0  |   1.00    |  1.00  |  1.00   |
| Datastructures6 |   1   |    1     |   ✅    | 1  | 0  | 0  |   1.00    |  1.00  |  1.00   |
|      TOTAL      |   7   |    5     |  4/6   | 4  | 2  | 0  |   0.67    |  1.00  |  0.80   |


- **securibench.micro.factories** - failed: 1, passed: 2 of 3 tests - (66.67%)

|    Test    | Found | Expected | Status | TP | FP | FN | Precision | Recall | F-score |
|:----------:|:-----:|:--------:|:------:|:--:|:--:|:---|:---------:|:------:|:-------:|
| Factories1 |   1   |    1     |   ✅    | 1  | 0  | 0  |   1.00    |  1.00  |  1.00   |
| Factories2 |   1   |    1     |   ✅    | 1  | 0  | 0  |   1.00    |  1.00  |  1.00   |
| Factories3 |   2   |    1     |   ❌    | 0  | 1  | 0  |   0.00    |  0.00  |  0.00   |
|   TOTAL    |   4   |    3     |  2/3   | 2  | 1  | 0  |   0.67    |  1.00  |  0.80   |


- **securibench.micro.inter** - failed: 6, passed: 8 of 14 tests - (57.14%)

|  Test   | Found | Expected | Status | TP | FP | FN | Precision | Recall | F-score |
|:-------:|:-----:|:--------:|:------:|:--:|:--:|:---|:---------:|:------:|:-------:|
| Inter1  |   1   |    1     |   ✅    | 1  | 0  | 0  |   1.00    |  1.00  |  1.00   |
| Inter10 |   1   |    1     |   ✅    | 1  | 0  | 0  |   1.00    |  1.00  |  1.00   |
| Inter11 |   0   |    1     |   ❌    | 0  | 0  | 1  |   0.00    |  0.00  |  0.00   |
| Inter12 |   0   |    1     |   ❌    | 0  | 0  | 1  |   0.00    |  0.00  |  0.00   |
| Inter13 |   1   |    1     |   ✅    | 1  | 0  | 0  |   1.00    |  1.00  |  1.00   |
| Inter14 |   1   |    1     |   ✅    | 1  | 0  | 0  |   1.00    |  1.00  |  1.00   |
| Inter2  |   2   |    2     |   ✅    | 2  | 0  | 0  |   1.00    |  1.00  |  1.00   |
| Inter3  |   1   |    1     |   ✅    | 1  | 0  | 0  |   1.00    |  1.00  |  1.00   |
| Inter4  |   0   |    1     |   ❌    | 0  | 0  | 1  |   0.00    |  0.00  |  0.00   |
| Inter5  |   1   |    1     |   ✅    | 1  | 0  | 0  |   1.00    |  1.00  |  1.00   |
| Inter6  |   0   |    1     |   ❌    | 0  | 0  | 1  |   0.00    |  0.00  |  0.00   |
| Inter7  |   0   |    1     |   ❌    | 0  | 0  | 1  |   0.00    |  0.00  |  0.00   |
| Inter8  |   1   |    1     |   ✅    | 1  | 0  | 0  |   1.00    |  1.00  |  1.00   |
| Inter9  |   1   |    2     |   ❌    | 0  | 0  | 1  |   0.00    |  0.00  |  0.00   |
|  TOTAL  |  10   |    16    |  8/14  | 9  | 0  | 6  |   1.00    |  0.60  |  0.75   |


- **securibench.micro.session** - failed: 3, passed: 0 of 3 tests - (0.0%)

|   Test   | Found | Expected | Status | TP | FP | FN | Precision | Recall | F-score |
|:--------:|:-----:|:--------:|:------:|:--:|:--:|:---|:---------:|:------:|:-------:|
| Session1 |   0   |    1     |   ❌    | 0  | 0  | 1  |   0.00    |  0.00  |  0.00   |
| Session2 |   0   |    1     |   ❌    | 0  | 0  | 1  |   0.00    |  0.00  |  0.00   |
| Session3 |   0   |    1     |   ❌    | 0  | 0  | 1  |   0.00    |  0.00  |  0.00   |
|  TOTAL   |   0   |    3     |  0/3   | 0  | 0  | 3  |   0.00    |  0.00  |  0.00   |


- **securibench.micro.strong_updates** - failed: 1, passed: 4 of 5 tests - (80.0%)

|      Test      | Found | Expected | Status | TP | FP | FN | Precision | Recall | F-score |
|:--------------:|:-----:|:--------:|:------:|:--:|:--:|:---|:---------:|:------:|:-------:|
| StrongUpdates1 |   0   |    0     |   ✅    | 0  | 0  | 0  |   0.00    |  0.00  |  0.00   |
| StrongUpdates2 |   0   |    0     |   ✅    | 0  | 0  | 0  |   0.00    |  0.00  |  0.00   |
| StrongUpdates3 |   0   |    0     |   ✅    | 0  | 0  | 0  |   0.00    |  0.00  |  0.00   |
| StrongUpdates4 |   0   |    1     |   ❌    | 0  | 0  | 1  |   0.00    |  0.00  |  0.00   |
| StrongUpdates5 |   0   |    0     |   ✅    | 0  | 0  | 0  |   0.00    |  0.00  |  0.00   |
|     TOTAL      |   0   |    1     |  4/5   | 0  | 0  | 1  |   0.00    |  0.00  |  0.00   |

**OBSERVATIONS**
- Flowdroid is not taking in count the TP expected in StrongUpdate4;
- Test Basic40 is commented in the test suite so the amount of TP differs from the original run by Flowdroid; 
- There are two flaky tests: Basic6 and Inter11.

> Extra Test
These tests are not executed by Flowdroid

- **securibench.micro.pred** - failed: 3, passed: 6 of 9 tests - (66.67%)

| Test  | Found | Expected | Status | TP | FP | FN | Precision | Recall | F-score |
|:-----:|:-----:|:--------:|:------:|:--:|:--:|:---|:---------:|:------:|:-------:|
| Pred1 |   0   |    0     |   ✅    | 0  | 0  | 0  |   0.00    |  0.00  |  0.00   |
| Pred2 |   1   |    1     |   ✅    | 1  | 0  | 0  |   1.00    |  1.00  |  1.00   |
| Pred3 |   1   |    0     |   ❌    | 0  | 1  | 0  |   0.00    |  0.00  |  0.00   |
| Pred4 |   1   |    1     |   ✅    | 1  | 0  | 0  |   1.00    |  1.00  |  1.00   |
| Pred5 |   1   |    1     |   ✅    | 1  | 0  | 0  |   1.00    |  1.00  |  1.00   |
| Pred6 |   1   |    0     |   ❌    | 0  | 1  | 0  |   0.00    |  0.00  |  0.00   |
| Pred7 |   1   |    0     |   ❌    | 0  | 1  | 0  |   0.00    |  0.00  |  0.00   |
| Pred8 |   1   |    1     |   ✅    | 1  | 0  | 0  |   1.00    |  1.00  |  1.00   |
| Pred9 |   1   |    1     |   ✅    | 1  | 0  | 0  |   1.00    |  1.00  |  1.00   |
| TOTAL |   8   |    5     |  6/9   | 5  | 3  | 0  |   0.63    |  1.00  |  0.77   |


- **securibench.micro.reflection** - failed: 4, passed: 0 of 4 tests - (0.0%)

| Test  | Found | Expected | Status | TP | FP | FN | Precision | Recall | F-score |
|:-----:|:-----:|:--------:|:------:|:--:|:--:|:---|:---------:|:------:|:-------:|
| Refl1 |   0   |    1     |   ❌    | 0  | 0  | 1  |   0.00    |  0.00  |  0.00   |
| Refl2 |   0   |    1     |   ❌    | 0  | 0  | 1  |   0.00    |  0.00  |  0.00   |
| Refl3 |   0   |    1     |   ❌    | 0  | 0  | 1  |   0.00    |  0.00  |  0.00   |
| Refl4 |   0   |    1     |   ❌    | 0  | 0  | 1  |   0.00    |  0.00  |  0.00   |
| TOTAL |   0   |    4     |  0/4   | 0  | 0  | 4  |   0.00    |  0.00  |  0.00   |


- **securibench.micro.sanitizers** - failed: 6, passed: 0 of 6 tests - (0.0%)
An exception is thrown when the tests run so it was not possible to compute metrics.