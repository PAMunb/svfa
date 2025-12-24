package br.unb.cic.securibench.suite

import br.unb.cic.securibench.SecuribenchMetricsComputer

/**
 * Phase 2: Compute metrics for Inter tests from saved results
 * 
 * Usage: sbt "project securibench" "testOnly *SecuribenchInterMetrics"
 */
class SecuribenchInterMetrics extends SecuribenchMetricsComputer {
  def basePackage(): String = "securibench.micro.inter"
}
