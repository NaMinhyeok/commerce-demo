package com.nmh.commerce.coupon;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("percentage")
@Entity
public class PercentageCouponEntity extends CouponEntity {
    private int percentage;

    public static PercentageCouponEntity from(PercentageCoupon coupon) {
        return PercentageCouponEntity.builder()
            .name(coupon.getName())
            .minimumAmount(coupon.getMinimumAmount())
            .startsAt(coupon.getStartsAt())
            .expiresAt(coupon.getExpiresAt())
            .percentage(coupon.getPercentage())
            .build();
    }

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
