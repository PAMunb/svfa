package br.unb.cic.securibench.tests

import br.unb.cic.securibench.SecuribenchDynamicTest

class SecuribenchAliasingTest extends SecuribenchDynamicTest {
   def basePackage(): String = "securibench.micro.aliasing"

   def entryPointMethod(): String = "doGet"
}
