#### TAINTBENCH metrics - Experiment II

This case emulates **Experiment 3 - TB3** that configures:
>For each benchmark app, a list of sources and sinks defined in this app is used to
configure all tools. Each tool analyzes each benchmark app with the associated list
of sources and sinks

### SUMMARY

|                    APK                     | Expected | Actual | Status | TP  | FP  | Precision | Recall | F-score |
|:------------------------------------------:|:--------:|:------:|:------:|:---:|:---:|:---------:|:------:|:-------:| 
|                 backflash                  |    13    |   17   | FAILED | 13  |  4  |   0.76    |  1.00  |  0.87   |
|          beita_com_beita_contact           |    3     |   14   | FAILED |  3  | 11  |   0.21    |  1.00  |  0.35   | 
|                cajino_baidu                |    12    |   92   | FAILED | 12  | 80  |   0.13    |  1.00  |  0.23   | 
|                 chat_hook                  |    12    |   14   | FAILED | 12  |  2  |   0.86    |  1.00  |  0.92   | 
|                   chulia                   |    4     |   0    | FAILED |  0  |  0  |     0     |   0    |    0    | 
|          death_ring_materialflow           |    1     |   44   | FAILED |  1  | 43  |   0.02    |  1.00  |  0.04   | 
|               dsencrypt_samp               |    1     |   2    | FAILED |  1  |  1  |   0.50    |  1.00  |  0.67   | 
|                 exprespam                  |    2     |   0    | FAILED |  0  |  0  |     0     |   0    |    0    | 
|                fakeappstore                |    3     |   0    | FAILED |  0  |  0  |     0     |   0    |    0    | 
|           fakebank_android_samp            |    5     |   6    | FAILED |  5  |  1  |   0.83    |  1.00  |  0.91   | 
|                  fakedaum                  |    2     |   10   | FAILED |  2  |  8  |   0.20    |  1.00  |  0.33   | 
|                  fakemart                  |    2     |   0    | FAILED |  0  |  0  |     0     |   0    |    0    | 
|                  fakeplay                  |    2     |   15   | FAILED |  2  | 13  |   0.13    |  1.00  |  0.24   | 
|                 faketaobao                 |    4     |   0    | FAILED |  0  |  0  |     0     |   0    |    0    | 
|                godwon_samp                 |    6     |   0    | FAILED |  0  |  0  |     0     |   0    |    0    | 
|          hummingbad_android_samp           |    2     |   23   | FAILED |  2  | 21  |   0.09    |  1.00  |  0.16   | 
|                 jollyserv                  |    1     |   6    | FAILED |  1  |  5  |   0.17    |  1.00  |  0.29   | 
|            overlay_android_samp            |    4     |   8    | FAILED |  4  |  4  |   0.50    |  1.00  |  0.67   | 
|        overlaylocker2_android_samp         |    7     |   34   | FAILED |  7  | 27  |   0.21    |  1.00  |  0.34   | 
|                   phospy                   |    2     |   1    | FAILED |  1  |  0  |   1.00    |  0.50  |  0.67   | 
|                 proxy_samp                 |    17    |   21   | FAILED | 17  |  4  |   0.81    |  1.00  |  0.89   | 
|            remote_control_smack            |    17    |   5    | FAILED |  5  |  0  |   1.00    |  0.29  |  0.45   | 
|                   repane                   |    1     |   0    | FAILED |  0  |  0  |     0     |   0    |    0    | 
|                  Roidsec                   |    6     |   1    | FAILED |  1  |  0  |   1.00    |  0.17  |  0.29   | 
|                  samsapo                   |    4     |   0    | FAILED |  0  |  0  |     0     |   0    |    0    | 
|                  save_me                   |    25    |   14   | FAILED | 14  |  0  |   1.00    |  0.56  |  0.72   | 
|                  scipiex                   |    3     |   0    | FAILED |  0  |  0  |     0     |   0    |    0    | 
|            slocker_android_samp            |    5     |   0    | FAILED |  0  |  0  |     0     |   0    |    0    | 
|                 sms_google                 |    4     |   9    | FAILED |  4  |  5  |   0.44    |  1.00  |  0.62   | 
|          sms_send_locker_qqmagic           |    6     |   9    | FAILED |  6  |  3  |   0.67    |  1.00  |  0.80   | 
|          smssend_packageInstaller          |    5     |   46   | FAILED |  5  | 41  |   0.11    |  1.00  |  0.20   | 
|           smssilience_fake_vertu           |    2     |   2    | PASSED |  2  |  0  |   1.00    |  1.00  |  1.00   | 
| smsstealer_kysn_assassincreed_android_samp |    5     |   1    | FAILED |  1  |  0  |   1.00    |  0.20  |  0.33   | 
|      stels_flashplayer_android_update      |    3     |   18   | FAILED |  3  | 15  |   0.17    |  1.00  |  0.29   | 
|                   tetus                    |    2     |   17   | FAILED |  2  | 15  |   0.12    |  1.00  |  0.21   | 
|          the_interview_movieshow           |    1     |   0    | FAILED |  0  |  0  |     0     |   0    |    0    |  
|             threatjapan_uracto             |    2     |   0    | FAILED |  0  |  0  |     0     |   0    |    0    |
|           vibleaker_android_samp           |    4     |   5    | FAILED |  4  |  1  |   0.80    |  1.00  |  0.89   | 
|             xbot_android_samp              |    3     |   8    | FAILED |  3  |  5  |   0.38    |  1.00  |  0.55   | 
|                   TOTAL                    |   203    |  442   |   -    | 133 | 309 |   0.30    |  0.66  |  0.41   |
|                   TOTAL*                   |   186    |  442   |   -    | 133 | 309 |   0.30    |  0.72  |  0.42   |