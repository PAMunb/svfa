#### TAINTBENCH metrics - Experiment I

This case emulates **Experiment 2 - TB2** that states:
>All tools are configured with sources and sinks defined in benchmark suite.

### SUMMARY

> failed: 36, passed: 3 of 39 tests - (7.69%) - 190 sg

|                     Test                     |  Found  |  Expected  |  Status  |  TP  |  FP  |  FN   |  Precision  |  Recall  |  F-score  |  Execution Time  |
|:--------------------------------------------:|:-------:|:----------:|:--------:|:----:|:----:|:-----:|:-----------:|:--------:|:---------:|:----------------:|
|                  backflash                   |    1    |     13     |    ❌     |  0   |  0   |  12   |    0.00     |   0.00   |   0.00    |    6030.94 ms    |
|           beita_com_beita_contact            |    0    |     3      |    ❌     |  0   |  0   |   3   |    0.00     |   0.00   |   0.00    |    812.41 ms     |
|                 cajino_baidu                 |    7    |     12     |    ❌     |  0   |  0   |   5   |    0.00     |   0.00   |   0.00    |   59441.85 ms    |
|                  chat_hook                   |    8    |     12     |    ❌     |  0   |  0   |   4   |    0.00     |   0.00   |   0.00    |    1072.00 ms    |
|                    chulia                    |    0    |     4      |    ❌     |  0   |  0   |   4   |    0.00     |   0.00   |   0.00    |    180.67 ms     |
|           death_ring_materialflow            |   12    |     1      |    ❌     |  0   |  11  |   0   |    0.00     |   0.00   |   0.00    |    9293.13 ms    |
|                dsencrypt_samp                |    1    |     1      |    ✅     |  1   |  0   |   0   |    1.00     |   1.00   |   1.00    |    196.63 ms     |
|                  exprespam                   |    0    |     2      |    ❌     |  0   |  0   |   2   |    0.00     |   0.00   |   0.00    |    197.39 ms     |
|                 fakeappstore                 |    0    |     3      |    ❌     |  0   |  0   |   3   |    0.00     |   0.00   |   0.00    |    207.70 ms     |
|            fakebank_android_samp             |    0    |     5      |    ❌     |  0   |  0   |   5   |    0.00     |   0.00   |   0.00    |    2277.70 ms    |
|                   fakedaum                   |    3    |     2      |    ❌     |  0   |  1   |   0   |    0.00     |   0.00   |   0.00    |    6725.05 ms    |
|                   fakemart                   |    0    |     2      |    ❌     |  0   |  0   |   2   |    0.00     |   0.00   |   0.00    |    152.90 ms     |
|                   fakeplay                   |    0    |     2      |    ❌     |  0   |  0   |   2   |    0.00     |   0.00   |   0.00    |    640.90 ms     |
|                  faketaobao                  |    0    |     4      |    ❌     |  0   |  0   |   4   |    0.00     |   0.00   |   0.00    |    277.34 ms     |
|                 godwon_samp                  |    0    |     6      |    ❌     |  0   |  0   |   6   |    0.00     |   0.00   |   0.00    |    136.82 ms     |
|           hummingbad_android_samp            |    2    |     2      |    ✅     |  2   |  0   |   0   |    1.00     |   1.00   |   1.00    |   22560.27 ms    |
|                  jollyserv                   |    1    |     1      |    ✅     |  1   |  0   |   0   |    1.00     |   1.00   |   1.00    |    835.60 ms     |
|             overlay_android_samp             |    0    |     4      |    ❌     |  0   |  0   |   4   |    0.00     |   0.00   |   0.00    |    788.22 ms     |
|         overlaylocker2_android_samp          |    0    |     7      |    ❌     |  0   |  0   |   7   |    0.00     |   0.00   |   0.00    |    747.47 ms     |
|                    phospy                    |    1    |     2      |    ❌     |  0   |  0   |   1   |    0.00     |   0.00   |   0.00    |   16743.61 ms    |
|                  proxy_samp                  |   12    |     17     |    ❌     |  0   |  0   |   5   |    0.00     |   0.00   |   0.00    |    732.74 ms     |
|             remote_control_smack             |    0    |     17     |    ❌     |  0   |  0   |  17   |    0.00     |   0.00   |   0.00    |    3075.16 ms    |
|                    repane                    |    0    |     1      |    ❌     |  0   |  0   |   1   |    0.00     |   0.00   |   0.00    |    169.23 ms     |
|                   roidsec                    |    1    |     6      |    ❌     |  0   |  0   |   5   |    0.00     |   0.00   |   0.00    |    673.83 ms     |
|                   samsapo                    |    0    |     4      |    ❌     |  0   |  0   |   4   |    0.00     |   0.00   |   0.00    |    184.66 ms     |
|                   save_me                    |    6    |     25     |    ❌     |  0   |  0   |  19   |    0.00     |   0.00   |   0.00    |    2258.44 ms    |
|                   scipiex                    |    2    |     3      |    ❌     |  0   |  0   |   1   |    0.00     |   0.00   |   0.00    |   35271.62 ms    |
|             slocker_android_samp             |    0    |     5      |    ❌     |  0   |  0   |   5   |    0.00     |   0.00   |   0.00    |    176.77 ms     |
|                  sms_google                  |    0    |     4      |    ❌     |  0   |  0   |   4   |    0.00     |   0.00   |   0.00    |    260.78 ms     |
|           sms_send_locker_qqmagic            |    2    |     6      |    ❌     |  0   |  0   |   4   |    0.00     |   0.00   |   0.00    |    365.83 ms     |
|           smssend_packageInstaller           |    0    |     5      |    ❌     |  0   |  0   |   5   |    0.00     |   0.00   |   0.00    |    6538.41 ms    |
|            smssilience_fake_vertu            |    0    |     2      |    ❌     |  0   |  0   |   2   |    0.00     |   0.00   |   0.00    |    139.57 ms     |
|  smsstealer_kysn_assassincreed_android_samp  |    0    |     5      |    ❌     |  0   |  0   |   5   |    0.00     |   0.00   |   0.00    |    480.03 ms     |
|       stels_flashplayer_android_update       |    0    |     3      |    ❌     |  0   |  0   |   3   |    0.00     |   0.00   |   0.00    |    1795.82 ms    |
|                    tetus                     |    0    |     2      |    ❌     |  0   |  0   |   2   |    0.00     |   0.00   |   0.00    |    931.39 ms     |
|           the_interview_movieshow            |    0    |     1      |    ❌     |  0   |  0   |   1   |    0.00     |   0.00   |   0.00    |    136.57 ms     |
|              threatjapan_uracto              |    1    |     2      |    ❌     |  0   |  0   |   1   |    0.00     |   0.00   |   0.00    |    156.19 ms     |
|            vibleaker_android_samp            |    0    |     4      |    ❌     |  0   |  0   |   4   |    0.00     |   0.00   |   0.00    |    7479.57 ms    |
|              xbot_android_samp               |    0    |     3      |    ❌     |  0   |  0   |   3   |    0.00     |   0.00   |   0.00    |     0.00 ms      |
| :------------------------------------------: | :-----: | :--------: | :------: | :--: | :--: | :---: | :---------: | :------: | :-------: | :--------------: |
|                    TOTAL                     |   60    |    203     |   3/39   |  4   |  12  |  155  |    0.25     |   0.03   |   0.05    |   190145.21 ms   |
