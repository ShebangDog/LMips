package dog.shebang.back

import dog.shebang.front.{AST, IR}
import dog.shebang.table.EmptyTable
import org.scalatest.funspec.AnyFunSpec

class MipsGeneratorTest extends AnyFunSpec {
  describe("generateArithmetic") {
    type ASTConstructor = (AST.Expression, AST.Expression) => AST.Arithmetic
    type IRConstructor = (IR.Mips, IR.Mips) => IR.Arithmetic
    val astIrTupleList = List[(ASTConstructor, IRConstructor)](
      (AST.Addition, IR.Addition),
      (AST.Subtraction, IR.Subtraction),
      (AST.Multiplication, IR.Multiplication),
      (AST.Division, IR.Division),
      (AST.Equal, IR.Equal),
      (AST.NotEqual, IR.NotEqual),
      //    TODO: GreaterThan用のIRをかく
      //      (AST.GreaterThan, IR.GreaterThan),
      (AST.GreaterThanEqual, IR.GreaterThanEqual),
      (AST.LessThan, IR.LessThan),
      (AST.LessThanEqual, IR.LessThanEqual),
    )

    astIrTupleList.foreach { case Tuple2(astConstructor, irConstructor) =>
      describe(s"when called by $astConstructor") {
        it(s"should generate $irConstructor") {
          val ast = astConstructor(AST.Number(1), AST.Number(1))
          val actual = MipsGenerator.generateArithmetic(ast, EmptyTable)
          val expect = irConstructor(IR.Number(1), IR.Number(1))

          assert(actual == expect)
        }
      }
    }
  }

  describe("generateExpression") {
    type AstIrTuple = (AST.Expression, IR.Expression)

    describe("AST.Number, AST.Ident, AST.IfExpression") {
      val astIrTupleList = List[AstIrTuple](
        (AST.Number(1), IR.Number(1)),
        (AST.Ident("ident"), IR.Ident("ident", EmptyTable)),
        (
          AST.IfExpression(AST.Number(1), AST.Number(2), AST.Number(3)),
          IR.IfExpression(IR.Number(1), IR.Number(2), IR.Number(3))
        ),
      )

      astIrTupleList.foreach { case Tuple2(ast, ir) =>
        describe(s"when called by $ast") {
          it(s"should generate $ir") {
            val actual = MipsGenerator.generateExpression(ast, EmptyTable)
            val expect = ir

            assert(actual == expect)
          }
        }
      }
    }

    describe("AST.CallFunction") {
      val astIrTupleList = List[AstIrTuple](
        (
          AST.CallFunction(AST.Ident("ident"), Nil),
          IR.CallFunction(AST.Ident("ident"), Nil),
        ),
        (
          AST.CallFunction(AST.Ident("ident"), List(AST.Number(1))),
          IR.CallFunction(AST.Ident("ident"), List(IR.Number(1)))
        )
      )

      astIrTupleList.foreach { case (ast, ir) =>
        describe(s"when called by $ast") {
          it(s"should generate $ir") {
            val actual = MipsGenerator.generateExpression(ast, EmptyTable)
            val expect = ir

            assert(actual == expect)
          }
        }
      }
    }

    describe("AST.Block") {
      val astIrTupleList = List[AstIrTuple](
        (AST.Block(Nil), IR.Block(Nil)),
        (AST.Block(List(AST.Ident("ident"))), IR.Block(List(IR.Ident("ident", EmptyTable)))),
        (
          AST.Block(List(AST.DeclareFunction(AST.Ident("ident"), Nil, AST.Number(1), EmptyTable))),
          IR.Block(List(IR.DeclareFunction(AST.Ident("ident"), Nil, IR.Number(1), EmptyTable)))
        ),
        (
          AST.Block(
            List(
              AST.DeclareFunction(AST.Ident("ident"), Nil, AST.Number(1), EmptyTable),
              AST.Ident("ident")
            )
          ),
          IR.Block(
            List(
              IR.DeclareFunction(AST.Ident("ident"), Nil, IR.Number(1), EmptyTable),
              IR.Ident("ident", EmptyTable)
            )
          )
        ),
        (
          AST.Block(
            List(
              AST.Ident("ident"),
              AST.DeclareFunction(AST.Ident("ident"), Nil, AST.Number(1), EmptyTable),
            )
          ),
          IR.Block(
            List(
              IR.Ident("ident", EmptyTable),
              IR.DeclareFunction(AST.Ident("ident"), Nil, IR.Number(1), EmptyTable),
            )
          )
        ),
      )

      astIrTupleList.foreach { case (ast, ir) =>
        describe(s"when called by $ast") {
          it(s"should generate $ir") {
            val actual = MipsGenerator.generateExpression(ast, EmptyTable)
            val expect = ir

            assert(actual == expect)
          }
        }
      }
    }

    describe("AST.Arithmetic") {
      type ASTConstructor = (AST.Expression, AST.Expression) => AST.Arithmetic
      type IRConstructor = (IR.Mips, IR.Mips) => IR.Arithmetic

      val astIrTupleList = List[(ASTConstructor, IRConstructor)](
        (AST.Addition, IR.Addition),
        (AST.Subtraction, IR.Subtraction),
        (AST.Multiplication, IR.Multiplication),
        (AST.Division, IR.Division),
        (AST.Equal, IR.Equal),
        (AST.NotEqual, IR.NotEqual),
        //    TODO: GreaterThan用のIRをかく
        //      (AST.GreaterThan, IR.GreaterThan),
        (AST.GreaterThanEqual, IR.GreaterThanEqual),
        (AST.LessThan, IR.LessThan),
        (AST.LessThanEqual, IR.LessThanEqual),
      )

      astIrTupleList.foreach { case Tuple2(astConstructor, irConstructor) =>
        describe(s"when called by $astConstructor") {
          it(s"should generate $irConstructor") {
            val ast = astConstructor(AST.Number(1), AST.Number(1))
            val actual = MipsGenerator.generateArithmetic(ast, EmptyTable)
            val expect = irConstructor(IR.Number(1), IR.Number(1))

            assert(actual == expect)
          }
        }
      }
    }
  }
}
