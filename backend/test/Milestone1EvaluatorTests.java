import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.springframework.expression.spel.ast.Operator;
import spreadsheet.Model.Cell.CellValue;
import spreadsheet.Model.Expression.OperandExpression;
import spreadsheet.Model.Expression.OperatorExpression;
import spreadsheet.Model.Expression.OperatorFactory;
import spreadsheet.Model.Parser.ExpressionParser;
import spreadsheet.Model.Expression.Expression;

public class Milestone1EvaluatorTests {
    @BeforeAll
    public static void setup() {
        OperatorFactory factory = new OperatorFactory();
    }

    // Evaluator Tests
    @Test
    public void testEvaluator1(){
        OperandExpression op1 = new OperandExpression(5);
        OperandExpression op2 = new OperandExpression(3);
        OperandExpression op3 = new OperandExpression(8);
        OperandExpression op4 = new OperandExpression(2);

        OperatorExpression divide = OperatorFactory.getOperator("/");
        divide.addOperand(op3);
        divide.addOperand(op4);

        OperatorExpression add = OperatorFactory.getOperator("+");
        add.addOperand(op2);
        add.addOperand(divide);

        OperatorExpression multiply = OperatorFactory.getOperator("*");
        multiply.addOperand(op1);
        multiply.addOperand(add);

        CellValue value = multiply.evaluate();
        assertEquals(35.0, value.asDouble());
    }

    @Test
    public void testEvaluator2(){
        OperandExpression op1 = new OperandExpression(2);
        OperandExpression op2 = new OperandExpression(5);
        OperandExpression op3 = new OperandExpression(10);
        OperandExpression op4 = new OperandExpression(3);

        OperatorExpression subtract = OperatorFactory.getOperator("-");
        subtract.addOperand(op3);
        subtract.addOperand(op4);

        OperatorExpression multiply = OperatorFactory.getOperator("*");
        multiply.addOperand(op2);
        multiply.addOperand(subtract);

        OperatorExpression add = OperatorFactory.getOperator("+");
        add.addOperand(op1);
        add.addOperand(multiply);


        CellValue value = add.evaluate();
        assertEquals(37.0, value.asDouble());
    }

    @Test
    public void testEvaluator3(){
        OperandExpression op1 = new OperandExpression(24);
        OperandExpression op2 = new OperandExpression(20);
        OperandExpression op3 = new OperandExpression(4);
        OperandExpression op4 = new OperandExpression(2);

        OperatorExpression multiply = OperatorFactory.getOperator("*");
        multiply.addOperand(op3);
        multiply.addOperand(op4);

        OperatorExpression subtract = OperatorFactory.getOperator("-");
        subtract.addOperand(multiply);
        subtract.addOperand(op2);

        OperatorExpression divide = OperatorFactory.getOperator("/");
        divide.addOperand(op1);
        divide.addOperand(subtract);


        CellValue value = divide.evaluate();
        assertEquals(-2.0, value.asDouble());
    }

    @Test
    public void testEvaluator4(){
        OperandExpression op1 = new OperandExpression(0);
        OperandExpression op2 = new OperandExpression(2);
        OperandExpression op3 = new OperandExpression(4);
        OperandExpression op4 = new OperandExpression(2);

        OperatorExpression multiply = OperatorFactory.getOperator("*");
        multiply.addOperand(op3);
        multiply.addOperand(op4);

        OperatorExpression subtract = OperatorFactory.getOperator("-");
        subtract.addOperand(multiply);
        subtract.addOperand(op2);

        OperatorExpression divide = OperatorFactory.getOperator("/");
        divide.addOperand(subtract);
        divide.addOperand(op1);


        assertThrows(ArithmeticException.class, divide::evaluate);
    }

    @Test
    public void testEvaluator5(){
        OperandExpression op1 = new OperandExpression(4);
        OperandExpression op2 = new OperandExpression(2);
        OperandExpression op3 = new OperandExpression(0);
        OperandExpression op4 = new OperandExpression(20);

        OperatorExpression multiply = OperatorFactory.getOperator("*");
        multiply.addOperand(op3);
        multiply.addOperand(op4);

        OperatorExpression subtract = OperatorFactory.getOperator("-");
        subtract.addOperand(multiply);
        subtract.addOperand(op2);

        OperatorExpression add = OperatorFactory.getOperator("+");
        add.addOperand(op1);
        add.addOperand(subtract);


        CellValue value = add.evaluate();
        assertEquals(2.0, value.asDouble());
    }

    @Test
    public void testEvaluator6(){
        OperandExpression op1 = new OperandExpression(4);
        OperandExpression op2 = new OperandExpression(2);
        OperandExpression op3 = new OperandExpression(3);
        OperandExpression op4 = new OperandExpression(10);
        OperandExpression op5 = new OperandExpression(2);

        OperatorExpression left = OperatorFactory.getOperator("*");
        left.addOperand(op1);
        left.addOperand(op2);

        OperatorExpression right = OperatorFactory.getOperator("/");
        right.addOperand(op2);
        right.addOperand(op5);

        OperatorExpression add = OperatorFactory.getOperator("+");
        add.addOperand(left);
        add.addOperand(right);

        OperatorExpression subtract = OperatorFactory.getOperator("-");
        subtract.addOperand(op4);
        subtract.addOperand(add);

        OperatorExpression multiply = OperatorFactory.getOperator("*");
        multiply.addOperand(subtract);
        multiply.addOperand(op3);

        CellValue value = multiply.evaluate();
        assertEquals(3.0, value.asDouble());
    }

    @Test
    public void testEvaluator7(){
        OperandExpression op1 = new OperandExpression(2);
        OperandExpression op2 = new OperandExpression(5);
        OperandExpression op3 = new OperandExpression(10);
        OperandExpression op4 = new OperandExpression(20);

        OperatorExpression left = OperatorFactory.getOperator("*");
        left.addOperand(op1);
        left.addOperand(op2);

        OperatorExpression right = OperatorFactory.getOperator("-");
        right.addOperand(op3);
        right.addOperand(op4);

        OperatorExpression divide = OperatorFactory.getOperator("/");
        divide.addOperand(left);
        divide.addOperand(right);

        OperatorExpression multiply = OperatorFactory.getOperator("*");
        multiply.addOperand(divide);
        multiply.addOperand(op4);

        CellValue value = multiply.evaluate();
        assertEquals(-20.0, value.asDouble());
    }

    @Test
    public void testEvaluator8(){
        OperandExpression op1 = new OperandExpression(0);
        OperandExpression op2 = new OperandExpression(5);
        OperandExpression op3 = new OperandExpression(1);
        OperandExpression op4 = new OperandExpression(20);

        OperatorExpression left = OperatorFactory.getOperator("*");
        left.addOperand(op1);
        left.addOperand(op4);

        OperatorExpression right = OperatorFactory.getOperator("/");
        right.addOperand(op2);
        right.addOperand(op3);

        OperatorExpression divide = OperatorFactory.getOperator("/");
        divide.addOperand(left);
        divide.addOperand(right);

        OperatorExpression add = OperatorFactory.getOperator("+");
        add.addOperand(divide);
        add.addOperand(op4);

        CellValue value = add.evaluate();
        assertEquals(20.0, value.asDouble());
    }

    @Test
    public void testEvaluator9(){
        OperandExpression op1 = new OperandExpression(3);
        OperandExpression op2 = new OperandExpression(1);
        OperandExpression op3 = new OperandExpression(5);
        OperandExpression op4 = new OperandExpression(2);

        OperatorExpression divide = OperatorFactory.getOperator("/");
        divide.addOperand(op3);
        divide.addOperand(op2);

        OperatorExpression add = OperatorFactory.getOperator("+");
        add.addOperand(op1);
        add.addOperand(op4);

        OperatorExpression left = OperatorFactory.getOperator("*");
        left.addOperand(divide);
        left.addOperand(add);

        OperatorExpression right = OperatorFactory.getOperator("/");
        right.addOperand(add);
        right.addOperand(divide);

        OperatorExpression subtract = OperatorFactory.getOperator("-");
        subtract.addOperand(left);
        subtract.addOperand(right);

        CellValue value = subtract.evaluate();
        assertEquals(24.0, value.asDouble());
    }

    @Test
    public void testEvaluator10(){
        OperandExpression op1 = new OperandExpression(5);
        OperandExpression op2 = new OperandExpression(1);
        OperandExpression op3 = new OperandExpression(5);
        OperandExpression op4 = new OperandExpression(2);

        OperatorExpression divide = OperatorFactory.getOperator("/");
        divide.addOperand(op3);
        divide.addOperand(op2);

        OperatorExpression add = OperatorFactory.getOperator("+");
        add.addOperand(op1);
        add.addOperand(op4);

        OperatorExpression multiply = OperatorFactory.getOperator("*");
        multiply.addOperand(op1);
        multiply.addOperand(op4);

        OperatorExpression left = OperatorFactory.getOperator("*");
        left.addOperand(divide);
        left.addOperand(add);

        OperatorExpression right = OperatorFactory.getOperator("+");
        right.addOperand(multiply);
        right.addOperand(add);

        OperatorExpression subtract = OperatorFactory.getOperator("-");
        subtract.addOperand(left);
        subtract.addOperand(right);

        CellValue value = subtract.evaluate();
        assertEquals(18.0, value.asDouble());
    }
}
