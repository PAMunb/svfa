package br.unb.cic.securibench.tests

import br.unb.cic.securibench.SecuribenchDynamicTest

class SecuribenchBasicTest extends SecuribenchDynamicTest {
   def basePackage(): String = "securibench.micro.basic"

   def entryPointMethod(): String = "doGet"
} 