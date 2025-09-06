package br.unb.cic.securibench.suite

import br.unb.cic.securibench.SecuribenchRuntimeTest

class SecuribenchCollectionsTest extends SecuribenchRuntimeTest {
   def basePackage(): String = "securibench.micro.collections"

   def entryPointMethod(): String = "doGet"
} 