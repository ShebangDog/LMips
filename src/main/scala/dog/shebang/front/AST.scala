package dog.shebang.front

import dog.shebang.table.Table

object AST {

  sealed trait Node

  sealed class Statement extends Node

  case class DeclareValue(identity: Ident, expression: Expression) extends Statement

  // FIXME: ASTにTable持たすべきではない気がする
  // 消せるなら消す
  case class DeclareFunction(identity: Ident, paramList: List[AST.Ident], body: AST.Expression, table: Table = Table()) extends Statement

  sealed class Expression extends Node

  case class IfExpression(condition: Expression, ifTrue: Expression, ifFalse: Expression) extends Expression

  case class Number(value: Int) extends Expression

  case class Ident(name: String) extends Expression

  object Main extends Ident("main")

  case class CallFunction(identity: Ident, argumentList: List[AST.Expression]) extends Expression

  case class Block(nodeList: List[AST.Node]) extends Expression

  sealed abstract class Arithmetic(operator: String, left: AST.Expression, right: AST.Expression) extends Expression

  case class Addition(left: AST.Expression, right: AST.Expression) extends Arithmetic("+", left, right)

  case class Subtraction(left: AST.Expression, right: AST.Expression) extends Arithmetic("-", left, right)

  case class Multiplication(left: AST.Expression, right: AST.Expression) extends Arithmetic("*", left, right)

  case class Division(left: AST.Expression, right: AST.Expression) extends Arithmetic("/", left, right)

  case class Equal(left: AST.Expression, right: AST.Expression) extends Arithmetic("==", left, right)

  case class NotEqual(left: AST.Expression, right: AST.Expression) extends Arithmetic("!=", left, right)

  case class GreaterThan(left: AST.Expression, right: AST.Expression) extends Arithmetic(">", left, right)

  case class GreaterThanEqual(left: AST.Expression, right: AST.Expression) extends Arithmetic(">=", left, right)

  case class LessThan(left: AST.Expression, right: AST.Expression) extends Arithmetic("<", left, right)

  case class LessThanEqual(left: AST.Expression, right: AST.Expression) extends Arithmetic("<=", left, right)


  sealed trait Type {
    override def equals(obj: Any): Boolean = obj match {
      case AST.Any => true
      case _ => super.equals(obj)
    }
  }

  case object Any extends Type {
    override def equals(obj: Any): Boolean = obj match {
      case _: AST.Type => true
      case _ => false
    }
  }

  case object Int extends Type

  case object Unit extends Type

}
