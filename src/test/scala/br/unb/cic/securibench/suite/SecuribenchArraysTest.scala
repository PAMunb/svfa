package br.unb.cic.securibench.suite

import br.unb.cic.securibench.SecuribenchRuntimeTest

class SecuribenchArraysTest extends SecuribenchRuntimeTest {
   def basePackage(): String = "securibench.micro.arrays"

   def entryPointMethod(): String = "doGet"
} 