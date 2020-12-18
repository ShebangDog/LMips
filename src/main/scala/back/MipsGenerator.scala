package back

import front.{AST, IR}

object MipsGenerator {
  def generateProgram(nodeList: List[AST.Node]): List[IR.Mips] = IR.Declare("result", IR.Number(0), IR.Global) ::
    ((IR.Header :: nodeList.map {
      case stmt: AST.Statement => IR.None
      case expr: AST.Expression => generateExpression(expr)
    }) :+ IR.Footer)

  def generateExpression(expression: AST.Expression): IR.Mips = expression match {
    case AST.Number(value) => IR.Number(value)
    case AST.Ident(name) => IR.Number(name.length)
    case AST.Symbol(name, value) => IR.Number(12)
    case arithmetic: AST.Arithmetic => generateArithmetic(arithmetic)
  }

  def generateArithmetic(arithmetic: AST.Arithmetic): IR.Mips = arithmetic match {
    case AST.Addition(left, right) => IR.Addition(generateExpression(left), generateExpression(right))
    case AST.Subtraction(left, right) => ???
    case AST.Multiplication(left, right) => ???
    case AST.Division(left, right) => ???
  }

}
