package antigravity.domain.product.repository;

import antigravity.domain.product.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long>, CustomPromotionRepository {
}
