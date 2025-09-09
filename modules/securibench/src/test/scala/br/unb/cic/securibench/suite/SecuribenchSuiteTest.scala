package br.unb.cic.securibench.suite

import br.unb.cic.securibench.SecuribenchRuntimeTest

class SecuribenchSuiteTest extends SecuribenchRuntimeTest {
  def basePackage(): String = "securibench.micro"

  def entryPointMethod(): String = "doGet"
}
