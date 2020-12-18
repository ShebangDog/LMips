import java.io.PrintWriter

import back.MipsGenerator
import front.Parser

import scala.util.Using

object Application {
  def main(args: Array[String]): Unit = {
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
