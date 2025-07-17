package br.unb.cic.securibench.tests

import br.unb.cic.securibench.SecuribenchDynamicTest

class SecuribenchStrongUpdatesTest extends SecuribenchDynamicTest {
   def basePackage(): String = "securibench.micro.strong_updates"

   def entryPointMethod(): String = "doGet"
} 