package Milestone3Tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spreadsheet.Model.Cell.CellComponent;
import spreadsheet.Model.Cell.CellValue;
import spreadsheet.Model.CellRepository;
import spreadsheet.Model.Expression.Expression;
import spreadsheet.Model.Parser.ExpressionParser;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Milestone3SampleTests {
    private CellRepository repository;
    private CellComponent cellA1, cellA2, cellB1, cellB2, cellC1, cellC2, cellD1, cellD2, cellE1, cellE2, cellE3, cellE4;

    @BeforeEach
    void setup(){
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
        cellE1 = repository.getReferenceCell(0, 4);
        cellE2 = repository.getReferenceCell(1, 4);


        cellA1.setCellValue(new CellValue(10));
        cellA2.setCellValue(new CellValue(30));
        cellB1.setCellValue(new CellValue(20));
        cellB2.setCellValue(new CellValue(40));
        cellD1.setCellValue(new CellValue(5));
        cellD2.setCellValue(new CellValue(15));
        cellE1.setCellValue(new CellValue(100));

        // C1 , C2, and E2 are not assigned values.
    }

    // Milestone 3 tests
    @Test
    public void test_COUNT(){
        Expression expression = ExpressionParser.convertExpression("=COUNT(A1:D2)");
        CellValue value = expression.evaluate();
        assertEquals(8.0, value.asDouble());  // C1 and C2 are empty but included in the count

        expression = ExpressionParser.convertExpression("=COUNT(A1:D2, E1, E2, 11.0)");
        value = expression.evaluate();
        assertEquals(11.0, value.asDouble()); // C1, C2, E2 are empty but included in the count

        expression = ExpressionParser.convertExpression("=COUNT(C1:C2)");
        value = expression.evaluate();
        assertEquals(2, value.asDouble()); // C1, C2 are empty but included in the count
    }

    @Test
    public void test_COUNTA(){
        Expression expression = ExpressionParser.convertExpression("=COUNTA(A1:D2)");
        CellValue value = expression.evaluate();
        assertEquals(6.0, value.asDouble());  // C1 and C2 are empty, and they are not included in the count

        expression = ExpressionParser.convertExpression("=COUNTA(A1:D2, E1, E2, 11.0)");
        value = expression.evaluate();
        assertEquals(8.0, value.asDouble()); // C1, C2, E2 are empty, and they are not included in the count

        expression = ExpressionParser.convertExpression("=COUNTA(C1:C2)");
        value = expression.evaluate();
        assertEquals(0, value.asDouble()); // C1, C2 are empty , and they are not included in the count
    }

    @Test
    public void test_AVE(){
        Expression expression = ExpressionParser.convertExpression("=AVE(A1:D2)"); // A1+A2+B1+B2+D1+D2 = 10+30+20+40+5+15 = 120/6 = 20.0
        CellValue value = expression.evaluate();
        assertEquals(20.0, value.asDouble());  // C1 and C2 are empty, and they are not included in the count when calculating the average

        expression = ExpressionParser.convertExpression("=AVE(A1:D2, E1, E2, 12)"); // A1+A2+B1+B2+D1+D2+E1+12 = 10+30+20+40+5+15+100+12 = 232/8 = 29
        value = expression.evaluate();
        assertEquals(29.0, value.asDouble()); // C1, C2, E2 are empty, and they are not included in the count when calculating the average

        //expression = ExpressionParser.convertExpression("=AVE(C1:C2)"); // both C1 and C2 are empty. You can either return 0 or return an error.
        // We will not test for this in milestone3.
        //value = expression.evaluate();
        //assertEquals(0.0, value.asDouble()); // C1, C2 are empty , and they are not included in the count when calculating the average
    }

    @Test
    public void test_MAX(){
        Expression expression = ExpressionParser.convertExpression("=MAX(A1:D2)");
        CellValue value = expression.evaluate();
        assertEquals(40.0, value.asDouble());  // C1 and C2 are empty

        expression = ExpressionParser.convertExpression("=MAX(A1:D2, E1, E2, 50)");
        value = expression.evaluate();
        assertEquals(100.0, value.asDouble()); // C1, C2, E2 are empty

        expression = ExpressionParser.convertExpression("=MAX(C1:C2)");
        // We will not test for this in milestone3.
        value = expression.evaluate();
        assertEquals(0.0, value.asDouble());  // both C1 and C2 are empty. You can return 0.
    }

    @Test
    public void test_MIN(){
        Expression expression = ExpressionParser.convertExpression("=MIN(A1:D2)");
        CellValue value = expression.evaluate();
        assertEquals(5.0, value.asDouble());  // C1 and C2 are empty

        expression = ExpressionParser.convertExpression("=MIN(A1:D2, E1, E2, 4)");
        value = expression.evaluate();
        assertEquals(4.0, value.asDouble()); // C1, C2, E2 are empty

        expression = ExpressionParser.convertExpression("=MIN(C1:C2)");
        // We will not test for this in milestone3.
        value = expression.evaluate();
        assertEquals(0.0, value.asDouble());  // both C1 and C2 are empty. You can return 0.
    }

    @Test
    public void test_MEDIAN(){
        Expression expression = ExpressionParser.convertExpression("=MEDIAN(A1:D2)");
        CellValue value = expression.evaluate();
        assertEquals(17.5, value.asDouble());  // C1 and C2 are empty

        expression = ExpressionParser.convertExpression("=MEDIAN(A1:D2, E1, E2)");
        value = expression.evaluate();
        assertEquals(20.0, value.asDouble()); // C1, C2, E2 are empty

        expression = ExpressionParser.convertExpression("=MEDIAN(A1:D2, E1, E2, 50)");
        value = expression.evaluate();
        assertEquals(25.0, value.asDouble()); // C1, C2, E2 are empty

        // expression = ExpressionParser.convertExpression("=MEDIAN(C1:C2)"); // You can give an error for this case since there are no values to compare.
        // We will not test for this in milestone3
    }

    @Test
    public void test_Increment(){
        String raw = "=++A2";
        Expression expression = ExpressionParser.convertExpression(raw);
        CellValue value = expression.evaluate();
        assertEquals(31.0, value.asDouble());

        expression = ExpressionParser.convertExpression("=SUM(++A2,++A1,++20)");
        value = expression.evaluate();
        assertEquals(63.0, value.asDouble());

        expression = ExpressionParser.convertExpression("=++C1"); // You can give an error or return 1, since C1 is empty.
        // We will not test for this in milestone3
//        value = expression.evaluate();
//        assertEquals(1, value.asDouble()); // C1, C2, E2 are empty
    }

    @Test
    public void test_ABSandNEG(){
        String raw = "=ABS(-1*A2)";
        Expression expression = ExpressionParser.convertExpression(raw);
        CellValue value = expression.evaluate();
        assertEquals(30.0, value.asDouble());

        expression = ExpressionParser.convertExpression("=NEG(A2)");
        value = expression.evaluate();
        assertEquals(-30.0, value.asDouble());

        expression = ExpressionParser.convertExpression("=SUM(ABS(A2),NEG(A2))");
        value = expression.evaluate();
        assertEquals(0.0, value.asDouble());

        expression = ExpressionParser.convertExpression("=ABS(C1)"); // You can give an error or return 0.0, since C1 is empty.
        // We will not test for this in milestone3
        value = expression.evaluate();
        assertEquals(0.0, value.asDouble()); // C1, C2, E2 are empty

        expression = ExpressionParser.convertExpression("=NEG(C1)"); // You can give an error or return 0.0 or -0.0, since C1 is empty.
        // We will not test for this in milestone3
        value = expression.evaluate();
        assertEquals(0.0, value.asDouble()); // C1, C2, E2 are empty
    }

}
