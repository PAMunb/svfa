package br.unb.cic.securibench.suite

import br.unb.cic.securibench.SecuribenchTestExecutor

class SecuribenchReflectionExecutor extends SecuribenchTestExecutor {
  override def basePackage: String = "securibench.micro.reflection"
  override def entryPointMethod: String = "doGet"
}
