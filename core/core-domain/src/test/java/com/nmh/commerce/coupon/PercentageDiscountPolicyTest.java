package com.nmh.commerce.coupon;

import com.nmh.commerce.domain.DiscountRate;
import com.nmh.commerce.domain.Money;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.BDDAssertions.then;

class PercentageDiscountPolicyTest {

    @Test
    void 쿠폰을_적용한_후_할인한_가격을_계산한다() {
        // given
        PercentageDiscountPolicy policy = new PercentageDiscountPolicy(
            DiscountRate.of(BigDecimal.valueOf(0.1))
        );
        // when
        Money result = policy.calculateDiscount(Money.of(BigDecimal.valueOf(10000)));
        // then
        Money expected = Money.of(BigDecimal.valueOf(9000));
        then(result).isEqualTo(expected);
    }

}