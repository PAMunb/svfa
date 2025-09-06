package br.unb.cic.securibench.suite

import br.unb.cic.securibench.SecuribenchRuntimeTest

class SecuribenchReflectionTest extends SecuribenchRuntimeTest {
  def basePackage(): String = "securibench.micro.reflection"

  def entryPointMethod(): String = "doGet"
}
