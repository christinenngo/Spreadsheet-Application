package spreadsheet.Model.Expression.ArithmeticOperators;

import spreadsheet.Model.Cell.CellValue;
import spreadsheet.Model.Expression.OperatorExpression;

public class DivideOperator extends OperatorExpression {
    @Override
    public CellValue evaluate() {
        double result = operands.stream()
                .mapToDouble(num -> num.evaluate().asDouble())
                .reduce((a, b) -> {
                    if (b == 0) {
                        throw new ArithmeticException("Division by zero");
                    }
                    return a / b;
                })
                .orElse(0.0);
        return new CellValue(result);
    }
}
