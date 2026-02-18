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

    public int getNumCellComponents() {
        return cellComponents.size();
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
