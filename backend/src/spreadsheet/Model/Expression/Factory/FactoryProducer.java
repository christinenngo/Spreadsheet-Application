package spreadsheet.Model.Expression.Factory;

import java.util.HashMap;
import java.util.function.Supplier;

public class FactoryProducer {
    private static final HashMap<String, Supplier<AbstractFactory>> operatorFactories = new HashMap<>();
    private static final Supplier<AbstractFactory> ARITHMETIC = ArithmeticOperatorFactory::new;
    private static final Supplier<AbstractFactory> AGGREGATE = AggregateOperatorFactory::new;

    static {
        operatorFactories.put("ARITHMETIC", ARITHMETIC);
        operatorFactories.put("AGGREGATE", AGGREGATE);
    }

    public static AbstractFactory getFactory(String factoryType) {
        return operatorFactories.get(factoryType).get();
    }
}
