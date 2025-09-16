#### TAINTBENCH metrics - Experiment II

This case emulates **Experiment 3 - TB3** that configures:
>For each benchmark app, a list of sources and sinks defined in this app is used to
configure all tools. Each tool analyzes each benchmark app with the associated list
of sources and sinks

### SUMMARY

> failed: 35, passed: 4 of 39 tests - (10.26%) - 181sg

|                    Test                    |  Found  |  Expected  |  Status  |  TP  |  FP  |  FN   |  Precision  |  Recall  |  F-score  |  Execution Time  |
|:------------------------------------------:|:-------:|:----------:|:--------:|:----:|:----:|:-----:|:-----------:|:--------:|:---------:|:----------------:|
|                 backflash                  |   17    |     13     |    ❌     |  0   |  4   |   0   |    0.00     |   0.00   |   0.00    |    6028.54 ms    |
|          beita_com_beita_contact           |   14    |     3      |    ❌     |  0   |  11  |   0   |    0.00     |   0.00   |   0.00    |    794.43 ms     |
|                cajino_baidu                |   166   |     12     |    ❌     |  0   | 154  |   0   |    0.00     |   0.00   |   0.00    |   60687.41 ms    |
|                 chat_hook                  |   17    |     12     |    ❌     |  0   |  5   |   0   |    0.00     |   0.00   |   0.00    |    887.24 ms     |
|                   chulia                   |    0    |     4      |    ❌     |  0   |  0   |   4   |    0.00     |   0.00   |   0.00    |    124.57 ms     |
|          death_ring_materialflow           |   41    |     1      |    ❌     |  0   |  40  |   0   |    0.00     |   0.00   |   0.00    |    8296.54 ms    |
|               dsencrypt_samp               |    2    |     1      |    ❌     |  0   |  1   |   0   |    0.00     |   0.00   |   0.00    |    175.39 ms     |
|                 exprespam                  |    0    |     2      |    ❌     |  0   |  0   |   2   |    0.00     |   0.00   |   0.00    |    159.70 ms     |
|                fakeappstore                |    0    |     3      |    ❌     |  0   |  0   |   3   |    0.00     |   0.00   |   0.00    |    172.08 ms     |
|           fakebank_android_samp            |    8    |     5      |    ❌     |  0   |  3   |   0   |    0.00     |   0.00   |   0.00    |    2833.74 ms    |
|                  fakedaum                  |   15    |     2      |    ❌     |  0   |  13  |   0   |    0.00     |   0.00   |   0.00    |    6398.97 ms    |
|                  fakemart                  |    0    |     2      |    ❌     |  0   |  0   |   2   |    0.00     |   0.00   |   0.00    |    125.06 ms     |
|        overlaylocker2_android_samp         |   35    |     7      |    ❌     |  0   |  28  |   0   |    0.00     |   0.00   |   0.00    |    816.65 ms     |
|                   phospy                   |    3    |     2      |    ❌     |  0   |  1   |   0   |    0.00     |   0.00   |   0.00    |   15144.24 ms    |
|                 proxy_samp                 |   44    |     17     |    ❌     |  0   |  27  |   0   |    0.00     |   0.00   |   0.00    |    655.99 ms     |
|            remote_control_smack            |    8    |     17     |    ❌     |  0   |  0   |   9   |    0.00     |   0.00   |   0.00    |    3068.90 ms    |
|                   repane                   |    1    |     1      |    ✅     |  1   |  0   |   0   |    1.00     |   1.00   |   1.00    |    183.78 ms     |
|                  roidsec                   |   10    |     6      |    ❌     |  0   |  4   |   0   |    0.00     |   0.00   |   0.00    |    711.44 ms     |
|                  samsapo                   |    0    |     4      |    ❌     |  0   |  0   |   4   |    0.00     |   0.00   |   0.00    |    164.12 ms     |
|                  save_me                   |   25    |     25     |    ✅     |  25  |  0   |   0   |    1.00     |   1.00   |   1.00    |    2240.43 ms    |
|                  scipiex                   |   10    |     3      |    ❌     |  0   |  7   |   0   |    0.00     |   0.00   |   0.00    |   32571.09 ms    |
|            slocker_android_samp            |    0    |     5      |    ❌     |  0   |  0   |   5   |    0.00     |   0.00   |   0.00    |    166.51 ms     |
|                 sms_google                 |   12    |     4      |    ❌     |  0   |  8   |   0   |    0.00     |   0.00   |   0.00    |    262.18 ms     |
|          sms_send_locker_qqmagic           |   12    |     6      |    ❌     |  0   |  6   |   0   |    0.00     |   0.00   |   0.00    |    396.03 ms     |
|          smssend_packageInstaller          |   48    |     5      |    ❌     |  0   |  43  |   0   |    0.00     |   0.00   |   0.00    |    6728.17 ms    |
|           smssilience_fake_vertu           |    2    |     2      |    ✅     |  2   |  0   |   0   |    1.00     |   1.00   |   1.00    |    129.17 ms     |
| smsstealer_kysn_assassincreed_android_samp |    1    |     5      |    ❌     |  0   |  0   |   4   |    0.00     |   0.00   |   0.00    |    471.95 ms     |
|      stels_flashplayer_android_update      |   28    |     3      |    ❌     |  0   |  25  |   0   |    0.00     |   0.00   |   0.00    |    1922.55 ms    |
|                   tetus                    |   25    |     2      |    ❌     |  0   |  23  |   0   |    0.00     |   0.00   |   0.00    |    961.05 ms     |
|          the_interview_movieshow           |    1    |     1      |    ✅     |  1   |  0   |   0   |    1.00     |   1.00   |   1.00    |    132.57 ms     |
|             threatjapan_uracto             |    9    |     2      |    ❌     |  0   |  7   |   0   |    0.00     |   0.00   |   0.00    |    152.71 ms     |
|           vibleaker_android_samp           |    8    |     4      |    ❌     |  0   |  4   |   0   |    0.00     |   0.00   |   0.00    |    7667.59 ms    |
|             xbot_android_samp              |    0    |     3      |    ❌     |  0   |  0   |   3   |    0.00     |   0.00   |   0.00    |     0.00 ms      |
|              :--------------:              | :-----: | :--------: | :------: | :--: | :--: | :---: | :---------: | :------: | :-------: | :--------------: |
|                   TOTAL                    |   620   |    203     |   4/39   |  29  | 463  |  46   |    0.06     |   0.39   |   0.10    |   181544.65 ms   |