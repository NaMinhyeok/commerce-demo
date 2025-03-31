package com.nmh.commerce.coupon;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

class CouponStockTest {

    @Test
    void 재고를_차감한다() {
        // given
        CouponStock stock = CouponStock.of(1L, 1);
        // when
        CouponStock deductedStock = stock.deductQuantity();
        // then
        then(deductedStock.getRemainingQuantity()).isEqualTo(0);
    }

    @Test
    void 남아있는_재고가_없으면_재고를_차감할_수_없다() {
        // given
        CouponStock stock = CouponStock.of(1L, 0);
        // when
        // then
        thenThrownBy(() -> stock.deductQuantity())
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("쿠폰 재고가 없습니다.");
    }

}