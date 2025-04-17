package com.nmh.commerce.coupon;

import jakarta.persistence.OptimisticLockException;
import org.hibernate.StaleObjectStateException;
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

    @Override
    public CouponStock update(CouponStock couponStock) {
        try {
            return couponStockJpaRepository.save(CouponStockEntity.from(couponStock)).toDomain();
        } catch (OptimisticLockException | StaleObjectStateException e) {
            throw new IllegalStateException("Failed to update coupon stock", e);
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred", e);
        }
    }

}
