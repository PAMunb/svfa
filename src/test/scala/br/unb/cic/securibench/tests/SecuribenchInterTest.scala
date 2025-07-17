package br.unb.cic.securibench.tests

import br.unb.cic.securibench.SecuribenchDynamicTest

class SecuribenchInterTest extends SecuribenchDynamicTest {
   def basePackage(): String = "securibench.micro.inter"

   def entryPointMethod(): String = "doGet"
} 