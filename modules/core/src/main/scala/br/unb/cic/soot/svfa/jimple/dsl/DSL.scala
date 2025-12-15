package br.unb.cic.soot.svfa.jimple.dsl

import br.unb.cic.soot.svfa.SVFA

trait DSL {
  this: SVFA =>
  def code(): String = {
    """
    rule arrayCopy =
      if NamedMethodRule(className: "java.lang.System", methodName: "arraycopy")
        then CopyBetweenArgs(from: 0, target: 2)

    rule stringBuilderToString =
      if NamedMethodRule(className: "java.lang.StringBuilder", methodName: "toString")
        then CopyFromMethodCallToLocal()

    rule stringBuilderAppend =
      if NamedMethodRule(className: "java.lang.StringBuilder", methodName: "append")
        then [
          CopyFromMethodArgumentToBaseObject(from: 0),
          CopyFromMethodArgumentToLocal(from: 0),
          CopyFromMethodCallToLocal()
        ]

    rule stringBufferAppend =
      if NamedMethodRule(className: "java.lang.StringBuffer", methodName: "append")
        then CopyFromMethodArgumentToBaseObject(from: 0)

    rule stringBufferInit =
      if NamedMethodRule(className: "java.lang.StringBuffer", methodName: "<init>")
        then CopyFromMethodArgumentToBaseObject(from: 0)

    rule fileInit =
      if NamedMethodRule(className: "java.io.File", methodName: "<init>")
        then CopyFromMethodArgumentToBaseObject(from: 0)

    rule entrySetOfMap =
      if NamedMethodRule(className: "java.util.Map", methodName: "entrySet")
        then CopyFromMethodCallToLocal()

    rule getKeyOfMapEntry =
      if NamedMethodRule(className: "java.util.Map$Entry", methodName: "getKey")
        then CopyFromMethodCallToLocal()

    rule getValueOfMapEntry =
      if NamedMethodRule(className: "java.util.Map$Entry", methodName: "getValue")
        then CopyFromMethodCallToLocal()

    rule iteratorOfSet =
      if NamedMethodRule(className: "java.util.Set", methodName: "iterator")
        then CopyFromMethodCallToLocal()

    rule iteratorNext =
      if NamedMethodRule(className: "java.util.Iterator", methodName: "next")
        then CopyFromMethodCallToLocal()

    rule nextElementOfEnumeration =
      if NamedMethodRule(className: "java.util.Enumeration", methodName: "nextElement")
        then CopyFromMethodCallToLocal()

    rule stringBufferToString =
      if NamedMethodRule(className: "java.lang.StringBuffer", methodName: "toString")
        then CopyFromMethodCallToLocal()

    rule stringConcat =
      if NamedMethodRule(className: "java.lang.String", methodName: "concat")
        then CopyFromMethodCallToLocal()

    rule cookieGetName =
      if NamedMethodRule(className: "javax.servlet.http.Cookie", methodName: "getName")
        then CopyFromMethodCallToLocal()

    rule cookieGetValue =
      if NamedMethodRule(className: "javax.servlet.http.Cookie", methodName: "getValue")
        then CopyFromMethodCallToLocal()

    rule cookieGetComment =
      if NamedMethodRule(className: "javax.servlet.http.Cookie", methodName: "getComment")
        then CopyFromMethodCallToLocal()

    rule setAttributeOfSession =
      if NamedMethodRule(className: "javax.servlet.http.HttpSession", methodName: "setAttribute")
        then CopyFromMethodArgumentToBaseObject(from: 1)

    rule getAttributeOfSession =
      if NamedMethodRule(className: "javax.servlet.http.HttpSession", methodName: "getAttribute")
        then CopyFromMethodCallToLocal()

    rule skipNativeMethods = if NativeRule() then DoNothing()

    rule skipMethodsWithoutActiveBody =
      if MissingActiveBodyRule() then DoNothing()
    """
  }
}
