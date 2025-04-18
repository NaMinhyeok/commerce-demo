package com.nmh.commerce.coupon

import com.nmh.commerce.coupon.CouponStock.Companion.of
import com.nmh.commerce.domain.Quantity
import com.nmh.commerce.domain.Quantity.Companion.of
import org.assertj.core.api.BDDAssertions
import org.assertj.core.api.ThrowableAssert
import org.junit.jupiter.api.Test

internal class CouponStockTest {
    @Test
    fun 재고를_차감한다() {
        // given
        val stock = of(1L, of(1))
        // when
        val deductedStock = stock!!.deductQuantity()
        // then
        BDDAssertions.then<Quantity?>(deductedStock.remainingQuantity).isEqualTo(of(0))
    }

    @Test
    fun 남아있는_재고가_없으면_재고를_차감할_수_없다() {
        // given
        val stock = of(1L, of(0))
        // when
        // then
        BDDAssertions.thenThrownBy(ThrowableAssert.ThrowingCallable { stock!!.deductQuantity() })
            .isInstanceOf(IllegalStateException::class.java)
            .hasMessage("쿠폰 재고가 없습니다.")
    }
}