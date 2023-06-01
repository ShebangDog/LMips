package dog.shebang.table

import dog.shebang.front.AST

import scala.collection.mutable
import scala.collection.mutable.ListBuffer


class Table(count: Int, registerList: List[AST.Ident]) {
  private val mutableIdList: mutable.ListBuffer[AST.Ident] = registerList.to(ListBuffer)

  val idList: List[AST.Ident] = mutableIdList.toList

  def store(ident: AST.Ident): Unit = {
    println(s"store ${ident.name} count: $count")
    mutableIdList += ident
  }

  def load(ident: AST.Ident): Option[Int] = {
    def offset(index: Int): Int = -(index + 1) * 4

    mutableIdList.indexOf(ident) match {
      case -1 =>
        println("None: " + ident + s"count: $count")
        None
      case other =>
        println(s"load ${ident.name} at ${offset(other)} count: $count")
        Some(offset(other))
    }
  }

}

object Table {
  var count = 0

  def apply(registerList: List[AST.Ident] = Nil): Table = {
    count = count + 1
    new Table(count, registerList)
  }

}
