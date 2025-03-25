package com.nmh.commerce.coupon;

import com.nmh.commerce.domain.Money;
import com.nmh.commerce.domain.DiscountRate;
import lombok.Getter;

@Getter
public class PercentageDiscountPolicy implements DiscountPolicy {
    private final DiscountRate discountRate;

    public PercentageDiscountPolicy(DiscountRate discountRate) {
        this.discountRate = discountRate;
    }

    @Override
    public Money calculateDiscount(Money originalPrice) {
        Money discountPrice = originalPrice.apply(discountRate);
        return originalPrice.minus(discountPrice);
    }
}
