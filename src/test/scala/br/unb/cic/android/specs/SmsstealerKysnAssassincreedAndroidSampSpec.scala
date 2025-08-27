package br.unb.cic.android.specs

import br.unb.cic.android.TaintBenchSpec

trait SmsstealerKysnAssassincreedAndroidSampSpec extends TaintBenchSpec {
//  URL https://github.com/TaintBench/smsstealer_kysn_assassincreed_android_samp/blob/master/README.md
  override val sourceList = List(
    "<java.io.BufferedReader: java.lang.String readLine()>",
    "<java.lang.String: byte[] getBytes(java.lang.String)>",
    "<android.content.Intent: java.lang.String getStringExtra(java.lang.String)>",
    "<android.content.ContentResolver: android.database.Cursor query(android.net.Uri,java.lang.String[],java.lang.String,java.lang.String[],java.lang.String)>",
    "<javax.crypto.Cipher: byte[] doFinal(byte[])>",
    "<android.net.Uri: android.net.Uri parse(java.lang.String)>",
    "<android.content.res.Resources: java.lang.String getString(int)>",
    "<android.telephony.SmsMessage: java.lang.String getOriginatingAddress()>",
    "<android.app.PendingIntent: android.app.PendingIntent getBroadcast(android.content.Context,int,android.content.Intent,int)>",
    "<java.net.HttpURLConnection: int getResponseCode()>",
    "<android.os.Environment: java.io.File getExternalStorageDirectory()>",
    "<android.telephony.gsm.SmsManager: java.util.ArrayList divideMessage(java.lang.String)>",
    "<android.content.Intent: java.lang.String getAction()>",
    "<java.lang.Integer: int parseInt(java.lang.String,int)>",
    "<android.telephony.SmsMessage: java.lang.String getMessageBody()>",
    "<android.accounts.AccountManager: android.accounts.Account[] getAccounts()>",
    "<android.telephony.gsm.SmsManager: android.telephony.gsm.SmsManager getDefault()>",
    "<android.telephony.SmsMessage: android.telephony.SmsMessage createFromPdu(byte[])>",
    "<java.net.URLDecoder: java.lang.String decode(java.lang.String,java.lang.String)>",
    "<javax.crypto.Cipher: javax.crypto.Cipher getInstance(java.lang.String)>",
    "<android.content.Context: java.lang.Object getSystemService(java.lang.String)>",
    "<android.preference.PreferenceManager: android.content.SharedPreferences getDefaultSharedPreferences(android.content.Context)>",
    "<org.json.JSONObject: java.lang.String getString(java.lang.String)>",
    "<android.accounts.AccountManager: android.accounts.AccountManager get(android.content.Context)>",
    "<android.database.Cursor: java.lang.String getString(int)>",
    "<java.io.File: void <init>",
    "<java.lang.String: byte[] getBytes()>",
    "<android.content.res.AssetManager: java.io.InputStream open(java.lang.String)>",
    "<java.net.HttpURLConnection: java.io.InputStream getInputStream()>",
    "<android.telephony.TelephonyManager: java.lang.String getLine1Number()>",
    "<android.content.Intent: android.os.Bundle getExtras()>",
    "<android.os.Bundle: java.lang.Object get(java.lang.String)>",
    "<android.telephony.TelephonyManager: java.lang.String getSubscriberId()>",
  )

  override val sinkList = List(
    "<java.net.URL: void <init>",
    "<java.net.URL: java.net.URLConnection openConnection()>",
    "<android.net.Uri: android.net.Uri parse(java.lang.String)>",
    "<android.content.res.AssetManager: java.io.InputStream open(java.lang.String)>",
    "<java.net.HttpURLConnection: java.io.InputStream getInputStream()>",
    "<java.io.FileOutputStream: void <init>",
    "<java.lang.String: java.lang.String substring(int,int)>",
    "<android.os.AsyncTask: android.os.AsyncTask execute(java.lang.Object[])>",
    "<java.lang.Integer: int parseInt(java.lang.String,int)>",
    "<org.apache.http.client.HttpClient: org.apache.http.HttpResponse execute(org.apache.http.client.methods.HttpUriRequest)>",
    "<android.app.AlarmManager: void setRepeating(int,long,long,android.app.PendingIntent)>",
    "<android.os.AsyncTask: void onPostExecute(java.lang.Object)>",
    "<android.app.Activity: void onCreate(android.os.Bundle)>",
    "<android.content.SharedPreferences$Editor: boolean commit()>",
    "<android.content.Context: android.content.ComponentName startService(android.content.Intent)>",
    "<android.telephony.gsm.SmsManager: void sendMultipartTextMessage(java.lang.String,java.lang.String,java.util.ArrayList,java.util.ArrayList,java.util.ArrayList)>",
    "<android.content.Intent: android.content.Intent setDataAndType(android.net.Uri,java.lang.String)>",
    "<java.io.OutputStream: void write(byte[],int,int)>",
  )
}