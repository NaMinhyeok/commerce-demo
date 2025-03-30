package com.nmh.commerce.coupon;

import com.nmh.commerce.domain.DiscountRate;
import com.nmh.commerce.utils.DiscountRateConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("PercentageDiscountPolicy")
public class PercentageDiscountPolicyEntity extends DiscountPolicyEntity {

    @Convert(converter = DiscountRateConverter.class)
    private DiscountRate discountRate;

    public PercentageDiscountPolicyEntity(CouponEntity coupon, DiscountRate discountRate) {
        super(coupon);
        this.discountRate = discountRate;
    }

    public static PercentageDiscountPolicyEntity from(PercentageDiscountPolicy percentageDiscountPolicy, CouponEntity coupon) {
        return new PercentageDiscountPolicyEntity(coupon, percentageDiscountPolicy.getDiscountRate());
    }

    @Override
    public DiscountPolicy toDomain() {
        return new PercentageDiscountPolicy(discountRate);
    }
}
