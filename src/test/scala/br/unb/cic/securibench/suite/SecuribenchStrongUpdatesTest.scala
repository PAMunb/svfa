package br.unb.cic.securibench.suite

import br.unb.cic.securibench.SecuribenchSuiteTest

class SecuribenchStrongUpdatesTest extends SecuribenchSuiteTest {
   def basePackage(): String = "securibench.micro.strong_updates"

   def entryPointMethod(): String = "doGet"
} 