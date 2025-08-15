package br.unb.cic.android.specs

import br.unb.cic.android.TaintBenchSpec

trait FaketaobaoSpec extends TaintBenchSpec {
//  URL https://github.com/TaintBench/faketaobao/blob/master/README.md
  override val sourceList = List(
    "<android.os.Environment: java.io.File getExternalStorageDirectory()>",
    "<android.telephony.TelephonyManager: java.lang.String getLine1Number()>",
    "<android.telephony.SmsManager: android.telephony.SmsManager getDefault()>",
    "<java.io.BufferedReader: java.lang.String readLine()>",
    "<android.app.AlertDialog$Builder: android.app.AlertDialog show()>",
    "<android.telephony.TelephonyManager: java.lang.String getDeviceId()>",
    "<java.lang.Runtime: java.lang.Runtime getRuntime()>",
    "<java.io.File: void <init>",
    "<android.content.res.AssetManager: java.io.InputStream open(java.lang.String)>",
    "<java.io.File: java.lang.String getPath()>",
    "<android.widget.EditText: android.text.Editable getText()>",
    "<java.net.HttpURLConnection: java.io.InputStream getInputStream()>",
  )

  override val sinkList = List(
    "<android.app.ProgressDialog: void setMessage(java.lang.CharSequence)>",
    "<android.widget.TextView: void setText(java.lang.CharSequence)>",
    "<org.apache.http.client.methods.HttpEntityEnclosingRequestBase: void setEntity(org.apache.http.HttpEntity)>",
    "<java.net.URL: void <init>",
    "<java.net.URL: java.net.URLConnection openConnection()>",
    "<android.content.Intent: android.content.Intent setAction(java.lang.String)>",
    "<android.content.res.AssetManager: java.io.InputStream open(java.lang.String)>",
    "<org.apache.http.impl.client.DefaultHttpClient: org.apache.http.HttpResponse execute(org.apache.http.client.methods.HttpUriRequest)>",
    "<java.net.HttpURLConnection: java.io.InputStream getInputStream()>",
    "<java.io.FileOutputStream: void <init>",
    "<java.io.FileOutputStream: void write(byte[])>",
    "<android.content.Context: void startActivity(android.content.Intent)>",
    "<android.content.Intent: android.content.Intent setFlags(int)>",
    "<android.widget.Toast: android.widget.Toast makeText(android.content.Context,java.lang.CharSequence,int)>",
    "<android.app.Activity: void onCreate(android.os.Bundle)>",
    "<android.content.Intent: android.content.Intent setComponent(android.content.ComponentName)>",
    "<android.content.Intent: android.content.Intent setDataAndType(android.net.Uri,java.lang.String)>",
    "<android.util.Log: int e(java.lang.String,java.lang.String)>",
    "<java.net.HttpURLConnection: void setRequestMethod(java.lang.String)>",
  )
}