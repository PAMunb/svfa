package br.unb.cic.securibench.suite

import br.unb.cic.securibench.SecuribenchTestExecutor

class SecuribenchSanitizersExecutor extends SecuribenchTestExecutor {
  override def basePackage: String = "securibench.micro.sanitizers"
  override def entryPointMethod: String = "doGet"
}
