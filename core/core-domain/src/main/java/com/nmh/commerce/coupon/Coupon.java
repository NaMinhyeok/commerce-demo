package com.nmh.commerce.coupon;

import com.nmh.commerce.domain.Money;
import com.nmh.commerce.product.Product;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class Coupon {
    private final Long id;
    private final String name;
    private final List<DiscountPolicy> discountPolicies;
    private final List<ConstraintPolicy> constraintPolicies;
    private final ExpirationPeriod expirationPeriod;

    @Builder
    private Coupon(Long id, String name, List<DiscountPolicy> discountPolicies, List<ConstraintPolicy> constraintPolicies, ExpirationPeriod expirationPeriod) {
        this.id = id;
        this.name = name;
        this.discountPolicies = discountPolicies;
        this.constraintPolicies = constraintPolicies;
        this.expirationPeriod = expirationPeriod;
    }

    public Money apply(Product product) {
        constraintPolicies.forEach(constraintPolicy -> constraintPolicy.verify(product));
        return discountPolicies.stream()
            .map(discountPolicy -> discountPolicy.calculateDiscount(product.getPrice()))
            .reduce(Money.ZERO, Money::plus);
    }
}
