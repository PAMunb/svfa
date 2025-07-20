package br.unb.cic.securibench.tests

import br.unb.cic.securibench.SecuribenchDynamicTest

class SecuribenchSanitizersTest extends SecuribenchDynamicTest {
   def basePackage(): String = "securibench.micro.sanitizers"

   def entryPointMethod(): String = "doGet"
} 