package dog.shebang.back.ir

sealed trait Section extends Framework

case object Text extends Section {
  override def mkString: String = ".text"
}
