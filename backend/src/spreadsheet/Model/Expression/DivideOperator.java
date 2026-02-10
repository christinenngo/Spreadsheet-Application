package spreadsheet.Model.Expression;

import spreadsheet.Model.Cell.CellValue;

public class DivideOperator extends AbstractExpression {
    @Override
    public CellValue evaluate() {
        double quotient = 0.0;
        for (Expression operand : operands) {
            quotient /= operand.evaluate().asDouble();
        }
        return new CellValue(quotient);
    }
}
