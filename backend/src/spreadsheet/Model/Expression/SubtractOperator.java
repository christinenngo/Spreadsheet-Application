package spreadsheet.Model.Expression;

import spreadsheet.Model.Cell.CellValue;

public class SubtractOperator extends OperatorExpression {
    @Override
    public CellValue evaluate() {
        double result = operands.stream()
                .mapToDouble(num -> num.evaluate().asDouble())
                .reduce((a, b) -> a - b)
                .orElse(0.0);
        return new CellValue(result);
    }
}
