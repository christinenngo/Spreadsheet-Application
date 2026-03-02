/*
 * Copyright (c) 2026 Sakire Arslan Ay
 *
 * This file was developed for educational purposes as part of CS4233:
 * Object-Oriented Analysis & Design at Worcester Polytechnic Institute.
 *
 * All rights reserved. Redistribution and modification outside the scope
 * of this course are not permitted without prior written permission.
 */
package spreadsheet.Model.Expression;

import spreadsheet.Model.Cell.CellComponent;
import spreadsheet.Model.Cell.CellValue;

import java.util.ArrayList;
import java.util.Set;

/*
 * Expression interface representing an evaluatable expression in the spreadsheet.
 */
public interface Expression {
    /* Evaluates the expression and returns the resulting CellValue.
     * @return the evaluated CellValue of the expression
     * Subclasses should implement their own evaluate logic. */
    public CellValue evaluate();

    /* Adds one or more operands to the expression.
     * @param expression the operands to be added to the expression */
    public void addOperand(Expression... expression);

    /* Retrieves the list of operands associated with the expression.
     * @return an ArrayList of Expression operands */
    public ArrayList<Expression> getOperands();

    public Set<CellComponent> getReferencedCells();

}
