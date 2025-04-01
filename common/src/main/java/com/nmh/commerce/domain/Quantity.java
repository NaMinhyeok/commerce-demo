package com.nmh.commerce.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class Quantity {
    private final int value;

    private Quantity(int value) {
        this.value = value;
    }

    public static Quantity of(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("수량은 0 이상이어야 합니다.");
        }
        return new Quantity(value);
    }

    public Quantity add(Quantity quantity) {
        return new Quantity(this.value + quantity.value);
    }

    public Quantity subtract(Quantity quantity) {
        if (this.value < quantity.value) {
            throw new IllegalArgumentException("수량이 부족합니다.");
        }
        return new Quantity(this.value - quantity.value);
    }

    public boolean isRemaining() {
        return this.value > 0;
    }

}
