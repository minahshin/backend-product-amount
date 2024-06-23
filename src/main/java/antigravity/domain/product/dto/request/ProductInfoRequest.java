package antigravity.domain.product.dto.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
public class ProductInfoRequest {

    @NotNull
    private Long productId;

    private List<Long> couponIds;
}
