import java.io.PrintWriter

import back.MipsGenerator
import front.Parser

import scala.util.Using

object Application {
  def main(args: Array[String]): Unit = {
    val nodeList = Parser.parseAll(Parser.program,
      """
        |
        | def main() = {
        |   val h = 8
        |   val e = 5
        |   val l = 13
        |   val o = 16
        |
        |   print(h)
        |   print(o + 12)
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
