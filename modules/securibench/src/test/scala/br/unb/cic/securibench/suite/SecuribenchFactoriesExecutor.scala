package br.unb.cic.securibench.suite

import br.unb.cic.securibench.SecuribenchTestExecutor

class SecuribenchFactoriesExecutor extends SecuribenchTestExecutor {
  override def basePackage: String = "securibench.micro.factories"
  override def entryPointMethod: String = "doGet"
}
