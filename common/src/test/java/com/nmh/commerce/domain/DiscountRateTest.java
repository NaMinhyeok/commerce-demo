package com.nmh.commerce.domain;

import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.assertj.core.api.BDDAssertions.then;

class DiscountRateTest {

    @Test
    void 할인율을_생성한다() {
        // given
        // when
        DiscountRate discountRate = DiscountRate.of(BigDecimal.valueOf(0.33));
        // then
        then(discountRate.value)
            .isEqualTo(BigDecimal.valueOf(0.33));
    }

    @ParameterizedTest
    @CsvSource(
        value = {
            "-0.1",
            "1.1"
        }
    )
    void 할인율은_0과1사이이다(BigDecimal value) {
        // given
        // when
        // then
        BDDAssertions.thenThrownBy(() -> DiscountRate.of(value))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("할인율은 0 이상 1 이하의 값이어야 합니다.");
    }

}