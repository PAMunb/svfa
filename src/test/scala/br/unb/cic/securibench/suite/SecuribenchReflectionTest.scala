package br.unb.cic.securibench.suite

import br.unb.cic.securibench.SecuribenchSuiteTest

class SecuribenchReflectionTest extends SecuribenchSuiteTest {
   def basePackage(): String = "securibench.micro.reflection"

   def entryPointMethod(): String = "doGet"
} 