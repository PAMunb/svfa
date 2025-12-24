package br.unb.cic.securibench.suite

import br.unb.cic.securibench.SecuribenchTestExecutor

class SecuribenchPredExecutor extends SecuribenchTestExecutor {
  override def basePackage: String = "securibench.micro.pred"
  override def entryPointMethod: String = "doGet"
}
