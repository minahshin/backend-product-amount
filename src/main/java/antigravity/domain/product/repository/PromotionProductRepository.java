package antigravity.domain.product.repository;

import antigravity.domain.product.entity.PromotionId;
import antigravity.domain.product.entity.PromotionProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromotionProductRepository extends JpaRepository<PromotionProducts, Long> {
    List<PromotionId> findByProductIdAndPromotionIdIn(Long productId, List<Long> promotionIdList);
}
