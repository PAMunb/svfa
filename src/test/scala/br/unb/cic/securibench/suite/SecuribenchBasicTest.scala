package br.unb.cic.securibench.suite

import br.unb.cic.securibench.SecuribenchSuiteTest

class SecuribenchBasicTest extends SecuribenchSuiteTest {
   def basePackage(): String = "securibench.micro.basic"

   def entryPointMethod(): String = "doGet"
} 