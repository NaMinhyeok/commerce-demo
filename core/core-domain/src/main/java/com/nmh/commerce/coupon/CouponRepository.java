package com.nmh.commerce.coupon;

import java.util.Optional;

public interface CouponRepository {

    Optional<AbstractCoupon> findById(Long id);

}
