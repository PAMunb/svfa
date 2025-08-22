#### TAINTBENCH metrics - Experiment II

This case emulates **Experiment 3 - TB3** that configures:
>For each benchmark app, a list of sources and sinks defined in this app is used to
configure all tools. Each tool analyzes each benchmark app with the associated list
of sources and sinks

### SUMMARY

> failed: 38, passed: 1 of 39 tests - (2.56%)

|                     Test                     |  Found  |  Expected  |  Status  |  TP  |  FP   | FN   |  Precision  |  Recall  |  F-score  |
|:--------------------------------------------:|:-------:|:----------:|:--------:|:----:|:-----:|:-----|:-----------:|:--------:|:---------:|
|                  backflash                   |   17    |     13     |    ❌     |  0   |   4   | 0    |    0.00     |   0.00   |   0.00    |
|           beita_com_beita_contact            |   14    |     3      |    ❌     |  0   |  11   | 0    |    0.00     |   0.00   |   0.00    |
|                 cajino_baidu                 |   92    |     12     |    ❌     |  0   |  80   | 0    |    0.00     |   0.00   |   0.00    |
|                  chat_hook                   |   14    |     12     |    ❌     |  0   |   2   | 0    |    0.00     |   0.00   |   0.00    |
|                    chulia                    |    0    |     4      |    ❌     |  0   |   0   | 4    |    0.00     |   0.00   |   0.00    |
|           death_ring_materialflow            |   41    |     1      |    ❌     |  0   |  40   | 0    |    0.00     |   0.00   |   0.00    |
|                dsencrypt_samp                |    2    |     1      |    ❌     |  0   |   1   | 0    |    0.00     |   0.00   |   0.00    |
|                  exprespam                   |    0    |     2      |    ❌     |  0   |   0   | 2    |    0.00     |   0.00   |   0.00    |
|                 fakeappstore                 |    0    |     3      |    ❌     |  0   |   0   | 3    |    0.00     |   0.00   |   0.00    |
|            fakebank_android_samp             |    6    |     5      |    ❌     |  0   |   1   | 0    |    0.00     |   0.00   |   0.00    |
|                   fakedaum                   |   10    |     2      |    ❌     |  0   |   8   | 0    |    0.00     |   0.00   |   0.00    |
|                   fakemart                   |    0    |     2      |    ❌     |  0   |   0   | 2    |    0.00     |   0.00   |   0.00    |
|                   fakeplay                   |   15    |     2      |    ❌     |  0   |  13   | 0    |    0.00     |   0.00   |   0.00    |
|                  faketaobao                  |    0    |     4      |    ❌     |  0   |   0   | 4    |    0.00     |   0.00   |   0.00    |
|                 godwon_samp                  |    0    |     6      |    ❌     |  0   |   0   | 6    |    0.00     |   0.00   |   0.00    |
|           hummingbad_android_samp            |   23    |     2      |    ❌     |  0   |  21   | 0    |    0.00     |   0.00   |   0.00    |
|                  jollyserv                   |    6    |     1      |    ❌     |  0   |   5   | 0    |    0.00     |   0.00   |   0.00    |
|             overlay_android_samp             |    8    |     4      |    ❌     |  0   |   4   | 0    |    0.00     |   0.00   |   0.00    |
|         overlaylocker2_android_samp          |   35    |     7      |    ❌     |  0   |  28   | 0    |    0.00     |   0.00   |   0.00    |
|                    phospy                    |    1    |     2      |    ❌     |  0   |   0   | 1    |    0.00     |   0.00   |   0.00    |
|                  proxy_samp                  |   20    |     17     |    ❌     |  0   |   3   | 0    |    0.00     |   0.00   |   0.00    |
|             remote_control_smack             |    6    |     17     |    ❌     |  0   |   0   | 11   |    0.00     |   0.00   |   0.00    |
|                    repane                    |    0    |     1      |    ❌     |  0   |   0   | 1    |    0.00     |   0.00   |   0.00    |
|                   roidsec                    |    1    |     6      |    ❌     |  0   |   0   | 5    |    0.00     |   0.00   |   0.00    |
|                   samsapo                    |    0    |     4      |    ❌     |  0   |   0   | 4    |    0.00     |   0.00   |   0.00    |
|                   save_me                    |   14    |     25     |    ❌     |  0   |   0   | 11   |    0.00     |   0.00   |   0.00    |
|                   scipiex                    |    0    |     3      |    ❌     |  0   |   0   | 3    |    0.00     |   0.00   |   0.00    |
|             slocker_android_samp             |    0    |     5      |    ❌     |  0   |   0   | 5    |    0.00     |   0.00   |   0.00    |
|                  sms_google                  |    9    |     4      |    ❌     |  0   |   5   | 0    |    0.00     |   0.00   |   0.00    |
|           sms_send_locker_qqmagic            |    9    |     6      |    ❌     |  0   |   3   | 0    |    0.00     |   0.00   |   0.00    |
|           smssend_packageInstaller           |   46    |     5      |    ❌     |  0   |  41   | 0    |    0.00     |   0.00   |   0.00    |
|            smssilience_fake_vertu            |    2    |     2      |    ✅     |  2   |   0   | 0    |    1.00     |   1.00   |   1.00    |
|  smsstealer_kysn_assassincreed_android_samp  |    1    |     5      |    ❌     |  0   |   0   | 4    |    0.00     |   0.00   |   0.00    |
|       stels_flashplayer_android_update       |   18    |     3      |    ❌     |  0   |  15   | 0    |    0.00     |   0.00   |   0.00    |
|                    tetus                     |   23    |     2      |    ❌     |  0   |  21   | 0    |    0.00     |   0.00   |   0.00    |
|           the_interview_movieshow            |    0    |     1      |    ❌     |  0   |   0   | 1    |    0.00     |   0.00   |   0.00    |
|              threatjapan_uracto              |    0    |     2      |    ❌     |  0   |   0   | 2    |    0.00     |   0.00   |   0.00    |
|            vibleaker_android_samp            |    5    |     4      |    ❌     |  0   |   1   | 0    |    0.00     |   0.00   |   0.00    |
|              xbot_android_samp               |    8    |     3      |    ❌     |  0   |   5   | 0    |    0.00     |   0.00   |   0.00    |
| :------------------------------------------: | :-----: | :--------: | :------: | :--: | :---: | :--- | :---------: | :------: | :-------: |
|                    TOTAL                     |   446   |    203     |   1/39   |  2   |  312  | 69   |    0.01     |   0.03   |   0.01    |