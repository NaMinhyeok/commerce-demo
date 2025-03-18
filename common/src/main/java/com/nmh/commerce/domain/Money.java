package com.nmh.commerce.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Money {
    private final BigDecimal value;

    private Money(BigDecimal value) {
        this.value = value;
    }

    public static Money of(BigDecimal value) {
        return new Money(value);
    }

    public Money add(Money other) {
        return new Money(value.add(other.value));
    }

    public Money subtract(Money other) {
        return new Money(value.subtract(other.value));
    }

    public Money multiply(Money other) {
        return new Money(value.multiply(other.value));
    }

    public Money divide(Money other) {
        return new Money(value.divide(other.value, 0, RoundingMode.HALF_UP));
    }

    public boolean isLessThan(Money other) {
        return value.compareTo(other.value) < 0;
    }

    public boolean isGreaterThanOrEqual(Money other) {
        return value.compareTo(other.value) >= 0;
    }
}
