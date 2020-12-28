import java.io.PrintWriter

import back.MipsGenerator
import front.Parser

import scala.util.Using

object Application {
  def main(args: Array[String]): Unit = {
    val nodeList = Parser.parseAll(Parser.program,
      """
        |
        | def main(p) = {
        |   print(plus(1, 2))
        | }
        |
        | def plus(left, right) = {
        |   left + right
        | }
        |
        |""".stripMargin)
      .map(MipsGenerator.generateMain)
      .get
      .map(_.genMips)
      .filter(_.nonEmpty)

    val writer = new PrintWriter("res.as")
    Using(writer) { writer =>
      for (line <- nodeList) writer.write(line + "\n")
    }

  }
}
