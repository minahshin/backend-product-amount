package antigravity.domain.product.service.calculator;

import antigravity.domain.product.entity.Promotion;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public interface DiscountCalculator {
    int MIN_PRICE = 10000;

    int calculateFinalPrice(int originalPrice, @NotEmpty List<Promotion> appliedPromotions);
}
