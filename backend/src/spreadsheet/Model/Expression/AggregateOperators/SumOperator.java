package spreadsheet.Model.Expression.AggregateOperators;

import spreadsheet.Model.Cell.CellValue;
import spreadsheet.Model.Expression.OperatorExpression;

public class SumOperator extends OperatorExpression {
    @Override
    public CellValue evaluate() {
        double result = operands.stream()
                .mapToDouble(num -> num.evaluate().asDouble())
                .sum();
        return new CellValue(result);
    }
}
