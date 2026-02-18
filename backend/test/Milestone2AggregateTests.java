import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spreadsheet.Model.Cell.CellComponent;
import spreadsheet.Model.Cell.CellValue;
import spreadsheet.Model.CellRepository;
import spreadsheet.Model.Expression.Expression;
import spreadsheet.Model.Parser.ExpressionParser;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Milestone2AggregateTests {
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
    public void testSingleCellSum1() {
        String raw = "=SUM(B1)";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(20.0, value.asDouble());
    }

    @Test
    public void testSingleCellSum2() {
        String raw = "=SUM(A1,B1,A2)";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(60.0, value.asDouble());
    }

    @Test
    public void testCellGroupSum() {
        String raw = "=SUM(A1:B2)";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(100.0, value.asDouble());
    }

    @Test
    public void testCellGroupSumComplex() {
        String raw = "=SUM(A1:B2, D1, 10/2)";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(110.0, value.asDouble());
    }

    @Test
    public void testSingleCellCount1() {
        String raw = "=COUNT(B2)";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(3.0, value.asDouble());
    }

    @Test
    public void testSingleCellCount2() {
        String raw = "=COUNT(A1,B1,A2)";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(3.0, value.asDouble());
    }

    @Test
    public void testCellGroupCount() {
        String raw = "=COUNT(A1:B2)";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(4.0, value.asDouble());
    }

    @Test
    public void testCellGroupCountComplex() {
        String raw = "=COUNT(A1:B2, D1, 10/2)";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(6.0, value.asDouble());
    }

    @Test
    public void testSingleCellAve1() {
        String raw = "=AVE(C2)";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(3.0, value.asDouble());
    }

    @Test
    public void testSingleCellAve2() {
        String raw = "=AVE(A1,B1,A2)";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(3.0, value.asDouble());
    }

    @Test
    public void testCellGroupAve() {
        String raw = "=AVE(A1:B2)";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(4.0, value.asDouble());
    }

    @Test
    public void testCellGroupAveComplex() {
        String raw = "=AVE(A1:B2, D1, 10/2)";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(6.0, value.asDouble());
    }

}
