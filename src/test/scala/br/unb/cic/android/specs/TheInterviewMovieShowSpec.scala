package br.unb.cic.android.specs

import br.unb.cic.android.TaintBenchSpec

trait TheInterviewMovieShowSpec extends TaintBenchSpec {
//  URL https://github.com/TaintBench/the_interview_movieshow/blob/master/README.md
  override val sourceList = List(
    "<android.os.Environment: java.io.File getExternalStorageDirectory()>",
    "<java.net.URL: java.io.InputStream openStream()>",
    "<java.lang.Integer: int parseInt(java.lang.String)>",
    "<java.net.URLConnection: int getContentLength()>",
    "<java.io.File: void <init>",
    "<java.io.File: java.lang.String getPath()>",
  )

  override val sinkList = List(
    "<android.app.ProgressDialog: void setMessage(java.lang.CharSequence)>",
    "<android.app.ProgressDialog: void setMax(int)>",
    "<java.lang.String: boolean startsWith(java.lang.String)>",
    "<android.app.ProgressDialog: void setIndeterminate(boolean)>",
    "<java.net.URL: void <init>",
    "<java.net.URL: java.net.URLConnection openConnection()>",
    "<java.io.FileOutputStream: void <init>",
    "<android.app.ProgressDialog: void setProgress(int)>",
    "<android.widget.Toast: android.widget.Toast makeText(android.content.Context,java.lang.CharSequence,int)>",
    "<java.lang.Integer: int parseInt(java.lang.String)>",
    "<android.app.Activity: void onCreate(android.os.Bundle)>",
    "<android.app.ProgressDialog: void setProgressStyle(int)>",
    "<android.content.Intent: android.content.Intent setDataAndType(android.net.Uri,java.lang.String)>",
    "<com.movieshow.down.Badaccents: void startActivity(android.content.Intent)>",
    "<android.util.Log: int e(java.lang.String,java.lang.String)>",
    "<java.io.OutputStream: void write(byte[],int,int)>",
  )
}