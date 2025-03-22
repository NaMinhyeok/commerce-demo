package com.nmh.commerce.coupon;

import com.nmh.commerce.domain.Money;
import lombok.Getter;

@Getter
public class FixedPriceDiscountPolicy implements DiscountPolicy {
    private final Money discountPrice;

    public FixedPriceDiscountPolicy(Money discountPrice) {
        this.discountPrice = discountPrice;
    }

    @Override
    public Money calculateDiscount(Money originalPrice) {
        return originalPrice.minus(discountPrice);
    }
}
