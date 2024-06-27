package antigravity.domain.product.dto.request;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
public class ProductInfoRequest {

    @NotNull(message = "Product ID should have one numeric value.")
    @Min(value = 1, message = "Product ID must be bigger than 0.")
    private final Long productId;

    private final List<Long> couponIds;

    @Builder
    public ProductInfoRequest(Long productId, List<Long> couponIds) {
        this.productId = productId;
        this.couponIds = couponIds;
    }
}
