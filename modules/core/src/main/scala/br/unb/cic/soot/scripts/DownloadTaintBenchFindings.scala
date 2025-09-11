package br.unb.cic.soot.scripts

import org.apache.commons.io.FileUtils

import java.net.URL
import java.io.File

/**
  * Usage:
  *   sbt "runMain br.unb.cic.soot.scripts.DownloadTaintBenchFindings <download-path>"
  *
  * Example:
  *   sbt "core/runMain br.unb.cic.soot.scripts.DownloadTaintBenchFindings docs-metrics/taintbench/source/findings"
  */
object DownloadTaintBenchFindings extends App {

    if (args.isEmpty) {
        println("No parameters provided.")
        System.exit(1)
    }

    val taintbenchBaseUrl = "https://raw.githubusercontent.com/TaintBench"
    val apks = List(
        "backflash",
        "beita_com_beita_contact",
        "cajino_baidu",
        "chat_hook",
        "chulia",
        "death_ring_materialflow",
        "dsencrypt_samp",
        "exprespam",
        "fakeappstore",
        "fakebank_android_samp",
        "fakedaum",
        "fakemart",
        "fakeplay",
        "faketaobao",
        "godwon_samp",
        "hummingbad_android_samp",
        "jollyserv",
        "overlay_android_samp",
        "overlaylocker2_android_samp",
        "phospy",
        "proxy_samp",
        "remote_control_smack",
        "repane",
        "roidsec",
        "samsapo",
        "save_me",
        "scipiex",
        "slocker_android_samp",
        "sms_google",
        "sms_send_locker_qqmagic",
        "smssend_packageInstaller",
        "smssilience_fake_vertu",
        "smsstealer_kysn_assassincreed_android_samp",
        "stels_flashplayer_android_update",
        "tetus",
        "the_interview_movieshow",
        "threatjapan_uracto",
        "vibleaker_android_samp",
        "xbot_android_samp"
    )

    val downloadPath = args(0)

    for (apk <- apks) {
        val fileUrl = s"$taintbenchBaseUrl/$apk/refs/heads/master/$apk" + "_findings.json"
        val destinationPath = s"$downloadPath/$apk" + "_findings.json"
        
        try {
            println(s"Downloading $fileUrl to $destinationPath")
            val url = new URL(fileUrl)
            val destinationFile = new File(destinationPath)
            FileUtils.copyURLToFile(url, destinationFile)
        } catch {
            case e: Exception =>
                println(s"Error downloading $apk: ${e.getMessage}")
        }
    }
}
