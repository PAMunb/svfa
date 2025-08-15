package br.unb.cic.android.specs

import br.unb.cic.android.TaintBenchSpec

trait GodwonSampSpec extends TaintBenchSpec {
//  URL https://github.com/TaintBench/roidsec/blob/master/README.md
  override val sourceList = List(
    "<android.telephony.TelephonyManager: java.lang.String getLine1Number()>",
    "<android.content.Intent: java.io.Serializable getSerializableExtra(java.lang.String)>",
    "<java.io.BufferedReader: java.lang.String readLine()>",
    "<android.content.Intent: java.lang.String getAction()>",
    "<android.telephony.TelephonyManager: java.lang.String getDeviceId()>",
    "<android.telephony.SmsMessage: android.telephony.SmsMessage createFromPdu(byte[])>",
    "<java.net.HttpURLConnection: java.io.InputStream getInputStream()>",
    "<android.telephony.SmsMessage: java.lang.String getOriginatingAddress()>",
    "<android.content.Context: java.lang.Object getSystemService(java.lang.String)>",
    "<android.telephony.SmsMessage: java.lang.String getDisplayMessageBody()>",
  )

  override val sinkList = List(
    "<org.apache.http.client.methods.HttpEntityEnclosingRequestBase: void setEntity(org.apache.http.HttpEntity)>",
    "<java.net.URL: void <init>",
    "<java.net.URL: java.net.URLConnection openConnection()>",
    "<android.app.Activity: void onCreate(android.os.Bundle)>",
    "<org.apache.http.impl.client.DefaultHttpClient: org.apache.http.HttpResponse execute(org.apache.http.client.methods.HttpUriRequest)>",
    "<java.net.HttpURLConnection: java.io.InputStream getInputStream()>",
    "<android.util.Log: int e(java.lang.String,java.lang.String)>",
    "<java.net.HttpURLConnection: void setRequestMethod(java.lang.String)>",
  )
}