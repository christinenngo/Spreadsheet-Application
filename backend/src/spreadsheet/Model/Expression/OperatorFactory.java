package spreadsheet.Model.Expression;

import spreadsheet.Model.Expression.AggregateOperators.AveOperator;
import spreadsheet.Model.Expression.AggregateOperators.CountAOperator;
import spreadsheet.Model.Expression.AggregateOperators.CountOperator;
import spreadsheet.Model.Expression.AggregateOperators.SumOperator;
import spreadsheet.Model.Expression.ArithmeticOperators.AddOperator;
import spreadsheet.Model.Expression.ArithmeticOperators.DivideOperator;
import spreadsheet.Model.Expression.ArithmeticOperators.MultiplyOperator;
import spreadsheet.Model.Expression.ArithmeticOperators.SubtractOperator;

import java.util.HashMap;
import java.util.function.Supplier;

public class OperatorFactory {
    public static OperatorExpression getOperator(String operatorType) {
        Supplier<OperatorExpression> ADD = AddOperator::new;
        Supplier<OperatorExpression> SUBTRACT = SubtractOperator::new;
        Supplier<OperatorExpression> MULTIPLY = MultiplyOperator::new;
        Supplier<OperatorExpression> DIVIDE = DivideOperator::new;
        Supplier<OperatorExpression> SUM = SumOperator::new;
        Supplier<OperatorExpression> COUNT = CountOperator::new;
        Supplier<OperatorExpression> COUNTA = CountAOperator::new;
        Supplier<OperatorExpression> AVE = AveOperator::new;

        HashMap<String, Supplier<OperatorExpression>> operators = new HashMap<>();
        operators.put("+", ADD);
        operators.put("-", SUBTRACT);
        operators.put("*", MULTIPLY);
        operators.put("/", DIVIDE);
        operators.put("SUM", SUM);
        operators.put("COUNT", COUNT);
        operators.put("COUNTA", COUNTA);
        operators.put("AVE", AVE);

        return operators.get(operatorType).get();
    }
}
