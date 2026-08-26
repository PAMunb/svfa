package br.unb.cic.soot.svfa.jimple

import java.util
import br.unb.cic.soot.svfa.jimple.rules.RuleAction
import br.unb.cic.soot.graph.{CallSiteCloseLabel, CallSiteLabel, CallSiteOpenLabel, ContextSensitiveRegion, GraphNode, SinkNode, SourceNode, SimpleNode}
import br.unb.cic.soot.svfa.jimple.dsl.{DSL, LanguageParser, RuleActions}
import br.unb.cic.soot.svfa.{SVFA, SourceSinkDef}
import com.typesafe.scalalogging.LazyLogging
import soot.jimple._
import soot.jimple.internal.{AbstractInvokeExpr, JArrayRef, JAssignStmt, JInvokeStmt}
import soot.jimple.spark.ondemand.DemandCSPointsTo
import soot.jimple.spark.pag
import soot.jimple.spark.pag.{AllocNode, PAG}
import soot.jimple.spark.sets.{DoublePointsToSet, HybridPointsToSet, P2SetVisitor}
import soot.toolkits.graph.ExceptionalUnitGraph
import soot.toolkits.scalar.SimpleLocalDefs
import soot.{ArrayType, Local, Scene, SceneTransformer, SootField, SootMethod, Transform, Value, jimple}

import scala.collection.mutable.ListBuffer
import scala.collection.JavaConverters._

/** A Jimple based implementation of SVFA.
  */
abstract class JSVFA
    extends SVFA
    with Analysis
    with FieldSensitiveness
    with ObjectPropagation
    with SourceSinkDef
    with LazyLogging
    with DSL
    with RuleActions.SVFAContext {

  var methods = 0
  val traversedMethods = scala.collection.mutable.Set.empty[SootMethod]
  val allocationSites =
    scala.collection.mutable.HashMap.empty[soot.Value, GraphNode]
  val arrayStores =
    scala.collection.mutable.HashMap.empty[Local, List[soot.Unit]]
  val instanceFieldStoreIndex =
    scala.collection.mutable.HashMap
      .empty[SootField, scala.collection.mutable.HashSet[GraphNode]]
  val languageParser = new LanguageParser(this)

  val methodRules = languageParser.evaluate(code())

  // Implementation of SVFAContext interface for rule actions
  override def hasBaseObject(expr: InvokeExpr): Boolean =
    expr.isInstanceOf[VirtualInvokeExpr] || 
    expr.isInstanceOf[SpecialInvokeExpr] || 
    expr.isInstanceOf[InterfaceInvokeExpr]

  override def getBaseObject(expr: InvokeExpr): Value =
    if (expr.isInstanceOf[VirtualInvokeExpr])
      expr.asInstanceOf[VirtualInvokeExpr].getBase
    else if (expr.isInstanceOf[SpecialInvokeExpr])
      expr.asInstanceOf[SpecialInvokeExpr].getBase
    else
      expr.asInstanceOf[InstanceInvokeExpr].getBase

  /**
   * Applies a rule action with proper context handling.
   * Handles both individual context-aware actions and composed rule actions.
   */
  private def applyRuleWithContext(
      rule: RuleAction,
      caller: SootMethod,
      stmt: jimple.Stmt,
      defs: SimpleLocalDefs
  ): Unit = {
    rule match {
      case contextAware: RuleActions.ContextAwareRuleAction =>
        // Direct context-aware rule action
        contextAware.applyWithContext(caller, stmt, defs, this)
        
      case composed: rules.ComposedRuleAction =>
        // Composed rule action - apply each action with context
        composed.actions.foreach(action => applyRuleWithContext(action, caller, stmt, defs))
        
      case _ =>
        // Regular rule action (e.g., DoNothing)
        rule.apply(caller, stmt, defs)
    }
  }

  def createSceneTransform(): (String, Transform) =
    ("wjtp", new Transform("wjtp.svfa", new Transformer()))

  def initAllocationSites(): Unit = {
    val listener = Scene.v().getReachableMethods.listener()

    while (listener.hasNext) {
      val m = listener.next().method()
      if (m.hasActiveBody) {
        val body = m.getActiveBody
        body.getUnits.forEach(unit => {
          if (unit.isInstanceOf[soot.jimple.AssignStmt]) {
            val right = unit.asInstanceOf[soot.jimple.AssignStmt].getRightOp
            if (
              right.isInstanceOf[NewExpr] || right
                .isInstanceOf[NewArrayExpr] || right
                .isInstanceOf[StringConstant]
            ) {
              allocationSites += (right -> createNode(m, unit))
            }
          } else if (unit.isInstanceOf[soot.jimple.ReturnStmt]) {
            val exp = unit.asInstanceOf[soot.jimple.ReturnStmt].getOp
            if (exp.isInstanceOf[StringConstant]) {
              allocationSites += (exp -> createNode(m, unit))
            }
          }
        })
      }
    }
  }

  class Transformer extends SceneTransformer {
    override def internalTransform(
        phaseName: String,
        options: util.Map[String, String]
    ): Unit = {
      pointsToAnalysis = Scene.v().getPointsToAnalysis
      initAllocationSites()
//      println(allocationSites.foreach(println(_)))
      Scene
        .v()
        .getEntryPoints
        .forEach(method => {
          traverse(method)
          methods = methods + 1
        })
    }
  }

  /**
   * Traverses a method to perform static value flow analysis.
   * 
   * This is the main entry point for analyzing a method's body. It:
   * 1. Checks if the method should be analyzed (not phantom, not already traversed)
   * 2. Sets up the control flow graph and local definitions analysis
   * 3. Processes each statement in the method body according to its type
   * 
   * @param method The method to analyze
   * @param forceNewTraversal If true, re-analyzes even if already traversed
   */
  def traverse(method: SootMethod, forceNewTraversal: Boolean = false): Unit = {
    // Skip analysis if method is not suitable or already processed
    if (shouldSkipMethod(method, forceNewTraversal)) {
      return
    }

    // Mark method as traversed to prevent infinite recursion
    traversedMethods.add(method)

    try {
      // Set up analysis infrastructure
      val analysisContext = setupMethodAnalysis(method)
      
      // Process each statement in the method body
      processMethodStatements(method, analysisContext)
      
    } catch {
      case e: Exception =>
        logger.warn(s"Failed to traverse method ${method.getName}: ${e.getMessage}")
    }
  }

  /**
   * Determines whether a method should be skipped during analysis.
   */
  private def shouldSkipMethod(method: SootMethod, forceNewTraversal: Boolean): Boolean = {
    !forceNewTraversal && (method.isPhantom || traversedMethods.contains(method))
  }

  /**
   * Sets up the analysis context for a method (control flow graph, local definitions).
   */
  private def setupMethodAnalysis(method: SootMethod): MethodAnalysisContext = {
    val body = method.retrieveActiveBody()
    val controlFlowGraph = new ExceptionalUnitGraph(body)
    val localDefinitions = new SimpleLocalDefs(controlFlowGraph)
    
    MethodAnalysisContext(body, controlFlowGraph, localDefinitions)
  }

  /**
   * Processes all statements in a method body according to their types.
   */
  private def processMethodStatements(method: SootMethod, context: MethodAnalysisContext): Unit = {
    context.body.getUnits.asScala.foreach { unit =>
      val statement = Statement.convert(unit)

      processStatement(statement, unit, method, context.localDefinitions)
    }
  }

  /**
   * Processes a single statement based on its type.
   */
  private def processStatement(
      statement: Statement, 
      unit: soot.Unit, 
      method: SootMethod, 
      defs: SimpleLocalDefs
  ): Unit = {
    statement match {
        case assignStmt: AssignStmt => 
          // Handle assignment statements (p = q, p = obj.field, etc.)
          processAssignment(assignStmt, method, defs)
          
        case invokeStmt: InvokeStmt => 
          // Handle method invocations without assignment
          processInvocation(invokeStmt, method, defs)
          
        case _ if analyze(unit) == SinkNode =>
          // Handle sink statements (potential vulnerability points)
          processSink(statement, method, defs)
        
        case _ =>
        // Other statement types don't require special handling
        logger.debug(s"Skipping statement type: ${statement.getClass.getSimpleName}")
  }
  }

  /**
   * Context object containing analysis infrastructure for a method.
   */
  private case class MethodAnalysisContext(
      body: soot.Body,
      controlFlowGraph: ExceptionalUnitGraph,
      localDefinitions: SimpleLocalDefs
  )

  

  /**
   * Processes an assignment statement and applies appropriate SVFA rules based on the
   * left-hand side (LHS) and right-hand side (RHS) operand types.
   * 
   * This method handles the core assignment patterns in static value flow analysis:
   * - Load operations: reading from fields, arrays, or method calls
   * - Store operations: writing to fields or arrays  
   * - Copy operations: variable-to-variable assignments
   * - Source detection: identifying taint sources
   */
  /**
   * Processes an assignment statement and applies appropriate SVFA rules based on the
   * left-hand side (LHS) and right-hand side (RHS) operand types.
   * 
   * This method handles the core assignment patterns in static value flow analysis:
   * - Load operations: reading from fields, arrays, or method calls into locals
   * - Store operations: writing from locals to fields, arrays, or other locations
   * - Copy operations: variable-to-variable assignments and expressions
   * - Source detection: identifying taint sources in assignments
   * 
   * The tuple pattern matching directly mirrors the assignment structure (LHS = RHS)
   * and provides efficient dispatch to the appropriate analysis rules.
   */
  private def processAssignment(
      assignStmt: AssignStmt,
      method: SootMethod,
      defs: SimpleLocalDefs
  ): Unit = {
    val left = assignStmt.stmt.getLeftOp
    val right = assignStmt.stmt.getRightOp

    (left, right) match {
      // ═══════════════════════════════════════════════════════════════
      // LOAD OPERATIONS: Assignments TO local variables (LHS = Local)
      // ═══════════════════════════════════════════════════════════════
      
      case (local: Local, fieldRef: InstanceFieldRef) =>
        // p = obj.field - Load from instance field
        loadRule(assignStmt.stmt, fieldRef, method, defs)
        
      case (local: Local, staticRef: StaticFieldRef) =>
        // p = ClassName.staticField - Load from static field
        loadRule(assignStmt.stmt, staticRef, method)
        
      case (local: Local, arrayRef: ArrayRef) =>
        // p = array[index] - Load from array element
        loadArrayRule(assignStmt.stmt, arrayRef, method, defs)
        
      case (local: Local, invokeExpr: InvokeExpr) =>
        // p = obj.method(args) - Method call with return value assignment
        invokeRule(assignStmt, invokeExpr, method, defs)
        
      case (local: Local, sourceLocal: Local) =>
        // p = q - Simple variable copy
        copyRule(assignStmt.stmt, sourceLocal, method, defs)
        
      case (local: Local, _) =>
        // p = expression - Arithmetic, casts, constants, etc.
        copyRuleInvolvingExpressions(assignStmt.stmt, method, defs)

      // ═══════════════════════════════════════════════════════════════
      // STORE OPERATIONS: Assignments FROM locals to other locations
      // ═══════════════════════════════════════════════════════════════
      
      case (fieldRef: InstanceFieldRef, local: Local) =>
        // obj.field = p - Store to instance field
        storeRule(assignStmt.stmt, fieldRef, method, defs)
        
      case (fieldRef: InstanceFieldRef, constant: Constant) =>
        // obj.field = constant - Check if this creates a taint source
        handleConstantFieldAssignment(assignStmt, fieldRef, method)
        
      case (staticRef: StaticFieldRef, local: Local) =>
        // ClassName.staticField = p - Store to static field
        storeRule(assignStmt.stmt, method, defs)
        
      case (arrayRef: JArrayRef, _) =>
        // array[index] = value - Store to array element (any RHS type)
        storeArrayRule(assignStmt, method, defs)

      // ═══════════════════════════════════════════════════════════════
      // UNHANDLED CASES: Log for debugging and future extension
      // ═══════════════════════════════════════════════════════════════
      
      case (lhs, rhs) =>
        logger.debug(s"Unhandled assignment: ${lhs.getClass.getSimpleName} = ${rhs.getClass.getSimpleName} in ${method.getName}")
    }
  }

  /**
   * Handles assignment of constants to instance fields, checking if this represents
   * a source node in the taint analysis.
   * 
   * This is a specialized helper for processAssignment when dealing with:
   * obj.field = "tainted_constant" or obj.field = 42
   */
  private def handleConstantFieldAssignment(assignStmt: AssignStmt, fieldRef: InstanceFieldRef, method: SootMethod): Unit = {
    if (analyze(assignStmt.stmt) == SourceNode) {
      val node = createNode(method, assignStmt.stmt)
      svg.addNode(node)
      instanceFieldStoreIndex
        .getOrElseUpdate(fieldRef.getField, scala.collection.mutable.HashSet.empty) += node
    }
  }

  /**
   * Processes a method invocation statement without return value assignment.
   * 
   * Examples: obj.method(), System.out.println(x)
   */
  private def processInvocation(
      stmt: InvokeStmt,
      method: SootMethod,
      defs: SimpleLocalDefs
  ): Unit = {
    val invokeExpr = stmt.stmt.getInvokeExpr
    invokeRule(stmt, invokeExpr, method, defs)
  }

  /**
   * Processes a sink statement (potential vulnerability point) by analyzing
   * all variables and fields used in the statement.
   * 
   * Sink statements are where tainted data might cause security issues,
   * such as SQL injection, XSS, or information disclosure.
   */
  private def processSink(
      statement: Statement,
      method: SootMethod,
      defs: SimpleLocalDefs
  ): Unit = {
    statement.base.getUseBoxes.asScala.foreach { box =>
      box.getValue match {
        case local: Local => 
          // Handle local variable usage in sink
          copyRule(statement.base, local, method, defs)
          
        case fieldRef: InstanceFieldRef =>
          // Handle field access in sink
          loadRule(statement.base, fieldRef, method, defs)
          
        case _ =>
          // TODO: Handle other cases like parameters, static fields, etc.
          logger.debug(s"Unhandled sink operand type: ${box.getValue.getClass.getSimpleName}")
      }
    }
  }

  /**
   * Handles invocation rules for a call statement by traversing the call
   * graph. This method avoids infinite recursion and limits the traversal
   * depth for performance.
   *
   * i.e:
   *
   * myObject.method()
   * myObject.method(q)
   * this.method()
   * this.method(q)
   */
  /**
   * Handles method invocation by traversing the call graph to find potential callees.
   * 
   * This method implements a bounded call graph traversal to balance precision and performance:
   * - If no call graph edges exist, falls back to the declared method from the invoke expression
   * - Prevents infinite recursion by tracking visited methods and avoiding self-calls
   * 
   * @param callStmt The statement containing the method call
   * @param exp The invoke expression with method details
   * @param caller The method containing this call site
   * @param defs Local definitions for data flow analysis
   */
  private def invokeRule(
      callStmt: Statement,
      exp: InvokeExpr,
      caller: SootMethod,
      defs: SimpleLocalDefs
  ): Unit = {
    val callGraph = Scene.v().getCallGraph
    val callGraphEdges = callGraph.edgesOutOf(callStmt.base)

    if (callGraphEdges.hasNext) {
      processCallGraphEdges(callStmt, exp, caller, defs, callGraphEdges)
    } else {
      processFallbackMethod(callStmt, exp, caller, defs)
    }
  }

  /**
   * Processes method calls using call graph edges for precise analysis.
   * Limits traversal depth to prevent performance issues with deep call chains.
   */
  private def processCallGraphEdges(
      callStmt: Statement,
      exp: InvokeExpr,
      caller: SootMethod,
      defs: SimpleLocalDefs,
      edges: java.util.Iterator[soot.jimple.toolkits.callgraph.Edge]
  ): Unit = {
    val visited = scala.collection.mutable.Set[SootMethod]()
    
    edges.asScala
      .map(_.getTgt.method())
      .filter(isValidCallee(_, caller, visited))
      .foreach { callee =>
          visited += callee
          invokeRule(callStmt, exp, caller, callee, defs)
        }
  }

  /**
   * Fallback method when no call graph edges are available.
   * Uses the statically declared method from the invoke expression.
   */
  private def processFallbackMethod(
      callStmt: Statement,
      exp: InvokeExpr,
      caller: SootMethod,
      defs: SimpleLocalDefs
  ): Unit = {
    val declaredMethod = exp.getMethod
    if (isValidCallee(declaredMethod, caller)) {
      invokeRule(callStmt, exp, caller, declaredMethod, defs)
      }
    }

  /**
   * Validates whether a method is a suitable callee for analysis.
   * 
   * @param callee The potential target method
   * @param caller The calling method
   * @param visited Set of already visited methods (optional, for recursion prevention)
   * @return true if the callee should be analyzed
   */
  private def isValidCallee(
      callee: SootMethod, 
      caller: SootMethod, 
      visited: scala.collection.mutable.Set[SootMethod] = scala.collection.mutable.Set.empty
  ): Boolean = {
    callee != null && 
    callee != caller && 
    !visited.contains(callee)
  }

  /**
   * Processes a method invocation with a specific callee, handling taint flow analysis
   * across method boundaries.
   * 
   * This method implements interprocedural analysis by:
   * 1. Handling special cases (sinks, sources, method rules)
   * 2. Creating data flow edges between caller and callee
   * 3. Recursively analyzing the callee method body
   * 
   * @param callStmt The statement containing the method call
   * @param exp The invoke expression with method details  
   * @param caller The method containing this call site
   * @param callee The target method being invoked
   * @param defs Local definitions for data flow analysis
   */
  private def invokeRule(
      callStmt: Statement,
      exp: InvokeExpr,
      caller: SootMethod,
      callee: SootMethod,
      defs: SimpleLocalDefs
  ): Unit = {
    // Guard against null callees
    if (callee == null) {
      logger.debug(s"Skipping null callee for call in ${caller.getName}")
      return
    }

    // Handle special node types first
    handleSpecialNodeTypes(callStmt, exp, caller, defs) match {
      case Some(_) => return // Early exit for sinks and handled method rules
      case None => // Continue with interprocedural analysis
    }

    // Skip interprocedural analysis if configured for intraprocedural only
    if (intraprocedural()) {
      logger.debug(s"Skipping interprocedural analysis for ${callee.getName} (intraprocedural mode)")
      return
    }

    // Perform interprocedural analysis
    performInterproceduralAnalysis(callStmt, exp, caller, callee, defs)
    
    // Recursively analyze the callee method
    traverse(callee)
  }

  /**
   * Handles special node types (sinks, sources) and method rules.
   * 
   * @return Some(Unit) if processing should stop, None if it should continue
   */
  private def handleSpecialNodeTypes(
      callStmt: Statement,
      exp: InvokeExpr,
      caller: SootMethod,
      defs: SimpleLocalDefs
  ): Option[Unit] = {
    val nodeType = analyze(callStmt.base)
    
    nodeType match {
      case SinkNode =>
        // Handle sink methods - create edges from arguments to call site
        defsToCallOfSinkMethod(callStmt, exp, caller, defs)
        // TODO: Consider exploring sink method bodies to find additional paths
        // Currently skipped to avoid potential performance issues
        Some(())
        
      case SourceNode =>
        // Handle source methods - add source node to graph
        val sourceNode = createNode(caller, callStmt.base)
        svg.addNode(sourceNode)
        None // Continue processing
        
      case _ =>
        // Check for applicable method rules (e.g., HttpSession.setAttribute)
        methodRules.find(_.check(exp.getMethod)) match {
          case Some(rule) =>
            // Apply rule with SVFA context
            applyRuleWithContext(rule, caller, callStmt.base.asInstanceOf[jimple.Stmt], defs)
            Some(()) // Rule handled the call, stop processing
          case None =>
            None // No special handling needed, continue
      }
    }
  }

  /**
   * Performs interprocedural analysis by creating data flow edges between
   * caller and callee for parameters, return values, and object references.
   */
  private def performInterproceduralAnalysis(
      callStmt: Statement,
      exp: InvokeExpr,
      caller: SootMethod,
      callee: SootMethod,
      defs: SimpleLocalDefs
  ): Unit = {
    // Skip phantom methods (e.g., servlet API methods without implementation)
    if (callee.isPhantom) {
      return
    }
    
    // Try to force load the method body if it's not available
    if (!callee.hasActiveBody) {
      try {
        callee.retrieveActiveBody()
      } catch {
        case _: Exception => 
          // If we can't retrieve the body, skip this method
          return
      }
    }
    
    try {
    val body = callee.retrieveActiveBody()
      val calleeGraph = new ExceptionalUnitGraph(body)
      val calleeDefs = new SimpleLocalDefs(calleeGraph)
      
      processCalleeStatements(callStmt, exp, caller, callee, defs, calleeDefs, body)
      
    } catch {
      case e: Exception =>
        // Only log warnings for non-phantom methods that we expect to be able to analyze
        if (!callee.isPhantom && callee.hasActiveBody) {
          logger.warn(s"Failed to analyze callee ${callee.getName}: ${e.getMessage}")
        }
    }
  }

  /**
   * Processes statements in the callee method body to create appropriate data flow edges.
   */
  private def processCalleeStatements(
      callStmt: Statement,
      exp: InvokeExpr,
      caller: SootMethod,
      callee: SootMethod,
      callerDefs: SimpleLocalDefs,
      calleeDefs: SimpleLocalDefs,
      calleeBody: soot.Body
  ): Unit = {
    var parameterCount = 0
    
    calleeBody.getUnits.asScala.foreach { stmt =>
      stmt match {
        case s if isThisInitStmt(exp, s) =>
          // Handle 'this' parameter: this := @this: ClassName
          defsToThisObject(callStmt, caller, callerDefs, s, exp, callee)
          
        case s if isParameterInitStmt(exp, parameterCount, s) =>
          // Handle method parameters: param := @parameter#: Type
          defsToFormalArgs(callStmt, caller, callerDefs, s, exp, callee, parameterCount)
          parameterCount += 1
          
        case s if isAssignReturnLocalStmt(callStmt.base, s) =>
          // Handle return statements with local variables: return localVar
          defsToCallSite(caller, callee, calleeDefs, callStmt.base, s, callStmt, callerDefs, exp)
          
        case s if isReturnStringStmt(callStmt.base, s) =>
          // Handle return statements with string constants: return "string"
          stringToCallSite(caller, callee, callStmt.base, s)
          
        case _ =>
          // Other statements don't need special interprocedural handling
      }
    }
  }

  private def applyPhantomMethodCallRule(
      callStmt: Statement,
      exp: InvokeExpr,
      caller: SootMethod,
      defs: SimpleLocalDefs
  ) = {
    val srcArg = exp.getArg(0)
    val destArg = exp.getArg(2)
    if (srcArg.isInstanceOf[Local] && destArg.isInstanceOf[Local]) {
      defs
        .getDefsOfAt(srcArg.asInstanceOf[Local], callStmt.base)
        .forEach(srcArgDefStmt => {
          val sourceNode = createNode(caller, srcArgDefStmt)
          val allocationNodes = findAllocationSites(destArg.asInstanceOf[Local])
          allocationNodes.foreach(targetNode => {
            updateGraph(sourceNode, targetNode) // add comment
          })
        })
    }
  }

  /*
   * This rule deals with the following situation:
   *
   * (*) p = q
   *
   * In this case, we create an edge from defs(q)
   * to the statement p = q.
   */
  protected def copyRule(
      targetStmt: soot.Unit,
      local: Local,
      method: SootMethod,
      defs: SimpleLocalDefs
  ) = {
    defs
      .getDefsOfAt(local, targetStmt)
      .forEach(sourceStmt => {
        val source = createNode(method, sourceStmt)
        val target = createNode(method, targetStmt)
        updateGraph(source, target) // add comment
      })
  }

  /*
   * This rule deals with the following situation:
   *
   * (*) p = q + r
   *
   * In this case, we create and edge from defs(q) and
   * from defs(r) to the statement p = q + r
   */
  def copyRuleInvolvingExpressions(
      stmt: jimple.AssignStmt,
      method: SootMethod,
      defs: SimpleLocalDefs
  ) = {

    if(stmt.getRightOp.getUseBoxes.isEmpty) {
      if(analyze(stmt).equals(SourceNode)) {
        createNode(method, stmt)
      }
    }
    else {
      stmt.getRightOp.getUseBoxes.forEach(box => {
        if (box.getValue.isInstanceOf[Local]) {
          val local = box.getValue.asInstanceOf[Local]
          copyRule(stmt, local, method, defs)
        }
      })
    }
  }

  /*
   * This rule deals with the following situations:
   *
   *  (*) p = q.f
   */
  protected def loadRule(
      stmt: soot.Unit,
      ref: InstanceFieldRef,
      method: SootMethod,
      defs: SimpleLocalDefs
  ): Unit = {
    val base = ref.getBase
    // value field of a string.
    val className = ref.getFieldRef.declaringClass().getName
    if ((className == "java.lang.String") && ref.getFieldRef.name == "value") {
      if (base.isInstanceOf[Local]) {
        defs
          .getDefsOfAt(base.asInstanceOf[Local], stmt)
          .forEach(source => {
            val sourceNode = createNode(method, source)
            val targetNode = createNode(method, stmt)
//          updateGraph(sourceNode, targetNode) // add comment
          })
      }
      return;
    }
    // default case
    if (base.isInstanceOf[Local]) {
      var allocationNodes =
        findFieldStores(base.asInstanceOf[Local], ref.getField)

      if (allocationNodes.isEmpty) {
        allocationNodes =
          findAllocationSites(base.asInstanceOf[Local], false, ref.getField)
      }

      if (allocationNodes.isEmpty) {
        allocationNodes =
          findAllocationSites(base.asInstanceOf[Local], false, ref.getField)
      }

      if (allocationNodes.isEmpty) {
        allocationNodes =
          findAllocationSites(base.asInstanceOf[Local], true, ref.getField)
      }

      allocationNodes.foreach(source => {
        val target = createNode(method, stmt)
        updateGraph(
          source,
          target
        ) // update 'edge' FROM allocationNode? stmt TO load rule stmt (current stmt)
//        svg.getAdjacentNodes(source).get.foreach(s => {
//            updateGraph(s, target) // update 'edge' FROM adjacent node of allocationNode? stmt TO load rule stmt (current stmt)
//        }) // add comment
      })

      // create an edge from the base defs to target
      // if an object is tainted, we should propagate the taint to all
      // fields as well. Not completely sure if this should be
      // the case.
      if (propagateObjectTaint()) {
        defs
          .getDefsOfAt(base.asInstanceOf[Local], stmt)
          .forEach(source => {
            val sourceNode = createNode(method, source)
            val targetNode = createNode(method, stmt)
            updateGraph(
              sourceNode,
              targetNode
            ) // update 'edge' FROM stmt where object (that calls load attribute) is instanced TO load rule stmt (current stmt)
          })
      }
    }
  }

  /*
   * This rule deals with the following situation
   * when "f" is an static variable (StaticFieldRef)
   *
   *  p = f
   */
  private def loadRule(
      stmt: soot.Unit,
      ref: StaticFieldRef,
      method: SootMethod
  ): Unit = {

    val findFieldStoresNodes = findFieldStores(
      ref
    ) // find fields stores for StaticFieldRef

    findFieldStoresNodes.foreach(source => {
      val target = createNode(method, stmt)
      updateGraph(
        source,
        target
      ) // update 'edge' FROM allocationNode? stmt TO load rule stmt (current stmt)
      svg
        .getAdjacentNodes(source)
        .get
        .foreach(s => {
          updateGraph(
            s,
            target
          ) // update 'edge' FROM adjacent node of allocationNode? stmt TO load rule stmt (current stmt)
        })
    })
  }

  /** Evaluates statements: p = q[i]
    */
  protected def loadArrayRule(
      targetStmt: soot.Unit,
      ref: ArrayRef,
      method: SootMethod,
      defs: SimpleLocalDefs
  ): Unit = {
    val base = ref.getBase

    if (base.isInstanceOf[Local]) {
      val local = base.asInstanceOf[Local]

      /** For each definition of the "base" variable (an array), creates an edge
        * FROM the definition statement TO the current array access statement
        */
      defs
        .getDefsOfAt(local, targetStmt)
        .forEach(sourceStmt => {
          val source = createNode(method, sourceStmt)
          val target = createNode(method, targetStmt)
          updateGraph(source, target)

          /** Handle special cases where the right-hand side of array indexes
            * assignments involves other local variables, so it creates edges
            * FROM those variable TO the source statement.
            */
          val stmt = Statement.convert(sourceStmt)
          stmt match {
            case AssignStmt(base) => {
              val rightOp = AssignStmt(base).stmt.getRightOp
              if (rightOp.isInstanceOf[Local]) {
                arrayStores
                  .getOrElseUpdate(rightOp.asInstanceOf[Local], List())
                  .foreach(storeStmt => {
                    val source = createNode(method, storeStmt)
                    val target = createNode(method, sourceStmt)
                    updateGraph(source, target)
                  })
              }
            }
            case _ =>
          }

        })

      /** If there are any array store operations for the array it creates edges
        * FROM those stores to the current statement
        */
      val stores = arrayStores.getOrElseUpdate(local, List())
      stores.foreach(sourceStmt => {
        val source = createNode(method, sourceStmt)
        val target = createNode(method, targetStmt)
        updateGraph(source, target) // add comment
      })
    }
  }

  /*
   * This rule deals with statements in the form:
   *
   * (*) p.f = expression
   */

  /** CASE 1 ??
    *
    * CASE 2 CREATE EDGE(S) FROM stmt(s) where right value was instanced TO:
    * stmt load store stmt (current stmt)
    */
  private def storeRule(
      targetStmt: jimple.AssignStmt,
      fieldRef: InstanceFieldRef,
      method: SootMethod,
      defs: SimpleLocalDefs
  ) = {
    val local = targetStmt.getRightOp.asInstanceOf[Local]
    if (fieldRef.getBase.isInstanceOf[Local]) {
      val base = fieldRef.getBase.asInstanceOf[Local]
      if (
        fieldRef.getField.getDeclaringClass.getName == "java.lang.String" && fieldRef.getField.getName == "value"
      ) {
        defs
          .getDefsOfAt(local, targetStmt)
          .forEach(sourceStmt => {
            val source = createNode(method, sourceStmt)
            val allocationNodes = findAllocationSites(base)
            allocationNodes.foreach(targetNode => {
              updateGraph(source, targetNode) // add comment
            })
          })
      } else {
        // CASE 2
        //        val allocationNodes = findAllocationSites(base)

        //        val allocationNodes = findAllocationSites(base, true, fieldRef.getField)
        //        if(!allocationNodes.isEmpty) {
        //          allocationNodes.foreach(targetNode => {
        defs
          .getDefsOfAt(local, targetStmt)
          .forEach(sourceStmt => {
            val source = createNode(method, sourceStmt)
            val target = createNode(method, targetStmt)
            updateGraph(
              source,
              target
            ) // update 'edge' FROM stmt where right value was instanced TO current stmt
            instanceFieldStoreIndex
              .getOrElseUpdate(fieldRef.getField, scala.collection.mutable.HashSet.empty) += target
          })
        //          })
        //        }
      }
    }
  }

  /*
   * This rule deals with statements in the form:
   * when "p" is an static variable (StaticFieldRef)
   *
   * (*) p = expression
   *
   * This behavior is like a simple CopyRule, so that method is called here.
   */
  private def storeRule(
      stmt: jimple.AssignStmt,
      method: SootMethod,
      defs: SimpleLocalDefs
  ) = {
    val local = stmt.getRightOp.asInstanceOf[Local]
    copyRule(stmt, local, method, defs)
  }

  /** statement: array[i] = <variable>
    *
    * CASE 1
    *
    * Create EDGE(S) "FROM" each stmt where the variable on the right is defined
    * "TO" current stmt.
    */
  def storeArrayRule(
      assignStmt: AssignStmt,
      method: SootMethod,
      defs: SimpleLocalDefs
  ) {
    val left = assignStmt.stmt.getLeftOp
    val right = assignStmt.stmt.getRightOp

    // stores all the place where the array was assigned
    val local = left.asInstanceOf[JArrayRef].getBase.asInstanceOf[Local]
    val stores = assignStmt.stmt :: arrayStores.getOrElseUpdate(local, List())
    arrayStores.put(local, stores)

    /** If the right-hand side is a local variable, create edges from all
      * definitions of that variable to the current statement
      */
    if (right.isInstanceOf[Local]) {
      val rightLocal = right.asInstanceOf[Local]
      defs
        .getDefsOfAt(rightLocal, assignStmt.stmt)
        .forEach(sourceStmt => {
          val source = createNode(method, sourceStmt)
          val target = createNode(method, assignStmt.stmt)
          svg.addEdge(
            source,
            target
          ) // create 'Edge' FROM the stmt where the variable on the right was defined TO the current stmt
        })
    }
  }

  /** CASE 1
    *
    * Create EDGE(S) "FROM" each stmt where the variable returned is defined.
    * "TO" call site stmt.
    *
    * An CS for "close" is added
    *   - Caller method
    *   - stmt from the callee method is called (stmt is in caller method)
    *   - Callee method
    *
    * CASE 2 ??
    */
  private def defsToCallSite(
      caller: SootMethod,
      callee: SootMethod,
      calleeDefs: SimpleLocalDefs,
      callStmt: soot.Unit,
      retStmt: soot.Unit,
      stmt: Statement,
      defs: SimpleLocalDefs,
      exp: InvokeExpr
  ) = {

    // CASE 1
    val target = createNode(caller, callStmt)
    val local = retStmt.asInstanceOf[ReturnStmt].getOp.asInstanceOf[Local]

    val allocationSites = getAllocationSites(exp)

    calleeDefs
      .getDefsOfAt(local, retStmt)
      .forEach(sourceStmt => {
        val source = createNode(callee, sourceStmt)

        if (allocationSites.nonEmpty) {
          allocationSites.foreach(al => {
            val csCloseLabel =
              createCSCloseLabel(caller, callStmt, callee, Set(al.show()))
            svg.addEdge(
              source,
              target,
              csCloseLabel
            ) // create an EDGE FROM "definition stmt from return variable " TO "call site stmt"
          })
        } else {
          val csCloseLabel = createCSCloseLabel(caller, callStmt, callee, Set())
          svg.addEdge(
            source,
            target,
            csCloseLabel
          ) // create an EDGE FROM "definition stmt from return variable " TO "call site stmt"
        }

        // CASE 2
        if (local.getType.isInstanceOf[ArrayType]) {
          val stores = arrayStores.getOrElseUpdate(local, List())
          stores.foreach(sourceStmt => {
            val source = createNode(callee, sourceStmt)
            val csCloseLabel =
              createCSCloseLabel(caller, callStmt, callee, Set())
            svg.addEdge(source, target, csCloseLabel) // add comment
          })
        }
      })
  }

  /** CREATE EDGE FROM: return stmt. TO: call site stmt.
    */
  private def stringToCallSite(
      caller: SootMethod,
      callee: SootMethod,
      callStmt: soot.Unit,
      retStmt: soot.Unit
  ): Unit = {
    val target = createNode(caller, callStmt)
    val source = createNode(callee, retStmt)
    svg.addEdge(
      source,
      target
    ) // create an 'edge' FROM "return stmt " TO "call site stmt"
  }

  /** CREATE EDGE(s) FROM: stmt where the object that calls the method was
    * instanced TO: the 'this' definition in constructor (init) from callee
    * method
    *
    * OPEN CS:
    */
  private def defsToThisObject(
      callStatement: Statement,
      caller: SootMethod,
      calleeDefs: SimpleLocalDefs,
      targetStmt: soot.Unit,
      expr: InvokeExpr,
      callee: SootMethod
  ): Unit = {

    // Check if the current expression belong to invokeExpr
    val invokeExpr = expr match {
      case e: VirtualInvokeExpr   => e
      case e: SpecialInvokeExpr   => e
      case e: InterfaceInvokeExpr => e
      case _                      => null // TODO: not sure if the other cases
      // are also relevant here. Otherwise,
      // we can just match with InstanceInvokeExpr
    }

    if (invokeExpr != null) {
      if (invokeExpr.getBase.isInstanceOf[Local]) {

        val target = createNode(callee, targetStmt)

        val base = invokeExpr.getBase.asInstanceOf[Local]

//        val al = getAllocationSites(callStatement, expr, calleeDefs)

        calleeDefs
          .getDefsOfAt(base, callStatement.base)
          .forEach(sourceStmt => {
            val source = createNode(caller, sourceStmt)
            val csOpenLabel =
              createCSOpenLabel(caller, callStatement.base, callee, Set())
            svg.addEdge(
              source,
              target,
              csOpenLabel
            ) // create 'Edge' FROM the stmt where the object that calls the method was instanced TO the this definition in callee method
          })
      }
    }
  }

  /** Create EDGE(S) "FROM" each stmt where the variable, passed as a parameter,
    * is defined. "TO" stmt where the variable is loaded inside the method.
    *
    * An "open" CS is added
    *   - Caller method
    *   - stmt where the callee method is called (it is in caller method)
    *   - Callee method
    */
  private def defsToFormalArgs(
      stmt: Statement,
      caller: SootMethod,
      defs: SimpleLocalDefs,
      assignStmt: soot.Unit,
      exp: InvokeExpr,
      callee: SootMethod,
      pmtCount: Int
  ) = {
    val target = createNode(callee, assignStmt)

    val local = exp.getArg(pmtCount).asInstanceOf[Local]

    val allocationSites = getAllocationSites(exp)

    defs
      .getDefsOfAt(local, stmt.base)
      .forEach(sourceStmt => {
        val source = createNode(caller, sourceStmt)

        if (allocationSites.nonEmpty) {
          allocationSites.foreach(al => {
            val csOpenLabel =
              createCSOpenLabel(caller, stmt.base, callee, Set(al.show())) //
            svg.addEdge(
              source,
              target,
              csOpenLabel
            ) // creates an 'edge' FROM stmt where the variable is defined TO stmt where the variable is loaded
          })
        } else {
          val csOpenLabel =
            createCSOpenLabel(caller, stmt.base, callee, Set()) //
          svg.addEdge(
            source,
            target,
            csOpenLabel
          ) // creates an 'edge' FROM stmt where the variable is defined TO stmt where the variable is loaded
        }
      })
  }

  private def getAllocationSites(
      invokeExpr: InvokeExpr
  ): ListBuffer[GraphNode] = invokeExpr match {
    case exp: VirtualInvokeExpr =>
      exp.getBase match {
        case base: Local => getAllocationSites(base)
        case _           => ListBuffer[GraphNode]()
      }
    case _ => ListBuffer[GraphNode]()
  }

  /**
   * Points-to set of a call's base local, as a live reference into the
   * already-solved PAG — never materialized into GraphNodes/strings. Not
   * wired into any call site yet.
   */
  private def getBasePointsToSet(invokeExpr: InvokeExpr): Option[soot.PointsToSet] =
    invokeExpr match {
      case exp: VirtualInvokeExpr =>
        exp.getBase match {
          case base: Local => getPointsToSet(base)
          case _           => None
        }
      case _ => None
    }

  private def getPointsToSet(local: Local): Option[soot.PointsToSet] = {
    val pta =
      if (pointsToAnalysis.isInstanceOf[PAG]) pointsToAnalysis.asInstanceOf[PAG]
      else if (pointsToAnalysis.isInstanceOf[DemandCSPointsTo])
        pointsToAnalysis.asInstanceOf[DemandCSPointsTo].getPAG
      else null

    if (pta == null) None else Some(pta.reachingObjects(local))
  }

  private def getAllocationSites(base: Local): ListBuffer[GraphNode] =
    findAllocationSites(base, false) match {
      case v if v.isEmpty => findAllocationSites(base)
      case v              => v
    }

  /** CASE #1: UPDATE EDGE(S) "FROM" each stmt where the variable, passed as an
   * argument, is defined. "TO" stmt where the method is called (call-site
   * stmt). i.e: [s1 -> s2]
   *
   * -------------------
   *  s1: p = ...
   *  s2: myObj.method(p)
   * -------------------
   *
   * CASE #2:
   * TO-DO
   *
   * CASE #3: UPDATE EDGE(S) "FROM" from definition of base object "TO" where it
   * calls any of its methods. The expression must be type (invoke).
   * i.e: [s1 -> s2]
   *
   * -------------------
   *  s1: myObj = new Object()
   *  s2: myObj.method()
   * -------------------
   *
   */
  private def defsToCallOfSinkMethod(
      stmt: Statement,
      exp: InvokeExpr,
      caller: SootMethod,
      defs: SimpleLocalDefs
  ) = {

    // CASE #1
    exp.getArgs
      .stream()
      .filter(a => a.isInstanceOf[Local])
      .forEach(a => {
        val local = a.asInstanceOf[Local]
        val targetStmt = stmt.base
        defs
          .getDefsOfAt(local, targetStmt)
          .forEach(sourceStmt => {
            val source = createNode(caller, sourceStmt)
            val target = createNode(caller, targetStmt)
            updateGraph(
              source,
              target
            )
          })

        // CASE #2
        if (local.getType.isInstanceOf[ArrayType]) {
          val stores = arrayStores.getOrElseUpdate(local, List())
          stores.foreach(sourceStmt => {
            val source = createNode(caller, sourceStmt)
            val target = createNode(caller, targetStmt)
            updateGraph(source, target)
          })
        }
      })

    // CASE #3
    if (isFieldSensitiveAnalysis() && exp.isInstanceOf[InstanceInvokeExpr]) {
      if (exp.asInstanceOf[InstanceInvokeExpr].getBase.isInstanceOf[Local]) {
        val local =
          exp.asInstanceOf[InstanceInvokeExpr].getBase.asInstanceOf[Local]
        val targetStmt = stmt.base
        defs
          .getDefsOfAt(local, targetStmt)
          .forEach(sourceStmt => {
            val source = createNode(caller, sourceStmt)
            val target = createNode(caller, targetStmt)
            updateGraph(source, target)
          })
      }
    }
  }

  /*
   * creates a graph node from a sootMethod / sootUnit
   */
  override def createNode(method: SootMethod, stmt: soot.Unit): GraphNode =
    svg.createNode(method, stmt, analyze)

  def createCSOpenLabel(
      method: SootMethod,
      stmt: soot.Unit,
      callee: SootMethod,
      context: Set[String]
  ): CallSiteLabel = {
    val statement = br.unb.cic.soot.graph.GraphNode(
      className = method.getDeclaringClass.toString,
      methodSignature = method.getSignature,
      stmt = stmt.toString,
      line = stmt.getJavaSourceStartLineNumber,
      nodeType = SimpleNode, // Context labels are typically for simple nodes
      sootUnit = stmt,
      sootMethod = method
    )
    CallSiteLabel(
      ContextSensitiveRegion(statement, callee.toString, context),
      CallSiteOpenLabel
    )
  }

  def createCSCloseLabel(
      method: SootMethod,
      stmt: soot.Unit,
      callee: SootMethod,
      context: Set[String]
  ): CallSiteLabel = {
    val statement = br.unb.cic.soot.graph.GraphNode(
      className = method.getDeclaringClass.toString,
      methodSignature = method.getSignature,
      stmt = stmt.toString,
      line = stmt.getJavaSourceStartLineNumber,
      nodeType = SimpleNode, // Context labels are typically for simple nodes
      sootUnit = stmt,
      sootMethod = method
    )
    CallSiteLabel(
      ContextSensitiveRegion(statement, callee.toString, context),
      CallSiteCloseLabel
    )
  }

  def isThisInitStmt(expr: InvokeExpr, unit: soot.Unit): Boolean =
    unit.isInstanceOf[IdentityStmt] && unit
      .asInstanceOf[IdentityStmt]
      .getRightOp
      .isInstanceOf[ThisRef]

  def isParameterInitStmt(
      expr: InvokeExpr,
      pmtCount: Int,
      unit: soot.Unit
  ): Boolean =
    unit.isInstanceOf[IdentityStmt] &&
      unit.asInstanceOf[IdentityStmt].getRightOp.isInstanceOf[ParameterRef] &&
      pmtCount >= 0 &&
      pmtCount < expr.getArgCount &&
      expr.getArg(pmtCount).isInstanceOf[Local]

  def isAssignReturnLocalStmt(callSite: soot.Unit, unit: soot.Unit): Boolean =
    unit.isInstanceOf[ReturnStmt] && unit
      .asInstanceOf[ReturnStmt]
      .getOp
      .isInstanceOf[Local] &&
      callSite.isInstanceOf[soot.jimple.AssignStmt]

  def isReturnStringStmt(callSite: soot.Unit, unit: soot.Unit): Boolean =
    unit.isInstanceOf[ReturnStmt] && unit
      .asInstanceOf[ReturnStmt]
      .getOp
      .isInstanceOf[StringConstant] &&
      callSite.isInstanceOf[soot.jimple.AssignStmt]

  def findAllocationSites(
      local: Local,
      oldSet: Boolean = true,
      field: SootField = null
  ): ListBuffer[GraphNode] = {
    val pta =
      if (pointsToAnalysis.isInstanceOf[PAG]) pointsToAnalysis.asInstanceOf[PAG]
      else if (pointsToAnalysis.isInstanceOf[DemandCSPointsTo])
        pointsToAnalysis.asInstanceOf[DemandCSPointsTo].getPAG
      else null

    if (pta != null) {
      val reachingObjects =
        if (field == null) pta.reachingObjects(local.asInstanceOf[Local])
        else pta.reachingObjects(local, field)

      if (!reachingObjects.isEmpty) {
        val allocations =
          if (oldSet) reachingObjects.asInstanceOf[DoublePointsToSet].getOldSet
          else reachingObjects.asInstanceOf[DoublePointsToSet].getNewSet

        val v = new AllocationVisitor()
        allocations.asInstanceOf[HybridPointsToSet].forall(v)
        return v.allocationNodes
      }
    }
    new ListBuffer[GraphNode]()
  }

  /*
   * a class to visit the allocation nodes of the objects that
   * a field might point to.
   *
   * @param method method of the statement stmt
   * @param stmt statement with a load operation
   */
  class AllocationVisitor() extends P2SetVisitor {

    var allocationNodes = new ListBuffer[GraphNode]()

    override def visit(n: pag.Node): Unit = {
      if (n.isInstanceOf[AllocNode]) {
        val allocationNode = n.asInstanceOf[AllocNode]

        var stmt: GraphNode = null

        if (allocationNode.getNewExpr.isInstanceOf[NewExpr]) {
          if (
            allocationSites.contains(
              allocationNode.getNewExpr.asInstanceOf[NewExpr]
            )
          ) {
            stmt = allocationSites(
              allocationNode.getNewExpr.asInstanceOf[NewExpr]
            )
          }
        } else if (allocationNode.getNewExpr.isInstanceOf[NewArrayExpr]) {
          if (
            allocationSites.contains(
              allocationNode.getNewExpr.asInstanceOf[NewArrayExpr]
            )
          ) {
            stmt = allocationSites(
              allocationNode.getNewExpr.asInstanceOf[NewArrayExpr]
            )
          }
        } else if (allocationNode.getNewExpr.isInstanceOf[String]) {
          val str: StringConstant =
            StringConstant.v(allocationNode.getNewExpr.asInstanceOf[String])
          stmt = allocationSites.getOrElseUpdate(str, null)
        }

        if (stmt != null) {
          allocationNodes += stmt
        }
      }
    }
  }

  /** Override this method in the case that a complete graph should be
    * generated.
    *
    * Otherwise, only nodes that can be reached from source nodes will be in the
    * graph
    *
    * @return
    *   true for a full sparse version of the graph. false otherwise.
    * @deprecated
    */
  def runInFullSparsenessMode() = true

  def findFieldStores(local: Local, field: SootField): ListBuffer[GraphNode] = {
    val res: ListBuffer[GraphNode] = new ListBuffer[GraphNode]()
    val candidates =
      instanceFieldStoreIndex.getOrElse(field, scala.collection.mutable.HashSet.empty)
    for (node <- candidates) {
      val assignment = node.unit().asInstanceOf[soot.jimple.AssignStmt]
      val base = assignment.getLeftOp
        .asInstanceOf[InstanceFieldRef]
        .getBase
        .asInstanceOf[Local]
      if (
        pointsToAnalysis
          .reachingObjects(base)
          .hasNonEmptyIntersection(
            pointsToAnalysis.reachingObjects(local)
          ) || areThisFromSameClass(base, local)
      ) {
        res += createNode(node.method(), node.unit())
      }
    }
    return res
  }

  // findFieldStores for static variables
  private def findFieldStores(field: StaticFieldRef): ListBuffer[GraphNode] = {
    val res: ListBuffer[GraphNode] = new ListBuffer[GraphNode]()
    for (node <- svg.nodes()) {
      if (node.unit().isInstanceOf[soot.jimple.AssignStmt]) {
        val assignment = node.unit().asInstanceOf[soot.jimple.AssignStmt]
        if (assignment.getLeftOp.isInstanceOf[StaticFieldRef]) {
          val base = assignment.getLeftOp.asInstanceOf[StaticFieldRef]
          if (field.getFieldRef.equals(base.getFieldRef)) {
            res += createNode(node.method(), node.unit())
          }
        }
      }
    }
    return res
  }

  private def areThisFromSameClass(base: Local, local: Local): Boolean = {
    base.getName == local.getName && base.getType == local.getType && base.getName
      .equals("this")
  }

  //  /*
  //   * It either updates the graph or not, depending on
  //   * the types of the nodes.
  //   */

  override def updateGraph(
      source: GraphNode,
      target: GraphNode
  ): Boolean = {
    updateGraph(source, target, forceNewEdge = false)
  }

  def updateGraph(
      source: GraphNode,
      target: GraphNode,
      forceNewEdge: Boolean = false
  ): Boolean = {
    var res = false
    if (!runInFullSparsenessMode() || true) {
      svg.addEdge(source, target)

      res = true
    }
    return res
  }

}
