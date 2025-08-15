package br.unb.cic.android.specs

import br.unb.cic.android.TaintBenchSpec

trait SmsSendLockerQqmagicSpec extends TaintBenchSpec {
//  URL https://github.com/TaintBench/sms_send_locker_qqmagic/blob/master/README.md
  override val sourceList = List(
    "<android.telephony.SmsManager: android.telephony.SmsManager getDefault()>",
    "<java.io.BufferedReader: java.lang.String readLine()>",
    "<android.content.Intent: java.lang.String getStringExtra(java.lang.String)>",
    "<javax.crypto.Cipher: byte[] doFinal(byte[])>",
    "<java.lang.Runtime: java.lang.Runtime getRuntime()>",
    "<java.lang.String: byte[] getBytes()>",
    "<android.net.ConnectivityManager: android.net.NetworkInfo getActiveNetworkInfo()>",
    "<android.telephony.SmsMessage: java.lang.String getDisplayMessageBody()>",
    "<android.telephony.SmsMessage: java.lang.String getDisplayOriginatingAddress()>",
    "<android.content.Intent: java.lang.String getAction()>",
    "<java.lang.Integer: int parseInt(java.lang.String,int)>",
    "<android.content.Intent: android.os.Bundle getExtras()>",
    "<android.os.Bundle: java.lang.Object get(java.lang.String)>",
    "<java.lang.Integer: int parseInt(java.lang.String)>",
    "<android.telephony.gsm.SmsManager: android.telephony.gsm.SmsManager getDefault()>",
    "<java.lang.Throwable: java.lang.String getMessage()>",
    "<android.telephony.SmsMessage: android.telephony.SmsMessage createFromPdu(byte[])>",
    "<javax.crypto.Cipher: javax.crypto.Cipher getInstance(java.lang.String)>",
    "<android.widget.EditText: android.text.Editable getText()>",
    "<android.content.Context: java.lang.Object getSystemService(java.lang.String)>",
  )

  override val sinkList = List(
    "<android.widget.TextView: void setText(java.lang.CharSequence)>",
    "<android.content.Intent: android.content.Intent putExtra(java.lang.String,java.lang.String)>",
    "<android.content.Intent: android.content.Intent setAction(java.lang.String)>",
    "<android.telephony.SmsManager: void sendTextMessage(java.lang.String,java.lang.String,java.lang.String,android.app.PendingIntent,android.app.PendingIntent)>",
    "<android.content.Intent: android.content.Intent setPackage(java.lang.String)>",
    "<android.content.Intent: android.content.Intent putExtra(java.lang.String,java.lang.String[])>",
    "<android.telephony.gsm.SmsManager: void sendTextMessage(java.lang.String,java.lang.String,java.lang.String,android.app.PendingIntent,android.app.PendingIntent)>",
    "<java.lang.String: java.lang.String substring(int,int)>",
    "<java.lang.Integer: int parseInt(java.lang.String,int)>",
    "<java.lang.Integer: int parseInt(java.lang.String)>",
    "<android.app.Activity: void onCreate(android.os.Bundle)>",
    "<android.content.SharedPreferences$Editor: boolean commit()>",
    "<android.content.Context: android.content.ComponentName startService(android.content.Intent)>",
    "<java.lang.Class: java.lang.Class forName(java.lang.String)>",
  )
}