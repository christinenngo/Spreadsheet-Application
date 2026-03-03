package Milestone1Tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import spreadsheet.Model.Cell.CellValue;
import spreadsheet.Model.Expression.*;

public class Milestone1EvaluatorTests {
    AbstractFactory arithmeticFactory = FactoryProducer.getFactory("ARITHMETIC");

    // Evaluator Tests
    @Test
    public void testEvaluator1(){
        OperandExpression op1 = new OperandExpression(5);
        OperandExpression op2 = new OperandExpression(3);
        OperandExpression op3 = new OperandExpression(8);
        OperandExpression op4 = new OperandExpression(2);

        ArithmeticOperatorExpression divide = arithmeticFactory.getArithmeticOperator("/");
        divide.addOperand(op3);
        divide.addOperand(op4);

        ArithmeticOperatorExpression add = arithmeticFactory.getArithmeticOperator("+");
        add.addOperand(op2);
        add.addOperand(divide);

        ArithmeticOperatorExpression multiply = arithmeticFactory.getArithmeticOperator("*");
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

        ArithmeticOperatorExpression subtract = arithmeticFactory.getArithmeticOperator("-");
        subtract.addOperand(op3);
        subtract.addOperand(op4);

        ArithmeticOperatorExpression multiply = arithmeticFactory.getArithmeticOperator("*");
        multiply.addOperand(op2);
        multiply.addOperand(subtract);

        ArithmeticOperatorExpression add = arithmeticFactory.getArithmeticOperator("+");
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

        ArithmeticOperatorExpression multiply = arithmeticFactory.getArithmeticOperator("*");
        multiply.addOperand(op3);
        multiply.addOperand(op4);

        ArithmeticOperatorExpression subtract = arithmeticFactory.getArithmeticOperator("-");
        subtract.addOperand(multiply);
        subtract.addOperand(op2);

        ArithmeticOperatorExpression divide = arithmeticFactory.getArithmeticOperator("/");
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

        ArithmeticOperatorExpression multiply = arithmeticFactory.getArithmeticOperator("*");
        multiply.addOperand(op3);
        multiply.addOperand(op4);

        ArithmeticOperatorExpression subtract = arithmeticFactory.getArithmeticOperator("-");
        subtract.addOperand(multiply);
        subtract.addOperand(op2);

        ArithmeticOperatorExpression divide = arithmeticFactory.getArithmeticOperator("/");
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

        ArithmeticOperatorExpression multiply = arithmeticFactory.getArithmeticOperator("*");
        multiply.addOperand(op3);
        multiply.addOperand(op4);

        ArithmeticOperatorExpression subtract = arithmeticFactory.getArithmeticOperator("-");
        subtract.addOperand(multiply);
        subtract.addOperand(op2);

        ArithmeticOperatorExpression add = arithmeticFactory.getArithmeticOperator("+");
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

        ArithmeticOperatorExpression left = arithmeticFactory.getArithmeticOperator("*");
        left.addOperand(op1);
        left.addOperand(op2);

        ArithmeticOperatorExpression right = arithmeticFactory.getArithmeticOperator("/");
        right.addOperand(op2);
        right.addOperand(op5);

        ArithmeticOperatorExpression add = arithmeticFactory.getArithmeticOperator("+");
        add.addOperand(left);
        add.addOperand(right);

        ArithmeticOperatorExpression subtract = arithmeticFactory.getArithmeticOperator("-");
        subtract.addOperand(op4);
        subtract.addOperand(add);

        ArithmeticOperatorExpression multiply = arithmeticFactory.getArithmeticOperator("*");
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

        ArithmeticOperatorExpression left = arithmeticFactory.getArithmeticOperator("*");
        left.addOperand(op1);
        left.addOperand(op2);

        ArithmeticOperatorExpression right = arithmeticFactory.getArithmeticOperator("-");
        right.addOperand(op3);
        right.addOperand(op4);

        ArithmeticOperatorExpression divide = arithmeticFactory.getArithmeticOperator("/");
        divide.addOperand(left);
        divide.addOperand(right);

        ArithmeticOperatorExpression multiply = arithmeticFactory.getArithmeticOperator("*");
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

        ArithmeticOperatorExpression left = arithmeticFactory.getArithmeticOperator("*");
        left.addOperand(op1);
        left.addOperand(op4);

        ArithmeticOperatorExpression right = arithmeticFactory.getArithmeticOperator("/");
        right.addOperand(op2);
        right.addOperand(op3);

        ArithmeticOperatorExpression divide = arithmeticFactory.getArithmeticOperator("/");
        divide.addOperand(left);
        divide.addOperand(right);

        ArithmeticOperatorExpression add = arithmeticFactory.getArithmeticOperator("+");
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

        ArithmeticOperatorExpression divide = arithmeticFactory.getArithmeticOperator("/");
        divide.addOperand(op3);
        divide.addOperand(op2);

        ArithmeticOperatorExpression add = arithmeticFactory.getArithmeticOperator("+");
        add.addOperand(op1);
        add.addOperand(op4);

        ArithmeticOperatorExpression left = arithmeticFactory.getArithmeticOperator("*");
        left.addOperand(divide);
        left.addOperand(add);

        ArithmeticOperatorExpression right = arithmeticFactory.getArithmeticOperator("/");
        right.addOperand(add);
        right.addOperand(divide);

        ArithmeticOperatorExpression subtract = arithmeticFactory.getArithmeticOperator("-");
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

        ArithmeticOperatorExpression divide = arithmeticFactory.getArithmeticOperator("/");
        divide.addOperand(op3);
        divide.addOperand(op2);

        ArithmeticOperatorExpression add = arithmeticFactory.getArithmeticOperator("+");
        add.addOperand(op1);
        add.addOperand(op4);

        ArithmeticOperatorExpression multiply = arithmeticFactory.getArithmeticOperator("*");
        multiply.addOperand(op1);
        multiply.addOperand(op4);

        ArithmeticOperatorExpression left = arithmeticFactory.getArithmeticOperator("*");
        left.addOperand(divide);
        left.addOperand(add);

        ArithmeticOperatorExpression right = arithmeticFactory.getArithmeticOperator("+");
        right.addOperand(multiply);
        right.addOperand(add);

        ArithmeticOperatorExpression subtract = arithmeticFactory.getArithmeticOperator("-");
        subtract.addOperand(left);
        subtract.addOperand(right);

        CellValue value = subtract.evaluate();
        assertEquals(18.0, value.asDouble());
    }
}
