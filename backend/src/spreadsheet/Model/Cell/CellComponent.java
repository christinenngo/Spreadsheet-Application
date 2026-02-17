package spreadsheet.Model.Cell;

import spreadsheet.Model.Expression.Expression;

public abstract class CellComponent {
    abstract void add(CellComponent newCellComponent);
    abstract void remove(CellComponent newCellComponent);
    abstract CellComponent getCellComponent(int componentIndex);
    abstract CellValue getCellValue();
    abstract Expression getExpression();
}
