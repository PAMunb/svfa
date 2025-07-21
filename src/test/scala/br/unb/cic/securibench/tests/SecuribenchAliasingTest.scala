package br.unb.cic.securibench.tests

import br.unb.cic.securibench.SecuribenchTest

class SecuribenchAliasingTest extends SecuribenchTest {
   def basePackage(): String = "securibench.micro.aliasing"

   def entryPointMethod(): String = "doGet"
}
