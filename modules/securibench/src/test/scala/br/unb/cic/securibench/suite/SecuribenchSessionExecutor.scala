package br.unb.cic.securibench.suite

import br.unb.cic.securibench.SecuribenchTestExecutor

class SecuribenchSessionExecutor extends SecuribenchTestExecutor {
  override def basePackage: String = "securibench.micro.session"
  override def entryPointMethod: String = "doGet"
}
