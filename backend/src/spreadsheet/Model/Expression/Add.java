package spreadsheet.Model.Expression;

import spreadsheet.Model.Cell.CellValue;

public class Add extends AbstractExpression {
    @Override
    public CellValue evaluate() {
        double sum = 0.0;
        for (Expression operand : operands) {
            sum += operand.evaluate().asDouble();
        }
        return new CellValue(sum);
    }
}
