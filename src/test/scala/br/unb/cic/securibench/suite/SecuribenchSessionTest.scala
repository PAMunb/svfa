package br.unb.cic.securibench.suite

import br.unb.cic.securibench.SecuribenchSuiteTest

class SecuribenchSessionTest extends SecuribenchSuiteTest {
   def basePackage(): String = "securibench.micro.session"

   def entryPointMethod(): String = "doGet"
} 