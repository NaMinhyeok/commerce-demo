package com.nmh.commerce.coupon;

import com.nmh.commerce.domain.Money;
import com.nmh.commerce.utils.MoneyConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("fixed_amount")
@Entity
public class FixedAmountCouponEntity extends CouponEntity {

    @Convert(converter = MoneyConverter.class)
    private Money amount;

    public static FixedAmountCouponEntity from(FixedAmountCoupon coupon) {
        return FixedAmountCouponEntity.builder()
            .name(coupon.getName())
            .minimumAmount(coupon.getMinimumAmount())
            .startsAt(coupon.getStartsAt())
            .expiresAt(coupon.getExpiresAt())
            .amount(coupon.getAmount())
            .build();
    }

    @Override
    FixedAmountCoupon toDomain() {
        return FixedAmountCoupon.builder()
            .id(getId())
            .name(name)
            .minimumAmount(minimumAmount)
            .startsAt(startsAt)
            .expiresAt(expiresAt)
            .build();
    }
}
