package antigravity.domain.product.service.calculator;

public class WonCalculatorStrategy implements CalculatorStrategy {

    public double calculateDiscount(int originalPrice, int discountValue) {
        return discountValue;
    }

}
