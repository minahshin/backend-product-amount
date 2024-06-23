package antigravity.domain.product.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@ToString
@Getter
public class PromotionProducts {

    @Id
    @Column(unique = true)
    private Long id;

    @Column(nullable = false)
    private Long promotionId;

    @Column(nullable = false)
    private Long productId;
}