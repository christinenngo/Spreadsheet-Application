package Milestone3Tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spreadsheet.Model.Cell.CellComponent;
import spreadsheet.Model.Cell.CellValue;
import spreadsheet.Model.CellRepository;
import spreadsheet.Model.Expression.Expression;
import spreadsheet.Model.Parser.ExpressionParser;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Milestone3UnaryTests {
    private CellRepository repository;
    private CellComponent cellA1, cellA2, cellB1, cellB2, cellC1, cellC2, cellD1, cellD2;

    @BeforeEach
    void setup() {
        CellRepository.resetInstance();
        repository = CellRepository.getInstance();
        cellA1 = repository.getReferenceCell(0, 0);
        cellA2 = repository.getReferenceCell(1, 0);
        cellB1 = repository.getReferenceCell(0, 1);
        cellB2 = repository.getReferenceCell(1, 1);
        cellC1 = repository.getReferenceCell(0, 2);
        cellC2 = repository.getReferenceCell(1, 2);
        cellD1 = repository.getReferenceCell(0, 3);
        cellD2 = repository.getReferenceCell(1, 3);

        cellA1.setCellValue(new CellValue(10));
        cellB1.setCellValue(new CellValue(20));
        cellD1.setCellValue(new CellValue(5));
        cellA2.setCellValue(new CellValue(30));
        cellB2.setCellValue(new CellValue(40));
        cellD2.setCellValue(new CellValue(15));
        // C1 and C2 are not assigned values.
    }

    @Test
    public void testIncrement1() {
        String raw = "=++A1+ ++A2 + ++20";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(63.0, value.asDouble());
    }

    @Test
    public void testIncrement2() {
        String raw = "=++SUM(A1:B2)";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(101.0, value.asDouble());
    }

    @Test
    public void testIncrement3() {
        String raw = "=++ ++C1";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(2.0, value.asDouble());
    }

    @Test
    public void testIncrement4() {
        String raw = "=++C1*2";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(2.0, value.asDouble());
    }

    @Test
    public void testIncrement5() {
        String raw = "=A1+ ++A2";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(41.0, value.asDouble());
    }

    @Test
    public void testIncrement6() {
        String raw = "=A1+++B1";

        assertThrows(NoSuchElementException.class, () -> ExpressionParser.convertExpression(raw));
    }

    @Test
    public void testDecrement1() {
        String raw = "=--A1- --A2 + 20";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(0.0, value.asDouble());
    }

    @Test
    public void testDecrement2() {
        String raw = "=--SUM(A1:B2)";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(99.0, value.asDouble());
    }

    @Test
    public void testDecrement3() {
        String raw = "=-- --C1";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(-2.0, value.asDouble());
    }

    @Test
    public void testDecrement4() {
        String raw = "=--C1*2";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(-2.0, value.asDouble());
    }

    @Test
    public void testDecrement5() {
        String raw = "=A1- --A2";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(-19.0, value.asDouble());
    }

    @Test
    public void testDecrement6() {
        String raw = "=A1---B1";

        assertThrows(NoSuchElementException.class, () -> ExpressionParser.convertExpression(raw));
    }

    @Test
    public void testAbs1() {
        String raw = "=ABS(A1-A2)";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(20.0, value.asDouble());
    }

    @Test
    public void testAbs2() {
        String raw = "=ABS(SUM(A1:B2)-200)";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(100.0, value.asDouble());
    }

    @Test
    public void testAbs3() {
        String raw = "=ABS(A1:B2)";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(100.0, value.asDouble());
    }

    @Test
    public void testAbs4() {
        String raw = "=ABS(NEG(A1:B2))";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(100.0, value.asDouble());
    }

    @Test
    public void testNeg1() {
        String raw = "=NEG(A2-A1)";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(-20.0, value.asDouble());
    }

    @Test
    public void testNeg2() {
        String raw = "=NEG(A1-A2)";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(20.0, value.asDouble());
    }

    @Test
    public void testNeg3() {
        String raw = "=NEG(A1:B2)";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(-100.0, value.asDouble());
    }

    @Test
    public void testNeg4() {
        String raw = "=NEG(SUM(A1:B2)-200)";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(100.0, value.asDouble());
    }

    @Test
    public void testNeg5() {
        String raw = "=NEG(ABS(A1:B2-200))";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(-100.0, value.asDouble());
    }
}
