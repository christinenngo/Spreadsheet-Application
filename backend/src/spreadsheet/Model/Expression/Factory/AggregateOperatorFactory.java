package spreadsheet.Model.Expression.Factory;

import spreadsheet.Model.Expression.AggregateOperatorExpression;
import spreadsheet.Model.Expression.AggregateOperators.AveOperator;
import spreadsheet.Model.Expression.AggregateOperators.CountAOperator;
import spreadsheet.Model.Expression.AggregateOperators.CountOperator;
import spreadsheet.Model.Expression.AggregateOperators.SumOperator;
import spreadsheet.Model.Expression.ArithmeticOperatorExpression;

import java.util.HashMap;
import java.util.function.Supplier;

public class AggregateOperatorFactory extends AbstractFactory {
    private static final HashMap<String, Supplier<AggregateOperatorExpression>> operators = new HashMap<>();
    private static final Supplier<AggregateOperatorExpression> SUM = SumOperator::new;
    private static final Supplier<AggregateOperatorExpression> COUNT = CountOperator::new;
    private static final Supplier<AggregateOperatorExpression> COUNTA = CountAOperator::new;
    private static final Supplier<AggregateOperatorExpression> AVE = AveOperator::new;

    static {
        operators.put("SUM", SUM);
        operators.put("COUNT", COUNT);
        operators.put("COUNTA", COUNTA);
        operators.put("AVE", AVE);
    }

    @Override
    public ArithmeticOperatorExpression getArithmeticOperator(String aggregateType) {
        return null;
    }

    @Override
    public AggregateOperatorExpression getAggregateOperator(String aggregateType) {
        return operators.get(aggregateType).get();
    }

    public static String getOperatorString(AggregateOperatorExpression operator) {
        for(String key : operators.keySet()) {
            if(operators.get(key).get().getClass() == operator.getClass()) {
                return key;
            }
        }
        return null;
    }
}
