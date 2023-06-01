package dog.shebang.back.ir

//package back.ir
//
//sealed trait Operand {
//  def mkString: String
//}
//
//case class Register(name: String) extends Operand {
//  override def mkString: String = name
//}
//
//case class ImmediateValue(value: Int) extends Operand {
//  override def mkString: String = value.toString
//}
//
//sealed abstract class Opcode(val name: String) {
//  def generate(left: Operand, right: Operand, dest: Register): String
//}
//
//object Code {
//
//  case class Arithmetic(override val name: String) extends Opcode(name) {
//    override def generate(left: Operand, right: Operand, dest: Register): String = s"$name $dest, $left, $right"
//  }
//
//  case object Add extends Arithmetic("add")
//
//  case object Sub extends Arithmetic("sub")
//
//  case object Mul extends Arithmetic("mul")
//
//  case object Div extends Arithmetic("div")
//
//  case object Lw extends Opcode("lw") {
//    override def generate(left: Operand, right: Operand, dest: Register): String = s"$name  $dest,  $left($right)"
//  }
//
//  case object Sw extends Opcode("sw") {
//    override def generate(left: Operand, right: Operand, dest: Register): String = s"$name  $right, $left($dest), "
//  }
//
//}
//
//case class Code(opcode: Opcode, left: Operand, right: Operand, dest: Register) extends Framework {
//  override def mkString: String =
//    s"""$opcode
//       |
//       |""".stripMargin
//}
