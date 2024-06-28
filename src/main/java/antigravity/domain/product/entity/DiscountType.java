package antigravity.domain.product.entity;

import antigravity.domain.product.service.calculator.CalculatorStrategy;
import antigravity.domain.product.service.calculator.PercentCalculatorStrategy;
import antigravity.domain.product.service.calculator.WonCalculatorStrategy;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DiscountType {
    PERCENT(new PercentCalculatorStrategy()),
    WON(new WonCalculatorStrategy())
    ;

    private final CalculatorStrategy calculator;
}
