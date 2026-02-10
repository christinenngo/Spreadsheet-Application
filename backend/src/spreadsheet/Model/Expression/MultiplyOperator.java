package spreadsheet.Model.Expression;

import spreadsheet.Model.Cell.CellValue;

public class MultiplyOperator extends AbstractExpression {
    @Override
    public CellValue evaluate() {
        double product = 0.0;
        for (Expression operand : operands) {
            product *= operand.evaluate().asDouble();
        }
        return new CellValue(product);
    }
}
