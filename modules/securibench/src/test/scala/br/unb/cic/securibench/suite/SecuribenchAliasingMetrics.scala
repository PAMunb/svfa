package br.unb.cic.securibench.suite

import br.unb.cic.securibench.SecuribenchMetricsComputer

class SecuribenchAliasingMetrics extends SecuribenchMetricsComputer {
  override def basePackage: String = "securibench.micro.aliasing"
}
