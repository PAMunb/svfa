package br.unb.cic.securibench.suite

import br.unb.cic.securibench.SecuribenchSuiteTest

class SecuribenchFactoriesTest extends SecuribenchSuiteTest {
   def basePackage(): String = "securibench.micro.factories"

   def entryPointMethod(): String = "doGet"
} 