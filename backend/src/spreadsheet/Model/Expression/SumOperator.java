package spreadsheet.Model.Expression;

import spreadsheet.Model.Cell.CellValue;

public class SumOperator extends OperatorExpression {
    @Override
    public CellValue evaluate() {
        double result = operands.stream()
                .mapToDouble(num -> num.evaluate().asDouble())
                .sum();
        return new CellValue(result);
    }
}
