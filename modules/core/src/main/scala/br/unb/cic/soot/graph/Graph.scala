package br.unb.cic.soot.graph

import scalax.collection.edge.LkDiEdge
import soot.SootMethod

import scala.collection.immutable.HashSet

/**
 * Represents different types of nodes in the program dependence graph for taint analysis.
 */
sealed trait NodeType

/** A node that introduces taint (e.g., user input, external data sources) */
case object SourceNode extends NodeType

/** A node that represents a potential vulnerability point (e.g., SQL query, file write) */
case object SinkNode extends NodeType

/** A regular program statement that propagates taint */
case object SimpleNode extends NodeType

/**
 * Represents a program statement node in the SVFA graph.
 * 
 * This is the primary node type used in static value flow analysis, containing
 * all necessary information about a program statement including its source location,
 * Soot representation, and taint analysis classification.
 * 
 * @param className The fully qualified class name containing this statement
 * @param method The method signature containing this statement  
 * @param stmt The string representation of the statement (usually Jimple)
 * @param line The source code line number (or -1 if unknown)
 * @param nodeType Classification as source, sink, or simple node
 * @param sootUnit The underlying Soot Unit (optional)
 * @param sootMethod The underlying Soot Method (optional)
 */
case class GraphNode(
    className: String,
    methodSignature: String,
    stmt: String,
    line: Int,
    nodeType: NodeType,
    sootUnit: soot.Unit = null,
    sootMethod: soot.SootMethod = null
) {

  /**
   * Returns a clean string representation for display purposes.
   * Removes quotes to avoid issues in DOT format and other outputs.
 */
  def show(): String = stmt.replace("\"", "'")
  
  /**
   * Returns the underlying Soot Unit for this node.
   */
  def unit(): soot.Unit = sootUnit
  
  /**
   * Returns the underlying Soot Method for this node.
   */
  def method(): soot.SootMethod = sootMethod

  override def toString: String =
    s"GraphNode($methodSignature, $stmt, $nodeType)"
}

/*
 * This trait define the base for all other labels classifications, like the NodeType
 * the LabelType is used to inform things relevant for the analysis like context sensitive
 * regions or field sensitive actions (store or load).
 */
trait LabelType

case object SimpleLabel extends LabelType {
  def instance: SimpleLabel.type = this
}

sealed trait CallSiteLabelType extends LabelType
case object CallSiteOpenLabel extends CallSiteLabelType {
  def instance: CallSiteOpenLabel.type = this
}
case object CallSiteCloseLabel extends CallSiteLabelType {
  def instance: CallSiteCloseLabel.type = this
}

/*
 * Like the graph nodes, the edge labels can be customized and this trait
 * define the abstraction needed to possibility the customization,
 * acting as a container to hold the labels data inside the value attribute.
 */
trait EdgeLabel {
  type T
  var value: T
  val labelType: LabelType
}

case class StringLabel(label: String) extends EdgeLabel {
  override type T = String
  override var value = label
  override val labelType: LabelType = SimpleLabel
}

/**
 * Represents a context-sensitive region for interprocedural analysis.
 * Used to track method call contexts in the SVFA graph.
 *
 * `context` is a *reference* to the call's base local's already-solved
 * points-to set (from Soot/Spark's PAG) — never materialized into a Scala
 * collection here. `isValidContext` uses `PointsToSet.hasNonEmptyIntersection`
 * (Soot's own native set operation) to decide path validity, instead of
 * comparing an arbitrary single representative for equality, or paying the
 * O(points-to-set size) cost of copying it into a Scala Set.
 */
case class ContextSensitiveRegion(
    statement: GraphNode,
    calleeMethod: String,
    context: Option[soot.PointsToSet]
)

case class CallSiteLabel(
    csRegion: ContextSensitiveRegion,
    labelType: CallSiteLabelType
) extends EdgeLabel {
  override type T = ContextSensitiveRegion
  override var value = csRegion

  def matchCallStatement(otherLabel: Any): Boolean = {
    otherLabel match {
      case defaultCSLabel: CallSiteLabel =>
        val csLabel = defaultCSLabel.value
        // Match close with open OR open with close
        if (labelType != defaultCSLabel.labelType) {
          return value.statement == csLabel.statement
        } else {
          false
        }
      case _ => false
    }
  }

  def matchCalleeMethod(otherLabel: Any): Boolean = {
    otherLabel match {
      case defaultCSLabel: CallSiteLabel =>
        val csLabel = defaultCSLabel.value
        // Match close with open OR open with close
        if (labelType != defaultCSLabel.labelType) {
          return value.calleeMethod == csLabel.calleeMethod
        } else {
          false
        }
      case _ => false
    }
  }

  override def equals(o: Any): Boolean = {
    o match {
      case csLabel: CallSiteLabel =>
        return value == csLabel.value && labelType == csLabel.labelType
      case _ => false
    }
  }
}

case class GraphEdge(from: GraphNode, to: GraphNode, label: EdgeLabel)

class Graph() {
  val graph = scalax.collection.mutable.Graph.empty[GraphNode, LkDiEdge]

  var fullGraph: Boolean = false
  var allPaths: Boolean = false
  var optimizeGraph: Boolean = false
  var permitedReturnEdge: Boolean = false

  def enableReturnEdge(): Unit = {
    permitedReturnEdge = true
  }

  def gNode(outerNode: GraphNode): graph.NodeT = graph.get(outerNode)
  def gEdge(outerEdge: LkDiEdge[GraphNode]): graph.EdgeT = graph.get(outerEdge)

  def contains(node: GraphNode): Boolean = graph.find(node).isDefined

  def addNode(node: GraphNode): Unit = graph.add(node)

  def addEdge(source: GraphNode, target: GraphNode): Unit =
    addEdge(source, target, StringLabel("Normal"))

  def addEdge(source: GraphNode, target: GraphNode, label: EdgeLabel): Unit = {
    if (source == target && !permitedReturnEdge) {
      return
    }

    implicit val factory = scalax.collection.edge.LkDiEdge
    graph.addLEdge(source, target)(label)
  }

  def getAdjacentNodes(node: GraphNode): Option[Set[GraphNode]] = {
    if (contains(node)) {
      return Some(gNode(node).diSuccessors.map(_node => _node.toOuter))
    }
    return None
  }

  def getIgnoredNodes(): HashSet[GraphNode] = {
    var ignoredNodes = HashSet.empty[GraphNode]
    var countChanges = 51
    while (countChanges > 50) {
      countChanges = 0
      this
        .nodes()
        .diff(ignoredNodes)
        .foreach(n => {
          val gNode = this.gNode(n)
          val hasValidSuccessors = gNode.diSuccessors
            .exists(gSuccessor => !ignoredNodes(gSuccessor.toOuter))
          val hasValidPredecessors = gNode.diPredecessors
            .exists(gPredecessor => !ignoredNodes(gPredecessor.toOuter))

          if (hasValidSuccessors || hasValidPredecessors) {
            n.nodeType match {
              case SourceNode =>
                if (!hasValidSuccessors) {
                  ignoredNodes = ignoredNodes + n
                  countChanges += 1
                }
              case SinkNode =>
                if (!hasValidPredecessors) {
                  ignoredNodes = ignoredNodes + n
                  countChanges += 1
                }
              case _ =>
                if (!(hasValidPredecessors && hasValidSuccessors)) {
                  ignoredNodes = ignoredNodes + n
                  countChanges += 1
                }
            }
          } else {
            ignoredNodes = ignoredNodes + n
            countChanges += 1
          }
        })
    }

    return ignoredNodes
  }

  def findPathsFullGraph(): List[List[GraphNode]] = {
    val ignoredNodes = getIgnoredNodes()

    if (this.optimizeGraph) {
      ignoredNodes.foreach(node => this.graph.remove(node))
      println("Optimize: " + ignoredNodes.size + " removed nodes.")
    }

    var sourceNodes = HashSet.empty[GraphNode]
    var sinkNodes = HashSet.empty[GraphNode]
    this
      .nodes()
      .diff(ignoredNodes)
      .foreach(n => {
        n.nodeType match {
          case SourceNode => sourceNodes = sourceNodes + n
          case SinkNode   => sinkNodes = sinkNodes + n
          case _          => ()
        }
      })

    val maxConflictsNumber = sinkNodes.size
    var paths = List.empty[List[GraphNode]]
    for (sourceNode <- sourceNodes; sinkNode <- sinkNodes) {
      if (paths.size >= maxConflictsNumber)
        return paths

      val foundedPaths = findPathsOP(
        sourceNode,
        sourceNode,
        sinkNode,
        HashSet(sourceNode),
        ignoredNodes,
        maxConflictsNumber
      )
      val validPaths =
        foundedPaths.filter(path => isValidPath(sourceNode, sinkNode, path))
      if (validPaths.nonEmpty)
        paths = paths ++ List(validPaths.head)
    }

    return paths
  }

  def findPathsOP(
      sourceNode: GraphNode,
      currentNode: GraphNode,
      sinkNode: GraphNode,
      visitedNodes: HashSet[GraphNode],
      ignoredNodes: HashSet[GraphNode],
      maxConflictsNumber: Int
  ): List[List[GraphNode]] = {
    var paths = List.empty[List[GraphNode]]

    var validSuccessors = this
      .gNode(currentNode)
      .diSuccessors
      .map(gSuccessor => gSuccessor.toOuter)

    if (this.optimizeGraph) {
      validSuccessors = validSuccessors.diff(ignoredNodes)
    }

    validSuccessors = validSuccessors.diff(visitedNodes)

    if (allPaths) {
      validSuccessors.foreach(successor => {
        if (paths.size < maxConflictsNumber) {
          if (successor == sinkNode) {
            val path = (visitedNodes + successor).toList
            paths = paths ++ List(path)
          } else {
            val foundedPaths =
              findPathsOP(
                sourceNode,
                successor,
                sinkNode,
                visitedNodes + successor,
                ignoredNodes,
                maxConflictsNumber
              )
            paths = paths ++ foundedPaths
          }
        }
      })
    } else {
      if (validSuccessors.contains(sinkNode)) {
        val path = (visitedNodes + sinkNode).toList
        if (isValidPath(sourceNode, sinkNode, path)) {
          paths = paths ++ List(path)
        }
      } else {
        validSuccessors.foreach(successor => {
          if (paths.size < maxConflictsNumber) {
            val foundedPaths =
              findPathsOP(
                sourceNode,
                successor,
                sinkNode,
                visitedNodes + successor,
                ignoredNodes,
                maxConflictsNumber
              )
            paths = paths ++ foundedPaths
          }
        })
      }
    }

    return paths
  }

  def isValidPath(
      sourceNode: GraphNode,
      sinkNode: GraphNode,
      path: List[GraphNode]
  ): Boolean = {
    val gPath = this
      .gNode(sourceNode)
      .withSubgraph(node => path.contains(node.toOuter))
      .pathTo(this.gNode(sinkNode))
    return isValidPath(gPath.get)
  }

  def findPath(source: GraphNode, target: GraphNode): List[List[GraphNode]] = {
    val fastPath = gNode(source).pathTo(gNode(target))
    val findAllConflictPaths = false

    if (
      !findAllConflictPaths && fastPath.isDefined && isValidPath(fastPath.get)
    ) {
      return List(fastPath.get.nodes.map(node => node.toOuter).toList)
    }

    val pathBuilder = graph.newPathBuilder(gNode(source))
    val paths =
      findPaths(source, target, HashSet[GraphNode](), pathBuilder, List())
    val validPaths = paths.filter(path => isValidPath(path))
    return validPaths.map(path => path.nodes.map(node => node.toOuter).toList)
  }

  def findPaths(
      source: GraphNode,
      target: GraphNode,
      visited: HashSet[GraphNode],
      currentPath: graph.PathBuilder,
      paths: List[graph.Path]
  ): List[graph.Path] = {
    // TODO: find some optimal way to travel in graph
    val adjacencyList = gNode(source).diSuccessors.map(_node => _node.toOuter)
    if (adjacencyList.contains(target)) {
      currentPath += gNode(target)
      //      return paths ++ List(currentPath.result)
      return List(currentPath.result)
    }

    adjacencyList.foreach(next => {
      if (!visited(next)) {
        var nextPath = currentPath
        nextPath += gNode(next)
        return findPaths(next, target, visited + next, nextPath, paths)
      }
    })
    return List()

  }

  def getUnmatchedCallSites(
      source: List[CallSiteLabel],
      target: List[CallSiteLabel]
  ): List[CallSiteLabel] = {
    var unvisitedTargets = target
    var unmatched = List.empty[CallSiteLabel]

    // verify if exists cs) edges without a (cs
    // or if exists (cs edges without a cs)
    source.foreach(s => {
      var matchedCS = List.empty[CallSiteLabel]
      var unmatchedCS = List.empty[CallSiteLabel]
      unvisitedTargets.foreach(label => {
        if (label.matchCallStatement(s))
          matchedCS = matchedCS ++ List(label)
        else
          unmatchedCS = unmatchedCS ++ List(label)
      })

      if (matchedCS.size > 0) {
        unvisitedTargets = unmatchedCS ++ matchedCS.init
      } else {
        unvisitedTargets = unmatchedCS
        unmatched = unmatched ++ List(s)
      }
    })

    return unmatched
  }

  def isValidPath(path: graph.Path): Boolean = {
    var csOpen = List.empty[CallSiteLabel]
    var csClose = List.empty[CallSiteLabel]

    // Filter the labels by type
    path.edges.foreach(edge => {
      val label = edge.toOuter.label

      label match {
        case l: CallSiteLabel => {
          if (l.labelType == CallSiteOpenLabel)
            csOpen = csOpen ++ List(l)
          else
            csClose = csClose ++ List(l)
        }
        case _ => {}
      }
    })

    // check if there path has only one context
    if (!isValidContext(csOpen, csClose)) {
      return false
    }

    // Get all the cs) without a (cs
    val unopenedCS = getUnmatchedCallSites(csClose, csOpen)
    // Get all the cs) without a (cs
    val unclosedCS = getUnmatchedCallSites(csOpen, csClose)

    // verify if the unopened and unclosed call-sites are not for the same method
    var matchedUnopenedUnclosedCSCalleeMethod
        : List[(CallSiteLabel, CallSiteLabel)] = List()
    unclosedCS.foreach(_csOpen => {
      unopenedCS
        .filter(label => label.matchCalleeMethod(_csOpen))
        .foreach(_csClose => {
          matchedUnopenedUnclosedCSCalleeMethod =
            matchedUnopenedUnclosedCSCalleeMethod ++ List((_csOpen, _csClose))
        })
    })

    val validCS =
      unopenedCS.isEmpty || unclosedCS.isEmpty || matchedUnopenedUnclosedCSCalleeMethod.isEmpty

    return validCS
  }

  /**
   * A candidate path is valid if some single allocation site could explain
   * every hop along it — i.e. the allocation-site sets (points-to sets) of
   * all its context-sensitive edges have a non-empty intersection. Edges
   * with no allocation-site info (empty context) are neutral: they neither
   * narrow nor validate the running intersection, since they carry no
   * object-identity information one way or the other.
   *
   * This replaces an earlier "pick one arbitrary allocation site per edge,
   * require exact equality across the whole path" heuristic that rejected
   * real flows whenever two unrelated edges happened to pick different
   * (but still overlapping, in their full sets) representatives.
   */
  def isValidContext(
      csOpen: List[CallSiteLabel],
      csClose: List[CallSiteLabel]
  ): Boolean = {
    val csOpenAndClose = csOpen ++ csClose

    // Anchor on the first edge that carries a points-to set, then require
    // every other edge's points-to set to share at least one possible
    // object with it — via Soot's own native PointsToSet intersection, not
    // by materializing/copying either set into a Scala collection. Edges
    // with no points-to info (None) are neutral: CHA never has one, and
    // they carry no object-identity information either way.
    var anchor: Option[soot.PointsToSet] = None
    var valid = true
    csOpenAndClose.foreach(label => {
      if (valid) {
        label.value.context.foreach(pts => {
          anchor match {
            case None    => anchor = Some(pts)
            case Some(a) => if (!a.hasNonEmptyIntersection(pts)) valid = false
          }
        })
      }
    })

    valid
  }

  def nodes(): scala.collection.Set[GraphNode] =
    graph.nodes.map(node => node.toOuter).toSet

  def edges(): scala.collection.Set[GraphEdge] = graph.edges
    .map(edge => {
      val from = edge._1.toOuter
      val to = edge._2.toOuter
      val label = edge.toOuter.label.asInstanceOf[EdgeLabel]

      GraphEdge(from, to, label)
    })
    .toSet

  def numberOfNodes(): Int = graph.nodes.size

  def numberOfEdges(): Int = graph.edges.size
  /**
   * Creates a graph node from a Soot method and statement.
   * 
   * @param method The Soot method containing the statement
   * @param stmt The Soot unit/statement
   * @param f Function to determine the node type (source, sink, or simple)
   * @return A new GraphNode representing this statement
   */
  def createNode(
      method: SootMethod,
      stmt: soot.Unit,
      f: (soot.Unit) => NodeType
  ): GraphNode =
    GraphNode(
      className = method.getDeclaringClass.toString,
      methodSignature = method.getSignature,
      stmt = stmt.toString,
      line = stmt.getJavaSourceStartLineNumber,
      nodeType = f(stmt),
      sootUnit = stmt,
      sootMethod = method
    )

  def reportConflicts(): scala.collection.Set[List[GraphNode]] = findConflictingPaths()

  /**
   * Reports unique conflicts by merging duplicate Jimple statements from the same source location.
   * This is the recommended method for conflict reporting as it eliminates duplicates caused
   * by Java-to-Jimple translation generating multiple instructions per source line.
   */
  def reportUniqueConflicts(): scala.collection.Set[List[GraphNode]] = findUniqueConflictingPaths()

  def findConflictingPaths(): scala.collection.Set[List[GraphNode]] = {
    if (fullGraph) {
      val conflicts = findPathsFullGraph()
      conflicts.toSet
    } else {
      val sourceNodes = nodes.filter(n => n.nodeType == SourceNode)
      val sinkNodes = nodes.filter(n => n.nodeType == SinkNode)

      var conflicts: List[List[GraphNode]] = List()

      sourceNodes.foreach(source => {
        sinkNodes.foreach(sink => {
          val paths = findPath(source, sink)
          if (paths.nonEmpty) {
            conflicts = conflicts ++ paths
          }
        })
      })
      conflicts.filter(p => p.nonEmpty).toSet
    }
  }

  /**
   * Finds unique conflicting paths by merging Jimple statements that originate 
   * from the same Java source location (class, method, line number).
   * 
   * This addresses the issue where a single Java line generates multiple Jimple
   * instructions, leading to duplicate conflict reports. The method groups nodes
   * by their source location and returns one representative path per unique conflict.
   * 
   * @return Set of unique conflict paths with merged source locations
   */
  def findUniqueConflictingPaths(): scala.collection.Set[List[GraphNode]] = {
    val allConflicts = findConflictingPaths()
    
    // Group conflicts by their source location signature (class + method + source-to-sink line range)
    val uniqueConflicts = allConflicts
      .map(mergeNodesWithSameSourceLocation)
      .groupBy(getConflictSignature)
      .values
      .map(_.head) // Take one representative from each group
      .toSet
    
    uniqueConflicts
  }

  /**
   * Merges consecutive nodes in a path that have the same source location
   * (class, method, line number), keeping only one representative node per location.
   */
  private def mergeNodesWithSameSourceLocation(path: List[GraphNode]): List[GraphNode] = {
    if (path.isEmpty) return path
    
    val mergedPath = scala.collection.mutable.ListBuffer[GraphNode]()
    var currentLocation: Option[SourceLocation] = None
    
    path.foreach { node =>
      val nodeLocation = getSourceLocation(node)
      
      if (currentLocation.isEmpty || currentLocation.get != nodeLocation) {
        // New source location - add this node
        mergedPath += node
        currentLocation = Some(nodeLocation)
      } else {
        // Same source location as previous node
        // Keep the more "important" node (source > sink > simple)
        val lastNode = mergedPath.last
        if (isMoreImportantNode(node, lastNode)) {
          mergedPath(mergedPath.length - 1) = node
        }
      }
    }
    
    mergedPath.toList
  }

  /**
   * Extracts source location information from a graph node.
   */
  private def getSourceLocation(node: GraphNode): SourceLocation = {
    SourceLocation(
      className = node.className,
      method = node.methodSignature,
      line = node.line
    )
  }

  /**
   * Generates a unique signature for a conflict path based on source and sink locations.
   * This helps identify duplicate conflicts that span the same source locations.
   */
  private def getConflictSignature(path: List[GraphNode]): ConflictSignature = {
    if (path.isEmpty) {
      return ConflictSignature(SourceLocation("", "", -1), SourceLocation("", "", -1))
    }
    
    val sourceNodes = path.filter(_.nodeType == SourceNode)
    val sinkNodes = path.filter(_.nodeType == SinkNode)
    
    val sourceLocation = if (sourceNodes.nonEmpty) getSourceLocation(sourceNodes.head) 
                        else getSourceLocation(path.head)
    val sinkLocation = if (sinkNodes.nonEmpty) getSourceLocation(sinkNodes.last)
                      else getSourceLocation(path.last)
    
    ConflictSignature(sourceLocation, sinkLocation)
  }

  /**
   * Determines if one node is more "important" than another for conflict reporting.
   * Priority: SourceNode > SinkNode > SimpleNode
   */
  private def isMoreImportantNode(node1: GraphNode, node2: GraphNode): Boolean = {
    val priority1 = getNodePriority(node1)
    val priority2 = getNodePriority(node2)
    priority1 > priority2
  }

  /**
   * Assigns priority values to node types for importance comparison.
   */
  private def getNodePriority(node: GraphNode): Int = node.nodeType match {
    case SourceNode => 3
    case SinkNode   => 2
    case SimpleNode => 1
  }

  /**
   * Represents a source code location for merging duplicate Jimple statements.
   */
  private case class SourceLocation(
      className: String,
      method: String, 
      line: Int
  )

  /**
   * Represents a unique conflict signature based on source and sink locations.
   */
  private case class ConflictSignature(
      sourceLocation: SourceLocation,
      sinkLocation: SourceLocation
  )

  def toDotModel(): String = {
    val s = new StringBuilder
    var nodeColor = ""
    s ++= "digraph { \n"

    for (n <- nodes) {
      nodeColor = n.nodeType match {
        case SourceNode => "[fillcolor=blue, style=filled]"
        case SinkNode   => "[fillcolor=red, style=filled]"
        case _          => ""
      }

      s ++= " " + "\"" + n.show() + "\"" + nodeColor + "\n"
    }

    s ++= "\n"

    for (e <- edges) {
      val edge =
        "\"" + e.from.show() + "\"" + " -> " + "\"" + e.to.show() + "\""
      var l = e.label
      val label: String = e.label match {
        case c: CallSiteLabel => {
          // Display only: `context` is now a live PointsToSet reference,
          // not enumerable cheaply — just note presence.
          val contextLabel = if (c.value.context.nonEmpty) "pts" else ""
          if (c.labelType == CallSiteOpenLabel) {
            s"""[label="CS([$contextLabel]"]"""
          } else {
            s"""[label="CS)[$contextLabel]"]"""
          }
        }
        case c: TrueLabelType  => { "[penwidth=3][label=\"T\"]" }
        case c: FalseLabelType => { "[penwidth=3][label=\"F\"]" }
        case c: DefLabelType   => { "[style=dashed, color=black]" }
        case _                 => ""
      }
      s ++= " " + edge + " " + label + "\n"
    }
    s ++= "}"
    s.toString()
  }

}




