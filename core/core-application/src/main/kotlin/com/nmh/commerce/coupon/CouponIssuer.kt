package com.nmh.commerce.coupon

import com.nmh.commerce.coupon.IssuedCoupon.Companion.issue
import com.nmh.commerce.user.UserRepository
import com.nmh.commerce.utils.LocalDateTimeHolder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.function.Supplier

@Component
class CouponIssuer(
    private val couponRepository: CouponRepository,
    private val issuedCouponRepository: IssuedCouponRepository,
    private val userRepository: UserRepository,
    private val stockManager: CouponStockManager,
    private val localDateTimeHolder: LocalDateTimeHolder
) {
    @Transactional
    fun issue(couponId: Long, userId: Long): IssuedCoupon {
        val coupon = couponRepository.findById(couponId)
        val user = userRepository.findById(userId)
        val issuedCoupon = issue(coupon, user, localDateTimeHolder.now())
        stockManager.deductStock(couponId)
        return issuedCouponRepository.save(issuedCoupon)
    }
}
