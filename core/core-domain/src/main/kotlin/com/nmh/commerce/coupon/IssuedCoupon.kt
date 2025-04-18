package com.nmh.commerce.coupon

import com.nmh.commerce.user.User
import java.time.LocalDateTime

class IssuedCoupon(
    val id: Long?,
    @JvmField val coupon: Coupon,
    @JvmField val user: User,
    @JvmField val issuedAt: LocalDateTime?
) {
    companion object {
        @JvmStatic
        fun issue(coupon: Coupon, user: User, issuedAt: LocalDateTime?): IssuedCoupon {
            return IssuedCoupon(null, coupon, user, issuedAt)
        }
    }
}
