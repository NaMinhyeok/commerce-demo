package com.nmh.commerce.domain;

import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
public class Money {
    private final BigDecimal value;

    private Money(BigDecimal value) {
        this.value = value;
    }

    public static Money of(BigDecimal value) {
        return new Money(value);
    }

    public Money plus(Money other) {
        return new Money(value.add(other.value));
    }

    public Money minus(Money other) {
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
