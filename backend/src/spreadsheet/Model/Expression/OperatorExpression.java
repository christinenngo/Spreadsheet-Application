package spreadsheet.Model.Expression;

import spreadsheet.Model.Cell.CellValue;

public abstract class OperatorExpression extends AbstractExpression {
    @Override
    public abstract CellValue evaluate();
}
