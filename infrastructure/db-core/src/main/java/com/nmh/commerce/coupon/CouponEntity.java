package com.nmh.commerce.coupon;

import com.nmh.commerce.BaseEntity;
import com.nmh.commerce.domain.Money;
import com.nmh.commerce.utils.MoneyConverter;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@SuperBuilder
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorColumn(name = "coupon_type")
public abstract class CouponEntity extends BaseEntity {

    protected String name;

    @Convert(converter = MoneyConverter.class)
    protected Money minimumAmount;

    protected LocalDateTime startsAt;

    protected LocalDateTime expiresAt;

    public static CouponEntity from(AbstractCoupon coupon) {
        if (coupon instanceof FixedAmountCoupon) {
            return FixedAmountCouponEntity.from((FixedAmountCoupon) coupon);
        } else if (coupon instanceof PercentageCoupon) {
            return PercentageCouponEntity.from((PercentageCoupon) coupon);
        }
        throw new IllegalArgumentException("지원하지 않는 쿠폰 타입입니다.");
    }

    abstract AbstractCoupon toDomain();
}
