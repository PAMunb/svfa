package br.unb.cic.securibench.suite

import br.unb.cic.securibench.SecuribenchTestExecutor

class SecuribenchArraysExecutor extends SecuribenchTestExecutor {
  override def basePackage: String = "securibench.micro.arrays"
  override def entryPointMethod: String = "doGet"
}
