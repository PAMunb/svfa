package br.unb.cic.securibench.tests

import br.unb.cic.securibench.SecuribenchDynamicTest

class SecuribenchSessionTest extends SecuribenchDynamicTest {
   def basePackage(): String = "securibench.micro.session"

   def entryPointMethod(): String = "doGet"
} 