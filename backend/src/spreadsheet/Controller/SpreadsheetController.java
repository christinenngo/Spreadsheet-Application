/*
 * Copyright (c) 2026 Sakire Arslan Ay
 *
 * This file was developed for educational purposes as part of CS4233:
 * Object-Oriented Analysis & Design at Worcester Polytechnic Institute.
 *
 * All rights reserved. Redistribution and modification outside the scope
 * of this course are not permitted without prior written permission.
 */
package spreadsheet.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spreadsheet.Model.Cell.*;
import spreadsheet.Model.CellCoord;
import spreadsheet.Model.CellRepository;
import spreadsheet.Model.Expression.*;
import spreadsheet.Model.Expression.Factory.AggregateOperatorFactory;
import spreadsheet.Model.Expression.Factory.ArithmeticOperatorFactory;
import spreadsheet.Model.Expression.Factory.UnaryOperatorFactory;
import spreadsheet.Model.Parser.ExpressionParser;

import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class SpreadsheetController {

    private static final int ROWS = CellRepository.ROWS;
    private static final int COLS = CellRepository.COLUMNS;

    /* ---------------------------------------
       GET entire spreadsheet in row-major grid
       Returns: List<Map<String,Object>> (JSON array)
       --------------------------------------- */
    @GetMapping("/spreadsheet")
    public List<Map<String, Object>> getSpreadsheetDataGrid() {
        List<Map<String, Object>> rows = new ArrayList<>(ROWS);
        try {
            for (int r = 0; r < ROWS; r++) {
                Map<String, Object> rowJson = buildEmptyRow(r);
                for (int c = 0; c < COLS; c++) {
                    // Get each cell component from CellRepository and build its JSON representation
                    CellComponent comp = CellRepository.getInstance().getReferenceCell(r, c);
                    Map<String, Object> cellJson = buildCellJson(comp);
                    rowJson.put(toColumnName(c), cellJson);
                }
                rows.add(rowJson);
            }
        } catch (Exception ex) {
            throw new RuntimeException("Unable to build spreadsheet grid", ex);
        }
        return rows;
        // See SampleGETResponse.md file for example output
    }

    /* ---------------------------------------
       POST request to update single cell
       Update cell at {coord} with provided value and/or expression
       Body: { "value": number } OR { "expression": "=A1+B1" } (leading '=' optional)
       Returns the single-row object containing the updated cell
       --------------------------------------- */
    @PostMapping("/cell/{coord}")
    public ResponseEntity<?> postCellGrid(@PathVariable String coord, @RequestBody Map<String, Object> body) {
        CellCoord cc;
        try {
            cc = parseCoord(coord);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid coordinate: " + coord));
        }

        CellComponent comp = getCellIfExists(cc);

        // Update value if provided
        if (body.containsKey("value")) {
            Object v = body.get("value");
            try {
                comp.setCellValue(parseCellValue(v));
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(Map.of("error", "'value' must be numeric"));
            }
        }
        // Update expression if provided
        if (body.containsKey("expression")) {
            Object exprObj = body.get("expression");
            if (exprObj == null) {
                comp.setExpression(null);
            } else {
                String exprStr = String.valueOf(exprObj);
                try {
                    Expression expr = ExpressionParser.convertExpression(exprStr);
                    comp.setExpression(expr);
                } catch (Exception ex) {
                    return ResponseEntity.badRequest().body(Map.of("error", "Invalid expression: " + ex.getMessage()));
                }
            }
        }
        Map<String, Object> rowJson = buildEmptyRow(cc.getRow());
        rowJson.put(toColumnName(cc.getCol()), buildCellJson(comp));
        return ResponseEntity.ok(rowJson);
    }

    /* ---------------------------------------
       Helper methods
     --------------------------------------- */

    /* ---------------------------------------
     *  Parse cell value from Object
     *  Supports Number (Double, Integer, etc.) and numeric String
     *  Returns CellValue
       --------------------------------------- */
    private CellValue parseCellValue(Object v) {
        if (v == null) return new CellValue(null);

        if (v instanceof Number) {
            return new CellValue(((Number) v).doubleValue());
        }

        if (v instanceof CharSequence) {
            String s = v.toString().trim();
            if (s.isEmpty()) return new CellValue(null);
            return new CellValue(Double.parseDouble(s));
        }

        return new CellValue(null);
    }

    /* ---------------------------------------
     * Build JSON representation of empty row
     * Returns Map<String,Object> with "id" and empty cells for each column
   --------------------------------------- */
    private Map<String, Object> buildEmptyRow(int rowIndex) {
        // pre-size map: one "id" + COLS cells
        Map<String, Object> row = new LinkedHashMap<>(COLS + 1);
        row.put("id", rowIndex + 1);

        for (int c = 0; c < COLS; c++) {
            Map<String, Object> cell = new LinkedHashMap<>(3);
            cell.put("value", "");
            cell.put("expression", "");
            row.put(toColumnName(c), cell);
        }
        return row;
    }

    /* ---------------------------------------
    * Build JSON representation of a cell
    * Returns Map<String,Object> with keys "value" and "expression"
   --------------------------------------- */
    private Map<String, Object> buildCellJson(CellComponent comp) {
        Map<String, Object> cell = new LinkedHashMap<>();

        CellValue val = Optional.ofNullable(comp)
                .map(CellComponent::getCellValue)
                .orElse(new CellValue(null));
        String valueStr = val.toString();

        Expression expr = Optional.ofNullable(comp)
                .map(CellComponent::getExpression)
                .orElse(null);
        String exprStr = (expr != null) ? "=" + expressionToString(expr) : valueStr;

        cell.put("value", valueStr);
        cell.put("expression", exprStr);
        return cell;
        // EXAMPLE OUTPUTS:
        // {value= "", expression= ""}
        // {value= "11.0", expression= ""}
        // {value="10.0", expression= "=C3"}
        // {value="80.5", expression= "=SUM(A1:D2,A3,B3:D3)/COUNTA(A1:D3)"}
    }

    /* ---------------------------------------
     * Convert Expression to String representation
     * Should handle constants, cell references, and operators
     * This method is not complete. You will revise and update this method in milestone 1, 2, and 3 , as needed.
     * --------------------------------------- */
    private String expressionToString(Expression expr) {
        if (expr == null) return null;

        // TODO: Implement proper conversion based on Expression type
        // EXAMPLE OUTPUTS:
        // For constant value 10: "10"
        // For cell reference A1: "A1"
        // For addition of A1 and B1: "A1 + B1"
        // For SUM(A1:D2,A3,B3:D3)/COUNT(A1:D3)-AVE(A1:D2,A3,B3:D3)  : "SUM(A1:D2,A3,B3:D3)/COUNT(A1:D3)-AVE(A1:D2,A3,B3:D3)"

        if(expr instanceof OperandExpression operandExpression) {
            return operandExpression.evaluate().toString();
        }

        if(expr instanceof CellReferenceExpression cellReferenceExpression) {
            CellComponent component = cellReferenceExpression.getCellComponent();
            if(component instanceof CellGroup cellGroup) {
                ArrayList<CellComponent> cells = cellGroup.getCellComponents();
                CellCoord leftCoord = CellRepository.getInstance().findCellCoord(cells.get(0));
                CellCoord rightCoord = CellRepository.getInstance().findCellCoord(cells.get(1));

                return convertCoord(leftCoord.getRow(), leftCoord.getCol()) + ":" + convertCoord(rightCoord.getRow(), rightCoord.getCol());
            } else {
                CellCoord coord = CellRepository.getInstance().findCellCoord(cellReferenceExpression.getCellComponent());
                return convertCoord(coord.getRow(), coord.getCol());
            }
        }

        if(expr instanceof ArithmeticOperatorExpression arithmeticExpression) {
            String operator = ArithmeticOperatorFactory.getOperatorString(arithmeticExpression);

            if("+-*/".contains(operator)) {
                return "(" + expressionToString(arithmeticExpression.getOperands().get(0)) + " " + operator + " " + expressionToString(arithmeticExpression.getOperands().get(1)) + ")";
            }
        }

        if(expr instanceof AggregateOperatorExpression aggregateExpression) {
            String operator = AggregateOperatorFactory.getOperatorString(aggregateExpression);
            int numOperands = aggregateExpression.getOperands().size();

            StringBuilder sb = new StringBuilder();
            sb.append(operator).append("(");
            for(int i = 0; i < numOperands; i++) {
                sb.append(expressionToString(aggregateExpression.getOperands().get(i)));
                if(i < numOperands - 1) {
                    sb.append(",");
                }
            }
            sb.append(")");
            return sb.toString();
        }

        if(expr instanceof UnaryOperatorExpression unaryExpression) {
            String operator = UnaryOperatorFactory.getOperatorString(unaryExpression);

            if(operator.equals("++") || operator.equals("--")) {
                return " " + operator +
                        expressionToString(unaryExpression.getOperands().get(0));
            } else {
                return operator + "(" +
                        expressionToString(unaryExpression.getOperands().get(0)) +
                        ")";
            }
        }

        return expr.toString();
    }

    /* ---------------------------------------
       Returns the CellComponent at given coordinates;
       if exists, returns it
       If not, create new Cell with null value and add to CellRepository
       Returns the CellComponent at the given coordinates
       --------------------------------------- */
    private CellComponent getCellIfExists(CellCoord coord) {
        CellComponent existing = CellRepository.getInstance().getReferenceCell(coord.getRow(), coord.getCol());
        if (existing != null)
            return existing;
        else
            return CellRepository.getInstance().addCellComponent(coord.getRow(), coord.getCol(), new Cell(new CellValue(null)));
    }

    /* ---------------------------------------
       Parse cell coordinate string (e.g., "A1") to CellCoord
       Returns CellCoord
       --------------------------------------- */
    private CellCoord parseCoord(String ref) {
        if (ref == null) throw new IllegalArgumentException("coord required");
        ref = ref.trim().toUpperCase(Locale.ROOT);
        int i = 0;
        while (i < ref.length() && Character.isLetter(ref.charAt(i))) i++;
        if (i == 0 || i >= ref.length()) throw new IllegalArgumentException("Invalid coord: " + ref);

        String colPart = ref.substring(0, i);
        String rowPart = ref.substring(i);

        int col = 0;
        for (char c : colPart.toCharArray()) {
            col = col * 26 + (c - 'A' + 1);
        }
        col -= 1;
        int row = Integer.parseInt(rowPart) - 1;

        return new CellCoord(row, col);
    }

    /* ---------------------------------------
       Convert row and column indices to cell coordinate string (e.g., 0,0 -> "A1")
       --------------------------------------- */
    private String convertCoord(int row, int col) {
        return toColumnName(col) + (row + 1);
    }

    /* ---------------------------------------
       Convert column index to column name (e.g., 0 -> "A", 25 -> "Z", 26 -> "AA")
       --------------------------------------- */
    private String toColumnName(int col) {
        StringBuilder sb = new StringBuilder();
        while (col >= 0) {
            sb.insert(0, (char) ('A' + (col % 26)));
            col = (col / 26) - 1;
        }
        return sb.toString();
    }
}