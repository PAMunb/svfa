package br.unb.cic.securibench.suite

import br.unb.cic.securibench.SecuribenchRuntimeTest

class SecuribenchInterTest extends SecuribenchRuntimeTest {
   def basePackage(): String = "securibench.micro.inter"

   def entryPointMethod(): String = "doGet"
} 