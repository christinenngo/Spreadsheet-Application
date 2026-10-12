package spreadsheet.Model.Cell;

import spreadsheet.Model.CellCoord;
import spreadsheet.Model.Expression.Expression;
import spreadsheet.Observer.Observer;

import java.util.List;
import java.util.ArrayList;

public abstract class CellComponent {
    public abstract void add(CellComponent newCellComponent);
    public abstract void remove(CellComponent cellComponent);
    public abstract int getNumCells();
    public abstract ArrayList<CellComponent> getCellComponents();
    public abstract int getNumNonEmptyCells();
    public abstract CellValue getCellValue();
    public abstract CellValue setCellValue(CellValue cellValue);
    public abstract void setExpression(Expression expression);
    public abstract Expression getExpression();
    public abstract void addObserver(Observer observer);
    public abstract void removeObserver(Observer observer);
    public abstract void notifyObservers();
    public abstract List<Observer> getObservers();
}
