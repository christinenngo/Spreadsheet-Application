package Milestone2Tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spreadsheet.Model.Cell.CellComponent;
import spreadsheet.Model.Cell.CellValue;
import spreadsheet.Model.CellRepository;
import spreadsheet.Model.Expression.Expression;
import spreadsheet.Model.Parser.ExpressionParser;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class Milestone2GradingTests {
    private CellRepository repository;
    private CellComponent cellA1, cellA2, cellB1, cellB2, cellB5, cellC1, cellC2, cellD1, cellD2, cellD4, cellE1, cellE5;

    @BeforeEach
    void setup(){
        CellRepository.resetInstance();
        repository = CellRepository.getInstance();
        cellA1 = repository.getReferenceCell(0, 0);
        cellA2 = repository.getReferenceCell(1, 0);
        cellB1 = repository.getReferenceCell(0, 1);
        cellB2 = repository.getReferenceCell(1, 1);
        cellB5 = repository.getReferenceCell(4, 1);

        cellC1 = repository.getReferenceCell(0, 2);
        cellC2 = repository.getReferenceCell(1, 2);
        cellD1 = repository.getReferenceCell(0, 3);
        cellD2 = repository.getReferenceCell(1, 3);
        cellD4 = repository.getReferenceCell(3, 3);

        cellE1 = repository.getReferenceCell(0, 4);
        cellE5 = repository.getReferenceCell(4, 4);


        cellA1.setCellValue(new CellValue(10));
        cellB1.setCellValue(new CellValue(20));
        cellD1.setCellValue(new CellValue(5));
        cellA2.setCellValue(new CellValue(30));
        cellB2.setCellValue(new CellValue(40));
        cellD2.setCellValue(new CellValue(15));

        cellB5.setCellValue(new CellValue(50));
        cellD4.setCellValue(new CellValue(40));
        cellE1.setCellValue(new CellValue(100));
        cellE5.setCellValue(new CellValue(60));
        // C1 and C2 are not assigned values.
    }


    @Test
    public void testGroup1_test1(){
        String raw = "=(E1/B1+20)/D1+E5+A2-C1*A2";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(95.0, value.asDouble());
    }

    @Test
    public void testGroup1_test2(){
        String raw = "=A1+B1*A2-B6/A1+E1-D1*D2+E5-F1";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(695.0, value.asDouble());
    }

    @Test
    public void testGroup1_test3(){
        String raw = "=A1+C1-E1/B1-B6*A1*E1-D1*D2+E5-F1";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(-10.0, value.asDouble());
    }

    @Test
    public void testGroup1_test4(){
        String raw = "=SUM(A4:B5)-E1/B1-B6*A1*E1-SUM(D1:F4)-F1";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(-115.0, value.asDouble());
    }

    @Test
    public void testGroup1_test5(){
        String raw = "=100-E1/B1-COUNT(A1:B3)";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(89.0, value.asDouble());
    }

    @Test
    public void testGroup1_test6(){
        String raw = "=SUM(A4:B5)-E1/B1-COUNTA(A1:F7)";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(35.0, value.asDouble());
    }

    @Test
    public void testGroup1_test7(){
        String raw = "=AVE(A1:B5)-E1/COUNT(A1:E2)-B6*A1*E1-SUM(D1:F4)-COUNTA(A1:F7)";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(-150.0, value.asDouble());
    }

    @Test
    public void testGroup1_test8(){
        String raw = "=COUNTA(A1:B5, E1/COUNT(A1:E2), B6*A1*E1-SUM(D1:F4), F1:F7)";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(7.0, value.asDouble());
    }

    @Test
    public void testGroup1_test9(){
        String raw = "=SUM(A1:B5, E1/COUNT(A1:E2)-B6*A1*E1, SUM(D1:F4), COUNTA(A1:F7))";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(330, value.asDouble());
    }

    @Test
    public void testGroup2_test1(){
        String raw = "=SUM(A1,A2,30,40,E5,E4)";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(170, value.asDouble());
    }

    @Test
    public void testGroup2_test2(){
        String raw = "=AVE(A1,A2,30,40,E5, E4)";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(34, value.asDouble());
    }

    @Test
    public void testGroup2_test3(){
        String raw = "=60.5+B2/-80-AVE(A1:A2, 10, B2)*SUM(D1:D2)";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(-390, value.asDouble());
    }

    @Test
    public void testGroup2_test4(){
        String raw = "=AVE(A1:A2, SUM(A1:B1), 20.0*(55+100*(-0.5)))";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(42.5, value.asDouble());
    }

    @Test
    public void testGroup2_test5(){
        String raw = "= SUM(A1:B1, 40, 20.0*(55+100*(-0.5))) - AVE(A1:A2)";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(150, value.asDouble());
    }

    @Test
    public void testGroup2_test6(){
        String raw = "=COUNTA(A1:B4, A2:F3, B5, E2:F6, 50) - COUNT(A1:B4, A2:F3, B5, E2:F6, 50)";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(-22, value.asDouble());
    }

    @Test
    public void testGroup2_test7(){
        String raw = "=SUM(B3:F5, 60, A3, B5, COUNTA(A1:B4, A2:F3, B5, E2:F6, 50)) - AVE(A1:A2)";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(250, value.asDouble());
    }


}
