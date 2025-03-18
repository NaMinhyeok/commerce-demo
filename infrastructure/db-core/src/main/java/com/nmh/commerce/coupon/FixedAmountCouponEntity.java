package com.nmh.commerce.coupon;

import com.nmh.commerce.domain.Money;
import com.nmh.commerce.utils.MoneyConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class FixedAmountCouponEntity extends CouponEntity {

    @Convert(converter = MoneyConverter.class)
    private Money amount;

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
