package com.nmh.commerce.domain

import org.assertj.core.api.BDDAssertions
import org.junit.jupiter.api.Test
import java.math.BigDecimal

internal class MoneyTest {
    @Test
    fun 금액을_더할_수_있다() {
        // given
        val target = Money.of(BigDecimal.ONE)
        val other = Money.of(BigDecimal.TEN)
        // when
        val result = target.plus(other)
        // then
        BDDAssertions.then<Money?>(result).extracting("value").isEqualTo(BigDecimal.valueOf(11))
    }

    @Test
    fun 금액을_뺄_수_있다() {
        // given
        val target = Money.of(BigDecimal.TEN)
        val other = Money.of(BigDecimal.ONE)
        // when
        val result = target.minus(other)
        // then
        BDDAssertions.then<Money?>(result).extracting("value").isEqualTo(BigDecimal.valueOf(9))
    }

    @Test
    fun 금액을_곱할_수_있다() {
        // given
        val target = Money.of(BigDecimal.TEN)
        val other = Money.of(BigDecimal.TWO)
        // when
        val result = target.multiply(other)
        // then
        BDDAssertions.then<Money?>(result).extracting("value").isEqualTo(BigDecimal.valueOf(20))
    }

    @Test
    fun 금액을_나눌_수_있다() {
        // given
        val target = Money.of(BigDecimal.TEN)
        val other = Money.of(BigDecimal.TWO)
        // when
        val result = target.divide(other)
        // then
        BDDAssertions.then<Money?>(result).extracting("value").isEqualTo(BigDecimal.valueOf(5))
    }

    @Test
    fun 입력받는_금액이_더_작은지_확인한다() {
        // given
        val target = Money.of(BigDecimal.TEN)
        val other = Money.of(BigDecimal.TWO)
        // when
        val result = target.isLessThan(other)
        // then
        BDDAssertions.then(result).isFalse()
    }

    @Test
    fun 입력받는_금액이_더_큰지_확인한다() {
        // given
        val target = Money.of(BigDecimal.TEN)
        val other = Money.of(BigDecimal.ONE)
        // when
        val result = target.isGreaterThanOrEqual(other)
        // then
        BDDAssertions.then(result).isTrue()
    }

    @Test
    fun 금액을_할인율을_이용하여_계산한다() {
        // given
        val target = Money.of(BigDecimal.TEN)
        val discountRate = DiscountRate.of(BigDecimal.valueOf(0.1))
        // when
        val result = target.apply(discountRate)
        // then
        val expected = Money.of(BigDecimal.ONE)
        BDDAssertions.then<Money?>(result).isEqualTo(expected)
    }
}
