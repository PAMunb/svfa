package br.unb.cic.android.specs

import br.unb.cic.android.TaintBenchSpec

trait ExprespamSpec extends TaintBenchSpec {
//  URL https://github.com/TaintBench/exprespam/blob/master/README.md
  override val sourceList = List(
    "<android.telephony.TelephonyManager: java.lang.String getLine1Number()>",
    "<frhfsd.siksdk.ujdsfjkfsd.WrehifsdkjsActivity: android.database.Cursor managedQuery(android.net.Uri,java.lang.String[],java.lang.String,java.lang.String[],java.lang.String)>",
    "<org.json.JSONObject: int getInt(java.lang.String)>",
    "<android.database.Cursor: java.lang.String getString(int)>",
  )

  override val sinkList = List(
    "<android.app.ProgressDialog: void setMessage(java.lang.CharSequence)>",
    "<android.widget.Toast: android.widget.Toast makeText(android.content.Context,java.lang.CharSequence,int)>",
    "<android.app.Activity: void onCreate(android.os.Bundle)>",
    "<android.os.Handler: boolean sendMessage(android.os.Message)>",
    "<android.app.ProgressDialog: void setProgressStyle(int)>",
    "<org.apache.http.impl.client.DefaultHttpClient: org.apache.http.HttpResponse execute(org.apache.http.client.methods.HttpUriRequest)>",
  )
}