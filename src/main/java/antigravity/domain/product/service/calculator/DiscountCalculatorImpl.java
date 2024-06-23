package antigravity.domain.product.service.calculator;

import antigravity.domain.product.entity.Promotion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Slf4j
@Component
public class DiscountCalculatorImpl implements DiscountCalculator{

    public int calculateFinalPrice(int originalPrice, @NotEmpty List<Promotion> appliedPromotions) {
        double totalDiscounted = 0;

        for(Promotion promotion : appliedPromotions) {
            switch (promotion.getDiscountType()) {
                case PERCENT:
                    totalDiscounted += originalPrice * ((double) promotion.getDiscountValue() / 100);
                    break;
                case WON:
                    totalDiscounted += promotion.getDiscountValue();
                    break;
                default:
                    log.error("Promotion ID : {}, {} is invalid discount type.", promotion.getId(), promotion.getDiscountType());
            }
        }

        return Math.max(MIN_PRICE, truncateToThousands(originalPrice - totalDiscounted));
    }

    private int truncateToThousands(double price) {
        return (int) (price / 1000) * 1000;
    }
}
