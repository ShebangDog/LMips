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

    describe("AST.Number, AST.Ident") {
      val astIrTupleList = List[AstIrTuple](
        (AST.Number(1), IR.Number(1)),
        (AST.Ident("ident"), IR.Ident("ident", EmptyTable)),
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

  }
}
