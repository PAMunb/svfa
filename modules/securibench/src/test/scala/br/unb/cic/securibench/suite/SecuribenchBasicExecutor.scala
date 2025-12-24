package br.unb.cic.securibench.suite

import br.unb.cic.securibench.SecuribenchTestExecutor

class SecuribenchBasicExecutor extends SecuribenchTestExecutor {
  def basePackage(): String = "securibench.micro.basic"
  def entryPointMethod(): String = "doGet"
}
