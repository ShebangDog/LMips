package dog.shebang.front

import dog.shebang.front.AST.Ident

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

  def expr: Parser[AST.Expression] = equality ~ rep(("+" | "-") ~ equality) ^^ {
    case equality ~ Nil => equality
    case equality ~ rest => rest.foldLeft(equality) {
      case (l, "+" ~ r) => AST.Addition(l, r)
      case (l, "-" ~ r) => AST.Subtraction(l, r)
    }
  }

  def equality: Parser[AST.Expression] = relational ~ rep(("==" | "!=") ~ relational) ^^ {
    case relational ~ Nil => relational
    case relational ~ rest => rest.foldLeft(relational) {
      case (l, "==" ~ r) => AST.Equal(l, r)
      case (l, "!=" ~ r) => AST.NotEqual(l, r)
    }
  }

  def relational: Parser[AST.Expression] = term ~ rep((">=" | ">" | "<=" | "<") ~ term) ^^ {
    case term ~ Nil => term
    case term ~ rest => rest.foldLeft(term) {
      case (l, ">" ~ r) => AST.GreaterThan(l, r)
      case (l, ">=" ~ r) => AST.GreaterThanEqual(l, r)
      case (l, "<" ~ r) => AST.LessThan(l, r)
      case (l, "<=" ~ r) => AST.LessThanEqual(l, r)
    }
  }

  def term: Parser[AST.Expression] = fact ~ rep(("*" | "/") ~ fact) ^^ {
    case fact ~ Nil => fact
    case fact ~ rest => rest.foldLeft(fact) {
      case (l, "*" ~ r) => AST.Multiplication(l, r)
      case (l, "/" ~ r) => AST.Division(l, r)
    }
  }

  def fact: Parser[AST.Expression] = {
    listExpression |
    ifExpression |
    ident ~ args ^^ {
      case ident ~ args => AST.CallFunction(AST.Ident(ident), args)
    } |
    "(" ~> expr <~ ")" |
    "{" ~> rep(stmt | expr) <~ "}" ^^ AST.Block |
    wholeNumber ^^ { num => AST.Number(num.toInt) } |
    ident ^^ AST.Ident
  }

  def listExpression: Parser[AST.Expression] = "list" ~> ("(" ~> expr ~ rep("," ~> expr) <~ ")") ^^ {
    case head ~ tail => AST.MyList(head :: tail)
  }

  def ifExpression: Parser[AST.Expression] = "if" ~> ("(" ~> expr <~ ")") ~ expr ~ ("else" ~> expr) ^^ {
    case condition ~ left ~ right => AST.IfExpression(condition, left, right)
  }

  def param: Parser[List[AST.Ident]] = "(" ~> opt(ident ~ rep("," ~> ident)) <~ ")" ^^ {
    case None => List()
    case Some(head ~ rest) => (head :: rest).map(AST.Ident)
  }

  def args: Parser[List[AST.Expression]] = "(" ~> opt(expr ~ rep("," ~> expr)) <~ ")" ^^ {
    case None => List()
    case Some(head ~ rest) => head :: rest
  }
}
