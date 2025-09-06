package br.unb.cic.android

import br.unb.cic.android.specs.{
  BackFlashSpec,
  BeitaComBeitaContactSpec,
  CajinoBaiduSpec,
  ChatHookSpec,
  ChuliaSpec,
  DeathRingMaterialflowSpec,
  DsencryptSampSpec,
  ExprespamSpec,
  FakeappstoreSpec,
  FakebankAndroidSampSpec,
  FakedaumSpec,
  FakemartSpec,
  FakeplaySpec,
  FaketaobaoSpec,
  GodwonSampSpec,
  HummingbadAndroidSampSpec,
  JollyservSpec,
  OverlayAndroidSampSpec,
  Overlaylocker2AndroidSampSpec,
  PhospySpec,
  ProxySampSpec,
  RemoteControlSmackSpec,
  RepaneSpec,
  RoidSecSpec,
  SamsapoSpec,
  SaveMeSpec,
  ScipiexSpec,
  SlockerAndroidSampSpec,
  SmsGoogleSpec,
  SmsSendLockerQqmagicSpec,
  SmssendPackageInstallerSpec,
  SmssilienceFakeVertuSpec,
  SmsstealerKysnAssassincreedAndroidSampSpec,
  StelsFlashplayerAndroidUpdateSpec,
  TetusSpec,
  TheInterviewMovieShowSpec,
  ThreatjapanUractoSpec,
  VibleakerAndroidSampSpec,
  XbotAndroidSampSpec
}
import br.unb.cic.metrics.TestResult
import org.scalatest.FunSuite

/** EXPERIMENT 2: Each test is run with its associated list of sources and sinks
  * We do it injecting the "trait" that contents these list at the instance of
  * the object
  */
class AndroidTaintBenchSuiteExperiment2Test extends FunSuite with TestResult {

  ignore("in the APK backflash, we should detect 13 flow") {
    val nameAPK = "backflash";
    val expected = 13;

    val svfa = new AndroidTaintBenchTest(nameAPK) with BackFlashSpec
    svfa.buildSparseValueFlowGraph()

    val found = svfa.reportConflictsSVG(true).size
    val executionTime = svfa.executionTime()

    this.compute(
      expected,
      found,
      nameAPK,
      executionTime,
      showReportSummary = true
    )
    assert(found == expected)
  }

  ignore("in the APK beita_com_beita_contact, we should detect 3 flow") {
    val nameAPK = "beita_com_beita_contact";
    val expected = 3;

    val svfa = new AndroidTaintBenchTest(nameAPK) with BeitaComBeitaContactSpec
    svfa.buildSparseValueFlowGraph()

    val found = svfa.reportConflictsSVG(true).size
    val executionTime = svfa.executionTime()
    this.compute(
      expected,
      found,
      nameAPK,
      executionTime,
      showReportSummary = true
    )
    assert(found == expected)
  }

  ignore("in the APK cajino_baidu, we should detect 12 flow") {
    val nameAPK = "cajino_baidu";
    val expected = 12;

    val svfa = new AndroidTaintBenchTest(nameAPK) with CajinoBaiduSpec
    svfa.buildSparseValueFlowGraph()

    val found = svfa.reportConflictsSVG(true).size
    val executionTime = svfa.executionTime()
    this.compute(
      expected,
      found,
      nameAPK,
      executionTime,
      showReportSummary = true
    )
    assert(found == expected)
  }

  ignore("in the APK chat_hook, we should detect 12 flow") {
    val nameAPK = "chat_hook";
    val expected = 12;

    val svfa = new AndroidTaintBenchTest(nameAPK) with ChatHookSpec
    svfa.buildSparseValueFlowGraph()

    val found = svfa.reportConflictsSVG(true).size
    val executionTime = svfa.executionTime()
    this.compute(
      expected,
      found,
      nameAPK,
      executionTime,
      showReportSummary = true
    )
    assert(found == expected)
  }

  ignore("in the APK chulia, we should detect 4 flow") {
    val nameAPK = "chulia";
    val expected = 4;

    val svfa = new AndroidTaintBenchTest(nameAPK) with ChuliaSpec
    svfa.buildSparseValueFlowGraph()

    val found = svfa.reportConflictsSVG(true).size
    val executionTime = svfa.executionTime()
    this.compute(
      expected,
      found,
      nameAPK,
      executionTime,
      showReportSummary = true
    )
    assert(found == expected)
  }

  ignore("in the APK death_ring_materialflow, we should detect 1 flow") {
    val nameAPK = "death_ring_materialflow";
    val expected = 1;

    val svfa = new AndroidTaintBenchTest(nameAPK) with DeathRingMaterialflowSpec
    svfa.buildSparseValueFlowGraph()

    val found = svfa.reportConflictsSVG(true).size
    val executionTime = svfa.executionTime()
    this.compute(
      expected,
      found,
      nameAPK,
      executionTime,
      showReportSummary = true
    )
    assert(found == expected)
  }

  ignore("in the APK dsencrypt_samp, we should detect 1 flow") {
    val nameAPK = "dsencrypt_samp";
    val expected = 1;

    val svfa = new AndroidTaintBenchTest(nameAPK) with DsencryptSampSpec
    svfa.buildSparseValueFlowGraph()

    val found = svfa.reportConflictsSVG(true).size
    val executionTime = svfa.executionTime()
    this.compute(
      expected,
      found,
      nameAPK,
      executionTime,
      showReportSummary = true
    )
    assert(found == expected)
  }

  ignore("in the APK exprespam, we should detect 2 flow") {
    val nameAPK = "exprespam";
    val expected = 2;

    val svfa = new AndroidTaintBenchTest(nameAPK) with ExprespamSpec
    svfa.buildSparseValueFlowGraph()

    val found = svfa.reportConflictsSVG(true).size
    val executionTime = svfa.executionTime()
    this.compute(
      expected,
      found,
      nameAPK,
      executionTime,
      showReportSummary = true
    )
    assert(found == expected)
  }

  ignore("in the APK fakeappstore, we should detect 3 flow") {
    val nameAPK = "fakeappstore";
    val expected = 3;

    val svfa = new AndroidTaintBenchTest(nameAPK) with FakeappstoreSpec
    svfa.buildSparseValueFlowGraph()

    val found = svfa.reportConflictsSVG(true).size
    val executionTime = svfa.executionTime()
    this.compute(
      expected,
      found,
      nameAPK,
      executionTime,
      showReportSummary = true
    )
    assert(found == expected)
  }

  ignore("in the APK fakebank_android_samp, we should detect 5 flow") {
    val nameAPK = "fakebank_android_samp";
    val expected = 5;

    val svfa = new AndroidTaintBenchTest(nameAPK) with FakebankAndroidSampSpec
    svfa.buildSparseValueFlowGraph()

    val found = svfa.reportConflictsSVG(true).size
    val executionTime = svfa.executionTime()
    this.compute(
      expected,
      found,
      nameAPK,
      executionTime,
      showReportSummary = true
    )
    assert(found == expected)
  }

  ignore("in the APK fakedaum, we should detect 2 flows") {
    val nameAPK = "fakedaum";
    val expected = 2;

    val svfa = new AndroidTaintBenchTest(nameAPK) with FakedaumSpec
    svfa.buildSparseValueFlowGraph()

    val found = svfa.reportConflictsSVG(true).size
    val executionTime = svfa.executionTime()
    this.compute(
      expected,
      found,
      nameAPK,
      executionTime,
      showReportSummary = true
    )
    assert(found == expected)
  }

  ignore("in the APK fakemart, we should detect 2 flows") {
    val nameAPK = "fakemart";
    val expected = 2;

    val svfa = new AndroidTaintBenchTest(nameAPK) with FakemartSpec
    svfa.buildSparseValueFlowGraph()

    val found = svfa.reportConflictsSVG(true).size
    val executionTime = svfa.executionTime()
    this.compute(
      expected,
      found,
      nameAPK,
      executionTime,
      showReportSummary = true
    )
    assert(found == expected)
  }

  ignore("in the APK fakeplay, we should detect 2 flows") {
    val nameAPK = "fakeplay";
    val expected = 2;

    val svfa = new AndroidTaintBenchTest(nameAPK) with FakeplaySpec
    svfa.buildSparseValueFlowGraph()

    val found = svfa.reportConflictsSVG(true).size
    val executionTime = svfa.executionTime()
    this.compute(
      expected,
      found,
      nameAPK,
      executionTime,
      showReportSummary = true
    )
    assert(found == expected)
  }

  ignore("in the APK faketaobao, we should detect 4 flows") {
    val nameAPK = "faketaobao";
    val expected = 4;

    val svfa = new AndroidTaintBenchTest(nameAPK) with FaketaobaoSpec
    svfa.buildSparseValueFlowGraph()

    val found = svfa.reportConflictsSVG(true).size
    val executionTime = svfa.executionTime()
    this.compute(
      expected,
      found,
      nameAPK,
      executionTime,
      showReportSummary = true
    )
    assert(found == expected)
  }

  ignore("in the APK godwon_samp, we should detect 6 flows") {
    val nameAPK = "godwon_samp";
    val expected = 6;

    val svfa = new AndroidTaintBenchTest(nameAPK) with GodwonSampSpec
    svfa.buildSparseValueFlowGraph()

    val found = svfa.reportConflictsSVG(true).size
    val executionTime = svfa.executionTime()
    this.compute(
      expected,
      found,
      nameAPK,
      executionTime,
      showReportSummary = true
    )
    assert(found == expected)
  }

  ignore("in the APK hummingbad_android_samp, we should detect 2 flows") {
    val nameAPK = "hummingbad_android_samp";
    val expected = 2;

    val svfa = new AndroidTaintBenchTest(nameAPK) with HummingbadAndroidSampSpec
    svfa.buildSparseValueFlowGraph()

    val found = svfa.reportConflictsSVG(true).size
    val executionTime = svfa.executionTime()
    this.compute(
      expected,
      found,
      nameAPK,
      executionTime,
      showReportSummary = true
    )
    assert(found == expected)
  }

  ignore("in the APK jollyserv, we should detect 1 flows") {
    val nameAPK = "jollyserv";
    val expected = 1;

    val svfa = new AndroidTaintBenchTest(nameAPK) with JollyservSpec
    svfa.buildSparseValueFlowGraph()

    val found = svfa.reportConflictsSVG(true).size
    val executionTime = svfa.executionTime()
    this.compute(
      expected,
      found,
      nameAPK,
      executionTime,
      showReportSummary = true
    )
    assert(found == expected)
  }

  ignore("in the APK overlay_android_samp, we should detect 4 flows") {
    val nameAPK = "overlay_android_samp";
    val expected = 4;

    val svfa = new AndroidTaintBenchTest(nameAPK) with OverlayAndroidSampSpec
    svfa.buildSparseValueFlowGraph()

    val found = svfa.reportConflictsSVG(true).size
    val executionTime = svfa.executionTime()
    this.compute(
      expected,
      found,
      nameAPK,
      executionTime,
      showReportSummary = true
    )
    assert(found == expected)
  }

  ignore("in the APK overlaylocker2_android_samp, we should detect 7 flows") {
    val nameAPK = "overlaylocker2_android_samp";
    val expected = 7;

    val svfa = new AndroidTaintBenchTest(nameAPK)
      with Overlaylocker2AndroidSampSpec
    svfa.buildSparseValueFlowGraph()

    val found = svfa.reportConflictsSVG(true).size
    val executionTime = svfa.executionTime()
    this.compute(
      expected,
      found,
      nameAPK,
      executionTime,
      showReportSummary = true
    )
    assert(found == expected)
  }

  ignore("in the APK phospy, we should detect 2 flows") {
    val nameAPK = "phospy";
    val expected = 2;

    val svfa = new AndroidTaintBenchTest(nameAPK) with PhospySpec
    svfa.buildSparseValueFlowGraph()

    val found = svfa.reportConflictsSVG(true).size
    val executionTime = svfa.executionTime()
    this.compute(
      expected,
      found,
      nameAPK,
      executionTime,
      showReportSummary = true
    )
    assert(found == expected)
  }

  ignore("in the APK proxy_samp, we should detect 17 flows") {
    val nameAPK = "proxy_samp";
    val expected = 17;

    val svfa = new AndroidTaintBenchTest(nameAPK) with ProxySampSpec
    svfa.buildSparseValueFlowGraph()

    val found = svfa.reportConflictsSVG(true).size
    val executionTime = svfa.executionTime()
    this.compute(
      expected,
      found,
      nameAPK,
      executionTime,
      showReportSummary = true
    )
    assert(found == expected)
  }

  ignore("in the APK remote_control_smack, we should detect 17 flows") {
    val nameAPK = "remote_control_smack";
    val expected = 17;

    val svfa = new AndroidTaintBenchTest(nameAPK) with RemoteControlSmackSpec
    svfa.buildSparseValueFlowGraph()

    val found = svfa.reportConflictsSVG(true).size
    val executionTime = svfa.executionTime()
    this.compute(
      expected,
      found,
      nameAPK,
      executionTime,
      showReportSummary = true
    )
    assert(found == expected)
  }

  test("in the APK repane, we should detect 1 flow") {
    val nameAPK = "repane";
    val expected = 1;

    val svfa = new AndroidTaintBenchTest(nameAPK) with RepaneSpec
    svfa.buildSparseValueFlowGraph()

    val found = svfa.reportConflictsSVG(true).size
    val executionTime = svfa.executionTime()
    this.compute(
      expected,
      found,
      nameAPK,
      executionTime,
      showReportSummary = true
    )
    assert(found == expected)
  }

  ignore("in the APK Roidsec, we should detect 6 flow") {
    val nameAPK = "roidsec";
    val expected = 6;

    val svfa = new AndroidTaintBenchTest(nameAPK) with RoidSecSpec
    svfa.buildSparseValueFlowGraph()

    val found = svfa.reportConflictsSVG(true).size
    val executionTime = svfa.executionTime()
    this.compute(
      expected,
      found,
      nameAPK,
      executionTime,
      showReportSummary = true
    )
    assert(found == expected)
  }

  ignore("in the APK samsapo, we should detect 4 flows") {
    val nameAPK = "samsapo";
    val expected = 4;

    val svfa = new AndroidTaintBenchTest(nameAPK) with SamsapoSpec
    svfa.buildSparseValueFlowGraph()

    val found = svfa.reportConflictsSVG(true).size
    val executionTime = svfa.executionTime()
    this.compute(
      expected,
      found,
      nameAPK,
      executionTime,
      showReportSummary = true
    )
    assert(found == expected)
  }

  test("in the APK save_me we should detect 25 flows") {
    val nameAPK = "save_me";
    val expected = 25;

    val svfa = new AndroidTaintBenchTest(nameAPK) with SaveMeSpec
    svfa.buildSparseValueFlowGraph()

    val found = svfa.reportConflictsSVG(true).size
    val executionTime = svfa.executionTime()
    this.compute(
      expected,
      found,
      nameAPK,
      executionTime,
      showReportSummary = true
    )
    assert(found == expected)
  }

  ignore("in the APK scipiex, we should detect 3 flows") {
    val nameAPK = "scipiex";
    val expected = 3;

    val svfa = new AndroidTaintBenchTest(nameAPK) with ScipiexSpec
    svfa.buildSparseValueFlowGraph()

    val found = svfa.reportConflictsSVG(true).size
    val executionTime = svfa.executionTime()
    this.compute(
      expected,
      found,
      nameAPK,
      executionTime,
      showReportSummary = true
    )
    assert(found == expected)
  }

  ignore("in the APK slocker_android_samp, we should detect 5 flows") {
    val nameAPK = "slocker_android_samp";
    val expected = 5;

    val svfa = new AndroidTaintBenchTest(nameAPK) with SlockerAndroidSampSpec
    svfa.buildSparseValueFlowGraph()

    val found = svfa.reportConflictsSVG(true).size
    val executionTime = svfa.executionTime()
    this.compute(
      expected,
      found,
      nameAPK,
      executionTime,
      showReportSummary = true
    )
    assert(found == expected)
  }

  ignore("in the APK sms_google, we should detect 4 flows") {
    val nameAPK = "sms_google";
    val expected = 4;

    val svfa = new AndroidTaintBenchTest(nameAPK) with SmsGoogleSpec
    svfa.buildSparseValueFlowGraph()

    val found = svfa.reportConflictsSVG(true).size
    val executionTime = svfa.executionTime()
    this.compute(
      expected,
      found,
      nameAPK,
      executionTime,
      showReportSummary = true
    )
    assert(found == expected)
  }

  ignore("in the APK sms_send_locker_qqmagic, we should detect 6 flows") {
    val nameAPK = "sms_send_locker_qqmagic";
    val expected = 6;

    val svfa = new AndroidTaintBenchTest(nameAPK) with SmsSendLockerQqmagicSpec
    svfa.buildSparseValueFlowGraph()

    val found = svfa.reportConflictsSVG(true).size
    val executionTime = svfa.executionTime()
    this.compute(
      expected,
      found,
      nameAPK,
      executionTime,
      showReportSummary = true
    )
    assert(found == expected)
  }

  ignore("in the APK smssend_packageInstaller, we should detect 5 flows") {
    val nameAPK = "smssend_packageInstaller";
    val expected = 5;

    val svfa = new AndroidTaintBenchTest(nameAPK)
      with SmssendPackageInstallerSpec
    svfa.buildSparseValueFlowGraph()

    val found = svfa.reportConflictsSVG(true).size
    val executionTime = svfa.executionTime()
    this.compute(
      expected,
      found,
      nameAPK,
      executionTime,
      showReportSummary = true
    )
    assert(found == expected)
  }

  test("in the APK smssilience_fake_vertu, we should detect 2 flows") {
    val nameAPK = "smssilience_fake_vertu";
    val expected = 2;

    val svfa = new AndroidTaintBenchTest(nameAPK) with SmssilienceFakeVertuSpec
    svfa.buildSparseValueFlowGraph()

    val found = svfa.reportConflictsSVG(true).size
    val executionTime = svfa.executionTime()
    this.compute(
      expected,
      found,
      nameAPK,
      executionTime,
      showReportSummary = true
    )
    assert(found == expected)
  }

  ignore(
    "in the APK smsstealer_kysn_assassincreed_android_samp, we should detect 5 flows"
  ) {
    val nameAPK = "smsstealer_kysn_assassincreed_android_samp";
    val expected = 5;

    val svfa = new AndroidTaintBenchTest(nameAPK)
      with SmsstealerKysnAssassincreedAndroidSampSpec
    svfa.buildSparseValueFlowGraph()

    val found = svfa.reportConflictsSVG(true).size
    val executionTime = svfa.executionTime()
    this.compute(
      expected,
      found,
      nameAPK,
      executionTime,
      showReportSummary = true
    )
    assert(found == expected)
  }

  ignore(
    "in the APK stels_flashplayer_android_update, we should detect 3 flows"
  ) {
    val nameAPK = "stels_flashplayer_android_update";
    val expected = 3;

    val svfa = new AndroidTaintBenchTest(nameAPK)
      with StelsFlashplayerAndroidUpdateSpec
    svfa.buildSparseValueFlowGraph()

    val found = svfa.reportConflictsSVG(true).size
    val executionTime = svfa.executionTime()
    this.compute(
      expected,
      found,
      nameAPK,
      executionTime,
      showReportSummary = true
    )
    assert(found == expected)
  }

  ignore("in the APK tetus, we should detect 2 flows") {
    val nameAPK = "tetus";
    val expected = 2;

    val svfa = new AndroidTaintBenchTest(nameAPK) with TetusSpec
    svfa.buildSparseValueFlowGraph()

    val found = svfa.reportConflictsSVG(true).size
    val executionTime = svfa.executionTime()
    this.compute(
      expected,
      found,
      nameAPK,
      executionTime,
      showReportSummary = true
    )
    assert(found == expected)
  }

  test("in the APK the_interview_movieshow, we should detect 1 flows") {
    val nameAPK = "the_interview_movieshow";
    val expected = 1;

    val svfa = new AndroidTaintBenchTest(nameAPK) with TheInterviewMovieShowSpec
    svfa.buildSparseValueFlowGraph()

    val found = svfa.reportConflictsSVG(true).size
    val executionTime = svfa.executionTime()
    this.compute(
      expected,
      found,
      nameAPK,
      executionTime,
      showReportSummary = true
    )
    assert(found == expected)
  }

  ignore("in the APK threatjapan_uracto, we should detect 2 flows") {
    val nameAPK = "threatjapan_uracto";
    val expected = 2;

    val svfa = new AndroidTaintBenchTest(nameAPK) with ThreatjapanUractoSpec
    svfa.buildSparseValueFlowGraph()

    val found = svfa.reportConflictsSVG(true).size
    val executionTime = svfa.executionTime()
    this.compute(
      expected,
      found,
      nameAPK,
      executionTime,
      showReportSummary = true
    )
    assert(found == expected)
  }

  ignore("in the APK vibleaker_android_samp, we should detect 4 flows") {
    val nameAPK = "vibleaker_android_samp";
    val expected = 4;

    val svfa = new AndroidTaintBenchTest(nameAPK) with VibleakerAndroidSampSpec
    svfa.buildSparseValueFlowGraph()

    val found = svfa.reportConflictsSVG(true).size
    val executionTime = svfa.executionTime()
    this.compute(
      expected,
      found,
      nameAPK,
      executionTime,
      showReportSummary = true
    )
    assert(found == expected)
  }

  /** DISABLE this test because it is using a lot of memory.
    */
  ignore("in the APK xbot_android_samp, we should detect 3 flows") {
    val nameAPK = "xbot_android_samp";
    val expected = 3;

    // val svfa = new AndroidTaintBenchTest(nameAPK) with XbotAndroidSampSpec
    // svfa.buildSparseValueFlowGraph()

    val found = 0
    val executionTime = 0.0
    this.compute(
      expected,
      found,
      nameAPK,
      executionTime,
      showReportSummary = true
    )
    assert(found == expected)
  }
}
