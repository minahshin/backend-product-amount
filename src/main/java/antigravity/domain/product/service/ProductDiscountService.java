package antigravity.domain.product.service;

import antigravity.domain.product.dto.request.ProductInfoRequest;
import antigravity.domain.product.dto.response.ProductAmountResponse;

public interface ProductDiscountService {
    ProductAmountResponse applyPromotionsToProduct(ProductInfoRequest request);
}
