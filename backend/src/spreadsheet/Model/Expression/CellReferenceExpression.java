package spreadsheet.Model.Expression;

import spreadsheet.Model.Cell.CellComponent;
import spreadsheet.Model.Cell.CellValue;

import java.util.ArrayList;

public class CellReferenceExpression extends OperatorExpression {
    private CellComponent cellComponent;

    public CellReferenceExpression(CellComponent cellComponent) {
        this.cellComponent = cellComponent;
    }

    @Override
    public CellValue evaluate() {
        return cellComponent.getCellValue();
    }
}
