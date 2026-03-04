package spreadsheet.Model.Expression.UnaryOperators;

import spreadsheet.Model.Cell.CellValue;
import spreadsheet.Model.Expression.UnaryOperatorExpression;

public class NegateOperator extends UnaryOperatorExpression {
    @Override
    public CellValue evaluate() {
        double result = operands.stream()
                .mapToDouble(num -> num.evaluate().asDouble())
                .sum() + 1;
        return new CellValue(result);
    }
}
