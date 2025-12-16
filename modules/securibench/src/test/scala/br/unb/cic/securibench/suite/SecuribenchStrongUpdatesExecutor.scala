package br.unb.cic.securibench.suite

import br.unb.cic.securibench.SecuribenchTestExecutor

class SecuribenchStrongUpdatesExecutor extends SecuribenchTestExecutor {
  override def basePackage: String = "securibench.micro.strong_updates"
  override def entryPointMethod: String = "doGet"
}
