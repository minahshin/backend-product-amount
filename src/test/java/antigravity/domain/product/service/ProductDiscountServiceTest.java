package antigravity.domain.product.service;

import antigravity.domain.product.MockItemGenerator;
import antigravity.domain.product.dto.request.ProductInfoRequest;
import antigravity.domain.product.dto.response.ProductAmountResponse;
import antigravity.domain.product.entity.Promotion;
import antigravity.domain.product.entity.PromotionId;
import antigravity.domain.product.repository.ProductRepository;
import antigravity.domain.product.repository.PromotionProductRepository;
import antigravity.domain.product.repository.PromotionRepository;
import antigravity.domain.product.service.calculator.DiscountCalculator;
import antigravity.global.exception.AntigravityException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductDiscountServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private PromotionRepository promotionRepository;

    @Mock
    private PromotionProductRepository promoProdRepository;

    @Mock
    private DiscountCalculator calculator;

    @Mock
    private Clock clock;

    @InjectMocks
    private ProductDiscountServiceImpl productDiscountService;

    @Test
    @DisplayName("프로모션 적용 가격 계산 test")
    public void applyPromotionTest() {
        // given
        when(productRepository.findById(1L))
                .thenReturn(Optional.of(MockItemGenerator.generateMockProduct(200000)));

        PromotionId mockPromotionId1 = mock(PromotionId.class);
        PromotionId mockPromotionId2 = mock(PromotionId.class);

        when(mockPromotionId1.getPromotionId()).thenReturn(1L);
        when(mockPromotionId2.getPromotionId()).thenReturn(2L);

        when(promoProdRepository.findByProductIdAndPromotionIdIn(1L, List.of(1L, 2L)))
                .thenReturn(List.of(mockPromotionId1, mockPromotionId2));

        // mocking localdate
        when(clock.instant())
                .thenReturn(Instant.parse("2024-06-23T00:00:00Z"));
        when(clock.getZone())
                .thenReturn(ZoneId.of("Asia/Seoul"));

        List<Promotion> mockPromotionList = MockItemGenerator.generatePromotions();
        when(promotionRepository.getValidPromotionsFromId(List.of(1L, 2L), LocalDate.now(clock)))
                .thenReturn(mockPromotionList);

        when(calculator.calculateFinalPrice(200000, mockPromotionList))
                .thenReturn(150000);

        // when
        ProductAmountResponse applied = productDiscountService.applyPromotionsToProduct(dummyRequest());

        // then
        assertThat(applied.getName()).isEqualTo("Mock Item");
        assertThat(applied.getOriginPrice()).isEqualTo(200000);
        assertThat(applied.getDiscountPrice()).isEqualTo(50000);
        assertThat(applied.getFinalPrice()).isEqualTo(150000);
    }

    @Test
    @DisplayName("Product id를 주지 않은 경우 test")
    public void productIdIsNullTest() {
        // given
        ProductInfoRequest invalidRequest = ProductInfoRequest.builder()
                        .productId(null)
                        .couponIds(List.of(1L, 2L))
                        .build();

        // when, then
        assertThatThrownBy(() -> productDiscountService.applyPromotionsToProduct(invalidRequest))
                .isInstanceOf(AntigravityException.class)
                .hasMessage("The product identified by 'null' is not present.");
    }

    @Test
    @DisplayName("Request에 Coupon이 없는 경우 test")
    public void emptyCouponsTest() {
        // given
        when(productRepository.findById(1L))
                .thenReturn(Optional.of(MockItemGenerator.generateMockProduct(200000)));

        // when
        ProductAmountResponse notApplied = productDiscountService.applyPromotionsToProduct(ProductInfoRequest.builder()
                .productId(1L)
                .couponIds(new ArrayList<>())
                .build());

        // then
        assertThat(notApplied.getName()).isEqualTo("Mock Item");
        assertThat(notApplied.getOriginPrice()).isEqualTo(200000);
        assertThat(notApplied.getDiscountPrice()).isEqualTo(0);
        assertThat(notApplied.getFinalPrice()).isEqualTo(200000);
    }

    @Test
    @DisplayName("Product가 존재하지 않는 경우 test")
    public void noProductTest() {
        // given
        when(productRepository.findById(1L))
                .thenReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> productDiscountService.applyPromotionsToProduct(dummyRequest()))
                .isInstanceOf(AntigravityException.class)
                .hasMessage("The product identified by '1' is not present.");
    }

    @Test
    @DisplayName("Product가 10000원 이하인 경우 test")
    public void under10000WonTest() {
        // given
        when(productRepository.findById(1L))
                .thenReturn(Optional.of(MockItemGenerator.generateMockProduct(10000)));

        // when
        ProductAmountResponse notApplied = productDiscountService.applyPromotionsToProduct(dummyRequest());

        // then
        assertThat(notApplied.getName()).isEqualTo("Mock Item");
        assertThat(notApplied.getOriginPrice()).isEqualTo(10000);
        assertThat(notApplied.getDiscountPrice()).isEqualTo(0);
        assertThat(notApplied.getFinalPrice()).isEqualTo(10000);
    }

    @Test
    @DisplayName("사용 가능한 프로모션이 없는 경우 test")
    public void noAvailablePromotionsTest() {
        // given
        when(productRepository.findById(1L))
                .thenReturn(Optional.of(MockItemGenerator.generateMockProduct(200000)));

        PromotionId mockPromotionId1 = mock(PromotionId.class);
        PromotionId mockPromotionId2 = mock(PromotionId.class);

        when(mockPromotionId1.getPromotionId()).thenReturn(1L);
        when(mockPromotionId2.getPromotionId()).thenReturn(2L);

        when(promoProdRepository.findByProductIdAndPromotionIdIn(1L, List.of(1L, 2L)))
                .thenReturn(List.of(mockPromotionId1, mockPromotionId2));

        // mocking localdate
        when(clock.instant())
                .thenReturn(Instant.parse("2027-06-23T00:00:00Z"));
        when(clock.getZone())
                .thenReturn(ZoneId.of("Asia/Seoul"));

        when(promotionRepository.getValidPromotionsFromId(List.of(1L, 2L), LocalDate.now(clock)))
                .thenReturn(new ArrayList<>());

        // when
        ProductAmountResponse applied = productDiscountService.applyPromotionsToProduct(dummyRequest());

        // then
        assertThat(applied.getName()).isEqualTo("Mock Item");
        assertThat(applied.getOriginPrice()).isEqualTo(200000);
        assertThat(applied.getDiscountPrice()).isEqualTo(0);
        assertThat(applied.getFinalPrice()).isEqualTo(200000);
    }

    public static ProductInfoRequest dummyRequest() {
        return ProductInfoRequest.builder()
                .productId(1L)
                .couponIds(List.of(1L, 2L))
                .build();
    }
}