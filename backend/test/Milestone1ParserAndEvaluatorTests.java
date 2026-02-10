import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import spreadsheet.Model.Cell.CellValue;
import spreadsheet.Model.Expression.OperatorFactory;
import spreadsheet.Model.Parser.ExpressionParser;
import spreadsheet.Model.Expression.Expression;

public class Milestone1ParserAndEvaluatorTests {

    // Parser and Evaluator Tests
    @Test
    public void testParserAndEvaluator1(){
        String raw = "=(10+4*(5+100/(10-2)))";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(80.0, value.asDouble());
    }

    @Test
    public void testParserAndEvaluator2(){
        String raw = "=((2+3)*(4+5)*6-7/8)";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(269.125, value.asDouble());
    }

    @Test
    public void testParserAndEvaluator3(){
        String raw = "=(10-3)*(3+(6/2))+1";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(43.0, value.asDouble());
    }

    @Test
    public void testParserAndEvaluator4(){
        String raw = "=((20/(5+5))+3*2)";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(8.0, value.asDouble());
    }

    @Test
    public void testParserAndEvaluator5(){
        String raw = "=((20/0)+3*2)/4+1";
        Expression expression = ExpressionParser.convertExpression(raw);

        assertThrows(ArithmeticException.class, expression::evaluate);
    }

    @Test
    public void testParserAndEvaluator6(){
        String raw = "=((0*20/(5-11))-3*2)";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(-6.0, value.asDouble());
    }

    @Test
    public void testParserAndEvaluator7(){
        String raw = "=((2+3)*(4+((8-3)/5)))-50";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(-25.0, value.asDouble());
    }

    @Test
    public void testParserAndEvaluator8(){
        String raw = "=(((6-2)/2)+(5*2))+3*1";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(15.0, value.asDouble());
    }

    @Test
    public void testParserAndEvaluator9(){
        String raw = "=((8+2)*(3+(6/(1+2))))-4";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(46.0, value.asDouble());
    }

    @Test
    public void testParserAndEvaluator10(){
        String raw = "=((10-2)*(5+((3*4)/2)))-((8/4)+1)";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(85.0, value.asDouble());
    }

}
