package com.nmh.commerce.coupon

import java.util.*

interface CouponStockRepository {
    fun save(couponStock: CouponStock?): CouponStock?

    fun findByCouponId(couponId: Long?): Optional<CouponStock?>?

    fun update(couponStock: CouponStock?): CouponStock?
}
