package br.unb.cic.securibench.suite

import br.unb.cic.securibench.SecuribenchTest

class SecuribenchPredTest extends SecuribenchTest {
   def basePackage(): String = "securibench.micro.pred"

   def entryPointMethod(): String = "doGet"
} 