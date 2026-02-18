import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spreadsheet.Model.Cell.CellComponent;
import spreadsheet.Model.Cell.CellValue;
import spreadsheet.Model.Parser.ExpressionParser;
import spreadsheet.Model.CellRepository;
import spreadsheet.Model.Expression.Expression;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class Milestone2CellReferenceTests{
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
    public void testSingleCellReference1() {
        String raw = "=A1";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(10.0, value.asDouble());
    }

    @Test
    public void testSingleCellReference2() {
        String raw = "=A2";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(30.0, value.asDouble());
    }

    @Test
    public void testSingleCellReference3() {
        String raw = "=D2";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(15.0, value.asDouble());
    }

    @Test
    public void testSingleCellReferenceNoValue() {
        String raw = "=C1";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(0.0, value.asDouble());
    }

    @Test
    public void testSingleCellReferenceAddition1() {
        String raw = "=A1 + 20";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(30.0, value.asDouble());
    }

    @Test
    public void testSingleCellReferenceAddition2() {
        String raw = "=A1 + D2";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(25.0, value.asDouble());
    }

    @Test
    public void testSingleCellReferenceComplex1() {
        String raw = "=A1*((100-B1)/((B1+D1)/D1))";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(160.0, value.asDouble());
    }

    @Test
    public void testSingleCellReferenceComplex2() {
        String raw = "=A1+C2*(D2+100/(10+B1+D1))";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(10.0, value.asDouble());
    }

    @Test
    public void testSingleCellReferenceComplex3() {
        String raw = "=D1*A2/(D1-B1)+C1";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(-10.0, value.asDouble());
    }

    @Test
    public void testSingleCellReferenceComplex4() {
        String raw = "=C1-D1+A1*B1/(D1+B1)";
        Expression expression = ExpressionParser.convertExpression(raw);

        CellValue value = expression.evaluate();
        assertEquals(3.0, value.asDouble());
    }



}