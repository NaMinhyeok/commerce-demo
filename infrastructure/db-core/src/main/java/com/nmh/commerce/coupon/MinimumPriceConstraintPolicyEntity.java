package com.nmh.commerce.coupon;

import com.nmh.commerce.domain.Money;
import com.nmh.commerce.utils.MoneyConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("MinimumPriceConstraintPolicy")
@Entity
public class MinimumPriceConstraintPolicyEntity extends ConstraintPolicyEntity {

    @Convert(converter = MoneyConverter.class)
    private Money minimumPrice;

    private MinimumPriceConstraintPolicyEntity(CouponEntity coupon, Money minimumPrice) {
        super(coupon);
        this.minimumPrice = minimumPrice;
    }

    public static MinimumPriceConstraintPolicyEntity from(MinimumPriceConstraintPolicy minimumPriceConstraintPolicy, CouponEntity coupon) {
        return new MinimumPriceConstraintPolicyEntity(coupon, minimumPriceConstraintPolicy.getMinimumPrice());
    }

    @Override
    public ConstraintPolicy toDomain() {
        return new MinimumPriceConstraintPolicy(minimumPrice);
    }
}
