package com.nmh.commerce.coupon

interface IssuedCouponRepository {
    fun save(issuedCoupon: IssuedCoupon): IssuedCoupon
}
