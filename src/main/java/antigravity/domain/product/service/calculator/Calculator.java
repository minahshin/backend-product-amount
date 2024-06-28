package antigravity.domain.product.service.calculator;

public class Calculator {

    public static final int MIN_PRICE = 10000;
    public static final int TRUNCATE = 1000;

    private final CalculatorStrategy calculatorStrategy;

    public Calculator(CalculatorStrategy calculatorStrategy) {
        this.calculatorStrategy = calculatorStrategy;
    }

    public double doCalculate(int originalPrice, int discountValue) {
        return calculatorStrategy.calculateDiscount(originalPrice, discountValue);
    }
}
