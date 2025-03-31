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

    public static CouponStock of(Long couponId, int remainingQuantity) {
        return CouponStock.builder()
            .couponId(couponId)
            .remainingQuantity(remainingQuantity)
            .build();
    }

    public CouponStock deductQuantity() {
        if (!isRemaining()) {
            throw new IllegalStateException("쿠폰 재고가 없습니다.");
        }
        return CouponStock.builder()
            .id(this.id)
            .couponId(this.couponId)
            .remainingQuantity(this.remainingQuantity - 1)
            .build();
    }

    private boolean isRemaining() {
        return remainingQuantity > 0;
    }
}
