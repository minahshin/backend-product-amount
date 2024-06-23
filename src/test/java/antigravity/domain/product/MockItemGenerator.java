package antigravity.domain.product;

import antigravity.domain.product.entity.*;

import java.time.LocalDate;
import java.util.List;

public class MockItemGenerator {

    public static List<Promotion> generatePromotions() {
        Promotion percentCoupon = Promotion.builder()
                .id(1L)
                .promotionType(PromotionType.COUPON)
                .name("24% 할인 쿠폰")
                .discountType(DiscountType.PERCENT)
                .discountValue(24)
                .useStartedAt(LocalDate.of(2023, 3, 1))
                .useEndedAt(LocalDate.of(2025, 3, 1))
                .build();

        Promotion wonCode = Promotion.builder()
                .id(2L)
                .promotionType(PromotionType.CODE)
                .name("2000원 할인 쿠폰")
                .discountType(DiscountType.WON)
                .discountValue(2000)
                .useStartedAt(LocalDate.of(2023, 3, 1))
                .useEndedAt(LocalDate.of(2025, 3, 1))
                .build();

        return List.of(percentCoupon, wonCode);
    }
}
