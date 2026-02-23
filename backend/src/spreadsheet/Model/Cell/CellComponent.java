package spreadsheet.Model.Cell;

import spreadsheet.Model.CellCoord;
import spreadsheet.Model.Expression.Expression;

import java.util.ArrayList;

public abstract class CellComponent {
    public abstract void add(CellComponent newCellComponent);
    public abstract void remove(CellComponent cellComponent);
    public abstract int getNumCells();
    public abstract ArrayList<CellComponent> getCellComponents();
    public abstract int getNumNonEmptyCells();
    public abstract CellValue getCellValue();
    public abstract CellValue setCellValue(CellValue cellValue);
    abstract Expression getExpression();
}
