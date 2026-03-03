package spreadsheet.Model.Expression.ArithmeticOperators;

import spreadsheet.Model.Cell.CellValue;
import spreadsheet.Model.Expression.ArithmeticOperatorExpression;

public class AddOperator extends ArithmeticOperatorExpression {
    @Override
    public CellValue evaluate() {
        double result = operands.stream()
                .mapToDouble(num -> num.evaluate().asDouble())
                .sum();
        return new CellValue(result);
    }
}
