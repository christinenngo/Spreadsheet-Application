package spreadsheet.Model.Expression;

import java.util.ArrayList;
import java.util.List;

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
}
