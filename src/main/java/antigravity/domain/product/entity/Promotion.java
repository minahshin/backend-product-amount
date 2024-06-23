package antigravity.domain.product.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Getter
public class Promotion {

    @Id
    @Column(unique = true)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PromotionType promotionType; //쿠폰 타입 (쿠폰, 코드)

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DiscountType discountType; // WON : 금액 할인, PERCENT : %할인

    @Column(nullable = false)
    private Integer discountValue; // 할인 금액 or 할인 %

    @Column(nullable = false)
    private LocalDate useStartedAt; // 쿠폰 사용가능 시작 기간

    @Column(nullable = false)
    private LocalDate useEndedAt; // 쿠폰 사용가능 종료 기간

    @Builder
    public Promotion(Long id, PromotionType promotionType, String name, DiscountType discountType,
                     Integer discountValue, LocalDate useStartedAt, LocalDate useEndedAt) {
        this.id = id;
        this.promotionType = promotionType;
        this.name = name;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.useStartedAt = useStartedAt;
        this.useEndedAt = useEndedAt;
    }
}