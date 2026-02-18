package spreadsheet.Model.Expression;

import spreadsheet.Model.Cell.CellComponent;
import spreadsheet.Model.Cell.CellValue;

import java.util.ArrayList;

public class CellReferenceExpression extends AbstractExpression {
    private CellComponent cellComponent;

    public CellReferenceExpression(CellComponent cellComponent) {
        this.cellComponent = cellComponent;
    }

    public CellValue evaluate() {
        return cellComponent.getCellValue();
    }

    @Override
    public void addOperand(Expression... expression) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public ArrayList<Expression> getOperands() {
        return new ArrayList<>();
    }
}
