package com.nmh.commerce.coupon

import com.nmh.commerce.user.User
import java.time.LocalDateTime

class IssuedCoupon(
    val id: Long?,
    val coupon: Coupon,
    val user: User,
    val issuedAt: LocalDateTime
) {
    companion object {
        fun issue(coupon: Coupon, user: User, issuedAt: LocalDateTime): IssuedCoupon {
            return IssuedCoupon(null, coupon, user, issuedAt)
        }
    }
}
