package antigravity.domain.product.service;

import antigravity.domain.product.dto.request.ProductInfoRequest;
import antigravity.domain.product.dto.response.ProductAmountResponse;
import antigravity.domain.product.entity.Product;
import antigravity.domain.product.entity.Promotion;
import antigravity.domain.product.entity.PromotionId;
import antigravity.domain.product.repository.ProductRepository;
import antigravity.domain.product.repository.PromotionProductRepository;
import antigravity.domain.product.repository.PromotionRepository;
import antigravity.domain.product.service.calculator.DiscountCalculator;
import antigravity.global.config.TimeConfig;
import antigravity.global.exception.AntigravityException;
import antigravity.global.exception.code.ProductErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final DiscountCalculator calculator;
    private final TimeConfig timeConfig;

    @Transactional(readOnly = true)
    @Override
    public ProductAmountResponse applyPromotionsToProduct(ProductInfoRequest request) {

        log.info("[Product Discount] Requested applying promotion to product id : {}", request.getProductId());

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new AntigravityException(ProductErrorCode.NOT_EXIST_PRODUCT_ID,
                        Map.of("id", String.valueOf(request.getProductId()))));

        if(request.getCouponIds() == null || request.getCouponIds().isEmpty() || product.getPrice() <= 10000) {
            log.debug("[Product Discount] Coupon isn't chosen or price is under 10,000 WON.");
            return new ProductAmountResponse(product);
        }

        List<Long> candidatePromoList = promoProdRepository.findByProductIdAndPromotionIdIn(product.getId(), request.getCouponIds()).stream()
                .map(PromotionId::getPromotionId)
                .collect(Collectors.toList());

        List<Promotion> promotionList = promotionRepository.getValidPromotionsFromId(candidatePromoList, LocalDate.now(timeConfig.clock()));

        log.info("[Product Discount] Get {} promotions from request", promotionList.size());

        if(promotionList.isEmpty()) {
            log.debug("[Product Discount] Cannot apply promotions.");
            return new ProductAmountResponse(product);
        }

        return ProductAmountResponse.builder()
                .product(product)
                .finalPrice(calculator.calculateFinalPrice(product.getPrice(), promotionList))
                .build();
    }
}
