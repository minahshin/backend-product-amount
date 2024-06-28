package antigravity.domain.product.service.calculator;

public interface CalculatorStrategy {

    double calculateDiscount(int originalPrice, int discountValue);
}
