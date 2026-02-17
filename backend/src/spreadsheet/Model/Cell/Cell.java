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

import java.util.ArrayList;
import spreadsheet.Model.Expression.Expression;

/**
 * Cell class representing a single cell in the spreadsheet.
 */
public class Cell extends CellComponent {
    /*The current value of the cell, either a constant or the result of evaluating its expression
    * if the expression of the cell is not null, then "value" holds the value the expression evaluates to.
    * */
    private CellValue value = null;
    /* expression is the expression assigned to the cell, if any
    * if the cell is assigned a constant value, expression will be null. */
    private Expression expression;

    public Cell(CellValue value){
        this.value = value;
    }

    public CellValue getCellValue(){
        return this.value;
    }

    public CellValue setCellValue(CellValue newValue){
        this.expression = null;
        this.value = newValue;
        return this.value;
    }

    /**
     * --------------------------------------------------------
     *          EXPRESSION METHODS
     * --------------------------------------------------------
     */
    public void setExpression(Expression expression){
        this.expression = expression;
        this.value = expression.evaluate();
    }

    public Expression getExpression(){
        return this.expression;
    }

    void add(CellComponent newCellComponent){
        throw new UnsupportedOperationException("Method is for cell groups only.");
    }

    void remove(CellComponent newCellComponent){
        throw new UnsupportedOperationException("Method is for cell groups only.");
    }

    CellComponent getCellComponent(int componentIndex){
        throw new UnsupportedOperationException("Method is for cell groups only.");
    }
}
