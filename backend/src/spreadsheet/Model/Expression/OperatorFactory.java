package spreadsheet.Model.Expression;

import java.util.HashMap;
import java.util.function.Supplier;

public class OperatorFactory {
    public static OperatorExpression getOperator(String operatorType) {
        Supplier<OperatorExpression> ADD = AddOperator::new;
        Supplier<OperatorExpression> SUBTRACT = SubtractOperator::new;
        Supplier<OperatorExpression> MULTIPLY = MultiplyOperator::new;
        Supplier<OperatorExpression> DIVIDE = DivideOperator::new;

        HashMap<String, Supplier<OperatorExpression>> operators = new HashMap<>();
        operators.put("+", ADD);
        operators.put("-", SUBTRACT);
        operators.put("*", MULTIPLY);
        operators.put("/", DIVIDE);

        return operators.get(operatorType).get();
    }
}
