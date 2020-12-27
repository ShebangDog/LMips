package back

import front.{AST, IR}

object MipsGenerator {
  def generateMain(nodeList: List[AST.Node]): List[IR.Mips] = {
    val list = generateProgram(nodeList)

    IR.Text :: list
  }

  def generate(nodeList: List[AST.Node]): List[IR.Mips] = {
    val (declare, rest) = generateProgram(nodeList).partition {
      case IR.DeclareValue(_, _) => true
      case _ => false
    }

    declare ::: rest
  }

  def generateProgram(nodeList: List[AST.Node]): List[IR.Mips] = nodeList.flatMap {
    case stmt: AST.Statement => List(generateStatement(stmt))
    case expr: AST.Expression => generateExpression(expr)
  }

  def generateStatement(statement: AST.Statement): IR.Mips = statement match {
    case AST.DeclareValue(identity, expr) => IR.DeclareValue(identity.name, generateExpression(expr))

    case AST.Println(expr) => IR.PrintInt(generateExpression(expr))

    case AST.DeclareFunction(identity, paramList, body) => IR.DeclareFunction(identity.name, generateExpression(body))
  }

  def generateExpression(expression: AST.Expression): List[IR.Mips] = expression match {
    case AST.Number(value) => List(IR.Number(value))
    case AST.Ident(name) => List(IR.Ident(name))
    case AST.Block(nodeList) => generate(nodeList)
    case arithmetic: AST.Arithmetic => List(generateArithmetic(arithmetic))
  }

  def generateArithmetic(arithmetic: AST.Arithmetic): IR.Mips = arithmetic match {
    case AST.Addition(left, right) => IR.Addition(generateExpression(left), generateExpression(right))
    case AST.Subtraction(left, right) => IR.Subtraction(generateExpression(left), generateExpression(right))
    case AST.Multiplication(left, right) => IR.Multiplication(generateExpression(left), generateExpression(right))
    case AST.Division(left, right) => IR.Division(generateExpression(left), generateExpression(right))
  }

}
