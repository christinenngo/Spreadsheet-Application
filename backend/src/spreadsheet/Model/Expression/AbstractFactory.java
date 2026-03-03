package spreadsheet.Model.Expression;

public abstract class AbstractFactory {
    public abstract ArithmeticOperatorExpression getArithmeticOperator(String arithmeticType);
    public abstract AggregateOperatorExpression getAggregateOperator(String aggregateType);
}
