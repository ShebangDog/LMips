package dog.shebang

import java.io.PrintWriter

import back.MipsGenerator
import front.Parser

import scala.util.Using

object Application {
  def main(args: Array[String]): Unit = {
    val nodeList = Parser.parseAll(Parser.program,
      """
        | def digitsRecurse(chunk, result) = {
        |   val trimmed = chunk / 10
        |
        |   if (trimmed == 0) result + 1 else digitsRecurse(trimmed, result + 1)
        | }
        |
        | def digits(chunk) = digitsRecurse(chunk, 0)
        |
        | def main() = {
        |   val result = digits(0)
        |   print(result)
        | }
        |
        |""".stripMargin)
      .map(MipsGenerator.generate)
      .get
      .map(_.genMips)
      .filter(_.nonEmpty)

    val writer = new PrintWriter("res.as")
    Using(writer) { writer =>
      for (line <- nodeList) writer.write(line + "\n")
    }

  }
}
