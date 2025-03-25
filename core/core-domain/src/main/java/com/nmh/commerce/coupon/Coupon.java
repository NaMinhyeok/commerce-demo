package com.nmh.commerce.coupon;

import com.nmh.commerce.domain.Money;
import com.nmh.commerce.product.Product;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Coupon {
    private final Long id;
    private final String name;
    private final DiscountPolicy discountPolicy;
    private final ConstraintPolicy constraintPolicy;

    @Builder
    private Coupon(Long id, String name, DiscountPolicy discountPolicy, ConstraintPolicy constraintPolicy, CouponItselfPolicy couponItselfPolicy) {
        this.id = id;
        this.name = name;
        this.discountPolicy = discountPolicy;
        this.constraintPolicy = constraintPolicy;
    }

    public Money apply(Product product) {
        constraintPolicy.verify(product);
        return discountPolicy.calculateDiscount(product.getPrice());
    }

}
