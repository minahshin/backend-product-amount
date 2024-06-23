package antigravity.domain.product;

import antigravity.domain.product.entity.DiscountType;
import antigravity.domain.product.entity.Product;
import antigravity.domain.product.entity.Promotion;
import antigravity.domain.product.entity.PromotionType;

import java.time.LocalDate;
import java.util.List;

public class MockItemGenerator {

    public static Product generateMockProduct(int price) {
        return new Product(1L, "Mock Item", price);
    }

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
