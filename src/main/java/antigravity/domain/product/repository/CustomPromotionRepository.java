package antigravity.domain.product.repository;

import antigravity.domain.product.entity.Promotion;

import java.time.LocalDate;
import java.util.List;

public interface CustomPromotionRepository {
    List<Promotion> getValidPromotionsFromId(List<Long> promotionIdList, LocalDate currentDate);
}
