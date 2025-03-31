package com.nmh.commerce.coupon;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CouponStockJpaRepository extends JpaRepository<CouponStockEntity, Long> {
    Optional<CouponStockEntity> findByCouponId(Long couponId);
}
