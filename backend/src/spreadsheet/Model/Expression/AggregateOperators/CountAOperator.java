package spreadsheet.Model.Expression.AggregateOperators;

import spreadsheet.Model.Cell.CellValue;
import spreadsheet.Model.Expression.CellReferenceExpression;
import spreadsheet.Model.Expression.Expression;
import spreadsheet.Model.Expression.OperatorExpression;

public class CountAOperator extends OperatorExpression {
    @Override
    public CellValue evaluate() {
        int count = 0;

        for(Expression operand : operands) {
            if(operand instanceof CellReferenceExpression cellReferenceExpression) {
                count += cellReferenceExpression.getCellComponent().getNumNonEmptyCells();
            } else {
                CellValue value = operand.evaluate();
                if(value != null && value.nonEmpty()) {
                    count++;
                }
            }
        }
        return new CellValue(count);
    }
}
