package br.unb.cic.securibench.suite

import br.unb.cic.securibench.SecuribenchTest

class SecuribenchFactoriesTest extends SecuribenchTest {
   def basePackage(): String = "securibench.micro.factories"

   def entryPointMethod(): String = "doGet"
} 