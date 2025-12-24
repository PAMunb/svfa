package br.unb.cic.soot.svfa.jimple.dsl

import br.unb.cic.soot.svfa.jimple.JSVFA
import br.unb.cic.soot.svfa.jimple.rules._

import scala.collection.mutable.HashMap

/**
 * Factory for creating method rules with associated actions.
 * Now uses standalone rule actions instead of JSVFA-dependent traits.
 */
class RuleFactory(val jsvfa: JSVFA) {
  def create(
      rule: String,
      actions: List[String],
      params: HashMap[String, String] = HashMap.empty[String, String],
      definitions: HashMap[String, HashMap[String, Int]] =
        HashMap.empty[String, HashMap[String, Int]]
  ): MethodRule = {

    var ruleActions = List.empty[RuleAction]
    actions.foreach(action => {
      action match {
        case "DoNothing" =>
          ruleActions = ruleActions ++ List(new DoNothing {})
          
        case "CopyBetweenArgs" =>
          val fromArg = definitions(action)("from")
          val targetArg = definitions(action)("target")
          ruleActions = ruleActions ++ List(RuleActions.CopyBetweenArgs(fromArg, targetArg))

        case "CopyFromMethodArgumentToBaseObject" =>
          val fromArg = definitions(action)("from")
          ruleActions = ruleActions ++ List(RuleActions.CopyFromMethodArgumentToBaseObject(fromArg))
          
        case "CopyFromMethodArgumentToLocal" =>
          val fromArg = definitions(action)("from")
          ruleActions = ruleActions ++ List(RuleActions.CopyFromMethodArgumentToLocal(fromArg))
          
        case "CopyFromMethodCallToLocal" =>
          ruleActions = ruleActions ++ List(RuleActions.CopyFromMethodCallToLocal())
          
        case "CopyFromBaseObjectToLocal" =>
          ruleActions = ruleActions ++ List(RuleActions.CopyFromBaseObjectToLocal())
          
        case _ =>
          ruleActions = ruleActions ++ List(new DoNothing {})
      }
    })

    rule match {
      case "NativeRule" => {
        new NativeRule with ComposedRuleAction {
          override def actions: List[RuleAction] = ruleActions
        }
      }
      case "MissingActiveBodyRule" => {
        new MissingActiveBodyRule with ComposedRuleAction {
          override def actions: List[RuleAction] = ruleActions
        }
      }
      case "NamedMethodRule" => {
        new NamedMethodRule(params("className"), params("methodName"))
          with ComposedRuleAction {
          override def actions: List[RuleAction] = ruleActions
        }
      }
    }

  }
}
