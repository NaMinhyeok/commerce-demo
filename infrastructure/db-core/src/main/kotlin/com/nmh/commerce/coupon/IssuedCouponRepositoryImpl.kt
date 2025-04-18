package com.nmh.commerce.coupon

import org.springframework.stereotype.Repository

@Repository
class IssuedCouponRepositoryImpl(private val issuedCouponJpaRepository: IssuedCouponJpaRepository) : IssuedCouponRepository {
    override fun save(issuedCoupon: IssuedCoupon): IssuedCoupon {
        return issuedCouponJpaRepository.save(IssuedCouponEntity.from(issuedCoupon)).toDomain()
    }
}
