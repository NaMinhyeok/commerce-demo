package com.nmh.commerce.coupon;

import com.nmh.commerce.domain.Quantity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CouponStock {
    private final Long id;
    private final Long couponId;
    private final Quantity remainingQuantity;
    private final Long version;

    @Builder
    private CouponStock(Long id, Long couponId, Quantity remainingQuantity, Long version) {
        this.id = id;
        this.couponId = couponId;
        this.remainingQuantity = remainingQuantity;
        this.version = version;
    }

    public static CouponStock of(Long couponId, Quantity remainingQuantity) {
        return CouponStock.builder()
            .couponId(couponId)
            .remainingQuantity(remainingQuantity)
            .version(0L)
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
            .version(this.version)
            .build();
    }

    private boolean isRemaining() {
        return this.remainingQuantity.isRemaining();
    }
}
