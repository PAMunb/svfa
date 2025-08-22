package br.unb.cic.android.specs

import br.unb.cic.android.TaintBenchSpec

trait ChatHookSpec extends TaintBenchSpec {
//  URL https://github.com/TaintBench/chat_hook/blob/master/README.md

  override val sourceList = List(
    "<android.telephony.SmsManager: android.telephony.SmsManager getDefault()>",
    "<java.lang.String: byte[] getBytes(java.lang.String)>",
    "<java.io.BufferedReader: java.lang.String readLine()>",
    "<android.content.Intent: java.lang.String getStringExtra(java.lang.String)>",
    "<java.lang.Runtime: java.lang.Runtime getRuntime()>",
    "<android.content.Intent: boolean getBooleanExtra(java.lang.String,boolean)>",
    "<java.io.File: void <init>",
    "<java.util.HashMap: java.lang.Object get(java.lang.Object)>",
    "<java.io.File: java.lang.String getAbsolutePath()>",
    "<java.lang.Class: java.lang.reflect.Method getDeclaredMethod(java.lang.String,java.lang.Class[])>",
    "<android.content.res.AssetManager: java.io.InputStream open(java.lang.String)>",
    "<android.content.Context: java.io.FileInputStream openFileInput(java.lang.String)>",
    "<android.app.ActivityManager: java.util.List getRecentTasks(int,int)>",
    "<android.content.Intent: java.lang.String getAction()>",
    "<android.telephony.TelephonyManager: java.lang.String getDeviceId()>",
    "<android.content.Intent: android.content.ComponentName getComponent()>",
    "<android.content.res.Resources: android.content.res.AssetManager getAssets()>",
    "<java.lang.Integer: int parseInt(java.lang.String)>",
    "<android.content.ComponentName: java.lang.String getPackageName()>",
    "<android.telephony.SmsManager: java.util.ArrayList divideMessage(java.lang.String)>",
    "<java.lang.reflect.Method: java.lang.Object invoke(java.lang.Object,java.lang.Object[])>",
    "<android.widget.EditText: android.text.Editable getText()>",
  )

  override val sinkList = List(
    "<android.widget.TextView: void setText(java.lang.CharSequence)>",
    "<android.content.Intent: android.content.Intent putExtra(java.lang.String,java.lang.String)>",
    "<java.io.DataOutputStream: void writeBytes(java.lang.String)>",
    "<android.content.res.AssetManager: java.io.InputStream open(java.lang.String)>",
    "<org.apache.http.impl.client.DefaultHttpClient: org.apache.http.HttpResponse execute(org.apache.http.client.methods.HttpUriRequest)>",
    "<java.io.FileOutputStream: void <init>",
    "<java.io.FileOutputStream: void write(byte[])>",
    "<java.lang.String: java.lang.String substring(int,int)>",
    "<android.util.Log: int i(java.lang.String,java.lang.String)>",
    "<android.view.View: void setOnClickListener(android.view.View$OnClickListener)>",
    "<org.apache.http.client.HttpClient: org.apache.http.HttpResponse execute(org.apache.http.client.methods.HttpUriRequest)>",
    "<android.widget.Toast: android.widget.Toast makeText(android.content.Context,java.lang.CharSequence,int)>",
    "<java.lang.Integer: int parseInt(java.lang.String)>",
    "<android.app.Activity: void onCreate(android.os.Bundle)>",
    "<java.io.DataOutputStream: void flush()>",
    "<android.content.Context: android.content.ComponentName startService(android.content.Intent)>",
    "<java.lang.reflect.Method: java.lang.Object invoke(java.lang.Object,java.lang.Object[])>",
    "<android.util.Log: int e(java.lang.String,java.lang.String)>",
    "<java.util.HashMap: java.lang.Object put(java.lang.Object,java.lang.Object)>",
    "<android.telephony.SmsManager: void sendMultipartTextMessage(java.lang.String,java.lang.String,java.util.ArrayList,java.util.ArrayList,java.util.ArrayList)>",
    "<java.lang.Class: java.lang.Class forName(java.lang.String)>",
  )
}