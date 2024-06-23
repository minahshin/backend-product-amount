package antigravity.domain.product.repository;

import antigravity.domain.product.entity.Promotion;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

import static antigravity.domain.product.entity.QPromotion.promotion;

@RequiredArgsConstructor
@Component
public class CustomPromotionRepositoryImpl implements CustomPromotionRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Promotion> getValidPromotionsFromId(List<Long> promotionIdList, LocalDate currentDate) {
        return queryFactory
                .selectFrom(promotion)
                .where(
                        promotion.id.in(promotionIdList),
                        promotion.useStartedAt.loe(currentDate),
                        promotion.useEndedAt.goe(currentDate)
                )
                .fetch();
    }
}
