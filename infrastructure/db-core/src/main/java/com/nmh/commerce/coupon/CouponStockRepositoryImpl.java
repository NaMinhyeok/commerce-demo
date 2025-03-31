package com.nmh.commerce.coupon;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CouponStockRepositoryImpl implements CouponStockRepository {

    private final CouponStockJpaRepository couponStockJpaRepository;

    public CouponStockRepositoryImpl(CouponStockJpaRepository couponStockJpaRepository) {
        this.couponStockJpaRepository = couponStockJpaRepository;
    }

    @Override
    public CouponStock save(CouponStock couponStock) {
        return couponStockJpaRepository.save(CouponStockEntity.from(couponStock)).toDomain();
    }

    @Override
    public Optional<CouponStock> findByCouponId(Long couponId) {
        return couponStockJpaRepository.findByCouponId(couponId)
            .map(CouponStockEntity::toDomain);
    }

}
