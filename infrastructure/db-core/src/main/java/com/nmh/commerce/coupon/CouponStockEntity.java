package com.nmh.commerce.coupon;

import com.nmh.commerce.BaseEntity;
import com.nmh.commerce.domain.Quantity;
import jakarta.persistence.*;
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
    @Version
    private Long version;

    @Builder
    private CouponStockEntity(Long id, Long couponId, Quantity remainingQuantity, Long version) {
        this.id = id;
        this.couponId = couponId;
        this.remainingQuantity = remainingQuantity;
        this.version = version;
    }

    public static CouponStockEntity from(CouponStock couponStock) {
        return CouponStockEntity.builder()
            .id(couponStock.getId())
            .couponId(couponStock.getCouponId())
            .remainingQuantity(couponStock.getRemainingQuantity())
            .version(couponStock.getVersion())
            .build();
    }

    public CouponStock toDomain() {
        return CouponStock.builder()
            .id(id)
            .couponId(couponId)
            .remainingQuantity(remainingQuantity)
            .version(version)
            .build();
    }
}
