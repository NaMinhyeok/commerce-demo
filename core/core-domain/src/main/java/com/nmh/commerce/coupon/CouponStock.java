package com.nmh.commerce.coupon;

import com.nmh.commerce.domain.Quantity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CouponStock {
    private final Long id;
    private final Long couponId;
    private final Quantity remainingQuantity;

    @Builder
    private CouponStock(Long id, Long couponId, Quantity remainingQuantity) {
        this.id = id;
        this.couponId = couponId;
        this.remainingQuantity = remainingQuantity;
    }

    public static CouponStock of(Long couponId, Quantity remainingQuantity) {
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
            .remainingQuantity(this.remainingQuantity.subtract(Quantity.of(1)))
            .build();
    }

    private boolean isRemaining() {
        return this.remainingQuantity.isRemaining();
    }
}
