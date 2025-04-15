package com.nmh.commerce.coupon;

import com.nmh.commerce.BaseEntity;
import com.nmh.commerce.domain.Quantity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class CouponStockEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long couponId;
    private Quantity remainingQuantity;

    @Builder
    private CouponStockEntity(Long id, Long couponId, Quantity remainingQuantity) {
        this.id = id;
        this.couponId = couponId;
        this.remainingQuantity = remainingQuantity;
    }

    public static CouponStockEntity from(CouponStock couponStock) {
        return CouponStockEntity.builder()
            .id(couponStock.getId())
            .couponId(couponStock.getCouponId())
            .remainingQuantity(couponStock.getRemainingQuantity())
            .build();
    }

    public CouponStock toDomain() {
        return CouponStock.builder()
            .id(id)
            .couponId(couponId)
            .remainingQuantity(remainingQuantity)
            .build();
    }
}
