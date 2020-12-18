package front

object IR {

  trait Mips {
    def genMips: String
  }

  case object Header extends Mips {
    override def genMips: String =
      """
        |  .text
        |  .globl main
        |
        |main:
        |""".stripMargin
  }

  case object Footer extends Mips {
    override def genMips: String = Store("result").genMips +
      """  li $v0, 0
        |  jr $ra
        |""".stripMargin
  }

  case object None extends Mips {
    override def genMips: String = ""
  }

  case class Declare(ident: String, number: IR.Number, scope: Scope) extends Mips {
    override def genMips: String =
      s"""  .data
         |  .globl $ident
         |
         |$ident: .word ${number.value}
         |""".stripMargin
  }

  case class Print(ident: String) extends Mips {
    override def genMips: String =
      s"""
         |  li $$v0, 4
         |  la $$a0, ${AST.Symbol.table(ident)}
         |  syscall
         |""".stripMargin
  }

  case class Exit(returnValue: String) extends Mips {
    override def genMips: String =
      s"""  li $$v0, $returnValue
         |  jr $$ra
         |""".stripMargin
  }

  sealed trait Scope

  case object Global extends Scope

  case class Number(value: Int) extends Mips {
    override def genMips: String =
      s"""  li  $$t0, $value
         |  ${push("$t0")}
         |""".stripMargin
  }

  case class Addition(left: IR.Mips, right: IR.Mips) extends Mips {
    override def genMips: String = "" +
      left.genMips +
      right.genMips +
      s"""  ${pop("$t1")}
         |  ${pop("$t2")}
         |
         |  add $$s0, $$t1, $$t2
         |
         |  ${push("$s0")}
         |""".stripMargin
  }

  case class Store(name: String) extends Mips {
    override def genMips: String =
      s"""  la  $$t0, $name
         |  ${pop("$t1")}
         |  sw  $$t1, 0($$t0)
         |
         |""".stripMargin
  }

  private def push(name: String): String =
    s"""sub  $$sp, $$sp, 4
       |  sw  $name, 0($$sp)
       |""".stripMargin

  private def pop(name: String): String =
    s"""lw $name, 0($$sp)
       |  addiu $$sp, $$sp, 4
       |""".stripMargin
}
