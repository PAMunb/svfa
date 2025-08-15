package br.unb.cic.android

import br.unb.cic.metrics.CustomMetrics
import org.scalatest.FunSuite

/**
 * EXPERIMENT 1:
 * All tests are configured with sources and sinks defined in benchmark suite.
 * We do it using a unique "trait" that contents these lists.
 */
class AndroidTaintBenchSuiteExperiment1Test extends FunSuite with CustomMetrics {

  test("in the APK backflash, we should detect 13 flow") {
    val expected = 13
    val nameAPK="backflash";

    val svfa = new AndroidTaintBenchTest(nameAPK)
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG().size
    this.compute(expected, actual, nameAPK)

    assert(actual == expected)
  }

  test("in the APK beita_com_beita_contact, we should detect 3 flow") {
    val expected = 3
    val nameAPK = "beita_com_beita_contact"

    val svfa = new AndroidTaintBenchTest(nameAPK)
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG().size
    this.compute(expected, actual, nameAPK)

    assert(actual == expected)
  }

  test("in the APK cajino_baidu, we should detect 12 flow") {
    val expected = 12
    val nameAPK = "cajino_baidu"

    val svfa = new AndroidTaintBenchTest(nameAPK)
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG().size
    this.compute(expected, actual, nameAPK)

    assert(actual == expected)
  }

  test("in the APK chat_hook, we should detect 12 flow") {
    val expected = 12
    val nameAPK = "chat_hook"

    val svfa = new AndroidTaintBenchTest(nameAPK)
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG().size
    this.compute(expected, actual, nameAPK)

    assert(actual == expected)
  }

  test("in the APK chulia, we should detect 4 flow") {
    val expected = 4
    val nameAPK = "chulia"

    val svfa = new AndroidTaintBenchTest(nameAPK)
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG().size
    this.compute(expected, actual, nameAPK)

    assert(actual == expected)
  }

  test("in the APK death_ring_materialflow, we should detect 1 flow") {
    val expected = 1
    val nameAPK = "death_ring_materialflow"

    val svfa = new AndroidTaintBenchTest(nameAPK)
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG().size
    this.compute(expected, actual, nameAPK)

    assert(actual == expected)
  }

  test("in the APK dsencrypt_samp, we should detect 1 flow") {
    val expected = 1
    val nameAPK = "dsencrypt_samp"

    val svfa = new AndroidTaintBenchTest(nameAPK)
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG().size
    this.compute(expected, actual, nameAPK)

    assert(actual == expected)
  }

  test("in the APK exprespam, we should detect 2 flow") {
    val expected = 2
    val nameAPK = "exprespam"

    val svfa = new AndroidTaintBenchTest(nameAPK)
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG().size
    this.compute(expected, actual, nameAPK)

    assert(actual == expected)
  }

  test("in the APK fakeappstore, we should detect 3 flow") {
    val expected = 3
    val nameAPK = "fakeappstore"

    val svfa = new AndroidTaintBenchTest(nameAPK)
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG().size
    this.compute(expected, actual, nameAPK)

    assert(actual == expected)
  }

  test("in the APK fakebank_android_samp, we should detect 5 flow") {
    val expected = 5
    val nameAPK = "fakebank_android_samp"

    val svfa = new AndroidTaintBenchTest(nameAPK)
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG().size
    this.compute(expected, actual, nameAPK)

    assert(actual == expected)
  }

  test("in the APK fakedaum, we should detect 2 flows") {
    val expected = 2
    val nameAPK = "fakedaum"

    val svfa = new AndroidTaintBenchTest(nameAPK)
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG().size
    this.compute(expected, actual, nameAPK)

    assert(actual == expected)
  }

  test("in the APK fakemart, we should detect 2 flows") {
    val expected = 2
    val nameAPK = "fakemart"

    val svfa = new AndroidTaintBenchTest(nameAPK)
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG().size
    this.compute(expected, actual, nameAPK)

    assert(actual == expected)
  }

  test("in the APK fakeplay, we should detect 2 flows") {
    val expected = 2
    val nameAPK = "fakeplay"

    val svfa = new AndroidTaintBenchTest(nameAPK)
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG().size
    this.compute(expected, actual, nameAPK)

    assert(actual == expected)
  }

  test("in the APK faketaobao, we should detect 4 flows") {
    val expected = 4
    val nameAPK = "faketaobao"

    val svfa = new AndroidTaintBenchTest(nameAPK)
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG().size
    this.compute(expected, actual, nameAPK)

    assert(actual == expected)
  }

  test("in the APK godwon_samp, we should detect 6 flows") {
    val expected = 6
    val nameAPK = "godwon_samp"

    val svfa = new AndroidTaintBenchTest(nameAPK)
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG().size
    this.compute(expected, actual, nameAPK)

    assert(actual == expected)
  }

  test("in the APK hummingbad_android_samp, we should detect 2 flows") {
    val expected = 2
    val nameAPK = "hummingbad_android_samp"

    val svfa = new AndroidTaintBenchTest(nameAPK)
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG().size
    this.compute(expected, actual, nameAPK)

    assert(actual == expected)
  }

  test("in the APK jollyserv, we should detect 1 flows") {
    val expected = 1
    val nameAPK = "jollyserv"

    val svfa = new AndroidTaintBenchTest(nameAPK)
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG().size
    this.compute(expected, actual, nameAPK)

    assert(actual == expected)
  }

  test("in the APK overlay_android_samp, we should detect 4 flows") {
    val expected = 4
    val nameAPK = "overlay_android_samp"

    val svfa = new AndroidTaintBenchTest(nameAPK)
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG().size
    this.compute(expected, actual, nameAPK)

    assert(actual == expected)
  }

  test("in the APK overlaylocker2_android_samp, we should detect 7 flows") {
    val expected = 7
    val nameAPK = "overlaylocker2_android_samp"

    val svfa = new AndroidTaintBenchTest(nameAPK)
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG().size
    this.compute(expected, actual, nameAPK)

    assert(actual == expected)
  }

  test("in the APK phospy, we should detect 2 flows") {
    val expected = 2
    val nameAPK = "phospy"

    val svfa = new AndroidTaintBenchTest(nameAPK)
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG().size
    this.compute(expected, actual, nameAPK)

    assert(actual == expected)
  }

  test("in the APK proxy_samp, we should detect 17 flows") {
    val expected = 17
    val nameAPK = "proxy_samp"

    val svfa = new AndroidTaintBenchTest(nameAPK)
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG().size
    this.compute(expected, actual, nameAPK)

    assert(actual == expected)
  }

  test("in the APK remote_control_smack, we should detect 17 flows") {
    val expected = 17
    val nameAPK = "remote_control_smack"

    val svfa = new AndroidTaintBenchTest(nameAPK)
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG().size
    this.compute(expected, actual, nameAPK)

    assert(actual == expected)
  }

  test("in the APK repane, we should detect 1 flow") {
    val expected = 1
    val nameAPK = "repane"

    val svfa = new AndroidTaintBenchTest(nameAPK)
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG().size
    this.compute(expected, actual, nameAPK)

    assert(actual == expected)
  }

  test("in the APK Roidsec, we should detect 6 flow") {
    val expected = 6
    val nameAPK = "roidsec"

    val svfa = new AndroidTaintBenchTest(nameAPK)
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG().size
    this.compute(expected, actual, nameAPK)

    assert(actual == expected)
  }

  test("in the APK samsapo, we should detect 4 flows") {
    val expected = 4
    val nameAPK = "samsapo"

    val svfa = new AndroidTaintBenchTest(nameAPK)
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG().size
    this.compute(expected, actual, nameAPK)

    assert(actual == expected)
  }

  test("in the APK save_me we should detect 25 flows") {
    val expected = 25
    val nameAPK = "save_me"

    val svfa = new AndroidTaintBenchTest(nameAPK)
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG().size
    this.compute(expected, actual, nameAPK)

    assert(actual == expected)
  }

  test("in the APK scipiex, we should detect 3 flows") {
    val expected = 3
    val nameAPK = "scipiex"

    val svfa = new AndroidTaintBenchTest(nameAPK)
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG().size
    this.compute(expected, actual, nameAPK)

    assert(actual == expected)
  }

  test("in the APK slocker_android_samp, we should detect 5 flows") {
    val expected = 5
    val nameAPK = "slocker_android_samp"

    val svfa = new AndroidTaintBenchTest(nameAPK)
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG().size
    this.compute(expected, actual, nameAPK)

    assert(actual == expected)
  }

  test("in the APK sms_google, we should detect 4 flows") {
    val expected = 4
    val nameAPK = "sms_google"

    val svfa = new AndroidTaintBenchTest(nameAPK)
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG().size
    this.compute(expected, actual, nameAPK)

    assert(actual == expected)
  }

  test("in the APK sms_send_locker_qqmagic, we should detect 6 flows") {
    val expected = 6
    val nameAPK = "sms_send_locker_qqmagic"

    val svfa = new AndroidTaintBenchTest(nameAPK)
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG().size
    this.compute(expected, actual, nameAPK)

    assert(actual == expected)
  }

  test("in the APK smssend_packageInstaller, we should detect 5 flows") {
    val expected = 5
    val nameAPK = "smssend_packageInstaller"

    val svfa = new AndroidTaintBenchTest(nameAPK)
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG().size
    this.compute(expected, actual, nameAPK)

    assert(actual == expected)
  }

  test("in the APK smssilience_fake_vertu, we should detect 2 flows") {
    val expected = 2
    val nameAPK = "smssilience_fake_vertu"

    val svfa = new AndroidTaintBenchTest(nameAPK)
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG().size
    this.compute(expected, actual, nameAPK)

    assert(actual == expected)
  }

  test("in the APK smsstealer_kysn_assassincreed_android_samp, we should detect 5 flows") {
    val expected = 5
    val nameAPK = "smsstealer_kysn_assassincreed_android_samp"

    val svfa = new AndroidTaintBenchTest(nameAPK)
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG().size
    this.compute(expected, actual, nameAPK)

    assert(actual == expected)
  }

  test("in the APK stels_flashplayer_android_update, we should detect 3 flows") {
    val expected = 3
    val nameAPK = "stels_flashplayer_android_update"

    val svfa = new AndroidTaintBenchTest(nameAPK)
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG().size
    this.compute(expected, actual, nameAPK)

    assert(actual == expected)
  }

  test("in the APK tetus, we should detect 2 flows") {
    val expected = 2
    val nameAPK = "tetus"

    val svfa = new AndroidTaintBenchTest(nameAPK)
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG().size
    this.compute(expected, actual, nameAPK)

    assert(actual == expected)
  }

  test("in the APK the_interview_movieshow, we should detect 1 flows") {
    val expected = 1
    val nameAPK = "the_interview_movieshow"

    val svfa = new AndroidTaintBenchTest(nameAPK)
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG().size
    this.compute(expected, actual, nameAPK)

    assert(actual == expected)
  }

  test("in the APK threatjapan_uracto, we should detect 2 flows") {
    val expected = 2
    val nameAPK = "threatjapan_uracto"

    val svfa = new AndroidTaintBenchTest(nameAPK)
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG().size
    this.compute(expected, actual, nameAPK)

    assert(actual == expected)
  }

  test("in the APK vibleaker_android_samp, we should detect 4 flows") {
    val expected = 4
    val nameAPK = "vibleaker_android_samp"

    val svfa = new AndroidTaintBenchTest(nameAPK)
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG().size
    this.compute(expected, actual, nameAPK)

    assert(actual == expected)
  }

  test("in the APK xbot_android_samp, we should detect 3 flows") {
    val expected = 3
    val nameAPK = "xbot_android_samp"

    val svfa = new AndroidTaintBenchTest(nameAPK)
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG().size
    this.compute(expected, actual, nameAPK)

    assert(actual == expected)
  }
}
