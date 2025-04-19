package com.nmh.commerce.domain

import org.assertj.core.api.BDDAssertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.math.BigDecimal

internal class DiscountRateTest {
    @Test
    fun 할인율을_생성한다() {
        // given
        // when
        val discountRate = DiscountRate.of(BigDecimal.valueOf(0.33))
        // then
        BDDAssertions.then(discountRate.value)
            .isEqualTo(BigDecimal.valueOf(0.33))
    }

    @ParameterizedTest
    @CsvSource(
        value = ["-0.1", "1.1"
        ]
    )
    fun 할인율은_0과1사이이다(value: BigDecimal) {
        // given
        // when
        // then
        BDDAssertions.thenThrownBy { DiscountRate.of(value) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("할인율은 0 이상 1 이하의 값이어야 합니다.")
    }
}