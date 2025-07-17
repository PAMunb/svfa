package br.unb.cic.securibench.tests

import br.unb.cic.securibench.SecuribenchDynamicTest

class SecuribenchArraysTest extends SecuribenchDynamicTest {
   def basePackage(): String = "securibench.micro.arrays"

   def entryPointMethod(): String = "doGet"
} 