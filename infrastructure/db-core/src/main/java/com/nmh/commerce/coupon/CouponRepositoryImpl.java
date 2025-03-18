package com.nmh.commerce.coupon;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CouponRepositoryImpl implements CouponRepository {

    private final CouponJpaRepository couponJpaRepository;

    @Override
    public Optional<AbstractCoupon> findById(Long id) {
        return couponJpaRepository.findById(id)
            .map(CouponEntity::toDomain);
    }
}
