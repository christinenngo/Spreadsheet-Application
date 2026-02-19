package spreadsheet.Model.Expression.ArithmeticOperators;

import spreadsheet.Model.Cell.CellValue;
import spreadsheet.Model.Expression.OperatorExpression;

public class MultiplyOperator extends OperatorExpression {
    @Override
    public CellValue evaluate() {
        double result = operands.stream()
                .mapToDouble(num -> num.evaluate().asDouble())
                .reduce((a, b) -> a * b)
                .orElse(0.0);
        return new CellValue(result);
    }
}
