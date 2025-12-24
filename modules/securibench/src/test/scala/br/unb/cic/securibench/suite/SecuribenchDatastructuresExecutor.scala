package br.unb.cic.securibench.suite

import br.unb.cic.securibench.SecuribenchTestExecutor

class SecuribenchDatastructuresExecutor extends SecuribenchTestExecutor {
  override def basePackage: String = "securibench.micro.datastructures"
  override def entryPointMethod: String = "doGet"
}
