package antigravity.domain.product.service.calculator;

import antigravity.domain.product.entity.DiscountType;
import antigravity.domain.product.entity.Promotion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CalculatorTest {

    @Test
    @DisplayName("PERCENT 할인 테스트")
    public void percentDiscountTest() {
        // given
        int originalPrice = 300000;
        Promotion promotion = Promotion.builder()
                .discountType(DiscountType.PERCENT)
                .discountValue(24)
                .build();

        // when
        double discountedPrice = new Calculator(promotion.getDiscountType().getCalculator())
                .doCalculate(originalPrice, promotion.getDiscountValue());

        // then
        assertThat(discountedPrice).isEqualTo(72000);
    }

    @Test
    @DisplayName("PERCENT 할인 테스트")
    public void wonDiscountTest() {
        // given
        int originalPrice = 120000;
        Promotion promotion = Promotion.builder()
                .discountType(DiscountType.WON)
                .discountValue(20000)
                .build();

        // when
        double discountedPrice = new Calculator(promotion.getDiscountType().getCalculator())
                .doCalculate(originalPrice, promotion.getDiscountValue());

        // then
        assertThat(discountedPrice).isEqualTo(20000);
    }
}
