package antigravity.domain.product.controller;

import antigravity.domain.product.dto.request.ProductInfoRequest;
import antigravity.domain.product.dto.response.ProductAmountResponse;
import antigravity.domain.product.service.ProductDiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductDiscountController {

    private final ProductDiscountService discountService;

    //상품 가격 추출 api
    @GetMapping("/amount")
    public ResponseEntity<ProductAmountResponse> getProductAmount(@Valid @ModelAttribute ProductInfoRequest request) {
        ProductAmountResponse response = discountService.applyPromotionsToProduct(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
