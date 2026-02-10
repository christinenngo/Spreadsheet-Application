package spreadsheet.Model.Expression;

import spreadsheet.Model.Cell.CellValue;

public class Subtract extends AbstractExpression {
    @Override
    public CellValue evaluate() {
        double difference = 0.0;
        for (Expression operand : operands) {
            difference -= operand.evaluate().asDouble();
        }
        return new CellValue(difference);
    }

}
