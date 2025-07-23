package br.unb.cic.securibench.suite

import br.unb.cic.securibench.SecuribenchTest

class SecuribenchSessionTest extends SecuribenchTest {
   def basePackage(): String = "securibench.micro.session"

   def entryPointMethod(): String = "doGet"
} 