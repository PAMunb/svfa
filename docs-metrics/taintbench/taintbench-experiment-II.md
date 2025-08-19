#### TAINTBENCH metrics - Experiment II

This case emulates **Experiment 3 - TB3** that configures:
>For each benchmark app, a list of sources and sinks defined in this app is used to
configure all tools. Each tool analyzes each benchmark app with the associated list
of sources and sinks

### SUMMARY

|                    APK                     | Found | Expected | Status | TP  | FP  | FN | Precision | Recall | F-score |
|:------------------------------------------:|:-----:|:--------:|:------:|:---:|:---:|:--:|:---------:|:------:|:-------:| 
|                 backflash                  |  17   |    13    | FAILED | 13  |  4  | 0  |   0.76    |  1.00  |  0.87   |
|          beita_com_beita_contact           |  14   |    3     | FAILED |  3  | 11  | 0  |   0.21    |  1.00  |  0.35   | 
|                cajino_baidu                |  92   |    12    | FAILED | 12  | 80  | 0  |   0.13    |  1.00  |  0.23   | 
|                 chat_hook                  |  14   |    12    | FAILED | 12  |  2  | 0  |   0.86    |  1.00  |  0.92   | 
|                   chulia                   |   0   |    4     | FAILED |  0  |  0  | 0  |     0     |   0    |    0    | 
|          death_ring_materialflow           |  44   |    1     | FAILED |  1  | 43  | 0  |   0.02    |  1.00  |  0.04   | 
|               dsencrypt_samp               |   2   |    1     | FAILED |  1  |  1  | 0  |   0.50    |  1.00  |  0.67   | 
|                 exprespam                  |   0   |    2     | FAILED |  0  |  0  | 0  |     0     |   0    |    0    | 
|                fakeappstore                |   0   |    3     | FAILED |  0  |  0  | 0  |     0     |   0    |    0    | 
|           fakebank_android_samp            |   6   |    5     | FAILED |  5  |  1  | 0  |   0.83    |  1.00  |  0.91   | 
|                  fakedaum                  |  10   |    2     | FAILED |  2  |  8  | 0  |   0.20    |  1.00  |  0.33   | 
|                  fakemart                  |   0   |    2     | FAILED |  0  |  0  | 0  |     0     |   0    |    0    | 
|                  fakeplay                  |  15   |    2     | FAILED |  2  | 13  | 0  |   0.13    |  1.00  |  0.24   | 
|                 faketaobao                 |   0   |    4     | FAILED |  0  |  0  | 0  |     0     |   0    |    0    | 
|                godwon_samp                 |   0   |    6     | FAILED |  0  |  0  | 0  |     0     |   0    |    0    | 
|          hummingbad_android_samp           |  23   |    2     | FAILED |  2  | 21  | 0  |   0.09    |  1.00  |  0.16   | 
|                 jollyserv                  |   6   |    1     | FAILED |  1  |  5  | 0  |   0.17    |  1.00  |  0.29   | 
|            overlay_android_samp            |   8   |    4     | FAILED |  4  |  4  | 0  |   0.50    |  1.00  |  0.67   | 
|        overlaylocker2_android_samp         |  34   |    7     | FAILED |  7  | 27  | 0  |   0.21    |  1.00  |  0.34   | 
|                   phospy                   |   1   |    2     | FAILED |  1  |  0  | 0  |   1.00    |  0.50  |  0.67   | 
|                 proxy_samp                 |  21   |    17    | FAILED | 17  |  4  | 0  |   0.81    |  1.00  |  0.89   | 
|            remote_control_smack            |   5   |    17    | FAILED |  5  |  0  | 0  |   1.00    |  0.29  |  0.45   | 
|                   repane                   |   0   |    1     | FAILED |  0  |  0  | 0  |     0     |   0    |    0    | 
|                  Roidsec                   |   1   |    6     | FAILED |  1  |  0  | 0  |   1.00    |  0.17  |  0.29   | 
|                  samsapo                   |   0   |    4     | FAILED |  0  |  0  | 0  |     0     |   0    |    0    | 
|                  save_me                   |  14   |    25    | FAILED | 14  |  0  | 0  |   1.00    |  0.56  |  0.72   | 
|                  scipiex                   |   0   |    3     | FAILED |  0  |  0  | 0  |     0     |   0    |    0    | 
|            slocker_android_samp            |   0   |    5     | FAILED |  0  |  0  | 0  |     0     |   0    |    0    | 
|                 sms_google                 |   9   |    4     | FAILED |  4  |  5  | 0  |   0.44    |  1.00  |  0.62   | 
|          sms_send_locker_qqmagic           |   9   |    6     | FAILED |  6  |  3  | 0  |   0.67    |  1.00  |  0.80   | 
|          smssend_packageInstaller          |  46   |    5     | FAILED |  5  | 41  | 0  |   0.11    |  1.00  |  0.20   | 
|           smssilience_fake_vertu           |   2   |    2     | PASSED |  2  |  0  | 0  |   1.00    |  1.00  |  1.00   | 
| smsstealer_kysn_assassincreed_android_samp |   1   |    5     | FAILED |  1  |  0  | 0  |   1.00    |  0.20  |  0.33   | 
|      stels_flashplayer_android_update      |  18   |    3     | FAILED |  3  | 15  | 0  |   0.17    |  1.00  |  0.29   | 
|                   tetus                    |  17   |    2     | FAILED |  2  | 15  | 0  |   0.12    |  1.00  |  0.21   | 
|          the_interview_movieshow           |   0   |    1     | FAILED |  0  |  0  | 0  |     0     |   0    |    0    |  
|             threatjapan_uracto             |   0   |    2     | FAILED |  0  |  0  | 0  |     0     |   0    |    0    |
|           vibleaker_android_samp           |   5   |    4     | FAILED |  4  |  1  | 0  |   0.80    |  1.00  |  0.89   | 
|             xbot_android_samp              |   8   |    3     | FAILED |  3  |  5  | 0  |   0.38    |  1.00  |  0.55   | 
|                   TOTAL                    |  442  |   203    |   -    | 133 | 309 | 0  |   0.30    |  0.66  |  0.41   |
|                   TOTAL*                   |  442  |   186    |   -    | 133 | 309 | 0  |   0.30    |  0.72  |  0.42   |