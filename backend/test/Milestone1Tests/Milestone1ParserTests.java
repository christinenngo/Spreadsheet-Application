package Milestone1Tests;

import org.junit.jupiter.api.Test;

import spreadsheet.Model.Expression.OperandExpression;
import spreadsheet.Model.Expression.ArithmeticOperatorExpression;
import spreadsheet.Model.Parser.ExpressionParser;
import spreadsheet.Model.Expression.Expression;

import java.util.ArrayDeque;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

public class Milestone1ParserTests {
    // Parser Tests
    @Test
    public void testParser1(){
        String raw = "=14";
        Expression expression = ExpressionParser.convertExpression(raw);

        assertTrue(expression instanceof OperandExpression);
    }

    @Test
    public void testParser2(){
        String raw = "=14+6";
        Expression expression = ExpressionParser.convertExpression(raw);

        assertTrue(expression instanceof ArithmeticOperatorExpression);
    }

    @Test
    public void testParser3(){
        String raw = "=(1+4)*2";
        Expression expression = ExpressionParser.convertExpression(raw);

        assertTrue(expression instanceof ArithmeticOperatorExpression);
    }

    @Test
    public void testParser4(){
        String raw = "=0*2+1";
        Expression expression = ExpressionParser.convertExpression(raw);

        assertTrue(expression instanceof ArithmeticOperatorExpression);
    }

    @Test
    public void testParser5(){
        String raw = "=2";
        Expression expression = ExpressionParser.convertExpression(raw);

        assertFalse(expression instanceof ArithmeticOperatorExpression);
    }

    @Test
    public void testParser6(){
        String raw = "=6/2+1";
        Expression expression = ExpressionParser.convertExpression(raw);

        assertFalse(expression instanceof OperandExpression);
    }

    @Test
    public void testPostfixToExpression() {
        Queue<String> postfix = new ArrayDeque<>();
        postfix.add("14");
        postfix.add("6");
        postfix.add("+");

        Expression expression = ExpressionParser.postfixToExpression(postfix);

        assertTrue(expression instanceof ArithmeticOperatorExpression);
    }

}
