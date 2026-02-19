package spreadsheet.Model.Cell;

import spreadsheet.Model.Expression.Expression;

import java.util.ArrayList;

public class CellGroup extends CellComponent {
    protected ArrayList<CellComponent> cellComponents = new <CellComponent> ArrayList();

    public void add(CellComponent newCellComponent) {
        cellComponents.add(newCellComponent);
    }

    public void remove(CellComponent newCellComponent) {
        cellComponents.remove(newCellComponent);
    }

    public CellComponent getCellComponent(int componentIndex) {
        return (CellComponent)cellComponents.get(componentIndex);
    }

    public int getNumCells() {
        return cellComponents.size();
    }

    public int getNumNonEmptyCells() {
        int count = 0;
        for (CellComponent component : cellComponents) {
            if (component.getCellValue() != null && component.getCellValue().nonEmpty()) {
                count++;
            }
        }
        return count;
    }

    public CellValue getCellValue() {
        throw new UnsupportedOperationException("Method is for cells only.");
    }

    public CellValue setCellValue(CellValue cellValue) {
        throw new UnsupportedOperationException("Method is for cells only.");
    }

    Expression getExpression() {
        throw new UnsupportedOperationException("Method is for cells only.");
    }
}
