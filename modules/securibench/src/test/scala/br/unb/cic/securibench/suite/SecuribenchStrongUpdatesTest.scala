package br.unb.cic.securibench.suite

import br.unb.cic.securibench.SecuribenchRuntimeTest

class SecuribenchStrongUpdatesTest extends SecuribenchRuntimeTest {
  def basePackage(): String = "securibench.micro.strong_updates"

  def entryPointMethod(): String = "doGet"
}
