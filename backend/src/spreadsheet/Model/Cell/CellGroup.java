package spreadsheet.Model.Cell;

import spreadsheet.Model.CellCoord;
import spreadsheet.Model.Expression.Expression;

import java.util.ArrayList;

public class CellGroup extends CellComponent {
    protected ArrayList<CellComponent> cellComponents = new <CellComponent> ArrayList();

    public void add(CellComponent newCellComponent) {
        cellComponents.add(newCellComponent);
    }

    public void remove(CellComponent cellComponent) {
        cellComponents.remove(cellComponent);
    }

    public int getNumCells() {
        return cellComponents.size();
    }

    public ArrayList<CellComponent> getCellComponents() {
        return cellComponents;
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
        double sum = 0.0;
        for (CellComponent component : cellComponents) {
            if (component.getCellValue() == null) {
                sum += 0.0;
            } else {
                sum += component.getCellValue().asDouble();
            }
        }
        return new CellValue(sum);
    }

    public CellValue setCellValue(CellValue cellValue) {
        throw new UnsupportedOperationException("Method is for cells only.");
    }

    Expression getExpression() {
        throw new UnsupportedOperationException("Method is for cells only.");
    }
}
