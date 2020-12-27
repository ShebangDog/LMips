package front

import table.Table

object IR {

  trait Mips {
    def genMips: String
  }

  trait Section extends Mips

  case object Text extends Section {
    override def genMips: String = "  .text"
  }

  case object None extends Mips {
    override def genMips: String = ""
  }

  case class DeclareValue(ident: String, value: List[IR.Mips]) extends Mips {
    Table.store(ident)

    override def genMips: String = value.map(_.genMips).mkString("\n")
  }

  case class DeclareFunction(ident: String, value: List[IR.Mips]) extends Mips {

    override def genMips: String = header + prologue + value.map(_.genMips).mkString("\n") + epilogue + ret

    private def header: String =
      s""".globl $ident
         |$ident:
         |
         |""".stripMargin

    private def prologue: String =
      s"""
         |  ${push("$fp")}
         |  ${push("$ra")}
         |  move  $$fp, $$sp
         |
         |""".stripMargin

    private def epilogue: String =
      s"""
         |  move  $$sp,  $$fp
         |  ${pop("$ra")}
         |  ${pop("$fp")}
         |
         |""".stripMargin

    private def ret: String =
      """
        |  li  $v0,  0
        |  jr  $ra
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
      s"""  ${load("$t0", name)}
         |  ${push("$t0")}
         |""".stripMargin
  }

  abstract sealed class Arithmetic(left: List[IR.Mips], right: List[IR.Mips]) extends Mips {
    val operand: String

    val destRegister: String

    override def genMips: String = left.map(_.genMips).mkString("\n") + right.map(_.genMips).mkString("\n") +
      s"""  ${pop("$t1")}
         |  ${pop("$t2")}
         |  $operand $destRegister, $$t2, $$t1
         |  ${push(destRegister)}""".stripMargin
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

  private def load(register: String, name: String): String =
    s""" lw  $register,  ${Table.load(name).get}($$fp)
       |""".stripMargin

  private def store(register: String, name: String): String =
    s"""  sub $$fp, $$fp, 4
       |  sw  $register, 0($$fp)
       |""".stripMargin
}
