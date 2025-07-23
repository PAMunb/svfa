package br.unb.cic.securibench.suite

import br.unb.cic.securibench.SecuribenchRuntimeTest

class SecuribenchAliasingTest extends SecuribenchRuntimeTest {
   def basePackage(): String = "securibench.micro.aliasing"

   def entryPointMethod(): String = "doGet"
}
