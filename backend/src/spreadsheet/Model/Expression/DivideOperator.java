package spreadsheet.Model.Expression;

import spreadsheet.Model.Cell.CellValue;

public class DivideOperator extends OperatorExpression {
    @Override
    public CellValue evaluate() {
        double result = operands.stream()
                .mapToDouble(num -> num.evaluate().asDouble())
                .filter(num -> num != 0)
                .reduce((a, b) -> a / b)
                .orElse(0.0);
        return new CellValue(result);
    }
}
