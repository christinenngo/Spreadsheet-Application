package spreadsheet.Model.Expression;

import spreadsheet.Model.Cell.CellComponent;
import spreadsheet.Model.Cell.CellValue;

import java.util.ArrayList;

public class CountAOperator extends OperatorExpression {
    @Override
    public CellValue evaluate() {
        int count = 0;

        for(Expression operand : operands) {
            if(operand instanceof CellReferenceExpression cellReferenceExpression) {
                count += cellReferenceExpression.getCellComponent().getNumNonEmptyCells();
            } else {
                if(operand.evaluate() != null && operand.evaluate().nonEmpty()) {
                    count++;
                }
            }
        }
        return new CellValue(count);
    }
}
