package Milestone3Tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spreadsheet.Model.Cell.CellComponent;
import spreadsheet.Model.Cell.CellValue;
import spreadsheet.Model.Parser.ExpressionParser;
import spreadsheet.Model.CellRepository;
import spreadsheet.Model.Expression.Expression;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class Milestone3ObserverTests{
    private CellRepository repository;
    private CellComponent cellA1, cellA2, cellA3, cellB1, cellB2, cellB3, cellC1, cellC2, cellC3, cellD1, cellD2;

    @BeforeEach
    void setup() {
        CellRepository.resetInstance();
        repository = CellRepository.getInstance();
        cellA1 = repository.getReferenceCell(0, 0);
        cellA2 = repository.getReferenceCell(1, 0);
        cellA3 = repository.getReferenceCell(2, 0);
        cellB1 = repository.getReferenceCell(0, 1);
        cellB2 = repository.getReferenceCell(1, 1);
        cellB3 = repository.getReferenceCell(2, 1);
        cellC1 = repository.getReferenceCell(0, 2);
        cellC2 = repository.getReferenceCell(1, 2);
        cellC3 = repository.getReferenceCell(2, 2);
        cellD1 = repository.getReferenceCell(0, 3);
        cellD2 = repository.getReferenceCell(1, 3);

        cellA1.setCellValue(new CellValue(10));
        cellB1.setCellValue(new CellValue(20));
        cellD1.setCellValue(new CellValue(5));
        cellA2.setCellValue(new CellValue(30));
        cellB2.setCellValue(new CellValue(40));
        cellD2.setCellValue(new CellValue(15));
        cellA3.setCellValue(new CellValue(0));
        // B3, C1, C2, and C3 are not assigned values.
    }

    @Test
    public void testObserver1() {
        String raw = "=AVE(A1:D2)+C1";
        Expression expression = ExpressionParser.convertExpression(raw);

        cellA3.setExpression(expression);
        assertEquals(20.0, cellA3.getCellValue().asDouble());

        cellD1.setCellValue(new CellValue(35));
        assertEquals(25.0, cellA3.getCellValue().asDouble());
    }

    @Test
    public void testObserver2() {
        String raw = "=A1*SUM(A1:B1, 5, C1)";
        Expression expression = ExpressionParser.convertExpression(raw);

        cellD2.setExpression(expression);
        assertEquals(350.0, cellD2.getCellValue().asDouble());

        cellA1.setCellValue(new CellValue(20));
        assertEquals(900.0, cellD2.getCellValue().asDouble());
    }

    @Test
    public void testObserver3() {
        String raw = "=((100-B1)/((B1:D1)/D1))";
        Expression expression = ExpressionParser.convertExpression(raw);

        cellD2.setExpression(expression);
        assertEquals(16.0, cellD2.getCellValue().asDouble());

        cellB1.setCellValue(new CellValue(10));
        assertEquals(30.0, cellD2.getCellValue().asDouble());
    }

    @Test
    public void testObserver4() {
        String raw = "AVE(A1:B2)/D1";
        Expression expression = ExpressionParser.convertExpression(raw);

        cellD2.setExpression(expression);
        assertEquals(5, cellD2.getCellValue().asDouble());

        assertThrows(ArithmeticException.class, () -> cellD1.setCellValue(new CellValue(0)));
    }

    @Test
    public void testObserver5() {
        String raw = "=COUNTA(A1:C2)*D1";
        Expression expression = ExpressionParser.convertExpression(raw);

        cellD2.setExpression(expression);
        assertEquals(20.0, cellD2.getCellValue().asDouble());

        cellA2.setCellValue(new CellValue(null));
        assertEquals(15.0, cellD2.getCellValue().asDouble());
    }

    @Test
    public void testObserver6() {
        String raw = "=COUNTA(A1:C2)*D1";
        Expression expression = ExpressionParser.convertExpression(raw);

        cellD2.setExpression(expression);
        assertEquals(20.0, cellD2.getCellValue().asDouble());

        cellC1.setCellValue(new CellValue(10));
        cellC2.setCellValue(new CellValue(1));
        assertEquals(30.0, cellD2.getCellValue().asDouble());
    }

    @Test
    public void testObserver7() {
        String raw1 = "=SUM(A1:B3)+A3";
        Expression expression1 = ExpressionParser.convertExpression(raw1);

        cellD1.setExpression(expression1);
        assertEquals(100.0, cellD1.getCellValue().asDouble());

        String raw2 = "=AVE(A3, 100)";
        Expression expression2 = ExpressionParser.convertExpression(raw2);
        cellD2.setExpression(expression2);
        assertEquals(50.0, cellD2.getCellValue().asDouble());

        cellA3.setCellValue(new CellValue(10));
        assertEquals(120.0, cellD1.getCellValue().asDouble());
        assertEquals(55.0, cellD2.getCellValue().asDouble());
    }

    @Test
    public void testObserver8() {
        String raw1 = "=SUM(A1:B3)";
        Expression expression1 = ExpressionParser.convertExpression(raw1);

        cellC1.setExpression(expression1);
        assertEquals(100.0, cellC1.getCellValue().asDouble());

        String raw2 = "=COUNTA(A1:C2)";
        Expression expression2 = ExpressionParser.convertExpression(raw2);
        cellC3.setExpression(expression2);
        assertEquals(5.0, cellC3.getCellValue().asDouble());

        cellA1.setCellValue(new CellValue(null));
        cellA2.setCellValue(new CellValue(null));
        cellA3.setCellValue(new CellValue(null));
        cellB1.setCellValue(new CellValue(null));
        cellB2.setCellValue(new CellValue(null));
        cellB3.setCellValue(new CellValue(null));
        assertEquals(1.0, cellC3.getCellValue().asDouble());
    }

    @Test
    public void testObserver9() {
        String raw = "=(A1:B2)/A1";
        Expression expression = ExpressionParser.convertExpression(raw);

        cellD2.setExpression(expression);
        assertEquals(10.0, cellD2.getCellValue().asDouble());

        cellA1.setCellValue(new CellValue(20));
        cellA2.setCellValue(new CellValue(40));
        cellB1.setCellValue(new CellValue(60));
        cellB2.setCellValue(new CellValue(80));
        assertEquals(10.0, cellD2.getCellValue().asDouble());
    }

    @Test
    public void testObserverComplex1() {
        String raw1 = "=A1*D2";
        Expression expression1 = ExpressionParser.convertExpression(raw1);
        cellC1.setExpression(expression1);

        String raw2 = "=A1:B1*SUM(50, C1)";
        Expression expression2 = ExpressionParser.convertExpression(raw2);
        cellC2.setExpression(expression2);
        assertEquals(6000.0, cellC2.getCellValue().asDouble());

        cellD2.setCellValue(new CellValue(5));
        assertEquals(3000.0, cellC2.getCellValue().asDouble());
    }

    @Test
    public void testObserverComplex2() {
        String raw1 = "=D2/D1";
        Expression expression1 = ExpressionParser.convertExpression(raw1);
        cellC1.setExpression(expression1);

        String raw2 = "=C1*COUNTA(A1:B1, 5)";
        Expression expression2 = ExpressionParser.convertExpression(raw2);
        cellC2.setExpression(expression2);
        assertEquals(9.0, cellC2.getCellValue().asDouble());

        cellB1.setCellValue(new CellValue(null));
        assertEquals(6.0, cellC2.getCellValue().asDouble());
    }

    @Test
    public void testObserverComplex3() {
        String raw1 = "=D1+D2";
        Expression expression1 = ExpressionParser.convertExpression(raw1);
        cellC1.setExpression(expression1);

        String raw2 = "=A1*SUM(A1:B1, 5, C1)";
        Expression expression2 = ExpressionParser.convertExpression(raw2);
        cellC2.setExpression(expression2);
        assertEquals(550.0, cellC2.getCellValue().asDouble());

        String raw3 = "=AVE(C1:C2)";
        Expression expression3 = ExpressionParser.convertExpression(raw3);
        cellB2.setExpression(expression3);
        assertEquals(285.0, cellB2.getCellValue().asDouble());

        cellD1.setCellValue(new CellValue(15));
        assertEquals(340.0, cellB2.getCellValue().asDouble());
    }

    @Test
    public void testObserverComplex4() {
        String raw1 = "=AVE(A1:B2)";
        Expression expression1 = ExpressionParser.convertExpression(raw1);
        cellC1.setExpression(expression1);

        String raw2 = "=D1*(C1+25)";
        Expression expression2 = ExpressionParser.convertExpression(raw2);
        cellC2.setExpression(expression2);
        assertEquals(250.0, cellC2.getCellValue().asDouble());

        String raw3 = "=SUM(C1, C2)";
        Expression expression3 = ExpressionParser.convertExpression(raw3);
        cellD2.setExpression(expression3);
        assertEquals(275.0, cellD2.getCellValue().asDouble());

        String raw4 = "=D2/5";
        Expression expression4 = ExpressionParser.convertExpression(raw4);
        cellA3.setExpression(expression4);
        assertEquals(55.0, cellA3.getCellValue().asDouble());

        cellA2.setCellValue(new CellValue(10));
        assertEquals(49.0, cellA3.getCellValue().asDouble());
    }

    @Test
    public void testObserverComplex5() {
        String raw1 = "=AVE(A1:B2, 0)";
        Expression expression1 = ExpressionParser.convertExpression(raw1);
        cellC1.setExpression(expression1);

        String raw2 = "=D1*(C1+25)";
        Expression expression2 = ExpressionParser.convertExpression(raw2);
        cellC2.setExpression(expression2);
        assertEquals(225.0, cellC2.getCellValue().asDouble());

        String raw3 = "=SUM(C1, C2)";
        Expression expression3 = ExpressionParser.convertExpression(raw3);
        cellD2.setExpression(expression3);
        assertEquals(245.0, cellD2.getCellValue().asDouble());

        String raw4 = "=D2/5";
        Expression expression4 = ExpressionParser.convertExpression(raw4);
        cellA3.setExpression(expression4);
        assertEquals(49.0, cellA3.getCellValue().asDouble());

        String raw5 = "=SUM(A1:A3)-100";
        Expression expression5 = ExpressionParser.convertExpression(raw5);
        cellB3.setExpression(expression5);
        assertEquals(-11.0, cellB3.getCellValue().asDouble());

        cellA2.setCellValue(new CellValue(null));
        assertEquals(-44.0, cellB3.getCellValue().asDouble());
    }

    @Test
    public void testObserverComplex6() {
        String raw1 = "=AVE(A1:B2)";
        Expression expression1 = ExpressionParser.convertExpression(raw1);
        cellC1.setExpression(expression1);

        String raw2 = "=D1*(C1+25)";
        Expression expression2 = ExpressionParser.convertExpression(raw2);
        cellC2.setExpression(expression2);
        assertEquals(250.0, cellC2.getCellValue().asDouble());

        String raw3 = "=SUM(C1, C2)";
        Expression expression3 = ExpressionParser.convertExpression(raw3);
        cellD2.setExpression(expression3);
        assertEquals(275.0, cellD2.getCellValue().asDouble());

        String raw4 = "=D2-265";
        Expression expression4 = ExpressionParser.convertExpression(raw4);
        cellA3.setExpression(expression4);
        assertEquals(10.0, cellA3.getCellValue().asDouble());

        String raw5 = "=(SUM(A1:A3)+5)/A3";
        Expression expression5 = ExpressionParser.convertExpression(raw5);
        cellB3.setExpression(expression5);
        assertEquals(5.5, cellB3.getCellValue().asDouble());

        assertThrows(ArithmeticException.class, () -> cellA2.setCellValue(new CellValue(null)));
    }

}