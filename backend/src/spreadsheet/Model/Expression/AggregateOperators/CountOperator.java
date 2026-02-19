package spreadsheet.Model.Expression.AggregateOperators;

import spreadsheet.Model.Cell.CellValue;
import spreadsheet.Model.Expression.CellReferenceExpression;
import spreadsheet.Model.Expression.Expression;
import spreadsheet.Model.Expression.OperatorExpression;

public class CountOperator extends OperatorExpression {
    @Override
    public CellValue evaluate() {
        int count = 0;

        for(Expression operand : operands) {
            if(operand instanceof CellReferenceExpression cellReferenceExpression) {
                count += cellReferenceExpression.getCellComponent().getNumCells();
            } else {
                count++;
            }
        }
        return new CellValue(count);
    }
}
