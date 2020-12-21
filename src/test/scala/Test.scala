import front.{AST, Parser}
import org.scalatest.FunSuite

class Test extends FunSuite {
  test("program") {

    val nodeList = Parser.parseAll(Parser.program,
      """  val result = 1 + 1 + 2 + 3
        |  val price = result
        |  val value = price - result
        |  val multiplication = 1 * 3 * 4
        |  val division = 20 / 3
        |
        |  print(division)
        |  print(1 + 1 + 2)
        |
        |""".stripMargin)
      .get

    nodeList.foreach(println)
  }

  test("expr") {

    val nodeList = Parser.parseAll(Parser.program,
      """  ( 1 )
        |  { 1 }
        |  1
        |  1 + 1
        |""".stripMargin)
      .get

    assert(nodeList == List(AST.Number(1), AST.Block(List(AST.Number(1))), AST.Number(1), AST.Addition(AST.Number(1), AST.Number(1))))
  }

  test("stmt") {
    val nodeList = Parser.parseAll(Parser.program,
      """  def main = {
        |    1
        |  }
        |
        |  def main = 1
        |  def main = 1 + 1
        |
        |  def main() = {
        |    1
        |  }
        |
        |  def main(param) = {
        |    1
        |  }
        |
        |  def main(param, param) = {
        |    1
        |  }
        |
        |""".stripMargin)
      .get

    assert(nodeList == List(
      AST.DeclareFunction(AST.Ident("main"), List(), AST.Block(List(AST.Number(1)))),
      AST.DeclareFunction(AST.Ident("main"), List(), AST.Number(1)),
      AST.DeclareFunction(AST.Ident("main"), List(), AST.Addition(AST.Number(1), AST.Number(1))),
      AST.DeclareFunction(AST.Ident("main"), List(), AST.Number(1)),
      AST.DeclareFunction(AST.Ident("main"), List(AST.Ident("param")), AST.Number(1)),
      AST.DeclareFunction(AST.Ident("main"), List(AST.Ident("param"), AST.Ident("param")), AST.Number(1)),
    ))
  }
}
