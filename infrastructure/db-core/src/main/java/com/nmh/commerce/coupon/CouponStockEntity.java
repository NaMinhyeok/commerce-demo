package com.nmh.commerce.coupon;

import com.nmh.commerce.BaseEntity;
import com.nmh.commerce.domain.Quantity;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class CouponStockEntity extends BaseEntity {
    private Long couponId;
    private Quantity remainingQuantity;

    @Builder
    private CouponStockEntity(Long couponId, Quantity remainingQuantity) {
        this.couponId = couponId;
        this.remainingQuantity = remainingQuantity;
    }

    public static CouponStockEntity from(CouponStock couponStock) {
        return CouponStockEntity.builder()
            .couponId(couponStock.getCouponId())
            .remainingQuantity(couponStock.getRemainingQuantity())
            .build();
    }

    public CouponStock toDomain() {
        return CouponStock.builder()
            .id(getId())
            .couponId(couponId)
            .remainingQuantity(remainingQuantity)
            .build();
    }
}
