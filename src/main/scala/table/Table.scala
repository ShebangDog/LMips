package table

import scala.collection.mutable

object Table {
  private val mutableIdList: mutable.ListBuffer[String] = mutable.ListBuffer()

  val idList: List[String] = mutableIdList.toList

  def store(ident: String): Int = {
    mutableIdList += ident

    mutableIdList.size
  }

  def load(ident: String): Option[Int] = {
    mutableIdList.indexOf(ident) match {
      case -1 => None
      case other => Some(other * 4 + 4)
    }
  }

}
