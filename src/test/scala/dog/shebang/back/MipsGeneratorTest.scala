package dog.shebang.back

import dog.shebang.front.{AST, IR}
import dog.shebang.table.Table
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
          //    TODO: Table()ではなく、定義された単位元を利用する
          val emptyTable = Table()

          val actual = MipsGenerator.generateArithmetic(ast, emptyTable)
          val expect = irConstructor(IR.Number(1), IR.Number(1))

          assert(actual == expect)
        }
      }
    }
  }

}