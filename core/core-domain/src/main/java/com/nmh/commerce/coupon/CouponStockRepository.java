package com.nmh.commerce.coupon;

import java.util.Optional;

public interface CouponStockRepository {

    CouponStock save(CouponStock couponStock);

    Optional<CouponStock> findByCouponId(Long couponId);

    CouponStock update(CouponStock couponStock);
}
