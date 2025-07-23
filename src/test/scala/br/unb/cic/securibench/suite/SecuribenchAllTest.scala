package br.unb.cic.securibench.suite

import br.unb.cic.securibench.SecuribenchSuiteTest

class SecuribenchAllTest extends SecuribenchSuiteTest {
   def basePackage(): String = "securibench.micro"

   def entryPointMethod(): String = "doGet"
} 