package com.nmh.commerce.coupon;

import com.nmh.commerce.domain.Money;
import com.nmh.commerce.utils.MoneyConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("FixedPriceDiscountPolicy")
public class FixedPriceDiscountPolicyEntity extends DiscountPolicyEntity {

    @Convert(converter = MoneyConverter.class)
    private Money discountPrice;

    public FixedPriceDiscountPolicyEntity(CouponEntity coupon, Money discountPrice) {
        super(coupon);
        this.discountPrice = discountPrice;
    }

    public static FixedPriceDiscountPolicyEntity from(FixedPriceDiscountPolicy fixedPriceDiscountPolicy, CouponEntity coupon) {
        return new FixedPriceDiscountPolicyEntity(coupon, fixedPriceDiscountPolicy.discountPrice);
    }

    @Override
    public DiscountPolicy toDomain() {
        return new FixedPriceDiscountPolicy(discountPrice);
    }
}
