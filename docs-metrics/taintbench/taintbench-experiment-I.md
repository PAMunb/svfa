#### TAINTBENCH metrics - Experiment I

This case emulates **Experiment 2 - TB2** that states:
>All tools are configured with sources and sinks defined in benchmark suite.

### SUMMARY

|         APK                                | Found  | Expected | Status |   TP   |  FP   | FN | Precision |  Recall  | F-score  |
|:------------------------------------------:|:------:|:--------:|:------:|:------:|:-----:|:--:|:---------:|:--------:|:--------:| 
|                 backflash                  |   1    |    13    | FAILED |   1    |   0   | 0  |     1     |   0.08   |   0.14   |
|          beita_com_beita_contact           |   0    |    3     | FAILED |   0    |   0   | 0  |     0     |    0     |    0     |
|                cajino_baidu                |   8    |    12    | FAILED |   8    |   0   | 0  |     1     |   0.67   |   0.80   |
|                 chat_hook                  |   6    |    12    | FAILED |   6    |   0   | 0  |     1     |   0.50   |   0.67   |
|                   chulia                   |   0    |    4     | FAILED |   0    |   0   | 0  |     0     |    0     |    0     |
|          death_ring_materialflow           |   9    |    1     | FAILED |   1    |   8   | 0  |   0.11    |   1.00   |   0.20   |
|               dsencrypt_samp               |   1    |    1     | PASSED |   1    |   0   | 0  |     1     |    1     |    1     |
|                 exprespam                  |   0    |    2     | FAILED |   0    |   0   | 0  |     0     |    0     |    0     |
|                fakeappstore                |   0    |    3     | FAILED |   0    |   0   | 0  |     0     |    0     |    0     |
|           fakebank_android_samp            |   0    |    5     | FAILED |   0    |   0   | 0  |     0     |    0     |    0     |
|                  fakedaum                  |   3    |    2     | FAILED |   2    |   1   | 0  |   0.67    |   1.00   |   0.80   |
|                  fakemart                  |   0    |    2     | FAILED |   0    |   0   | 0  |     0     |    0     |    0     |
|                  fakeplay                  |   0    |    2     | FAILED |   0    |   0   | 0  |     0     |    0     |    0     |
|                 faketaobao                 |   0    |    4     | FAILED |   0    |   0   | 0  |     0     |    0     |    0     |
|                godwon_samp                 |   0    |    6     | FAILED |   0    |   0   | 0  |     0     |    0     |    0     |
|          hummingbad_android_samp           |   2    |    2     | PASSED |   2    |   0   | 0  |     1     |    1     |    1     |
|                 jollyserv                  |   0    |    1     | FAILED |   0    |   0   | 0  |     0     |    0     |    0     |
|            overlay_android_samp            |   0    |    4     | FAILED |   0    |   0   | 0  |     0     |    0     |    0     |
|        overlaylocker2_android_samp         |   0    |    7     | FAILED |   0    |   0   | 0  |     0     |    0     |    0     |
|                   phospy                   |   0    |    2     | FAILED |   0    |   0   | 0  |     0     |    0     |    0     |
|                 proxy_samp                 |   11   |    17    | FAILED |   11   |   0   | 0  |   1.00    |   0.65   |   0.79   |
|            remote_control_smack            |   0    |    17    | FAILED |   0    |   0   | 0  |     0     |    0     |    0     |
|                   repane                   |   0    |    1     | FAILED |   0    |   0   | 0  |     0     |    0     |    0     |
|                  Roidsec                   |   1    |    6     | FAILED |   1    |   0   | 0  |   1.00    |   0.17   |   0.29   |
|                  samsapo                   |   0    |    4     | FAILED |   0    |   0   | 0  |     0     |    0     |    0     |
|                  save_me                   |   6    |    25    | FAILED |   6    |   0   | 0  |   1.00    |   0.24   |   0.39   |
|                  scipiex                   |   0    |    3     | FAILED |   0    |   0   | 0  |     0     |    0     |    0     |
|            slocker_android_samp            |   0    |    5     | FAILED |   0    |   0   | 0  |     0     |    0     |    0     |
|                 sms_google                 |   0    |    4     | FAILED |   0    |   0   | 0  |     0     |    0     |    0     |
|          sms_send_locker_qqmagic           |   2    |    6     | FAILED |   2    |   0   | 0  |   1.00    |   0.33   |   0.50   |
|          smssend_packageInstaller          |   0    |    5     | FAILED |   0    |   0   | 0  |     0     |    0     |    0     |
|           smssilience_fake_vertu           |   0    |    2     | FAILED |   0    |   0   | 0  |     0     |    0     |    0     |
| smsstealer_kysn_assassincreed_android_samp |   0    |    5     | FAILED |   0    |   0   | 0  |     0     |    0     |    0     |
|      stels_flashplayer_android_update      |   0    |    3     | FAILED |   0    |   0   | 0  |     0     |    0     |    0     |
|                   tetus                    |   0    |    2     | FAILED |   0    |   0   | 0  |     0     |    0     |    0     |
|          the_interview_movieshow           |   0    |    1     | FAILED |   0    |   0   | 0  |     0     |    0     |    0     |
|             threatjapan_uracto             |   0    |    2     | FAILED |   0    |   0   | 0  |     0     |    0     |    0     |
|           vibleaker_android_samp           |   0    |    4     | FAILED |   0    |   0   | 0  |     0     |    0     |    0     |
|             xbot_android_samp              |   0    |    3     | FAILED |   0    |   0   | 0  |     0     |    0     |    0     |
|                 ~~TOTAL~~                  | ~~50~~ | ~~203~~  |   -    | ~~41~~ | ~~9~~ | 0  | ~~0.82~~  | ~~0.20~~ | ~~0.32~~ |
|                   TOTAL*                   |   50   |   186    |   -    |   41   |   9   | 0  |   0.82    |   0.22   |   0.35   |
