package br.unb.cic.android.specs

import br.unb.cic.android.TaintBenchSpec

trait DsencryptSampSpec extends TaintBenchSpec {
//  URL https://github.com/TaintBench/dsencrypt_samp/blob/master/README.md
  override val sourceList = List(
    "<java.io.File: boolean delete()>",
    "<dalvik.system.DexFile: java.lang.Class loadClass(java.lang.String,java.lang.ClassLoader)>",
    "<android.content.res.Resources: android.util.DisplayMetrics getDisplayMetrics()>",
    "<java.lang.Package: java.lang.String getName()>",
    "<javax.crypto.Cipher: byte[] doFinal(byte[])>",
    "<java.lang.Class: java.lang.Package getPackage()>",
    "<java.lang.Class: java.lang.reflect.Field getDeclaredField(java.lang.String)>",
    "<java.io.File: void <init>",
    "<java.lang.Class: java.lang.reflect.Method getDeclaredMethod(java.lang.String,java.lang.Class[])>",
    "<java.lang.String: byte[] getBytes()>",
    "<android.content.res.AssetManager: java.io.InputStream open(java.lang.String)>",
    "<java.lang.Integer: int parseInt(java.lang.String,int)>",
    "<java.lang.reflect.Field: java.lang.Object get(java.lang.Object)>",
    "<java.lang.Class: java.lang.reflect.Constructor getConstructor(java.lang.Class[])>",
    "<java.lang.reflect.Method: java.lang.Object invoke(java.lang.Object,java.lang.Object[])>",
    "<android.content.res.Resources: android.content.res.Configuration getConfiguration()>",
    "<javax.crypto.Cipher: javax.crypto.Cipher getInstance(java.lang.String)>",
    "<java.lang.Class: java.lang.reflect.Method getMethod(java.lang.String,java.lang.Class[])>",
  )

  override val sinkList = List(
    "<java.io.File: boolean delete()>",
    "<java.lang.ProcessBuilder: java.lang.Process start()>",
    "<java.lang.Integer: int parseInt(java.lang.String,int)>",
    "<java.lang.reflect.Method: java.lang.Object invoke(java.lang.Object,java.lang.Object[])>",
    "<android.content.res.AssetManager: java.io.InputStream open(java.lang.String)>",
    "<java.io.FileOutputStream: void <init>",
    "<java.io.FileOutputStream: void write(byte[])>",
    "<java.lang.Class: java.lang.Class forName(java.lang.String)>",
    "<java.lang.reflect.Field: void set(java.lang.Object,java.lang.Object)>",
  )
}