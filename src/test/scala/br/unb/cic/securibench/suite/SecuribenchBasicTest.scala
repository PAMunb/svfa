package br.unb.cic.securibench.suite

import br.unb.cic.securibench.SecuribenchTest

class SecuribenchBasicTest extends SecuribenchTest {
   def basePackage(): String = "securibench.micro.basic"

   def entryPointMethod(): String = "doGet"
} 