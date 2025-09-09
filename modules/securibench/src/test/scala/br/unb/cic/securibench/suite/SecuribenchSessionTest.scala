package br.unb.cic.securibench.suite

import br.unb.cic.securibench.SecuribenchRuntimeTest

class SecuribenchSessionTest extends SecuribenchRuntimeTest {
  def basePackage(): String = "securibench.micro.session"

  def entryPointMethod(): String = "doGet"
}
