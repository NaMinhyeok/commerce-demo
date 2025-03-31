package com.nmh.commerce.coupon;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CouponStock {
    private final Long id;
    private final Long couponId;
    private final int remainingQuantity;

    @Builder
    private CouponStock(Long id, Long couponId, int remainingQuantity) {
        this.id = id;
        this.couponId = couponId;
        this.remainingQuantity = remainingQuantity;
    }

}
