package br.unb.cic.android.specs

import br.unb.cic.android.TaintBenchSpec

trait SmssilienceFakeVertuSpec extends TaintBenchSpec {
//  URL https://github.com/TaintBench/smssilience_fake_vertu/blob/master/README.md
  override val sourceList = List(
    "<android.telephony.TelephonyManager: java.lang.String getLine1Number()>",
    "<android.app.AlertDialog$Builder: android.app.AlertDialog show()>",
    "<java.io.BufferedReader: java.lang.String readLine()>",
    "<android.content.Intent: android.os.Bundle getExtras()>",
    "<android.os.Bundle: java.lang.Object get(java.lang.String)>",
    "<android.telephony.SmsMessage: java.lang.String getMessageBody()>",
    "<android.net.Uri: android.net.Uri parse(java.lang.String)>",
    "<android.telephony.SmsMessage: android.telephony.SmsMessage createFromPdu(byte[])>",
    "<java.net.HttpURLConnection: java.io.InputStream getInputStream()>",
    "<android.telephony.SmsMessage: java.lang.String getOriginatingAddress()>",
    "<android.content.Context: java.lang.Object getSystemService(java.lang.String)>",
  )

  override val sinkList = List(
    "<java.net.URL: void <init>",
    "<java.net.URL: java.net.URLConnection openConnection()>",
    "<android.net.Uri: android.net.Uri parse(java.lang.String)>",
    "<java.net.HttpURLConnection: java.io.InputStream getInputStream()>",
    "<android.view.Window: void setFlags(int,int)>",
    "<java.lang.String: java.lang.String substring(int,int)>",
    "<android.content.Context: void startActivity(android.content.Intent)>",
    "<java.io.OutputStreamWriter: void <init>",
    "<android.widget.Toast: android.widget.Toast makeText(android.content.Context,java.lang.CharSequence,int)>",
    "<android.app.Activity: void onCreate(android.os.Bundle)>",
    "<java.io.PrintWriter: void write(java.lang.String)>",
    "<java.net.HttpURLConnection: java.io.OutputStream getOutputStream()>",
    "<java.net.HttpURLConnection: void setRequestMethod(java.lang.String)>",
  )
}