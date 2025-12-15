package br.unb.cic.soot.svfa.jimple.dsl

import br.unb.cic.soot.svfa.jimple.rules.RuleAction
import soot.jimple._
import soot.toolkits.scalar.SimpleLocalDefs
import soot.{ArrayType, Local, SootMethod, Value, jimple}
import com.typesafe.scalalogging.LazyLogging

/**
 * Standalone implementations of DSL rule actions.
 * These actions define how taint flows through specific method calls.
 */
object RuleActions extends LazyLogging {

  /**
   * Context interface that provides access to SVFA operations.
   * This allows rule actions to be independent while still accessing necessary functionality.
   */
  trait SVFAContext {
    def createNode(method: SootMethod, stmt: soot.Unit): br.unb.cic.soot.graph.GraphNode
    def updateGraph(source: br.unb.cic.soot.graph.GraphNode, target: br.unb.cic.soot.graph.GraphNode): Boolean
    def hasBaseObject(expr: InvokeExpr): Boolean
    def getBaseObject(expr: InvokeExpr): Value
  }

  /**
   * Base trait for rule actions that need access to SVFA context.
   */
  trait ContextAwareRuleAction extends RuleAction {
    def applyWithContext(
        sootMethod: SootMethod,
        invokeStmt: jimple.Stmt,
        localDefs: SimpleLocalDefs,
        context: SVFAContext
    ): Unit

    // Default implementation delegates to context-aware version
    override def apply(
        sootMethod: SootMethod,
        invokeStmt: jimple.Stmt,
        localDefs: SimpleLocalDefs
    ): Unit = {
      // This should not be called directly - use applyWithContext instead
      throw new UnsupportedOperationException("Use applyWithContext for context-aware rule actions")
    }
  }

  /**
   * Creates an edge from the definition of a method argument to the base object.
   * 
   * Example: virtualinvoke r3.<StringBuilder: StringBuilder append(String)>(r1)
   * Creates edge from definitions of r1 to definitions of r3.
   * 
   * @param from The argument index to copy from
   */
  case class CopyFromMethodArgumentToBaseObject(from: Int) extends ContextAwareRuleAction {
    def applyWithContext(
        sootMethod: SootMethod,
        invokeStmt: jimple.Stmt,
        localDefs: SimpleLocalDefs,
        context: SVFAContext
    ): Unit = {
      
      var srcArg: Value = null
      var expr: InvokeExpr = null

      try {
        srcArg = invokeStmt.getInvokeExpr.getArg(from)
        expr = invokeStmt.getInvokeExpr
      } catch {
        case e: Throwable =>
          val invokedMethod =
            if (invokeStmt.getInvokeExpr != null)
              invokeStmt.getInvokeExpr.getMethod.getName
            else ""
          logger.warn(s"Could not execute copy from argument to base object rule for methods: ${sootMethod.getName} $invokedMethod")
          return
      }

      if (context.hasBaseObject(expr)) {
        val base = context.getBaseObject(expr)

        if (base.isInstanceOf[Local]) {
          val localBase = base.asInstanceOf[Local]

          // Create edges: argument definitions -> base object definitions
          localDefs
            .getDefsOfAt(localBase, invokeStmt)
            .forEach(targetStmt => {
              val currentNode = context.createNode(sootMethod, invokeStmt)
              val targetNode = context.createNode(sootMethod, targetStmt)
              context.updateGraph(currentNode, targetNode)
            })

          if (srcArg.isInstanceOf[Local]) {
            val local = srcArg.asInstanceOf[Local]
            // Create edges: argument definitions -> base object definitions
            localDefs
              .getDefsOfAt(local, invokeStmt)
              .forEach(sourceStmt => {
                val sourceNode = context.createNode(sootMethod, sourceStmt)
                localDefs
                  .getDefsOfAt(localBase, invokeStmt)
                  .forEach(targetStmt => {
                    val targetNode = context.createNode(sootMethod, targetStmt)
                    context.updateGraph(sourceNode, targetNode)
                  })
              })
          }
        }
      }
    }
  }

  /**
   * Creates an edge from a method call to a local variable.
   * 
   * Example: $r6 = virtualinvoke r3.<StringBuilder: String toString()>()
   * Creates edge from definitions of r3 to the current statement.
   */
  case class CopyFromMethodCallToLocal() extends ContextAwareRuleAction {
    def applyWithContext(
        sootMethod: SootMethod,
        invokeStmt: jimple.Stmt,
        localDefs: SimpleLocalDefs,
        context: SVFAContext
    ): Unit = {
      val expr = invokeStmt.getInvokeExpr
      var isLocalLeftOpFromAssignStmt = true

      if (invokeStmt.isInstanceOf[jimple.AssignStmt]) {
        val local = invokeStmt.asInstanceOf[jimple.AssignStmt].getLeftOp
        if (!local.isInstanceOf[Local]) {
          isLocalLeftOpFromAssignStmt = false
        }
      }

      if (context.hasBaseObject(expr) && isLocalLeftOpFromAssignStmt) {
        val base = context.getBaseObject(expr)
        if (base.isInstanceOf[Local]) {
          val localBase = base.asInstanceOf[Local]
          localDefs
            .getDefsOfAt(localBase, invokeStmt)
            .forEach(source => {
              val sourceNode = context.createNode(sootMethod, source)
              val targetNode = context.createNode(sootMethod, invokeStmt)
              context.updateGraph(sourceNode, targetNode)
            })
        }
      }
    }
  }

  /**
   * Creates an edge from the definitions of a method argument to the assignment statement.
   * 
   * Example: $r12 = virtualinvoke $r11.<StringBuilder: StringBuilder append(String)>(r6)
   * Creates edge from definitions of r6 to the current statement.
   * 
   * @param from The argument index to copy from
   */
  case class CopyFromMethodArgumentToLocal(from: Int) extends ContextAwareRuleAction {
    def applyWithContext(
        sootMethod: SootMethod,
        invokeStmt: jimple.Stmt,
        localDefs: SimpleLocalDefs,
        context: SVFAContext
    ): Unit = {
      val srcArg = invokeStmt.getInvokeExpr.getArg(from)
      
      if (srcArg.isInstanceOf[Local]) {
        val local = srcArg.asInstanceOf[Local]
        val targetStmt = invokeStmt
        localDefs
          .getDefsOfAt(local, targetStmt)
          .forEach(sourceStmt => {
            val source = context.createNode(sootMethod, sourceStmt)
            val target = context.createNode(sootMethod, targetStmt)
            context.updateGraph(source, target)
          })
      }
    }
  }

  /**
   * Creates edges between the definitions of method arguments.
   * 
   * Example: System.arraycopy(l1, _, l2, _)
   * Creates edge from definitions of l1 to definitions of l2.
   * 
   * @param from The source argument index
   * @param target The target argument index
   */
  case class CopyBetweenArgs(from: Int, target: Int) extends ContextAwareRuleAction {
    def applyWithContext(
        sootMethod: SootMethod,
        invokeStmt: jimple.Stmt,
        localDefs: SimpleLocalDefs,
        context: SVFAContext
    ): Unit = {
      val srcArg = invokeStmt.getInvokeExpr.getArg(from)
      val destArg = invokeStmt.getInvokeExpr.getArg(target)
      
      if (srcArg.isInstanceOf[Local] && destArg.isInstanceOf[Local]) {
        localDefs
          .getDefsOfAt(srcArg.asInstanceOf[Local], invokeStmt)
          .forEach(sourceStmt => {
            val sourceNode = context.createNode(sootMethod, sourceStmt)
            localDefs
              .getDefsOfAt(destArg.asInstanceOf[Local], invokeStmt)
              .forEach(targetStmt => {
                val targetNode = context.createNode(sootMethod, targetStmt)
                context.updateGraph(sourceNode, targetNode)
              })
          })
      }
    }
  }
}
