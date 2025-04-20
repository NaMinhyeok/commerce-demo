package com.nmh.commerce.coupon

import com.nmh.commerce.domain.Quantity
import com.nmh.commerce.domain.Quantity.Companion.of

class CouponStock(
    val id: Long = 0,
    val couponId: Long,
    val remainingQuantity: Quantity,
    val version: Long?
) {
    fun deductQuantity(): CouponStock {
        check(this.isRemaining) { "쿠폰 재고가 없습니다." }
        return CouponStock(this.id, this.couponId, this.remainingQuantity.subtract(of(1)), this.version)
    }

    private val isRemaining: Boolean
        get() = this.remainingQuantity.isRemaining

    companion object {
        fun of(couponId: Long, remainingQuantity: Quantity): CouponStock? {
            return CouponStock(couponId = couponId, remainingQuantity = remainingQuantity, version = null)
        }
    }
}
