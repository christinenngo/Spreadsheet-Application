/*
 * Copyright (c) 2026 Sakire Arslan Ay
 *
 * This file was developed for educational purposes as part of CS4233:
 * Object-Oriented Analysis & Design at Worcester Polytechnic Institute.
 *
 * All rights reserved. Redistribution and modification outside the scope
 * of this course are not permitted without prior written permission.
 */
package spreadsheet.Model.Cell;

import spreadsheet.Model.CellCoord;
import spreadsheet.Model.Expression.Expression;
import spreadsheet.Observer.Observer;
import spreadsheet.Observer.Subject;

import java.util.ArrayList;
import java.util.List;

/**
 * Cell class representing a single cell in the spreadsheet.
 */
public class Cell extends CellComponent implements Observer, Subject {
    /*The current value of the cell, either a constant or the result of evaluating its expression
    * if the expression of the cell is not null, then "value" holds the value the expression evaluates to.
    * */
    private CellValue value = null;
    /* expression is the expression assigned to the cell, if any
    * if the cell is assigned a constant value, expression will be null. */
    private Expression expression;

    private List<Observer> observers = new ArrayList<>();

    public Cell(CellValue value){
        this.value = value;
    }

    public CellValue getCellValue(){
        return this.value;
    }

    public CellValue setCellValue(CellValue newValue){
        this.expression = null;
        this.value = newValue;
        notifyObservers();
        return this.value;
    }

    /**
     * --------------------------------------------------------
     *          EXPRESSION METHODS
     * --------------------------------------------------------
     */
    public void setExpression(Expression expression){
        if (this.expression != null){
            for (CellComponent cellComponent : this.expression.getReferencedCells()){
                cellComponent.removeObserver(this);
            }
        }

        this.expression = expression;

        for (CellComponent cellComponent : expression.getReferencedCells()){
            cellComponent.addObserver(this);
        }

        this.value = expression.evaluate();
        notifyObservers();
    }

    public Expression getExpression(){
        return this.expression;
    }

    public int getNumCells() {
        return 1;
    }

    public int getNumNonEmptyCells() {
        if(value != null && value.nonEmpty()){
            return 1;
        }
        return 0;
    }

    public ArrayList<CellComponent> getCellComponents(){
        ArrayList<CellComponent> cellComponents = new ArrayList<>();
        cellComponents.add(this);
        return cellComponents;
    }

    public void add(CellComponent newCellComponent){
        throw new UnsupportedOperationException("Method is for cell groups only.");
    }

    public void remove(CellComponent newCellComponent){
        throw new UnsupportedOperationException("Method is for cell groups only.");
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
        if (expression != null) {
            this.value = expression.evaluate();
        }
        notifyObservers();
    }

    @Override
    public List<Observer> getObservers() {
        return observers;
    }
}
