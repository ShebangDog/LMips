package front

object AST {

  trait Node

  sealed class Statement extends Node

  case class Declare(identity: Ident, expression: Expression) extends Statement

  case class Println(value: AST.Expression) extends Statement


  sealed class Expression extends Node

  case class Number(value: Int) extends Expression

  case class Ident(name: String) extends Expression

  sealed abstract class Arithmetic(operator: String, left: AST.Expression, right: AST.Expression) extends Expression

  case class Addition(left: AST.Expression, right: AST.Expression) extends Arithmetic("+", left, right)

  case class Subtraction(left: AST.Expression, right: AST.Expression) extends Arithmetic("-", left, right)

  case class Multiplication(left: AST.Expression, right: AST.Expression) extends Arithmetic("*", left, right)

  case class Division(left: AST.Expression, right: AST.Expression) extends Arithmetic("/", left, right)

}
