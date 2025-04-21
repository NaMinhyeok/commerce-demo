package com.nmh.commerce.coupon

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class CouponRepositoryImpl(
    private val couponJpaRepository: CouponJpaRepository,
) : CouponRepository {
    override fun findById(id: Long): Coupon =
        couponJpaRepository.findByIdOrNull(id)?.toDomain()
            ?: throw NoSuchElementException("Coupon not found")
}
