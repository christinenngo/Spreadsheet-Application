package spreadsheet.Model.Expression;

import spreadsheet.Model.Cell.CellComponent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public abstract class AbstractExpression implements Expression {
    protected ArrayList<Expression> operands = new ArrayList<>();

    @Override
    public void addOperand(Expression... expression) {
        this.operands.addAll(List.of(expression));
    }

    @Override
    public ArrayList<Expression> getOperands() {
        return operands;
    }

    @Override
    public Set<CellComponent> getReferencedCells() {
        return Collections.emptySet();
    }
}
