package br.unb.cic.android

import br.unb.cic.android.specs.{BackFlashSpec, BeitaComBeitaContactSpec, CajinoBaiduSpec, ChatHookSpec, ChuliaSpec, DeathRingMaterialflowSpec, DsencryptSampSpec, ExprespamSpec, FakeappstoreSpec, FakebankAndroidSampSpec, FakedaumSpec, FakemartSpec, FakeplaySpec, FaketaobaoSpec, GodwonSampSpec, HummingbadAndroidSampSpec, JollyservSpec, OverlayAndroidSampSpec, Overlaylocker2AndroidSampSpec, PhospySpec, ProxySampSpec, RemoteControlSmackSpec, RepaneSpec, RoidSecSpec, SamsapoSpec, SaveMeSpec, ScipiexSpec, SlockerAndroidSampSpec, SmsGoogleSpec, SmsSendLockerQqmagicSpec, SmssendPackageInstallerSpec, SmssilienceFakeVertuSpec, SmsstealerKysnAssassincreedAndroidSampSpec, StelsFlashplayerAndroidUpdateSpec, TetusSpec, TheInterviewMovieShowSpec, ThreatjapanUractoSpec, VibleakerAndroidSampSpec, XbotAndroidSampSpec}
import br.unb.cic.metrics.Metrics
import org.scalatest.FunSuite

/**
 * EXPERIMENT 2:
 * Each test is run with its associated list of sources and sinks
 * We do it injecting the "trait" that contents these list at the instance of the object
 */
class AndroidTaintBenchSuiteExperiment2Test extends FunSuite with Metrics {

  test("in the APK backflash, we should detect 13 flow") {
    val expected = 13;

    val svfa = new AndroidTaintBenchTest("backflash") with BackFlashSpec
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG(true).size
    this.computeMetricsByResults(expected, actual)
    assert(actual == expected)
  }

  test("in the APK beita_com_beita_contact, we should detect 3 flow") {
    val expected = 3

    val svfa = new AndroidTaintBenchTest("beita_com_beita_contact") with BeitaComBeitaContactSpec
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG(true).size
    this.computeMetricsByResults(expected, actual)
    assert(actual == expected)
  }

  test("in the APK cajino_baidu, we should detect 12 flow") {
    val expected = 12

    val svfa = new AndroidTaintBenchTest("cajino_baidu") with CajinoBaiduSpec
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG(true).size
    this.computeMetricsByResults(expected, actual)
    assert(actual == expected)
  }

  test("in the APK chat_hook, we should detect 12 flow") {
    val expected = 12

    val svfa = new AndroidTaintBenchTest("chat_hook") with ChatHookSpec
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG(true).size
    this.computeMetricsByResults(expected, actual)
    assert(actual == expected)
  }

  test("in the APK chulia, we should detect 4 flow") {
    val expected = 4

    val svfa = new AndroidTaintBenchTest("chulia") with ChuliaSpec
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG(true).size
    this.computeMetricsByResults(expected, actual)
    assert(actual == expected)
  }

  test("in the APK death_ring_materialflow, we should detect 1 flow") {
    val expected = 1

    val svfa = new AndroidTaintBenchTest("death_ring_materialflow") with DeathRingMaterialflowSpec
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG(true).size
    this.computeMetricsByResults(expected, actual)
    assert(actual == expected)
  }

  test("in the APK dsencrypt_samp, we should detect 1 flow") {
    val expected = 1

    val svfa = new AndroidTaintBenchTest("dsencrypt_samp") with DsencryptSampSpec
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG(true).size
    this.computeMetricsByResults(expected, actual)
    assert(actual == expected)
  }

  test("in the APK exprespam, we should detect 2 flow") {
    val expected = 2

    val svfa = new AndroidTaintBenchTest("exprespam") with ExprespamSpec
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG(true).size
    this.computeMetricsByResults(expected, actual)
    assert(actual == expected)
  }

  test("in the APK fakeappstore, we should detect 3 flow") {
    val expected = 3

    val svfa = new AndroidTaintBenchTest("fakeappstore") with FakeappstoreSpec
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG(true).size
    this.computeMetricsByResults(expected, actual)
    assert(actual == expected)
  }

  test("in the APK fakebank_android_samp, we should detect 5 flow") {
    val expected = 5

    val svfa = new AndroidTaintBenchTest("fakebank_android_samp") with FakebankAndroidSampSpec
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG(true).size
    this.computeMetricsByResults(expected, actual)
    assert(actual == expected)
  }

  test("in the APK fakedaum, we should detect 2 flows") {
    val expected = 2

    val svfa = new AndroidTaintBenchTest("fakedaum") with FakedaumSpec
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG(true).size
    this.computeMetricsByResults(expected, actual)
    assert(actual == expected)
  }

  test("in the APK fakemart, we should detect 2 flows") {
    val expected = 2

    val svfa = new AndroidTaintBenchTest("fakemart") with FakemartSpec
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG(true).size
    this.computeMetricsByResults(expected, actual)
    assert(actual == expected)
  }

  test("in the APK fakeplay, we should detect 2 flows") {
    val expected = 2

    val svfa = new AndroidTaintBenchTest("fakeplay") with FakeplaySpec
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG(true).size
    this.computeMetricsByResults(expected, actual)
    assert(actual == expected)
  }

  test("in the APK faketaobao, we should detect 4 flows") {
    val expected = 4

    val svfa = new AndroidTaintBenchTest("faketaobao") with FaketaobaoSpec
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG(true).size
    this.computeMetricsByResults(expected, actual)
    assert(actual == expected)
  }

  test("in the APK godwon_samp, we should detect 6 flows") {
    val expected = 6

    val svfa = new AndroidTaintBenchTest("godwon_samp") with GodwonSampSpec
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG(true).size
    this.computeMetricsByResults(expected, actual)
    assert(actual == expected)
  }

  test("in the APK hummingbad_android_samp, we should detect 2 flows") {
    val expected = 2

    val svfa = new AndroidTaintBenchTest("hummingbad_android_samp") with HummingbadAndroidSampSpec
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG(true).size
    this.computeMetricsByResults(expected, actual)
    assert(actual == expected)
  }

  test("in the APK jollyserv, we should detect 1 flows") {
    val expected = 1

    val svfa = new AndroidTaintBenchTest("jollyserv") with JollyservSpec
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG(true).size
    this.computeMetricsByResults(expected, actual)
    assert(actual == expected)
  }

  test("in the APK overlay_android_samp, we should detect 4 flows") {
    val expected = 4

    val svfa = new AndroidTaintBenchTest("overlay_android_samp") with OverlayAndroidSampSpec
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG(true).size
    this.computeMetricsByResults(expected, actual)
    assert(actual == expected)
  }

  test("in the APK overlaylocker2_android_samp, we should detect 7 flows") {
    val expected = 7

    val svfa = new AndroidTaintBenchTest("overlaylocker2_android_samp") with Overlaylocker2AndroidSampSpec
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG(true).size
    this.computeMetricsByResults(expected, actual)
    assert(actual == expected)
  }

  test("in the APK phospy, we should detect 2 flows") {
    val expected = 2

    val svfa = new AndroidTaintBenchTest("phospy") with PhospySpec
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG(true).size
    this.computeMetricsByResults(expected, actual)
    assert(actual == expected)
  }

  test("in the APK proxy_samp, we should detect 17 flows") {
    val expected = 17

    val svfa = new AndroidTaintBenchTest("proxy_samp") with ProxySampSpec
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG(true).size
    this.computeMetricsByResults(expected, actual)
    assert(actual == expected)
  }

  test("in the APK remote_control_smack, we should detect 17 flows") {
    val expected = 17

    val svfa = new AndroidTaintBenchTest("remote_control_smack") with RemoteControlSmackSpec
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG(true).size
    this.computeMetricsByResults(expected, actual)
    assert(actual == expected)
  }

  test("in the APK repane, we should detect 1 flow") {
    val expected = 1

    val svfa = new AndroidTaintBenchTest("repane") with RepaneSpec
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG(true).size
    this.computeMetricsByResults(expected, actual)
    assert(actual == expected)
  }

  test("in the APK Roidsec, we should detect 6 flow") {
    val expected = 6

    val svfa = new AndroidTaintBenchTest("roidsec") with RoidSecSpec
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG(true).size
    this.computeMetricsByResults(expected, actual)
    assert(actual == expected)
  }

  test("in the APK samsapo, we should detect 4 flows") {
    val expected = 4

    val svfa = new AndroidTaintBenchTest("samsapo") with SamsapoSpec
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG(true).size
    this.computeMetricsByResults(expected, actual)
    assert(actual == expected)
  }

  test("in the APK save_me we should detect 25 flows") {
    val expected = 25

    val svfa = new AndroidTaintBenchTest("save_me") with SaveMeSpec
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG(true).size
    this.computeMetricsByResults(expected, actual)
    assert(actual == expected)
  }

  test("in the APK scipiex, we should detect 3 flows") {
    val expected = 3

    val svfa = new AndroidTaintBenchTest("scipiex") with ScipiexSpec
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG(true).size
    this.computeMetricsByResults(expected, actual)
    assert(actual == expected)
  }

  test("in the APK slocker_android_samp, we should detect 5 flows") {
    val expected = 5

    val svfa = new AndroidTaintBenchTest("slocker_android_samp") with SlockerAndroidSampSpec
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG(true).size
    this.computeMetricsByResults(expected, actual)
    assert(actual == expected)
  }

  test("in the APK sms_google, we should detect 4 flows") {
    val expected = 4

    val svfa = new AndroidTaintBenchTest("sms_google") with SmsGoogleSpec
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG(true).size
    this.computeMetricsByResults(expected, actual)
    assert(actual == expected)
  }

  test("in the APK sms_send_locker_qqmagic, we should detect 6 flows") {
    val expected = 6

    val svfa = new AndroidTaintBenchTest("sms_send_locker_qqmagic") with SmsSendLockerQqmagicSpec
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG(true).size
    this.computeMetricsByResults(expected, actual)
    assert(actual == expected)
  }

  test("in the APK smssend_packageInstaller, we should detect 5 flows") {
    val expected = 5

    val svfa = new AndroidTaintBenchTest("smssend_packageInstaller") with SmssendPackageInstallerSpec
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG(true).size
    this.computeMetricsByResults(expected, actual)
    assert(actual == expected)
  }

  test("in the APK smssilience_fake_vertu, we should detect 2 flows") {
    val expected = 2

    val svfa = new AndroidTaintBenchTest("smssilience_fake_vertu") with SmssilienceFakeVertuSpec
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG(true).size
    this.computeMetricsByResults(expected, actual)
    assert(actual == expected)
  }

  test("in the APK smsstealer_kysn_assassincreed_android_samp, we should detect 5 flows") {
    val expected = 5

    val svfa = new AndroidTaintBenchTest("smsstealer_kysn_assassincreed_android_samp") with SmsstealerKysnAssassincreedAndroidSampSpec
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG(true).size
    this.computeMetricsByResults(expected, actual)
    assert(actual == expected)
  }

  test("in the APK stels_flashplayer_android_update, we should detect 3 flows") {
    val expected = 3

    val svfa = new AndroidTaintBenchTest("stels_flashplayer_android_update") with StelsFlashplayerAndroidUpdateSpec
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG(true).size
    this.computeMetricsByResults(expected, actual)
    assert(actual == expected)
  }

  test("in the APK tetus, we should detect 2 flows") {
    val expected = 2

    val svfa = new AndroidTaintBenchTest("tetus") with TetusSpec
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG(true).size
    this.computeMetricsByResults(expected, actual)
    assert(actual == expected)
  }

  test("in the APK the_interview_movieshow, we should detect 1 flows") {
    val expected = 1

    val svfa = new AndroidTaintBenchTest("the_interview_movieshow") with TheInterviewMovieShowSpec
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG(true).size
    this.computeMetricsByResults(expected, actual)
    assert(actual == expected)
  }

  test("in the APK threatjapan_uracto, we should detect 2 flows") {
    val expected = 2

    val svfa = new AndroidTaintBenchTest("threatjapan_uracto") with ThreatjapanUractoSpec
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG(true).size
    this.computeMetricsByResults(expected, actual)
    assert(actual == expected)
  }

  test("in the APK vibleaker_android_samp, we should detect 4 flows") {
    val expected = 4

    val svfa = new AndroidTaintBenchTest("vibleaker_android_samp") with VibleakerAndroidSampSpec
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG(true).size
    this.computeMetricsByResults(expected, actual)
    assert(actual == expected)
  }

  test("in the APK xbot_android_samp, we should detect 3 flows") {
    val expected = 3

    val svfa = new AndroidTaintBenchTest("xbot_android_samp") with XbotAndroidSampSpec
    svfa.buildSparseValueFlowGraph()

    val actual = svfa.reportConflictsSVG(true).size
    this.computeMetricsByResults(expected, actual)
    assert(actual == expected)
  }
}
