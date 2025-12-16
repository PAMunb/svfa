package br.unb.cic.securibench.suite

import br.unb.cic.securibench.SecuribenchTestExecutor

/**
 * Phase 1: Execute Inter tests and save results
 * 
 * Usage: sbt "project securibench" "testOnly *SecuribenchInterExecutor"
 */
class SecuribenchInterExecutor extends SecuribenchTestExecutor {
  def basePackage(): String = "securibench.micro.inter"
  def entryPointMethod(): String = "doGet"
}
