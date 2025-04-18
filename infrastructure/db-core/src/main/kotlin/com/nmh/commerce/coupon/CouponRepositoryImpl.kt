package com.nmh.commerce.coupon

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import java.util.*
import java.util.function.Function

@Repository
class CouponRepositoryImpl(private val couponJpaRepository: CouponJpaRepository) : CouponRepository {
    public override fun findById(id: Long): Coupon {
        return couponJpaRepository.findByIdOrNull(id)?.toDomain() ?: throw NoSuchElementException("Coupon not found")
    }
}
