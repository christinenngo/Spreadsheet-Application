package spreadsheet.Model.Cell;

import spreadsheet.Model.Expression.Expression;

import java.util.ArrayList;

public class CellGroup extends CellComponent {
    ArrayList<CellComponent> cellComponents = new <CellComponent> ArrayList();
    String groupName;

    public CellGroup(String newGroupName) {
        groupName = newGroupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void add(CellComponent newCellComponent) {
        cellComponents.add(newCellComponent);
    }

    public void remove(CellComponent newCellComponent) {
        cellComponents.remove(newCellComponent);
    }

    public CellComponent getCellComponent(int componentIndex) {
        return (CellComponent)cellComponents.get(componentIndex);
    }

    CellValue getCellValue() {
        throw new UnsupportedOperationException("Method is for cells only.");
    }

    Expression getExpression() {
        throw new UnsupportedOperationException("Method is for cells only.");
    }
}
