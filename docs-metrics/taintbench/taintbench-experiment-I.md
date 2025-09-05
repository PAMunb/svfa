#### TAINTBENCH metrics - Experiment I

This case emulates **Experiment 2 - TB2** that states:
>All tools are configured with sources and sinks defined in benchmark suite.

### SUMMARY

>failed: 36, passed: 3 of 39 tests - (7.69%) - 165sg

|                     Test                     |  Found   |  Expected   |  Status   |  TP   |  FP   |  FN   |  Precision   |  Recall  |  F-score  |  Execution Time  |
|:--------------------------------------------:|:--------:|:-----------:|:---------:|:-----:|:-----:|:-----:|:------------:|:--------:|:---------:|:----------------:|
|                  backflash                   |    1     |     13      |     ❌     |   0   |   0   |  12   |     0.00     |   0.00   |   0.00    |    5900.28 ms    |
|           beita_com_beita_contact            |    0     |      3      |     ❌     |   0   |   0   |   3   |     0.00     |   0.00   |   0.00    |    713.14 ms     |
|                 cajino_baidu                 |    7     |     12      |     ❌     |   0   |   0   |   5   |     0.00     |   0.00   |   0.00    |   50684.01 ms    |
|                  chat_hook                   |    8     |     12      |     ❌     |   0   |   0   |   4   |     0.00     |   0.00   |   0.00    |    905.10 ms     |
|                    chulia                    |    0     |      4      |     ❌     |   0   |   0   |   4   |     0.00     |   0.00   |   0.00    |    124.20 ms     |
|           death_ring_materialflow            |    12    |      1      |     ❌     |   0   |  11   |   0   |     0.00     |   0.00   |   0.00    |    7103.09 ms    |
|                dsencrypt_samp                |    1     |      1      |     ✅     |   1   |   0   |   0   |     1.00     |   1.00   |   1.00    |    168.87 ms     |
|                  exprespam                   |    0     |      2      |     ❌     |   0   |   0   |   2   |     0.00     |   0.00   |   0.00    |    155.44 ms     |
|                 fakeappstore                 |    0     |      3      |     ❌     |   0   |   0   |   3   |     0.00     |   0.00   |   0.00    |    182.27 ms     |
|            fakebank_android_samp             |    0     |      5      |     ❌     |   0   |   0   |   5   |     0.00     |   0.00   |   0.00    |    2301.89 ms    |
|                   fakedaum                   |    3     |      2      |     ❌     |   0   |   1   |   0   |     0.00     |   0.00   |   0.00    |    5702.75 ms    |
|                   fakemart                   |    0     |      2      |     ❌     |   0   |   0   |   2   |     0.00     |   0.00   |   0.00    |    127.54 ms     |
|                   fakeplay                   |    0     |      2      |     ❌     |   0   |   0   |   2   |     0.00     |   0.00   |   0.00    |    497.06 ms     |
|                  faketaobao                  |    0     |      4      |     ❌     |   0   |   0   |   4   |     0.00     |   0.00   |   0.00    |    240.70 ms     |
|                 godwon_samp                  |    0     |      6      |     ❌     |   0   |   0   |   6   |     0.00     |   0.00   |   0.00    |    125.66 ms     |
|                    repane                    |    0     |      1      |     ❌     |   0   |   0   |   1   |     0.00     |   0.00   |   0.00    |    159.02 ms     |
|                   roidsec                    |    1     |      6      |     ❌     |   0   |   0   |   5   |     0.00     |   0.00   |   0.00    |    653.31 ms     |
|                   samsapo                    |    0     |      4      |     ❌     |   0   |   0   |   4   |     0.00     |   0.00   |   0.00    |    156.36 ms     |
|                   save_me                    |    6     |     25      |     ❌     |   0   |   0   |  19   |     0.00     |   0.00   |   0.00    |    2042.71 ms    |
|                   scipiex                    |    2     |      3      |     ❌     |   0   |   0   |   1   |     0.00     |   0.00   |   0.00    |   32514.50 ms    |
|             slocker_android_samp             |    0     |      5      |     ❌     |   0   |   0   |   5   |     0.00     |   0.00   |   0.00    |    164.52 ms     |
|                  sms_google                  |    0     |      4      |     ❌     |   0   |   0   |   4   |     0.00     |   0.00   |   0.00    |    236.64 ms     |
|           sms_send_locker_qqmagic            |    2     |      6      |     ❌     |   0   |   0   |   4   |     0.00     |   0.00   |   0.00    |    346.04 ms     |
|           smssend_packageInstaller           |    0     |      5      |     ❌     |   0   |   0   |   5   |     0.00     |   0.00   |   0.00    |    6586.48 ms    |
|            smssilience_fake_vertu            |    0     |      2      |     ❌     |   0   |   0   |   2   |     0.00     |   0.00   |   0.00    |    128.70 ms     |
|  smsstealer_kysn_assassincreed_android_samp  |    0     |      5      |     ❌     |   0   |   0   |   5   |     0.00     |   0.00   |   0.00    |    447.03 ms     |
|       stels_flashplayer_android_update       |    0     |      3      |     ❌     |   0   |   0   |   3   |     0.00     |   0.00   |   0.00    |    1766.98 ms    |
|                    tetus                     |    0     |      2      |     ❌     |   0   |   0   |   2   |     0.00     |   0.00   |   0.00    |    885.15 ms     |
|           the_interview_movieshow            |    0     |      1      |     ❌     |   0   |   0   |   1   |     0.00     |   0.00   |   0.00    |    129.47 ms     |
|              threatjapan_uracto              |    1     |      2      |     ❌     |   0   |   0   |   1   |     0.00     |   0.00   |   0.00    |    150.90 ms     |
|            vibleaker_android_samp            |    0     |      4      |     ❌     |   0   |   0   |   4   |     0.00     |   0.00   |   0.00    |    7335.31 ms    |
|              xbot_android_samp               |    0     |      3      |     ❌     |   0   |   0   |   3   |     0.00     |   0.00   |   0.00    |     0.00 ms      |
| :------------------------------------------: | :------: | :---------: | :-------: | :---: | :---: | :---: | :----------: | :------: | :-------: | :--------------: |
|                    TOTAL                     |    61    |     203     |   3/39    |   4   |  12   |  154  |     0.25     |   0.03   |   0.05    |   165781.01 ms   |
