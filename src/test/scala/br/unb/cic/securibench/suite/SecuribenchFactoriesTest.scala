package br.unb.cic.securibench.suite

import br.unb.cic.securibench.SecuribenchRuntimeTest

class SecuribenchFactoriesTest extends SecuribenchRuntimeTest {
   def basePackage(): String = "securibench.micro.factories"

   def entryPointMethod(): String = "doGet"
} 