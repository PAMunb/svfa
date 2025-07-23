package br.unb.cic.securibench.suite

import br.unb.cic.securibench.SecuribenchTest

class SecuribenchSanitizersTest extends SecuribenchTest {
   def basePackage(): String = "securibench.micro.sanitizers"

   def entryPointMethod(): String = "doGet"
} 