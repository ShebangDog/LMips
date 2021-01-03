import java.io.PrintWriter

import back.MipsGenerator
import front.Parser

import scala.util.Using

object Application {
  def main(args: Array[String]): Unit = {
    val nodeList = Parser.parseAll(Parser.program,
      """
        | def main() = {
        |
        |   val value = 1
        |   val result = {
        |     print(value)
        |     print(1)
        |     1000
        |     1 * 3
        |   }
        |
        |   val equal = value == 1
        |   print(equal)
        |
        |   val notEqual = value != 1
        |   print(notEqual)
        |
        |   val greater = value > 1
        |   print(greater)
        |
        |   val greaterEqual = value >= 1
        |   print(greaterEqual)
        |
        |   val less = value < 1
        |   print(less)
        |
        |   val lessEqual = value <= 1
        |   print(lessEqual)
        |
        |   print(12)
        |   print(result)
        |   print(value + 1)
        |
        |   val mips = 12 + 3
        |
        |   print(mips + value)
        |   print(plus(1, 3))
        | }
        |
        | def plus(left, right) = {
        |   val spim = 1
        |
        |   print(spim + left + right)
        | }
        |
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
