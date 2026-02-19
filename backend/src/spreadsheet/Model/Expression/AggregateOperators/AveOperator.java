package spreadsheet.Model.Expression.AggregateOperators;

import spreadsheet.Model.Cell.CellValue;
import spreadsheet.Model.Expression.CellReferenceExpression;
import spreadsheet.Model.Expression.Expression;
import spreadsheet.Model.Expression.OperatorExpression;

public class AveOperator extends OperatorExpression {
    @Override
    public CellValue evaluate() {
        double result = operands.stream()
                .mapToDouble(num -> num.evaluate().asDouble())
                .sum();

        int count = 0;
        for(Expression operand : operands) {
            if(operand instanceof CellReferenceExpression cellReferenceExpression) {
                count += cellReferenceExpression.getCellComponent().getNumCells();
            } else {
                count++;
            }
        }

        if(count == 0) {
            return new CellValue(0);
        }
        return new CellValue(result/count);
    }
}
