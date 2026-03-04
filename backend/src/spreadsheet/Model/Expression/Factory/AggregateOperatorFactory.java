package spreadsheet.Model.Expression.Factory;

import spreadsheet.Model.Expression.AggregateOperatorExpression;
import spreadsheet.Model.Expression.AggregateOperators.*;
import spreadsheet.Model.Expression.ArithmeticOperatorExpression;
import spreadsheet.Model.Expression.UnaryOperatorExpression;

import java.util.HashMap;
import java.util.function.Supplier;

public class AggregateOperatorFactory extends AbstractFactory {
    private static final HashMap<String, Supplier<AggregateOperatorExpression>> operators = new HashMap<>();
    private static final Supplier<AggregateOperatorExpression> SUM = SumOperator::new;
    private static final Supplier<AggregateOperatorExpression> COUNT = CountOperator::new;
    private static final Supplier<AggregateOperatorExpression> COUNTA = CountAOperator::new;
    private static final Supplier<AggregateOperatorExpression> AVE = AveOperator::new;
    private static final Supplier<AggregateOperatorExpression> MIN = MinOperator::new;
    private static final Supplier<AggregateOperatorExpression> MAX = MaxOperator::new;
    private static final Supplier<AggregateOperatorExpression> MEDIAN = MedianOperator::new;

    static {
        operators.put("SUM", SUM);
        operators.put("COUNT", COUNT);
        operators.put("COUNTA", COUNTA);
        operators.put("AVE", AVE);
        operators.put("MIN", MIN);
        operators.put("MAX", MAX);
        operators.put("MEDIAN", MEDIAN);
    }

    @Override
    public ArithmeticOperatorExpression getArithmeticOperator(String aggregateType) {
        return null;
    }

    @Override
    public AggregateOperatorExpression getAggregateOperator(String aggregateType) {
        return operators.get(aggregateType).get();
    }

    @Override
    public UnaryOperatorExpression getUnaryOperator(String aggregateType) {
        return null;
    }

    public static String getOperatorString(AggregateOperatorExpression operator) {
        for (String key : operators.keySet()) {
            if (operators.get(key).get().getClass() == operator.getClass()) {
                return key;
            }
        }
        return null;
    }
}
