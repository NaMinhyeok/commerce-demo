package com.nmh.commerce.coupon

import com.nmh.commerce.domain.Quantity
import com.nmh.commerce.domain.Quantity.Companion.of

class CouponStock (
    @JvmField val id: Long?,
    @JvmField val couponId: Long,
    @JvmField val remainingQuantity: Quantity,
    @JvmField val version: Long?
) {
    fun deductQuantity(): CouponStock {
        check(this.isRemaining) { "쿠폰 재고가 없습니다." }
        return CouponStock(this.id, this.couponId, this.remainingQuantity.subtract(of(1)), this.version)
    }

    private val isRemaining: Boolean
        get() = this.remainingQuantity.isRemaining

    companion object {
        @JvmStatic
        fun of(couponId: Long, remainingQuantity: Quantity): CouponStock? {
            return CouponStock(null, couponId, remainingQuantity, null)
        }
    }
}
