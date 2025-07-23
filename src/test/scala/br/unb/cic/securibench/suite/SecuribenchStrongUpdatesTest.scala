package br.unb.cic.securibench.suite

import br.unb.cic.securibench.SecuribenchTest

class SecuribenchStrongUpdatesTest extends SecuribenchTest {
   def basePackage(): String = "securibench.micro.strong_updates"

   def entryPointMethod(): String = "doGet"
} 