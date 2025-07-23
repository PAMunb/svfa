package br.unb.cic.securibench.suite

import br.unb.cic.securibench.SecuribenchRuntimeTest

class SecuribenchAllTest extends SecuribenchRuntimeTest {
   def basePackage(): String = "securibench.micro"

   def entryPointMethod(): String = "doGet"
} 