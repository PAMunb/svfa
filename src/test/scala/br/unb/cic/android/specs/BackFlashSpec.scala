package br.unb.cic.android.specs

import br.unb.cic.android.AndroidTaintBenchTest

trait BackFlashSpec extends AndroidTaintBenchTest {

  override val sourceList = List(
    "<android.content.Intent: java.lang.String getStringExtra(java.lang.String)>",
    "<android.content.ContentResolver: android.database.Cursor query(android.net.Uri,java.lang.String[],java.lang.String,java.lang.String[],java.lang.String)>",
    "<android.net.Uri: android.net.Uri parse(java.lang.String)>",
    "<java.lang.Class: java.lang.reflect.Field getDeclaredField(java.lang.String)>",
    "<android.app.ActivityManager: java.util.List getRunningTasks(int)>",
    "<android.content.ComponentName: java.lang.String getClassName()>",
    "<android.telephony.TelephonyManager: java.lang.String getDeviceId()>",
    "<java.lang.reflect.Field: java.lang.Object get(java.lang.Object)>",
    "<android.telephony.SmsManager: java.util.ArrayList divideMessage(java.lang.String)>",
    "<java.io.FileInputStream: void <init>",
    "<android.webkit.WebView: void loadUrl(java.lang.String)>",
    "<android.os.AsyncTask: java.lang.Object get()>",
    "<android.database.Cursor: java.lang.String getString(int)>",
    "<java.util.Calendar: java.util.Calendar getInstance()>",
    "<java.lang.String: byte[] getBytes()>",
    "<android.webkit.WebSettings: void setLoadWithOverviewMode(boolean)>",
    "<android.net.ConnectivityManager: android.net.NetworkInfo getActiveNetworkInfo()>",
    "<java.io.File: java.lang.String getPath()>",
    "<android.webkit.WebView: void loadData(java.lang.String,java.lang.String,java.lang.String)>",
    "<android.telephony.TelephonyManager: java.lang.String getLine1Number()>",
    "<android.content.Intent: android.os.Bundle getExtras()>",
    "<android.telephony.TelephonyManager: java.lang.String getNetworkOperatorName()>",
    "<android.content.Intent: android.net.Uri getData()>",
    "<android.telephony.SmsManager: android.telephony.SmsManager getDefault()>",
    "<java.io.BufferedReader: java.lang.String readLine()>",
    "<java.lang.Class: java.lang.reflect.Method getDeclaredMethod(java.lang.String,java.lang.Class[])>",
    "<android.content.Context: java.io.FileInputStream openFileInput(java.lang.String)>",
    "<java.lang.Class: java.lang.String getName()>",
    "<android.telephony.SmsMessage: java.lang.String getOriginatingAddress()>",
    "<android.provider.Settings$Secure: java.lang.String getString(android.content.ContentResolver,java.lang.String)>",
    "<android.os.Environment: java.io.File getExternalStorageDirectory()>",
    "<android.content.Intent: java.lang.String getAction()>",
    "<java.util.Calendar: java.util.Date getTime()>",
    "<android.telephony.SmsMessage: java.lang.String getMessageBody()>",
    "<org.apache.http.HttpEntity: java.io.InputStream getContent()>",
    "<android.database.Cursor: long getLong(int)>",
    "<android.telephony.SmsMessage: android.telephony.SmsMessage createFromPdu(byte[])>",
    "<java.lang.reflect.Method: java.lang.Object invoke(java.lang.Object,java.lang.Object[])>",
    "<android.app.ActivityManager: android.os.Debug$MemoryInfo[] getProcessMemoryInfo(int[])>",
    "<android.telephony.TelephonyManager: java.lang.String getSimCountryIso()>",
    "<android.content.Context: java.lang.Object getSystemService(java.lang.String)>",
    "<android.app.ActivityManager: java.util.List getRunningAppProcesses()>",
    "<java.io.File: java.io.File[] listFiles()>",
    "<android.os.Environment: java.lang.String getExternalStorageState()>",
    "<java.io.DataInputStream: java.lang.String readLine()>",
    "<java.io.File: void <init>",
    "<java.io.File: java.lang.String getAbsolutePath()>",
    "<java.net.HttpURLConnection: java.io.InputStream getInputStream()>",
    "<android.telephony.SmsMessage: java.lang.String getDisplayMessageBody()>",
    "<android.os.Bundle: java.lang.Object get(java.lang.String)>",
    "<android.os.PowerManager: android.os.PowerManager$WakeLock newWakeLock(int,java.lang.String)>",
    "<java.lang.Integer: int parseInt(java.lang.String)>",
    "<android.webkit.WebView: android.webkit.WebSettings getSettings()>",
  )

  override val sinkList = List(
    "<android.content.Intent: android.content.Intent setAction(java.lang.String)>",
    "<android.net.Uri: android.net.Uri parse(java.lang.String)>",
    "<android.app.ActivityManager: java.util.List getRunningTasks(int)>",
    "<android.view.Window: void setFlags(int,int)>",
    "<java.lang.String: java.lang.String substring(int,int)>",
    "<java.io.OutputStreamWriter: void <init>",
    "<android.content.Intent: android.content.Intent setFlags(int)>",
    "<android.widget.Toast: android.widget.Toast makeText(android.content.Context,java.lang.CharSequence,int)>",
    "<java.io.DataOutputStream: void write(byte[],int,int)>",
    "<java.io.DataOutputStream: void flush()>",
    "<android.content.Context: android.content.ComponentName startService(android.content.Intent)>",
    "<java.lang.reflect.Method: java.lang.Object invoke(java.lang.Object,java.lang.Object[])>",
    "<java.io.OutputStreamWriter: void write(java.lang.String)>",
    "<android.content.Intent: android.content.Intent putExtra(java.lang.String,android.os.Parcelable)>",
    "<android.view.View: void setVisibility(int)>",
    "<android.widget.TextView: void setText(java.lang.CharSequence)>",
    "<android.content.Intent: android.content.Intent putExtra(java.lang.String,java.lang.String)>",
    "<java.net.URL: void <init>",
    "<java.net.URL: java.net.URLConnection openConnection()>",
    "<android.webkit.WebSettings: void setJavaScriptEnabled(boolean)>",
    "<java.io.DataOutputStream: void writeBytes(java.lang.String)>",
    "<android.webkit.WebSettings: void setBuiltInZoomControls(boolean)>",
    "<android.webkit.WebView: void setWebViewClient(android.webkit.WebViewClient)>",
    "<java.util.Calendar: void setTimeInMillis(long)>",
    "<android.webkit.WebSettings: void setLoadWithOverviewMode(boolean)>",
    "<java.net.HttpURLConnection: java.io.InputStream getInputStream()>",
    "<android.content.Context: void startActivity(android.content.Intent)>",
    "<org.apache.http.client.HttpClient: org.apache.http.HttpResponse execute(org.apache.http.client.methods.HttpUriRequest)>",
    "<android.net.wifi.WifiManager: boolean setWifiEnabled(boolean)>",
    "<java.lang.Integer: int parseInt(java.lang.String)>",
    "<android.app.Activity: void onCreate(android.os.Bundle)>",
    "<java.net.HttpURLConnection: java.io.OutputStream getOutputStream()>",
    "<android.telephony.SmsManager: void sendMultipartTextMessage(java.lang.String,java.lang.String,java.util.ArrayList,java.util.ArrayList,java.util.ArrayList)>",
    "<java.net.HttpURLConnection: void setRequestMethod(java.lang.String)>",
    "<java.lang.Class: java.lang.Class forName(java.lang.String)>",
  )
}