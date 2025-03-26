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

    public FixedPriceDiscountPolicyEntity(Money discountPrice) {
        this.discountPrice = discountPrice;
    }

    public static FixedPriceDiscountPolicyEntity from(FixedPriceDiscountPolicy fixedPriceDiscountPolicy) {
        return new FixedPriceDiscountPolicyEntity(fixedPriceDiscountPolicy.getDiscountPrice());
    }

    @Override
    public DiscountPolicy toDomain() {
        return new FixedPriceDiscountPolicy(discountPrice);
    }
}
