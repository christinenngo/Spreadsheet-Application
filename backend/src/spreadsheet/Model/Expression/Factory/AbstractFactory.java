package spreadsheet.Model.Expression.Factory;

import spreadsheet.Model.Expression.AggregateOperatorExpression;
import spreadsheet.Model.Expression.ArithmeticOperatorExpression;
import spreadsheet.Model.Expression.UnaryOperatorExpression;

public abstract class AbstractFactory {
    public abstract ArithmeticOperatorExpression getArithmeticOperator(String arithmeticType);
    public abstract AggregateOperatorExpression getAggregateOperator(String aggregateType);
    public abstract UnaryOperatorExpression getUnaryOperator(String aggregateType);
}
