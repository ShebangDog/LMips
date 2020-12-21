package front

object IR {

  trait Mips {
    def genMips: String
  }

  trait Section extends Mips

  case object Data extends Section {
    override def genMips: String = "  .data"
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
    override def genMips: String =
      """  li $v0, 0
        |  jr $ra
        |""".stripMargin
  }

  case object None extends Mips {
    override def genMips: String = ""
  }

  case class Declare(ident: String, scope: Scope) extends Mips {
    override def genMips: String =
      s"""  .globl $ident
         |
         |$ident: .word 0
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

  case class Print(ident: IR.Ident) extends Mips {
    override def genMips: String =
      s"""
         |  li  $$v0, 1
         |  la  $$t0, ${ident.name}
         |  lw  $$a0, 0($$t0)
         |  syscall
         |""".stripMargin
  }

  case class PrintInt(expr: List[IR.Mips]) extends Mips {
    override def genMips: String = expr.map(_.genMips).mkString("\n") +
      s"""  ${pop("$t0")}
         |  li  $$v0, 1
         |  move $$a0, $$t0
         |  syscall
         |""".stripMargin
  }

  case class Number(value: Int) extends Mips {
    override def genMips: String =
      s"""  li $$t0, $value
         |  ${push("$t0")}
         |""".stripMargin
  }

  case class Ident(name: String) extends Mips {
    override def genMips: String =
      s"""  la $$t0, $name
         |  lw $$t1, 0($$t0)
         |  ${push("$t1")}
         |""".stripMargin
  }

  abstract sealed class Arithmetic(left: List[IR.Mips], right: List[IR.Mips]) extends Mips {
    val operand: String

    val destRegister: String

    override def genMips: String = left.map(_.genMips).mkString("\n") + right.map(_.genMips).mkString("\n") +
      s"""  ${pop("$t1")}
         |  ${pop("$t2")}
         |
         |  $operand $destRegister, $$t2, $$t1
         |
         |  ${push(destRegister)}
         |""".stripMargin
  }

  case class Addition(left: List[IR.Mips], right: List[IR.Mips]) extends Arithmetic(left, right) {
    override val operand: String = "add"
    override val destRegister: String = "$s0"
  }

  case class Subtraction(left: List[IR.Mips], right: List[IR.Mips]) extends Arithmetic(left, right) {
    override val operand: String = "sub"
    override val destRegister: String = "$s0"
  }

  case class Multiplication(left: List[IR.Mips], right: List[IR.Mips]) extends Arithmetic(left, right) {
    override val operand: String = "mul"
    override val destRegister: String = "$s0"
  }

  case class Division(left: List[IR.Mips], right: List[IR.Mips]) extends Arithmetic(left, right) {
    override val operand: String = "div"
    override val destRegister: String = "$s0"
  }


  sealed trait Scope {
    val name: String
  }

  case object Global extends Scope {
    override val name: String = "globl"
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
