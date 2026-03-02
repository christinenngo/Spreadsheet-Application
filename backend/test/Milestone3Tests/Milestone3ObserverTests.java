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
    public void testObserver1() {
        String raw = "=A1*SUM(A1:B1, 5, C1)";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(350.0, value.asDouble());

        cellA1.setCellValue(new CellValue(20));
        CellValue newValue = expression.evaluate();
        assertEquals(900.0, newValue.asDouble());
    }

    @Test
    public void testObserver2() {
        String raw = "=((100-B1)/((B1:D1)/D1))";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(16.0, value.asDouble());

        cellB1.setCellValue(new CellValue(10));
        CellValue newValue = expression.evaluate();
        assertEquals(30.0, newValue.asDouble());
    }

    @Test
    public void testObserver3() {
        String raw = "AVE(A1:B2)/D1";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(5, value.asDouble());

        cellD1.setCellValue(new CellValue(0));
        assertThrows(ArithmeticException.class, expression::evaluate);
    }

    @Test
    public void testObserver4() {
        String raw = "=COUNTA(A1:D2)";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(6.0, value.asDouble());

        cellA2.setCellValue(new CellValue(null));
        CellValue newValue = expression.evaluate();
        assertEquals(5.0, newValue.asDouble());
    }



}