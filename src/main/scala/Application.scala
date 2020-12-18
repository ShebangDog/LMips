import back.MipsGenerator
import front.Parser

object Application {
  def main(args: Array[String]): Unit = {
    val nodeList = Parser.parseAll(Parser.program,
      """
        | val value = 12
        | val number = 1 + (2 + 3) * 4
        |
        | 3 + number + dfa + fffff
        |
        | print(1)
        | print(value)
        |
        |""".stripMargin)
      .map(MipsGenerator.generateProgram)
      .get
      .map(_.genMips)
      .filter(_.nonEmpty)

    nodeList.foreach(println)

  }
}
