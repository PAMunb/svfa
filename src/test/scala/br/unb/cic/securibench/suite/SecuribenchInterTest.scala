package br.unb.cic.securibench.suite

import br.unb.cic.securibench.SecuribenchSuiteTest

class SecuribenchInterTest extends SecuribenchSuiteTest {
   def basePackage(): String = "securibench.micro.inter"

   def entryPointMethod(): String = "doGet"
} 