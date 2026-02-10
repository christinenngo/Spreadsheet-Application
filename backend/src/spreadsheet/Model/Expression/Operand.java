package spreadsheet.Model.Expression;

import spreadsheet.Model.Cell.CellValue;

import java.util.ArrayList;

public class Operand implements Expression {
    private final CellValue value;

    public Operand(double val) {
        this.value = new CellValue(val);
    }

    public CellValue evaluate() {
        return value;
    }

    @Override
    public void addOperand(Expression... expression) {

    }

    @Override
    public ArrayList<Expression> getOperands() {
        return new ArrayList<>();
    }
}
