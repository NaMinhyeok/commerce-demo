package com.nmh.commerce.coupon;

import com.nmh.commerce.domain.Money;
import com.nmh.commerce.domain.Percentage;
import lombok.Getter;

@Getter
public class PercentageDiscountPolicy implements DiscountPolicy {
    private final Percentage discountRate;

    public PercentageDiscountPolicy(Percentage discountRate) {
        this.discountRate = discountRate;
    }

    @Override
    public Money calculateDiscount(Money originalPrice) {
        Money discountPrice = discountRate.apply(originalPrice);
        return originalPrice.minus(discountPrice);
    }
}
