package com.nmh.commerce.coupon;

import jakarta.persistence.Entity;

@Entity
public class PercentageCouponEntity extends CouponEntity {
    private int percentage;

    @Override
    PercentageCoupon toDomain() {
        return PercentageCoupon.builder()
            .id(getId())
            .name(name)
            .minimumAmount(minimumAmount)
            .startsAt(startsAt)
            .expiresAt(expiresAt)
            .percentage(percentage)
            .build();
    }
}
