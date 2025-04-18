package com.nmh.commerce.coupon

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface CouponStockJpaRepository : JpaRepository<CouponStockEntity, Long> {
    fun findByCouponId(couponId: Long): CouponStockEntity?
}
