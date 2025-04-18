package com.nmh.commerce.coupon

import java.util.*

interface CouponRepository {
    fun findById(id: Long): Coupon
}
