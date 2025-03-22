package com.nmh.commerce.coupon;

import com.nmh.commerce.domain.Money;

public interface DiscountPolicy {
    Money calculateDiscount(Money originalPrice);
}
