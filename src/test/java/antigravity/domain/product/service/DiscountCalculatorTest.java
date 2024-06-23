package antigravity.domain.product.service;

import antigravity.domain.product.MockItemGenerator;
import antigravity.domain.product.entity.Promotion;
import antigravity.domain.product.service.calculator.DiscountCalculator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DiscountCalculatorTest.TestConfig.class})
public class DiscountCalculatorTest {
    @Autowired
    private DiscountCalculator discountCalculator;

    @Configuration
    @ComponentScan(basePackageClasses = DiscountCalculator.class)
    static class TestConfig {
    }

    @Test
    @DisplayName("할인 금액 계산 test")
    public void calculateFinalPriceTest() {
        // given
        int originalPrice = 300000;
        List<Promotion> promotions = MockItemGenerator.generatePromotions();

        // when
        int finalPrice = discountCalculator.calculateFinalPrice(originalPrice, promotions);

        // then
        assertThat(finalPrice).isEqualTo(226000);
    }

    @Test
    @DisplayName("천원 단위 절삭 test")
    public void truncateToThousandTest() {
        // given
        int originalPrice = 311000;
        List<Promotion> promotions = MockItemGenerator.generatePromotions();

        // when
        int finalPrice = discountCalculator.calculateFinalPrice(originalPrice, promotions);

        // then
        assertThat(finalPrice).isEqualTo(234000);
    }

    @Test
    @DisplayName("최소 금액 보전 test")
    public void minPriceTest() {
        // given
        int originalPrice = 12000;
        List<Promotion> promotions = MockItemGenerator.generatePromotions();

        // when
        int finalPrice = discountCalculator.calculateFinalPrice(originalPrice, promotions);

        // then
        assertThat(finalPrice).isEqualTo(10000);
    }
}
