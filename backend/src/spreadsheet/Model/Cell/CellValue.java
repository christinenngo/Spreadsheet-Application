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
import java.util.List;
import java.util.Objects;

/*
 * Represents the value of a cell in the spreadsheet.
 * Provides methods to check the type of the value and to retrieve the value in the desired type.
 * Can be of different types: String, Double, Integer, List, or Empty.
 *   * For simplicity, we assume that all numeric values are represented as Double.
 *   * Integer values are converted to Double when necessary.
 *   * Empty cells are represented by a null value.
 *   * You may assume that String cell values are no supported.
 */
public class CellValue {
    private Object value;

    public CellValue(Object value) {
        this.value = value;
    }
    public boolean isEmpty() { return Objects.isNull(value); }
    public boolean nonEmpty() { return Objects.nonNull(value); }
    public boolean isString() { return value instanceof String; }
    public boolean isDouble() { return value instanceof Double; }
    public boolean isInteger() { return value instanceof Integer; }
    public boolean isList() { return value instanceof List; }


    public Double asDouble()  {
        if (isDouble() || isInteger())  return ((Number) value).doubleValue();
        else if (isString() && value.equals(""))  return 0.0;
        else if (isEmpty())  return 0.0;
        else  return null;
    }
    public List<?> asList()  {
        return (isList())  ? ((List<?>) value) : null;
    }

    public String toString() {
        return  (isEmpty()) ? "" : value.toString();
    }
}