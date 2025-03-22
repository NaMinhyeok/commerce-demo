package com.nmh.commerce.domain;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class Percentage {
    private final BigDecimal value;

    public Percentage(BigDecimal value) {
        this.value = value;
    }

    public Money apply(Money price) {
        return Money.of(price.getValue().multiply(value));
    }
}
