package front

import front.AST.Ident

import scala.util.parsing.combinator.JavaTokenParsers

object Parser extends JavaTokenParsers {

  def program: Parser[List[AST.Node]] = rep((stmt | expr) <~ rep("\n")) ^^ { statement =>
    statement.foldRight(List[AST.Node]()) { case (head, rest) => head :: rest }
  }

  def stmt: Parser[AST.Statement] = decl

  def decl: Parser[AST.Statement] = ("val" ~> ident) ~ ("=" ~> expr) ^^ {
    case name ~ expr => AST.DeclareValue(Ident(name), expr)
  } |
    ("def" ~> ident) ~ opt(param) ~ ("=" ~> expr) ^^ {
      case name ~ None ~ expr => AST.DeclareFunction(Ident(name), List(), expr)
      case name ~ Some(param) ~ expr => AST.DeclareFunction(Ident(name), param, expr)
    }

  def expr: Parser[AST.Expression] = term ~ rep(("+" | "-") ~ term) ^^ {
    case term ~ Nil => term
    case term ~ rest => rest.foldLeft(term) {
      case (l, "+" ~ r) => AST.Addition(l, r)
      case (l, "-" ~ r) => AST.Subtraction(l, r)
    }
  }

  def term: Parser[AST.Expression] = fact ~ rep(("*" | "/") ~ fact) ^^ {
    case fact ~ Nil => fact
    case fact ~ rest => rest.foldLeft(fact) {
      case (l, "*" ~ r) => AST.Multiplication(l, r)
      case (l, "/" ~ r) => AST.Division(l, r)
    }
  }

  def fact: Parser[AST.Expression] = ident ~ args ^^ {
    case ident ~ args => AST.CallFunction(AST.Ident(ident), args)
  } |
    "(" ~> expr <~ ")" |
    "{" ~> rep(stmt | expr) <~ "}" ^^ AST.Block |
    wholeNumber ^^ { num => AST.Number(num.toInt) } |
    ident ^^ AST.Ident

  def param: Parser[List[AST.Ident]] = "(" ~> opt(ident ~ rep("," ~> ident)) <~ ")" ^^ {
    case None => List()
    case Some(head ~ rest) => (head :: rest).map(AST.Ident)
  }

  def args: Parser[List[AST.Expression]] = "(" ~> opt(expr ~ rep("," ~> expr)) <~ ")" ^^ {
    case None => List()
    case Some(head ~ rest) => head :: rest
  }
}
