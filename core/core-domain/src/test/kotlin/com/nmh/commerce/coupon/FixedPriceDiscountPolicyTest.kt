package com.nmh.commerce.coupon

import com.nmh.commerce.domain.Money
import org.assertj.core.api.BDDAssertions
import org.junit.jupiter.api.Test
import java.math.BigDecimal

internal class FixedPriceDiscountPolicyTest {
    @Test
    fun 쿠폰을_적용한_후_할인한_가격을_계산한다() {
        // given
        val policy = FixedPriceDiscountPolicy(
            Money.of(BigDecimal.valueOf(1000))
        )
        // when
        val result = policy.calculateDiscount(Money.of(BigDecimal.valueOf(10000)))
        // then
        val expected = Money.of(BigDecimal.valueOf(9000))
        BDDAssertions.then<Money?>(result).isEqualTo(expected)
    }
}