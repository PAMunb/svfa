package br.unb.cic.securibench.suite

import br.unb.cic.securibench.SecuribenchRuntimeTest

class SecuribenchBasicTest extends SecuribenchRuntimeTest {
  def basePackage(): String = "securibench.micro.basic"

  def entryPointMethod(): String = "doGet"
}
