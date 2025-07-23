package br.unb.cic.securibench.suite

import br.unb.cic.securibench.SecuribenchSuiteTest

class SecuribenchPredTest extends SecuribenchSuiteTest {
   def basePackage(): String = "securibench.micro.pred"

   def entryPointMethod(): String = "doGet"
} 