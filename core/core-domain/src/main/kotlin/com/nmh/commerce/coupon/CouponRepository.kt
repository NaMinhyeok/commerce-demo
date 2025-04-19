package com.nmh.commerce.coupon

interface CouponRepository {
    fun findById(id: Long): Coupon
}
