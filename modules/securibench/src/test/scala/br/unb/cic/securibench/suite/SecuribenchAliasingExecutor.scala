package br.unb.cic.securibench.suite

import br.unb.cic.securibench.SecuribenchTestExecutor

class SecuribenchAliasingExecutor extends SecuribenchTestExecutor {
  override def basePackage: String = "securibench.micro.aliasing"
  override def entryPointMethod: String = "doGet"
}
