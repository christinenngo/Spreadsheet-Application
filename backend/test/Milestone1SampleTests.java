import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import spreadsheet.Model.Cell.CellValue;
import spreadsheet.Model.Parser.ExpressionParser;
import spreadsheet.Model.Expression.Expression;


public class Milestone1SampleTests {

    @Test
    public void testEvaluate_constant(){
        String raw = "11.0";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(11.0, value.asDouble());
    }

    @Test
    public void testEvaluate_constantAsExpr(){
        String raw = "=11.0";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(11.0, value.asDouble());
    }

    @Test
    public void testEvaluate_SimpleArithmetic1(){
        String raw = "=5+3*2";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(11.0, value.asDouble());
    }

    @Test
    public void testEvaluate_SimpleArithmetic2(){
        String raw = "=(1+9)+4*2";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(18.0, value.asDouble());
    }

    @Test
    public void testEvaluate_SimpleArithmetic3(){
        String raw = "=(1+2+3+4+5)*2-30/2.5*5";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(-30.0, value.asDouble());
    }


    @Test
    public void testEvaluate_SimpleArithmetic4(){
        String raw = "=2+10*3+10/4.0";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(34.5, value.asDouble());
    }

    @Test
    public void testEvaluate_SimpleArithmetic5(){
        String raw = "=5+4*(2+100/(10.5-2.5))";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(63.0, value.asDouble());
    }

}
