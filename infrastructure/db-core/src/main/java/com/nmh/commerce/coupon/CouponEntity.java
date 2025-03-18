package com.nmh.commerce.coupon;

import com.nmh.commerce.BaseEntity;
import com.nmh.commerce.domain.Money;
import com.nmh.commerce.utils.MoneyConverter;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorColumn(name = "coupon_type")
public abstract class CouponEntity extends BaseEntity {

    protected String name;

    @Convert(converter = MoneyConverter.class)
    protected Money minimumAmount;

    protected LocalDateTime startsAt;

    protected LocalDateTime expiresAt;

    abstract AbstractCoupon toDomain();
}
