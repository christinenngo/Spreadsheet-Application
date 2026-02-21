package spreadsheet.Model.Expression;

import spreadsheet.Model.Cell.CellComponent;
import spreadsheet.Model.Cell.CellValue;


public class CellReferenceExpression extends OperatorExpression {
    private CellComponent cellComponent;

    public CellReferenceExpression(CellComponent cellComponent) {
        this.cellComponent = cellComponent;
    }

    public CellComponent getCellComponent() {
        return cellComponent;
    }

    @Override
    public CellValue evaluate() {
        return cellComponent.getCellValue();
    }
}
