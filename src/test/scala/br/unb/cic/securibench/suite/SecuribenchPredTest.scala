package br.unb.cic.securibench.suite

import br.unb.cic.securibench.SecuribenchRuntimeTest

class SecuribenchPredTest extends SecuribenchRuntimeTest {
   def basePackage(): String = "securibench.micro.pred"

   def entryPointMethod(): String = "doGet"
} 