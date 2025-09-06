package br.unb.cic.android.specs

import br.unb.cic.android.TaintBenchSpec

trait _ExampleSpec extends TaintBenchSpec {
//  URL https://github.com/TaintBench/roidsec/blob/master/README.md
  override val sourceList = List(
    "<android.telephony.SmsManager: android.telephony.SmsManager getDefault()>",
  )

  override val sinkList = List(
    "<java.io.File: boolean delete()>",
  )
}