package br.unb.cic.securibench.tests

import br.unb.cic.securibench.SecuribenchDynamicTest

class SecuribenchCollectionsTest extends SecuribenchDynamicTest {
   def basePackage(): String = "securibench.micro.collections"

   def entryPointMethod(): String = "doGet"
} 