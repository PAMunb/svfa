package br.unb.cic.securibench.suite

import br.unb.cic.securibench.SecuribenchRuntimeTest

class SecuribenchSanitizersTest extends SecuribenchRuntimeTest {
  def basePackage(): String = "securibench.micro.sanitizers"

  def entryPointMethod(): String = "doGet"
}
