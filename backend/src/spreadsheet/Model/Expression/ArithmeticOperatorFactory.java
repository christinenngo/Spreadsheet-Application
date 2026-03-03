package spreadsheet.Model.Expression;

import spreadsheet.Model.Expression.ArithmeticOperators.AddOperator;
import spreadsheet.Model.Expression.ArithmeticOperators.DivideOperator;
import spreadsheet.Model.Expression.ArithmeticOperators.MultiplyOperator;
import spreadsheet.Model.Expression.ArithmeticOperators.SubtractOperator;

import java.util.HashMap;
import java.util.function.Supplier;

public class ArithmeticOperatorFactory extends AbstractFactory {
    private static final HashMap<String, Supplier<ArithmeticOperatorExpression>> operators = new HashMap<>();
    private static final Supplier<ArithmeticOperatorExpression> ADD = AddOperator::new;
    private static final Supplier<ArithmeticOperatorExpression> SUBTRACT = SubtractOperator::new;
    private static final Supplier<ArithmeticOperatorExpression> MULTIPLY = MultiplyOperator::new;
    private static final Supplier<ArithmeticOperatorExpression> DIVIDE = DivideOperator::new;

    static {
        operators.put("+", ADD);
        operators.put("-", SUBTRACT);
        operators.put("*", MULTIPLY);
        operators.put("/", DIVIDE);
    }

    @Override
    public ArithmeticOperatorExpression getArithmeticOperator(String arithmeticType) {
        return operators.get(arithmeticType).get();
    }

    @Override
    public AggregateOperatorExpression getAggregateOperator(String arithmeticType) {
        return null;
    }

    public static String getOperatorString(ArithmeticOperatorExpression operator) {
        for(String key : operators.keySet()) {
            if(operators.get(key).get().getClass() == operator.getClass()) {
                return key;
            }
        }
        return null;
    }
}
