package back

import front.{AST, IR}

object MipsGenerator {
  def generate(nodeList: List[AST.Node]): List[IR.Mips] = {
    val (declare, rest) = generateProgram(nodeList).partition {
      case IR.Declare(_, _) => true
      case _ => false
    }

    (IR.Data :: declare) ::: ((IR.Header :: rest) :+ IR.Footer)
  }

  def generateProgram(nodeList: List[AST.Node]): List[IR.Mips] = nodeList.flatMap {
    case stmt: AST.Statement => generateStatement(stmt)
    case expr: AST.Expression => List(generateExpression(expr))
  }

  def generateStatement(statement: AST.Statement): List[IR.Mips] = statement match {
    case AST.Declare(identity, expr) => List(
      IR.Declare(identity.name, IR.Global),
      generateExpression(expr),
      IR.Store(identity.name)
    )

    case AST.Println(expr) => List(IR.PrintInt(generateExpression(expr)))

  }

  def generateExpression(expression: AST.Expression): IR.Mips = expression match {
    case AST.Number(value) => IR.Number(value)
    case AST.Ident(name) => IR.Ident(name)
    case arithmetic: AST.Arithmetic => generateArithmetic(arithmetic)
  }

  def generateArithmetic(arithmetic: AST.Arithmetic): IR.Mips = arithmetic match {
    case AST.Addition(left, right) => IR.Addition(generateExpression(left), generateExpression(right))
    case AST.Subtraction(left, right) => IR.Subtraction(generateExpression(left), generateExpression(right))
    case AST.Multiplication(left, right) => IR.Multiplication(generateExpression(left), generateExpression(right))
    case AST.Division(left, right) => IR.Division(generateExpression(left), generateExpression(right))
  }

}
