package antigravity.domain.product.service.calculator;

public class PercentCalculatorStrategy implements CalculatorStrategy {

    public double calculateDiscount(int originalPrice, int discountValue) {
        return originalPrice * ((double) discountValue / 100);
    }

}
