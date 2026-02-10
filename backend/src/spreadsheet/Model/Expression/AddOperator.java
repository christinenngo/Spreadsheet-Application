package spreadsheet.Model.Expression;

import spreadsheet.Model.Cell.CellValue;

public class AddOperator extends OperatorExpression {
    @Override
    public CellValue evaluate() {
        double sum = 0.0;
        for (Expression operand : operands) {
            sum += operand.evaluate().asDouble();
        }
        return new CellValue(sum);
    }
}
