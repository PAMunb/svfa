package br.unb.cic.metrics

case class Metrics(
  var truePositives: Int = 0,
  var falsePositives: Int = 0,
  var falseNegatives: Int = 0,
  var trueNegatives: Int = 0,
  var passedTests: Int = 0,
  var failedTests: Int = 0,
  var expected: Int = 0,
  var found: Int = 0
)
