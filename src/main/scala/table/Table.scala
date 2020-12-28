package table

import scala.collection.mutable

object Table {
  private val mutableIdList: mutable.ListBuffer[String] = mutable.ListBuffer()

  val idList: List[String] = mutableIdList.toList

  def store(ident: String): Unit = mutableIdList += ident

  def load(ident: String): Option[Int] = {
    def offset(index: Int): Int = -(index + 1) * 4

    mutableIdList.indexOf(ident) match {
      case -1 =>
        println("None: " + ident)
        None
      case other => Some(offset(other))
    }
  }

  def prepare(): Unit = Keyword.list.map(_.value).foreach(store)

}
