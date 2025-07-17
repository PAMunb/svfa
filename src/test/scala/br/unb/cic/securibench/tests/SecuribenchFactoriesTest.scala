package br.unb.cic.securibench.tests

import br.unb.cic.securibench.SecuribenchDynamicTest

class SecuribenchFactoriesTest extends SecuribenchDynamicTest {
   def basePackage(): String = "securibench.micro.factories"

   def entryPointMethod(): String = "doGet"
} 