package antigravity.domain.product.service;

import antigravity.domain.product.dto.request.ProductInfoRequest;
import antigravity.domain.product.dto.response.ProductAmountResponse;
import antigravity.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductDiscountServiceImpl implements ProductDiscountService{
    private final ProductRepository repository;

    public ProductAmountResponse calculateProductAmount(ProductInfoRequest request) {
        System.out.println("상품 가격 추출 로직을 완성 시켜주세요.");

//        Product product = repository.getProduct(request.getProductId());

        return null;
    }

}
