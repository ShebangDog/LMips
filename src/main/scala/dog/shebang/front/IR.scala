package dog.shebang.front

import dog.shebang.back.ir.Framework
import dog.shebang.table.Table

object IR {

  sealed trait Mips extends Framework {

    override def mkString: String = genMips

    def genMips: String
  }

  trait Section extends Mips

  object Text extends Section {
    override def genMips: String = "  .text"
  }

  case object None extends Mips {
    override def genMips: String = ""
  }

  case object Zero extends Mips {
    override def genMips: String = push("$zero")
  }

  sealed trait Expression extends Mips

  sealed trait Statement extends Mips

  case class Block(body: List[IR.Mips]) extends Expression {

    override def genMips: String = body.map(_.genMips).mkString("\n")
  }

  case class DeclareValue(ident: AST.Ident, value: IR.Mips, table: Table) extends IR.Statement {
    table.store(ident)

    override def genMips: String = s"#${ident.name} = value\n" + value.genMips + look("$t0") + store("$t0", table.load(ident).get) + s"#${ident.name} = value\n"
  }

  case class DeclareFunction(ident: AST.Ident, paramList: List[AST.Ident], value: IR.Mips, table: Table) extends IR.Statement {

    private def functionName = if (ident == AST.Main) ident.name else s"function_${ident.name}"

    override def genMips: String = header + prologue + value.genMips + ret + epilogue + exit

    private val paramRegList = paramList.zipWithIndex.map(_._2).map("$a" + _)

    private def header =
      s"""  .globl $functionName
         |$functionName:
         |""".stripMargin

    private def prologue =
      s"""  #prologue
         |  ${push("$fp")}
         |  ${push("$ra")}
         |  move  $$fp, $$sp
         |  ${paramRegList.map(push).mkString("\n")}
         |  #prologue
         |""".stripMargin

    private def epilogue =
      s"""  #epilogue
         |  ${paramRegList.map(pop).mkString("\n")}
         |  move  $$sp,  $$fp
         |  ${pop("$ra")}
         |  ${pop("$fp")}
         |  #epilogue
         |""".stripMargin

    private def ret =
      s"""  ${pop("$v0")}
         |""".stripMargin

    private def exit =
      s"""  jr  $$ra
         |""".stripMargin
  }

  case class IfExpression(condition: IR.Mips, ifTrue: IR.Mips, ifFalse: IR.Mips) extends IR.Expression {
    private val counter = IfExpression.countUp

    private def label(cond: String) = cond + counter

    private val trueLabel = label("true")
    private val falseLabel = label("false")
    private val endLabel = label("end")

    override def genMips: String =
      s"""  ${condition.genMips}
         |  ${pop("$s0")}
         |  beq $$s0,  $$zero,  $falseLabel
         |$trueLabel:
         |    ${ifTrue.genMips}
         |    j $endLabel
         |$falseLabel:
         |    ${ifFalse.genMips}
         |$endLabel:
         |""".stripMargin
  }

  object IfExpression {
    private var counter = 0

    private def countUp: Int = {
      counter += 1
      counter
    }
  }

  case class CallFunction(ident: AST.Ident, argumentList: List[IR.Mips]) extends Expression {
    private def functionName = if (ident == AST.Main) ident.name else s"function_${ident.name}"

    private val argumentRegList = argumentList.zipWithIndex.map(_._2).map("$a" + _)

    override def genMips: String = argumentList.map(_.genMips).mkString("\n") +
      s"""  ${argumentRegList.reverse.map(pop).mkString("\n")}
         |  jal $functionName
         |  ${push("$v0")}
         |""".stripMargin
  }

  case class Number(value: Int) extends Expression {
    override def genMips: String =
      s"""  li $$t0, $value
         |  ${push("$t0")}
         |""".stripMargin
  }

  case class Ident(name: String, table: Table) extends Expression {
    override def genMips: String =
      s"""  ${load("$t0", name, table)}
         |  ${push("$t0")}
         |""".stripMargin
  }

  abstract sealed class Arithmetic(left: IR.Mips, right: IR.Mips) extends Expression {
    val operand: String

    val destRegister: String

    override def genMips: String = left.genMips + right.genMips +
      s"""  ${pop("$t1")}
         |  ${pop("$t2")}
         |  $operand $destRegister, $$t2, $$t1
         |  ${push(destRegister)}
         |""".stripMargin
  }

  case class Addition(left: IR.Mips, right: IR.Mips) extends Arithmetic(left, right) {
    override val operand: String = "add"
    override val destRegister: String = "$s0"
  }

  case class Subtraction(left: IR.Mips, right: IR.Mips) extends Arithmetic(left, right) {
    override val operand: String = "sub"
    override val destRegister: String = "$s0"
  }

  case class Multiplication(left: IR.Mips, right: IR.Mips) extends Arithmetic(left, right) {
    override val operand: String = "mul"
    override val destRegister: String = "$s0"
  }

  case class Division(left: IR.Mips, right: IR.Mips) extends Arithmetic(left, right) {
    override val operand: String = "div"
    override val destRegister: String = "$s0"
  }

  case class Equal(left: IR.Mips, right: IR.Mips) extends Arithmetic(left, right) {
    override val operand: String = "seq"
    override val destRegister: String = "$s0"
  }

  case class NotEqual(left: IR.Mips, right: IR.Mips) extends Arithmetic(left, right) {
    override val operand: String = "sne"
    override val destRegister: String = "$s0"
  }

  case class MoreThan(left: IR.Mips, right: IR.Mips) extends Arithmetic(left, right) {
    override val operand: String = "sgt"
    override val destRegister: String = "$s0"
  }

  case class GreaterThanEqual(left: IR.Mips, right: IR.Mips) extends Arithmetic(left, right) {
    override val operand: String = "sge"
    override val destRegister: String = "$s0"
  }

  case class LessThan(left: IR.Mips, right: IR.Mips) extends Arithmetic(right, left) {
    override val operand: String = "sgt"
    override val destRegister: String = "$s0"
  }

  case class LessThanEqual(left: IR.Mips, right: IR.Mips) extends Arithmetic(left, right) {
    override val operand: String = "sle"
    override val destRegister: String = "$s0"
  }

  object PrintInt extends DeclareFunction(AST.Ident("print"), List(), PrintIntBody, Table())

  object PrintIntBody extends Mips {
    override def genMips: String = {
      val retReg = "$v0"
      val retVal = "0"

      s"""  li  $$v0, 1
         |  syscall
         |
         |  li  $retReg, $retVal
         |  ${push(retReg)}
         |""".stripMargin
    }
  }

  private def push(name: String): String =
    s"""#push
       |  sub  $$sp, $$sp, 4
       |  sw  $name, 0($$sp)
       |""".stripMargin

  private def pop(name: String): String =
    s"""#pop
       |  lw $name, 0($$sp)
       |  addiu $$sp, $$sp, 4
       |""".stripMargin

  private def look(name: String): String = s"  lw  $name,  0($$sp)"

  private def load(register: String, name: String, table: Table): String =
    s"""lw  $register,  ${table.load(AST.Ident(name)).get}($$fp)
       |""".stripMargin

  private def store(register: String, offset: Int): String =
    s"""  #store
       |  sw  $register, $offset($$fp)
       |  #store""".stripMargin

}
