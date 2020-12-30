package back

import front.{AST, IR}
import table.Table

object MipsGenerator {
  def generate(nodeList: List[AST.Node]): List[IR.Mips] = {
    val list = generateProgram(nodeList)

    (IR.Text :: list) :+ IR.PrintInt
  }

  def generateProgram(nodeList: List[AST.Node], table: Table = Table()): List[IR.Mips] = nodeList.flatMap {
    case stmt: AST.Statement => List(generateStatement(stmt, table))
    case expr: AST.Expression => generateExpression(expr, table)
  }

  def generateStatement(statement: AST.Statement, table: Table): IR.Statement = statement match {
    case AST.DeclareValue(identity, expr) => IR.DeclareValue(identity, generateExpression(expr, table), table)

    case AST.DeclareFunction(identity, paramList, body) =>
      val table = Table(paramList)

      IR.DeclareFunction(
        identity,
        paramList,
        generateExpression(body, table),
        table
      )
  }

  def generateExpression(expression: AST.Expression, table: Table): List[IR.Mips] = {
    def generateExpr: AST.Expression => List[IR.Mips] = expr => generateExpression(expr, table)

    expression match {
      case AST.Number(value) => List(IR.Number(value))
      case AST.Ident(name) => List(IR.Ident(name, table))
      case AST.CallFunction(identity, argumentList) => List(IR.CallFunction(identity, argumentList.flatMap(generateExpr)))
      case AST.Block(nodeList) => List(IR.Block(generateProgram(nodeList, table)))
      case arithmetic: AST.Arithmetic => List(generateArithmetic(arithmetic, table))
    }
  }

  def generateArithmetic(arithmetic: AST.Arithmetic, table: Table): IR.Arithmetic = {
    def generateExpr: AST.Expression => List[IR.Mips] = expr => generateExpression(expr, table)

    arithmetic match {
      case AST.Addition(left, right) => IR.Addition(generateExpr(left), generateExpr(right))
      case AST.Subtraction(left, right) => IR.Subtraction(generateExpr(left), generateExpr(right))
      case AST.Multiplication(left, right) => IR.Multiplication(generateExpr(left), generateExpr(right))
      case AST.Division(left, right) => IR.Division(generateExpr(left), generateExpr(right))
    }
  }

}
