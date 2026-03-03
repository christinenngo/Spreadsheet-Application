package spreadsheet.Model.Expression;

import spreadsheet.Model.Cell.CellComponent;
import spreadsheet.Model.Cell.CellValue;

import java.util.HashSet;
import java.util.Set;

public abstract class ArithmeticOperatorExpression extends AbstractExpression {
    @Override
    public abstract CellValue evaluate();

    @Override
    public Set<CellComponent> getReferencedCells() {
        Set<CellComponent> referencedCells = new HashSet<>();
        for (Expression operand : operands) {
            referencedCells.addAll(operand.getReferencedCells());
        }
        return referencedCells;
    }
}
