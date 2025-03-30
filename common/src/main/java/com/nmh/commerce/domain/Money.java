package com.nmh.commerce.domain;

import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
public class Money {
    public static final Money ZERO = Money.of(BigDecimal.ZERO);

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

    public Money apply(DiscountRate rate) {
        return new Money(value.multiply(rate.getValue()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return value.compareTo(money.value) == 0;
    }

    @Override
    public int hashCode() {
        return value.stripTrailingZeros().hashCode();
    }
}
