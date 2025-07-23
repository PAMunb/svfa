package br.unb.cic.securibench.suite

import br.unb.cic.securibench.SecuribenchTest

class SecuribenchAllTest extends SecuribenchTest {
   def basePackage(): String = "securibench.micro"

   def entryPointMethod(): String = "doGet"
} 