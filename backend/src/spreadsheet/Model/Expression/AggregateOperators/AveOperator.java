package spreadsheet.Model.Expression.AggregateOperators;

import spreadsheet.Model.Cell.CellValue;
import spreadsheet.Model.Expression.AggregateOperatorExpression;
import spreadsheet.Model.Expression.CellReferenceExpression;
import spreadsheet.Model.Expression.Expression;

public class AveOperator extends AggregateOperatorExpression {
    @Override
    public CellValue evaluate() {
        double result = operands.stream()
                .mapToDouble(num -> num.evaluate().asDouble())
                .sum();

        int count = 0;
        for(Expression operand : operands) {
            if(operand instanceof CellReferenceExpression cellReferenceExpression) {
                count += cellReferenceExpression.getCellComponent().getNumNonEmptyCells();
            } else {
                count++;
            }
        }

        if(count == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return new CellValue(result/count);
    }
}
