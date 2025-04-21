package com.nmh.commerce.coupon

import com.nmh.commerce.domain.Quantity
import com.nmh.commerce.domain.Quantity.Companion.of

data class CouponStock(
    val id: Long = 0,
    val couponId: Long,
    val remainingQuantity: Quantity,
    val version: Long?,
) {
    fun deductQuantity(): CouponStock {
        check(this.isRemaining) { "쿠폰 재고가 없습니다." }
        return this.copy(remainingQuantity = this.remainingQuantity.subtract(of(1)))
    }

    private val isRemaining: Boolean
        get() = this.remainingQuantity.isRemaining

    companion object {
        fun of(
            couponId: Long,
            remainingQuantity: Quantity,
        ): CouponStock? = CouponStock(couponId = couponId, remainingQuantity = remainingQuantity, version = null)
    }
}
