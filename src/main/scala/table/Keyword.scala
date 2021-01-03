package table

sealed class Keyword(val value: String)

case object Plus extends Keyword("+")

case object Minus extends Keyword("-")

case object Times extends Keyword("*")

case object Divide extends Keyword("/")

object Keyword {
  val list = List(Plus, Minus, Times, Divide)
}


