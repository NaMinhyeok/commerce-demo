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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiscountRate discountRate = (DiscountRate) o;
        return value.compareTo(discountRate.value) == 0;
    }

    @Override
    public int hashCode() {
        return value.stripTrailingZeros().hashCode();
    }
}
