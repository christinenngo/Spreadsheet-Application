package spreadsheet.Model.Expression;

import spreadsheet.Model.Cell.CellComponent;
import spreadsheet.Model.Cell.CellValue;
import spreadsheet.Observer.Observer;

import java.util.Set;


public class CellReferenceExpression extends AbstractExpression {
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

    @Override
    public Set<CellComponent> getReferencedCells() {
        return Set.of(cellComponent);
    }
}
