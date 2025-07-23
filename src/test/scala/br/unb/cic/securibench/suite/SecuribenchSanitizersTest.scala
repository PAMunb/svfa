package br.unb.cic.securibench.suite

import br.unb.cic.securibench.SecuribenchSuiteTest

class SecuribenchSanitizersTest extends SecuribenchSuiteTest {
   def basePackage(): String = "securibench.micro.sanitizers"

   def entryPointMethod(): String = "doGet"
} 