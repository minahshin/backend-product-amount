package antigravity.domain.product.service;

import antigravity.domain.product.dto.request.ProductInfoRequest;
import antigravity.domain.product.dto.response.ProductAmountResponse;
import antigravity.domain.product.entity.Product;
import antigravity.domain.product.entity.Promotion;
import antigravity.domain.product.entity.PromotionId;
import antigravity.domain.product.repository.ProductRepository;
import antigravity.domain.product.repository.PromotionProductRepository;
import antigravity.domain.product.repository.PromotionRepository;
import antigravity.domain.product.service.calculator.Calculator;
import antigravity.global.exception.AntigravityException;
import antigravity.global.exception.code.ProductErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductDiscountServiceImpl implements ProductDiscountService{
    private final ProductRepository productRepository;
    private final PromotionRepository promotionRepository;
    private final PromotionProductRepository promoProdRepository;
    private final Clock clock;

    @Transactional(readOnly = true)
    @Override
    public ProductAmountResponse applyPromotionsToProduct(@NotNull ProductInfoRequest request) {

        log.info("[Product Discount] Requested applying promotion to product id : {}", request.getProductId());

        Product product = getProductFromId(request.getProductId());

        if(!isCouponUsable(product.getPrice(), request.getCouponIds())) {
            log.debug("[Product Discount] Coupon isn't chosen or price is under {} WON.", Calculator.MIN_PRICE);
            return new ProductAmountResponse(product);
        }

        List<Promotion> promotions = getValidPromotions(product.getId(), request.getCouponIds());

        if(promotions.isEmpty()) {
            log.debug("[Product Discount] Cannot apply promotions.");
            return new ProductAmountResponse(product);
        }

        return ProductAmountResponse.builder()
                .product(product)
                .finalPrice(applyAndTruncate(product.getPrice(), getTotalDiscount(product.getPrice(), promotions)))
                .build();
    }

    private Product getProductFromId(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new AntigravityException(ProductErrorCode.NOT_EXIST_PRODUCT_ID, Map.of("id", String.valueOf(productId))));
    }

    private boolean isCouponUsable(int price, List<Long> couponIds) {
        return (couponIds != null && !couponIds.isEmpty() && price > Calculator.MIN_PRICE);
    }

    private List<Promotion> getValidPromotions(Long productId, List<Long> couponIds) {
        List<Long> candidatePromoList = promoProdRepository.findByProductIdAndPromotionIdIn(productId, couponIds).stream()
                .map(PromotionId::getPromotionId)
                .collect(Collectors.toList());

        List<Promotion> promotionList = promotionRepository.getValidPromotionsFromId(candidatePromoList, LocalDate.now(clock));

        log.info("[Product Discount] Get {} promotions from request", promotionList.size());

        return promotionList;
    }

    private double getTotalDiscount(int originalPrice, List<Promotion> promotions) {
        return promotions.stream()
                .map(promotion -> new Calculator(promotion.getDiscountType().getCalculator())
                        .doCalculate(originalPrice, promotion.getDiscountValue()))
                .reduce(0D, Double::sum);
    }

    private int applyAndTruncate(int originalPrice, double totalDiscounted) {
        return Math.max(Calculator.MIN_PRICE, truncate(originalPrice - totalDiscounted));
    }

    private int truncate(double price) {
        return (int) (price / Calculator.TRUNCATE) * Calculator.TRUNCATE;
    }
}
