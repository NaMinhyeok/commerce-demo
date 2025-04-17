package com.nmh.commerce.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class QuantityTest {

    @Test
    void 수량은_0_이상이어야_한다() {
        // given
        // when
        // then
        thenThrownBy(() -> Quantity.of(-1))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("수량은 0 이상이어야 합니다.");
    }

    @Test
    void 수량을_더한다() {
        // given
        Quantity quantity = Quantity.of(10);
        // when
        Quantity result = quantity.add(Quantity.of(5));
        // then
        then(result.value).isEqualTo(15);
    }

    @Test
    void 수량을_뺀다() {
        // given
        Quantity quantity = Quantity.of(10);
        // when
        Quantity result = quantity.subtract(Quantity.of(5));
        // then
        then(result.value).isEqualTo(5);
    }

    @Test
    void 재고가_0보다_작아지게_subtract_할_수_없다() {
        // given
        Quantity quantity = Quantity.of(10);
        // when
        // then
        thenThrownBy(() -> quantity.subtract(Quantity.of(11)))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("수량이 부족합니다.");
    }

    @Test
    void 재고가_남아있는지_여부를_확인한다() {
        assertAll(
            () -> then(Quantity.of(1).isRemaining()).isTrue(),
            () -> then(Quantity.of(0).isRemaining()).isFalse()
        );
    }

}