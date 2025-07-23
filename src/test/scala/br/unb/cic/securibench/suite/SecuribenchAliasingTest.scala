package br.unb.cic.securibench.suite

import br.unb.cic.securibench.SecuribenchSuiteTest

class SecuribenchAliasingTest extends SecuribenchSuiteTest {
   def basePackage(): String = "securibench.micro.aliasing"

   def entryPointMethod(): String = "doGet"
}
