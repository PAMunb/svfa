package br.unb.cic.securibench.tests

import br.unb.cic.securibench.SecuribenchDynamicTest

class SecuribenchReflectionTest extends SecuribenchDynamicTest {
   def basePackage(): String = "securibench.micro.reflection"

   def entryPointMethod(): String = "doGet"
} 