package spreadsheet.Model.Cell;

import spreadsheet.Model.CellCoord;
import spreadsheet.Model.Expression.Expression;
import spreadsheet.Observer.Observer;
import spreadsheet.Observer.Subject;

import java.util.ArrayList;
import java.util.List;

public class CellGroup extends CellComponent implements Observer, Subject {
    protected ArrayList<CellComponent> cellComponents = new ArrayList<>();

    private List<Observer> observers = new ArrayList<>();

    public void add(CellComponent newCellComponent) {
        cellComponents.add(newCellComponent);
        newCellComponent.addObserver(this);
        notifyObservers();
    }

    public void remove(CellComponent cellComponent) {
        cellComponents.remove(cellComponent);
        cellComponent.removeObserver(this);
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

    public void setExpression(Expression expression) {
        throw new UnsupportedOperationException("Method is for cells only.");
    }

    public Expression getExpression() {
        throw new UnsupportedOperationException("Method is for cells only.");
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers){
            observer.update();
        }
    }

    @Override
    public void update() {
        notifyObservers();
    }

    @Override
    public List<Observer> getObservers() {
        return observers;
    }

}
