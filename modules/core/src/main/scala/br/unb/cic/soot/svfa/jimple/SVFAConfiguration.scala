package br.unb.cic.soot.svfa.jimple

/**
 * Unified configuration system for SVFA analysis.
 * 
 * This replaces the trait-based configuration with a more flexible
 * attribute-based system while maintaining backward compatibility.
 */

// ============================================================================
// CALL GRAPH CONFIGURATION
// ============================================================================

/**
 * Call graph algorithm configuration.
 * Supports various call graph construction algorithms including SPARK variants.
 */
sealed trait CallGraphAlgorithm {
  def name: String
  def description: String
}

object CallGraphAlgorithm {
  case object Spark extends CallGraphAlgorithm {
    override def name: String = "SPARK"
    override def description: String = "SPARK points-to analysis (most precise, slower)"
  }
  
  case object CHA extends CallGraphAlgorithm {
    override def name: String = "CHA"
    override def description: String = "Class Hierarchy Analysis (fastest, least precise)"
  }
  
  case object SparkLibrary extends CallGraphAlgorithm {
    override def name: String = "SPARK_LIBRARY"
    override def description: String = "SPARK with library support (comprehensive coverage)"
  }
  
  case object RTA extends CallGraphAlgorithm {
    override def name: String = "RTA"
    override def description: String = "Rapid Type Analysis via SPARK (fast, moderately precise)"
  }
  
  case object VTA extends CallGraphAlgorithm {
    override def name: String = "VTA"
    override def description: String = "Variable Type Analysis via SPARK (balanced speed/precision)"
  }
  
  def fromString(algorithm: String): CallGraphAlgorithm = algorithm.toLowerCase match {
    case "spark" => Spark
    case "cha" => CHA
    case "spark_library" | "sparklibrary" | "spark-library" => SparkLibrary
    case "rta" => RTA
    case "vta" => VTA
    case _ => throw new IllegalArgumentException(
      s"Unsupported call graph algorithm: $algorithm. " +
      s"Supported algorithms: ${availableNames.mkString(", ")}"
    )
  }
  
  /**
   * Get all available call graph algorithms.
   */
  def all: List[CallGraphAlgorithm] = List(Spark, CHA, SparkLibrary, RTA, VTA)
  
  /**
   * Get algorithm names for help/documentation.
   */
  def availableNames: List[String] = all.map(_.name.toLowerCase)
}

// ============================================================================
// SVFA CONFIGURATION
// ============================================================================

/**
 * Complete SVFA configuration.
 * Can be used for constructor injection, config files, or runtime modification.
 */
case class SVFAConfig(
  interprocedural: Boolean = true,
  fieldSensitive: Boolean = true,
  propagateObjectTaint: Boolean = true,
  callGraphAlgorithm: CallGraphAlgorithm = CallGraphAlgorithm.Spark
) {
  
  /**
   * Convenience methods for common configurations.
   */
  def withInterprocedural: SVFAConfig = copy(interprocedural = true)
  def withIntraprocedural: SVFAConfig = copy(interprocedural = false)
  def withFieldSensitive: SVFAConfig = copy(fieldSensitive = true)
  def withFieldInsensitive: SVFAConfig = copy(fieldSensitive = false)
  def withTaintPropagation: SVFAConfig = copy(propagateObjectTaint = true)
  def withoutTaintPropagation: SVFAConfig = copy(propagateObjectTaint = false)
  def withCallGraph(algorithm: CallGraphAlgorithm): SVFAConfig = copy(callGraphAlgorithm = algorithm)
}

object SVFAConfig {
  /**
   * Default configuration (interprocedural, field-sensitive, propagate taint, SPARK call graph).
   */
  val Default = SVFAConfig()
  
  /**
   * Fast configuration (intraprocedural, field-insensitive, propagate taint, SPARK call graph).
   */
  val Fast = SVFAConfig(
    interprocedural = false,
    fieldSensitive = false,
    propagateObjectTaint = true,
    callGraphAlgorithm = CallGraphAlgorithm.Spark
  )
  
  /**
   * Precise configuration (interprocedural, field-sensitive, propagate taint, SPARK call graph).
   */
  val Precise = SVFAConfig(
    interprocedural = true,
    fieldSensitive = true,
    propagateObjectTaint = true,
    callGraphAlgorithm = CallGraphAlgorithm.Spark
  )
  
  // ============================================================================
  // CALL GRAPH SPECIFIC CONFIGURATIONS
  // ============================================================================
  
  /**
   * CHA-based configuration (interprocedural, field-sensitive, propagate taint, CHA call graph).
   * Faster but less precise than SPARK.
   */
  val WithCHA = SVFAConfig(
    interprocedural = true,
    fieldSensitive = true,
    propagateObjectTaint = true,
    callGraphAlgorithm = CallGraphAlgorithm.CHA
  )
  
  /**
   * SPARK Library configuration (interprocedural, field-sensitive, propagate taint, SPARK_LIBRARY call graph).
   * SPARK with library support for better coverage.
   */
  val WithSparkLibrary = SVFAConfig(
    interprocedural = true,
    fieldSensitive = true,
    propagateObjectTaint = true,
    callGraphAlgorithm = CallGraphAlgorithm.SparkLibrary
  )
  
  /**
   * Fast CHA configuration (intraprocedural, field-insensitive, propagate taint, CHA call graph).
   * Fastest possible configuration.
   */
  val FastCHA = SVFAConfig(
    interprocedural = false,
    fieldSensitive = false,
    propagateObjectTaint = true,
    callGraphAlgorithm = CallGraphAlgorithm.CHA
  )
  
  /**
   * Create configuration from string values (useful for CLI args, config files).
   */
  def fromStrings(
    interprocedural: String,
    fieldSensitive: String,
    propagateObjectTaint: String,
    callGraphAlgorithm: String = "spark"
  ): SVFAConfig = SVFAConfig(
    interprocedural.toLowerCase == "true",
    fieldSensitive.toLowerCase == "true",
    propagateObjectTaint.toLowerCase == "true",
    CallGraphAlgorithm.fromString(callGraphAlgorithm)
  )
}

// Note: The existing Analysis, FieldSensitiveness, ObjectPropagation traits and their
// implementations (Interprocedural, FieldSensitive, PropagateTaint, etc.) are defined 
// in separate files and remain unchanged for backward compatibility.

// ============================================================================
// CONFIGURABLE TRAITS (NEW FLEXIBLE APPROACH)
// ============================================================================

/**
 * Mixin trait that adds runtime configuration capabilities to JSVFA.
 * Use this when you want to configure analysis settings at runtime.
 * 
 * This trait works with the existing Analysis, FieldSensitiveness, and ObjectPropagation
 * trait signatures while adding configuration flexibility.
 */
trait ConfigurableAnalysis extends Analysis with FieldSensitiveness with ObjectPropagation {
  private var _config: SVFAConfig = SVFAConfig.Default
  
  // Implement existing trait methods using the configuration
  override def interprocedural(): Boolean = _config.interprocedural
  override def isFieldSensitiveAnalysis(): Boolean = _config.fieldSensitive
  override def propagateObjectTaint(): Boolean = _config.propagateObjectTaint
  
  /**
   * Set the complete configuration.
   */
  def setConfig(config: SVFAConfig): Unit = {
    _config = config
  }
  
  /**
   * Get the current configuration.
   */
  def getConfig: SVFAConfig = _config
  
  /**
   * Individual setters for convenience.
   */
  def setInterprocedural(interprocedural: Boolean): Unit = {
    _config = _config.copy(interprocedural = interprocedural)
  }
  
  def setFieldSensitive(fieldSensitive: Boolean): Unit = {
    _config = _config.copy(fieldSensitive = fieldSensitive)
  }
  
  def setPropagateObjectTaint(propagateObjectTaint: Boolean): Unit = {
    _config = _config.copy(propagateObjectTaint = propagateObjectTaint)
  }
  
  def setCallGraphAlgorithm(algorithm: CallGraphAlgorithm): Unit = {
    _config = _config.copy(callGraphAlgorithm = algorithm)
  }
}
