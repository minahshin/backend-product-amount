package antigravity.domain.product.repository;

import antigravity.domain.product.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@DataJpaTest
@Import(TestJpaConfig.class)
public class RepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private PromotionProductRepository promotionProductRepository;

    @BeforeEach
    void init(){
        productRepository.save(new Product(1L, "Mock Item", 10000));
        promotionRepository.saveAllAndFlush(generatePromotionList());
        promotionProductRepository.saveAllAndFlush(generatePromotionProducts());
    }

    @Test
    @DisplayName("존재하는 product를 가져오는 test")
    public void findByExistProductTest() {

        // when
        Optional<Product> existEntity = productRepository.findById(1L);

        // then
        assertThat(existEntity).isPresent();
        assertThat(existEntity.get().getId()).isEqualTo(1L);
        assertThat(existEntity.get().getName()).isEqualTo("Mock Item");
        assertThat(existEntity.get().getPrice()).isEqualTo(10000);
    }

    @Test
    @DisplayName("존재하지 않는 product를 가져오는 test")
    public void findByNotExistProductTest() {
        // when
        Optional<Product> existEntity = productRepository.findById(100L);

        // then
        assertThat(existEntity).isNotPresent();
    }

    @Test
    @DisplayName("전달받은 id와 현재 날짜에 사용 가능한 promotion을 가져오는 test")
    public void findValidPromotionById() {

        // when
        List<Promotion> validCoupons = promotionRepository.getValidPromotionsFromId(List.of(1L, 2L, 3L),
                LocalDate.of(2024, 6, 23));

        // then
        assertThat(validCoupons.size()).isEqualTo(2);
        assertThat(validCoupons.get(0).getName()).isEqualTo("24% 할인 쿠폰");
        assertThat(validCoupons.get(1).getDiscountValue()).isEqualTo(2000);
    }

    @Test
    @DisplayName("날짜가 맞지 않아 사용 가능한 쿠폰이 없는 경우 test")
    public void findNonPromotionDueToExpired() {
        // when
        List<Promotion> validCoupons = promotionRepository.getValidPromotionsFromId(List.of(1L, 2L, 3L),
                LocalDate.of(2026, 6, 23));

        // then
        assertThat(validCoupons.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("주어진 product Id와 promotion Id를 모두 충족하는 Promotion Id를 반환하는 test")
    public void findProperPromotions() {
        // when
        List<PromotionId> properPromotionIdList = promotionProductRepository.findByProductIdAndPromotionIdIn(1L, List.of(1L, 2L));

        // then
        assertThat(properPromotionIdList.size()).isEqualTo(1);
        assertThat(properPromotionIdList.get(0).getPromotionId()).isEqualTo(1L);
    }

    // TODO : Consider moving these generation methods due to testing service layer
    public static List<Promotion> generatePromotionList() {
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

        Promotion expiredPromotion = Promotion.builder()
                .id(3L)
                .promotionType(PromotionType.CODE)
                .name("2000원 할인 쿠폰")
                .discountType(DiscountType.WON)
                .discountValue(2000)
                .useStartedAt(LocalDate.of(2023, 3, 1))
                .useEndedAt(LocalDate.of(2024, 3, 1))
                .build();

        return List.of(percentCoupon, wonCode, expiredPromotion);
    }

    public static List<PromotionProducts> generatePromotionProducts() {
        return List.of(new PromotionProducts(1L, 1L, 1L),
                new PromotionProducts(2L, 1L, 2L),
                new PromotionProducts(3L, 2L, 2L));
    }
}
