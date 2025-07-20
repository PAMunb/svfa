package br.unb.cic.securibench.tests

import br.unb.cic.securibench.SecuribenchTest

class SecuribenchInterTest extends SecuribenchTest {
   def basePackage(): String = "securibench.micro.inter"

   def entryPointMethod(): String = "doGet"
} 