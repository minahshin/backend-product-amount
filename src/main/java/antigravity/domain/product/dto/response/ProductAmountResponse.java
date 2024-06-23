package antigravity.domain.product.dto.response;

import antigravity.domain.product.entity.Product;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductAmountResponse {

    private final String name; //상품명

    private final int originPrice; //상품 기존 가격
    private final int discountPrice; //총 할인 금액
    private final int finalPrice; //확정 상품 가격

    public ProductAmountResponse(Product product) {
        this.name = product.getName();
        this.originPrice = product.getPrice();
        this.finalPrice = product.getPrice();
        this.discountPrice = 0;
    }

    @Builder
    public ProductAmountResponse(Product product, int finalPrice) {
        this.name = product.getName();
        this.originPrice = product.getPrice();
        this.finalPrice = finalPrice;
        this.discountPrice = product.getPrice() - finalPrice;
    }
}
