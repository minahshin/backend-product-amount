package antigravity.config;

import antigravity.domain.product.repository.CustomPromotionRepository;
import antigravity.domain.product.repository.CustomPromotionRepositoryImpl;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@TestConfiguration
public class TestJpaConfig {
    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }

    @Bean
    public CustomPromotionRepository customPromotionRepository() {
        return new CustomPromotionRepositoryImpl(jpaQueryFactory());
    }
}