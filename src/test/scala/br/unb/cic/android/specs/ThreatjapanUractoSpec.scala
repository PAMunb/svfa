package br.unb.cic.android.specs

import br.unb.cic.android.TaintBenchSpec

trait ThreatjapanUractoSpec extends TaintBenchSpec {
//  URL https://github.com/TaintBench/threatjapan_uracto/blob/master/README.md
  override val sourceList = List(
    "<java.lang.String: byte[] getBytes(java.lang.String)>",
    "<java.io.BufferedReader: java.lang.String readLine()>",
    "<java.io.File: java.lang.String getName()>",
    "<android.content.ContentResolver: android.database.Cursor query(android.net.Uri,java.lang.String[],java.lang.String,java.lang.String[],java.lang.String)>",
    "<java.io.ByteArrayOutputStream: byte[] toByteArray()>",
    "<java.nio.charset.Charset: java.lang.String name()>",
    "<android.database.Cursor: java.lang.String getString(int)>",
    "<java.io.File: java.lang.String getAbsolutePath()>",
    "<java.io.File: void <init>",
    "<java.io.File: java.lang.String getPath()>",
    "<android.os.Environment: java.io.File getExternalStorageDirectory()>",
    "<android.app.AlertDialog$Builder: android.app.AlertDialog show()>",
    "<java.io.File: java.io.File getParentFile()>",
    "<org.apache.http.HttpEntity: java.io.InputStream getContent()>",
    "<org.apache.http.util.ByteArrayBuffer: byte[] buffer()>",
    "<java.io.FileInputStream: void <init>",
  )

  override val sinkList = List(
    "<android.app.ProgressDialog: void setMessage(java.lang.CharSequence)>",
    "<org.apache.http.params.HttpConnectionParams: void setConnectionTimeout(org.apache.http.params.HttpParams,int)>",
    "<java.io.OutputStream: void write(byte[])>",
    "<org.apache.http.impl.client.DefaultHttpClient: org.apache.http.HttpResponse execute(org.apache.http.client.methods.HttpUriRequest)>",
    "<java.io.FileOutputStream: void <init>",
    "<java.lang.String: java.lang.String substring(int,int)>",
    "<java.io.OutputStreamWriter: void <init>",
    "<java.io.PrintWriter: java.io.PrintWriter append(java.lang.CharSequence)>",
    "<android.os.Handler: boolean sendEmptyMessage(int)>",
    "<android.util.Log: int d(java.lang.String,java.lang.String)>",
    "<org.apache.http.params.HttpConnectionParams: void setSoTimeout(org.apache.http.params.HttpParams,int)>",
    "<android.app.Activity: void onCreate(android.os.Bundle)>",
    "<android.app.ProgressDialog: void setProgressStyle(int)>",
    "<java.io.OutputStream: void write(byte[],int,int)>",
  )
}