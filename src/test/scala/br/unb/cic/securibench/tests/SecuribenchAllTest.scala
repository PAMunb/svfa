package br.unb.cic.securibench.tests

import br.unb.cic.securibench.SecuribenchDynamicTest

class SecuribenchAllTest extends SecuribenchDynamicTest {
   def basePackage(): String = "securibench.micro"

   def entryPointMethod(): String = "doGet"
} 