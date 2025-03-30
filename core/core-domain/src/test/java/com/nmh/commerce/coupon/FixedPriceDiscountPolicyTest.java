package com.nmh.commerce.coupon;

import com.nmh.commerce.domain.Money;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.BDDAssertions.then;

class FixedPriceDiscountPolicyTest {

    @Test
    void 쿠폰을_적용한_후_할인한_가격을_계산한다() {
        // given
        FixedPriceDiscountPolicy policy = new FixedPriceDiscountPolicy(
            Money.of(BigDecimal.valueOf(1000))
        );
        // when
        Money result = policy.calculateDiscount(Money.of(BigDecimal.valueOf(10000)));
        // then
        Money expected = Money.of(BigDecimal.valueOf(9000));
        then(result).isEqualTo(expected);

    }

}