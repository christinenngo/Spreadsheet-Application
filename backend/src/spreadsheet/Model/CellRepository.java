/*
 * Copyright (c) 2026 Sakire Arslan Ay
 *
 * This file was developed for educational purposes as part of CS4233:
 * Object-Oriented Analysis & Design at Worcester Polytechnic Institute.
 *
 * All rights reserved. Redistribution and modification outside the scope
 * of this course are not permitted without prior written permission.
 */

package spreadsheet.Model;

import java.util.HashMap;
import java.util.Map;

import spreadsheet.Model.Cell.Cell;
import org.springframework.stereotype.Repository;
import spreadsheet.Model.Cell.CellValue;

/**
 * A singleton central data point to fetch CellComponents.
 * Each CellComponent is identified by its CellCoord.
 *
 * Consist of 50 ROWS and 26 COLUMNS
 *
 * Singleton pattern allows the repository to be consistent across all parts of the code.
 */
@Repository
public class CellRepository {
    private static CellRepository instance;
    // Every CellCoord maps to a Cell. In milestone2, you will revise this HashMap have it store "cell components" ( common interface for cells or cell groups)
    private static HashMap<CellCoord, Cell> cellMap;

    public static final int ROWS = 50;
    public static final int COLUMNS = 26;

    /**
     * private constructor to prevent accidentally reset the data point
     */
    private CellRepository(){
        cellMap = new HashMap<>();
        initialize();
    }

    /**
     * get the reference/instance of the CellRepository
     * HOW TO USE:
     *      CellRepository repo = CellRepository.getInstance();
     *      repo.getReferenceCellComponent(row, col);
     *  OR
     *      CellRepository.getInstance().getReferencedCellComponent(row, col);
     *
     * @return the CellRepository instance
     */
    public static synchronized CellRepository getInstance() {
        if(instance == null){
            instance = new CellRepository();
        }
        return instance;
    }

    /**
     * Reset the instance (clear all data) -- likely will not use outside of testing
     */
    public static void resetInstance() {
        instance = null;
    }

    /**
     * Initialize the Hashmap with empty Cells
     */
    private static void initialize(){
        for(int row=0; row<ROWS; row++){
            for(int col=0; col<COLUMNS; col++){
                CellCoord coord = new CellCoord(row, col);
                Cell cell = new Cell(new CellValue(null));
                cellMap.put(coord, cell);
            }
        }
    }

    /**
     * get the referenced Cell within the hashmap based on its CellCoord
     * @param row the numerical row
     * @param col the numerical column (0=a, 1=b, etc.)
     * @return the referenced Cell
     */
    public Cell getReferenceCell(int row, int col){
        return cellMap.get(new CellCoord(row, col));
    }

    /**
     * Find the CellCoord of a given CellComponent
     * @param target the target CellComponent
     * @return the CellCoord of the target, or null if not found
     */
    public CellCoord findCellCoord(Cell target) {
        try {
            for (Map.Entry<CellCoord, Cell> e : cellMap.entrySet()) {
                if (e.getValue() == target) return e.getKey();
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Add a new CellComponent to the repository at the specified row and column.
     *
     * @param row the numerical row
     * @param col the numerical column (0=a, 1=b, etc.)
     * @param newCell the CellComponent to add
     * @return the added CellComponent
     */
    public Cell addCellComponent(int row, int col, Cell newCell){
        try {
            cellMap.put(new CellCoord(row, col), newCell);
            return newCell;
        } catch (Exception ex) {
            throw new RuntimeException("Unable to add the cell to the CellRepository", ex);
        }
    }
}
