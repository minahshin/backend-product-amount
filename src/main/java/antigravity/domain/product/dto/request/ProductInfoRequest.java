package antigravity.domain.product.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@Getter
public class ProductInfoRequest {

    @NotNull(message = "Product ID should have one numeric value.")
    @Min(value = 1, message = "Product ID must be bigger than 0.")
    private Long productId;

    private List<Long> couponIds;
}
