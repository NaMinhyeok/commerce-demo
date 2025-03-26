package com.nmh.commerce.domain;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class DiscountRate {
    private final BigDecimal value;

    private DiscountRate(BigDecimal value) {
        if (value.compareTo(BigDecimal.ZERO) < 0 || value.compareTo(BigDecimal.ONE) > 0) {
            throw new IllegalArgumentException("할인율은 0 이상 1 이하의 값이어야 합니다.");
        }
        this.value = value;
    }

    public static DiscountRate of(BigDecimal value) {
        return new DiscountRate(value);
    }
}
