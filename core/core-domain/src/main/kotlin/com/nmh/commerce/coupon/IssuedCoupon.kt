package com.nmh.commerce.coupon

import com.nmh.commerce.user.User
import java.time.LocalDateTime

data class IssuedCoupon(
    val id: Long = 0,
    val coupon: Coupon,
    val user: User,
    val issuedAt: LocalDateTime,
) {
    companion object {
        fun issue(
            coupon: Coupon,
            user: User,
            issuedAt: LocalDateTime,
        ): IssuedCoupon = IssuedCoupon(coupon = coupon, user = user, issuedAt = issuedAt)
    }
}
