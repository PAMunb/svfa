#### TAINTBENCH metrics - Experiment I

This case emulates **Experiment 2 - TB2** that states:
>All tools are configured with sources and sinks defined in benchmark suite.

### SUMMARY

|                    APK                     | Found  | Expected | Status |   TP   |  FP   | FN | Precision |  Recall  | F-score  |
|:------------------------------------------:|:------:|:--------:|:------:|:------:|:-----:|:--:|:---------:|:--------:|:--------:| 
|                 backflash                  |   1    |    13    |   ❌    |   0    |   0   | 12 |   0.00    |   0.00   |   0.00   |
|          beita_com_beita_contact           |   0    |    3     | FAILED |   0    |   0   | 0  |     0     |    0     |    0     |X
|                cajino_baidu                |   8    |    12    | FAILED |   8    |   0   | 0  |     1     |   0.67   |   0.80   |X
|                 chat_hook                  |   6    |    12    | FAILED |   6    |   0   | 0  |     1     |   0.50   |   0.67   |X
|                   chulia                   |   0    |    4     | FAILED |   0    |   0   | 0  |     0     |    0     |    0     |X
|          death_ring_materialflow           |   9    |    1     | FAILED |   1    |   8   | 0  |   0.11    |   1.00   |   0.20   |X
|               dsencrypt_samp               |   1    |    1     | PASSED |   1    |   0   | 0  |     1     |    1     |    1     |X
|                 exprespam                  |   0    |    2     | FAILED |   0    |   0   | 0  |     0     |    0     |    0     |X
|                fakeappstore                |   0    |    3     | FAILED |   0    |   0   | 0  |     0     |    0     |    0     |X
|           fakebank_android_samp            |   0    |    5     | FAILED |   0    |   0   | 0  |     0     |    0     |    0     |X
|                  fakedaum                  |   3    |    2     | FAILED |   2    |   1   | 0  |   0.67    |   1.00   |   0.80   |X
|                  fakemart                  |   0    |    2     | FAILED |   0    |   0   | 0  |     0     |    0     |    0     |X
|                  fakeplay                  |   0    |    2     | FAILED |   0    |   0   | 0  |     0     |    0     |    0     |X
|                 faketaobao                 |   0    |    4     | FAILED |   0    |   0   | 0  |     0     |    0     |    0     |X
|                godwon_samp                 |   0    |    6     | FAILED |   0    |   0   | 0  |     0     |    0     |    0     |X
|          hummingbad_android_samp           |   2    |    2     | PASSED |   2    |   0   | 0  |     1     |    1     |    1     |X
|                 jollyserv                  |   0    |    1     | FAILED |   0    |   0   | 0  |     0     |    0     |    0     |X
|            overlay_android_samp            |   0    |    4     | FAILED |   0    |   0   | 0  |     0     |    0     |    0     |X
|        overlaylocker2_android_samp         |   0    |    7     | FAILED |   0    |   0   | 0  |     0     |    0     |    0     |X
|                   phospy                   |   0    |    2     | FAILED |   0    |   0   | 0  |     0     |    0     |    0     |X
|                 proxy_samp                 |   11   |    17    | FAILED |   11   |   0   | 0  |   1.00    |   0.65   |   0.79   |X
|            remote_control_smack            |   0    |    17    | FAILED |   0    |   0   | 0  |     0     |    0     |    0     |X
|                   repane                   |   0    |    1     | FAILED |   0    |   0   | 0  |     0     |    0     |    0     |X
|                  Roidsec                   |   1    |    6     | FAILED |   1    |   0   | 0  |   1.00    |   0.17   |   0.29   |X
|                  samsapo                   |   0    |    4     | FAILED |   0    |   0   | 0  |     0     |    0     |    0     |X
|                  save_me                   |   6    |    25    | FAILED |   6    |   0   | 0  |   1.00    |   0.24   |   0.39   |X
|                  scipiex                   |   0    |    3     | FAILED |   0    |   0   | 0  |     0     |    0     |    0     |X
|            slocker_android_samp            |   0    |    5     | FAILED |   0    |   0   | 0  |     0     |    0     |    0     |X
|                 sms_google                 |   0    |    4     | FAILED |   0    |   0   | 0  |     0     |    0     |    0     |X
|          sms_send_locker_qqmagic           |   2    |    6     | FAILED |   2    |   0   | 0  |   1.00    |   0.33   |   0.50   |X
|          smssend_packageInstaller          |   0    |    5     | FAILED |   0    |   0   | 0  |     0     |    0     |    0     |X
|           smssilience_fake_vertu           |   0    |    2     | FAILED |   0    |   0   | 0  |     0     |    0     |    0     |X
| smsstealer_kysn_assassincreed_android_samp |   0    |    5     | FAILED |   0    |   0   | 0  |     0     |    0     |    0     |X
|      stels_flashplayer_android_update      |   0    |    3     | FAILED |   0    |   0   | 0  |     0     |    0     |    0     |XX
|                   tetus                    |   0    |    2     | FAILED |   0    |   0   | 0  |     0     |    0     |    0     |X
|          the_interview_movieshow           |   0    |    1     | FAILED |   0    |   0   | 0  |     0     |    0     |    0     |X
|             threatjapan_uracto             |   0    |    2     | FAILED |   0    |   0   | 0  |     0     |    0     |    0     |X
|           vibleaker_android_samp           |   0    |    4     | FAILED |   0    |   0   | 0  |     0     |    0     |    0     |X
|             xbot_android_samp              |   0    |    3     | FAILED |   0    |   0   | 0  |     0     |    0     |    0     |X
|                 ~~TOTAL~~                  | ~~50~~ | ~~203~~  |   -    | ~~41~~ | ~~9~~ | 0  | ~~0.82~~  | ~~0.20~~ | ~~0.32~~ |
|                   TOTAL*                   |   50   |   186    |   -    |   41   |   9   | 0  |   0.82    |   0.22   |   0.35   |X
