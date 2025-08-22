package br.unb.cic.android.specs

import br.unb.cic.android.TaintBenchSpec

trait RepaneSpec extends TaintBenchSpec {
//  URL https://github.com/TaintBench/repane/blob/master/README.md
  override val sourceList = List(
    "<android.os.Environment: java.io.File getExternalStorageDirectory()>",
    "<java.io.File: boolean delete()>",
    "<java.security.MessageDigest: byte[] digest(byte[])>",
    "<java.lang.Integer: int parseInt(java.lang.String)>",
    "<java.security.MessageDigest: java.security.MessageDigest getInstance(java.lang.String)>",
    "<java.io.File: void <init>",
    "<java.lang.Class: java.lang.String getName()>",
    "<java.lang.String: byte[] getBytes()>",
    "<android.content.res.AssetManager: java.io.InputStream open(java.lang.String)>",
    "<java.io.File: java.lang.String getPath()>",
    "<java.util.ArrayList: java.lang.Object get(int)>",
  )

  override val sinkList = List(
    "<java.io.File: boolean delete()>",
    "<android.widget.TextView: void setText(java.lang.CharSequence)>",
    "<android.widget.ProgressBar: void setProgress(int)>",
    "<android.widget.TextView: void setText(int)>",
    "<android.content.res.AssetManager: java.io.InputStream open(java.lang.String)>",
    "<java.io.FileOutputStream: void write(byte[],int,int)>",
    "<android.widget.Toast: void setGravity(int,int,int)>",
    "<java.io.FileOutputStream: void <init>",
    "<java.lang.String: java.lang.String substring(int,int)>",
    "<android.content.Context: void startActivity(android.content.Intent)>",
    "<android.content.Intent: android.content.Intent setFlags(int)>",
    "<android.widget.Toast: android.widget.Toast makeText(android.content.Context,java.lang.CharSequence,int)>",
    "<java.lang.Integer: int parseInt(java.lang.String)>",
    "<android.app.Activity: void onCreate(android.os.Bundle)>",
    "<android.content.Intent: android.content.Intent setDataAndType(android.net.Uri,java.lang.String)>",
    "<android.view.View: void setVisibility(int)>",
    "<android.util.Log: int e(java.lang.String,java.lang.String)>",
  )
}