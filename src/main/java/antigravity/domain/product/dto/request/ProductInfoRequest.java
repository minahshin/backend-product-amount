package antigravity.domain.product.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProductInfoRequest {
    private int productId;
    private List<Long> couponIds;
}
