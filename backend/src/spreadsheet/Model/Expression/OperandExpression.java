package spreadsheet.Model.Expression;

import spreadsheet.Model.Cell.CellValue;

import java.util.ArrayList;

public class OperandExpression extends AbstractExpression {
    private final CellValue value;

    public OperandExpression(double val) {
        this.value = new CellValue(val);
    }

    public CellValue evaluate() {
        return value;
    }

}
