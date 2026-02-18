package spreadsheet.Model.Cell;

import spreadsheet.Model.Expression.Expression;

public abstract class CellComponent {
    public abstract void add(CellComponent newCellComponent);
    public abstract void remove(CellComponent newCellComponent);
    abstract CellComponent getCellComponent(int componentIndex);
    public abstract int getNumCellComponents();
    public abstract CellValue getCellValue();
    public abstract CellValue setCellValue(CellValue cellValue);
    abstract Expression getExpression();
}
