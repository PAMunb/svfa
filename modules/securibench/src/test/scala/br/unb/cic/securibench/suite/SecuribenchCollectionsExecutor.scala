package br.unb.cic.securibench.suite

import br.unb.cic.securibench.SecuribenchTestExecutor

class SecuribenchCollectionsExecutor extends SecuribenchTestExecutor {
  override def basePackage: String = "securibench.micro.collections"
  override def entryPointMethod: String = "doGet"
}
