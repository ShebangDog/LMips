package dog.shebang.back

import dog.shebang.front.{AST, IR}
import dog.shebang.front.AST.Node
import dog.shebang.table.Table

object MipsGenerator {
  def generate(nodeList: List[Node]): List[IR.Mips] = {
    val list = generateProgram(nodeList)

    (IR.Text :: list) :+ IR.PrintInt
  }

  def generateProgram(nodeList: List[AST.Node], table: Table = Table()): List[IR.Mips] = nodeList.map {
    case stmt: AST.Statement => generateStatement(stmt, table)
    case expr: AST.Expression => generateExpression(expr, table)
  }

  def generateStatement(statement: AST.Statement, table: Table): IR.Statement = statement match {
    case AST.DeclareValue(identity, expr) => IR.DeclareValue(identity, generateExpression(expr, table), table)

    case AST.DeclareFunction(identity, paramList, body, functionTable) =>
      paramList.foreach(functionTable.store)

      IR.DeclareFunction(
        identity,
        paramList,
        generateExpression(body, functionTable),
        functionTable
      )
  }

  def generateExpression(expression: AST.Expression, table: Table): IR.Expression = {
    def generateExpr: AST.Expression => IR.Mips = expr => generateExpression(expr, table)

    expression match {
      case AST.Number(value) => IR.Number(value)
      case AST.Ident(name) => IR.Ident(name, table)
      case AST.CallFunction(identity, argumentList) => IR.CallFunction(identity, argumentList.map(generateExpr))
      case AST.Block(nodeList) => IR.Block(generateProgram(nodeList, table))
      case AST.IfExpression(condition, ifTrue, ifFalse) => IR.IfExpression(generateExpr(condition), generateExpr(ifTrue), generateExpr(ifFalse))
      case arithmetic: AST.Arithmetic => generateArithmetic(arithmetic, table)
    }
  }

  def generateArithmetic(arithmetic: AST.Arithmetic, table: Table): IR.Arithmetic = {
    def generateExpr: AST.Expression => IR.Mips = expr => generateExpression(expr, table)

    arithmetic match {
      case AST.Addition(left, right) => IR.Addition(generateExpr(left), generateExpr(right))
      case AST.Subtraction(left, right) => IR.Subtraction(generateExpr(left), generateExpr(right))
      case AST.Multiplication(left, right) => IR.Multiplication(generateExpr(left), generateExpr(right))
      case AST.Division(left, right) => IR.Division(generateExpr(left), generateExpr(right))
      case AST.Equal(left, right) => IR.Equal(generateExpr(left), generateExpr(right))
      case AST.NotEqual(left, right) => IR.NotEqual(generateExpr(left), generateExpr(right))
      case AST.GreaterThan(left, right) => IR.GreaterThan(generateExpr(left), generateExpr(right))
      case AST.GreaterThanEqual(left, right) => IR.GreaterThanEqual(generateExpr(left), generateExpr(right))
      case AST.LessThan(left, right) => IR.LessThan(generateExpr(left), generateExpr(right))
      case AST.LessThanEqual(left, right) => IR.LessThanEqual(generateExpr(left), generateExpr(right))
    }
  }

}
