package com.nmh.commerce.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.BDDAssertions.then;

class MoneyTest {

    @Test
    void 금액을_더할_수_있다() {
        // given
        Money target = Money.of(BigDecimal.ONE);
        Money other = Money.of(BigDecimal.TEN);
        // when
        Money result = target.plus(other);
        // then
        then(result).extracting("value")
            .isEqualTo(BigDecimal.valueOf(11));
    }

    @Test
    void 금액을_뺄_수_있다() {
        // given
        Money target = Money.of(BigDecimal.TEN);
        Money other = Money.of(BigDecimal.ONE);
        // when
        Money result = target.minus(other);
        // then
        then(result).extracting("value")
            .isEqualTo(BigDecimal.valueOf(9));
    }

    @Test
    void 금액을_곱할_수_있다() {
        // given
        Money target = Money.of(BigDecimal.TEN);
        Money other = Money.of(BigDecimal.TWO);
        // when
        Money result = target.multiply(other);
        // then
        then(result).extracting("value")
            .isEqualTo(BigDecimal.valueOf(20));
    }

    @Test
    void 금액을_나눌_수_있다() {
        // given
        Money target = Money.of(BigDecimal.TEN);
        Money other = Money.of(BigDecimal.TWO);
        // when
        Money result = target.divide(other);
        // then
        then(result).extracting("value")
            .isEqualTo(BigDecimal.valueOf(5));
    }

    @Test
    void 입력받는_금액이_더_작은지_확인한다() {
        // given
        Money target = Money.of(BigDecimal.TEN);
        Money other = Money.of(BigDecimal.TWO);
        // when
        boolean result = target.isLessThan(other);
        // then
        then(result).isFalse();
    }

    @Test
    void 입력받는_금액이_더_큰지_확인한다() {
        // given
        Money target = Money.of(BigDecimal.TEN);
        Money other = Money.of(BigDecimal.ONE);
        // when
        boolean result = target.isGreaterThanOrEqual(other);
        // then
        then(result).isTrue();
    }

    @Test
    void 금액을_할인율을_이용하여_계산한다() {
        // given
        Money target = Money.of(BigDecimal.TEN);
        DiscountRate discountRate = DiscountRate.of(BigDecimal.valueOf(0.1));
        // when
        Money result = target.apply(discountRate);
        // then
        Money expected = Money.of(BigDecimal.ONE);
        then(result).isEqualTo(expected);
    }
}