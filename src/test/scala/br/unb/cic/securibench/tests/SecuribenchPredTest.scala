package br.unb.cic.securibench.tests

import br.unb.cic.securibench.SecuribenchDynamicTest

class SecuribenchPredTest extends SecuribenchDynamicTest {
   def basePackage(): String = "securibench.micro.pred"

   def entryPointMethod(): String = "doGet"
} 