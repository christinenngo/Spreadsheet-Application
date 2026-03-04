package spreadsheet.Model.Expression.Factory;

import spreadsheet.Model.Expression.AggregateOperatorExpression;
import spreadsheet.Model.Expression.AggregateOperators.*;
import spreadsheet.Model.Expression.ArithmeticOperatorExpression;
import spreadsheet.Model.Expression.UnaryOperatorExpression;
import spreadsheet.Model.Expression.UnaryOperators.AbsOperator;
import spreadsheet.Model.Expression.UnaryOperators.DecrementOperator;
import spreadsheet.Model.Expression.UnaryOperators.IncrementOperator;
import spreadsheet.Model.Expression.UnaryOperators.NegateOperator;

import java.util.HashMap;
import java.util.function.Supplier;

public class UnaryOperatorFactory extends AbstractFactory {
    private static final HashMap<String, Supplier<UnaryOperatorExpression>> operators = new HashMap<>();
    private static final Supplier<UnaryOperatorExpression> INCREMENT = IncrementOperator::new;
    private static final Supplier<UnaryOperatorExpression> DECREMENT = DecrementOperator::new;
    private static final Supplier<UnaryOperatorExpression> ABS = AbsOperator::new;
    private static final Supplier<UnaryOperatorExpression> NEG = NegateOperator::new;

    static {
        operators.put("++", INCREMENT);
        operators.put("--", DECREMENT);
        operators.put("ABS", ABS);
        operators.put("NEG", NEG);
    }

    @Override
    public ArithmeticOperatorExpression getArithmeticOperator(String aggregateType) {
        return null;
    }

    @Override
    public AggregateOperatorExpression getAggregateOperator(String aggregateType) {
        return null;
    }

    @Override
    public UnaryOperatorExpression getUnaryOperator(String unaryType) {
        return operators.get(unaryType).get();
    }

    public static String getOperatorString(UnaryOperatorExpression operator) {
        for(String key : operators.keySet()) {
            if(operators.get(key).get().getClass() == operator.getClass()) {
                return key;
            }
        }
        return null;
    }
}
