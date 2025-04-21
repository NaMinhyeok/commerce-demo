package com.nmh.commerce.coupon

import com.nmh.commerce.domain.Money
import com.nmh.commerce.product.Product
import java.util.function.Consumer

data class Coupon(
    val id: Long,
    val name: String,
    val discountPolicies: MutableList<DiscountPolicy>,
    val constraintPolicies: MutableList<ConstraintPolicy>,
    val expirationPeriodPolicies: MutableList<ExpirationPeriodPolicy>,
) {
    fun apply(product: Product): Money {
        constraintPolicies.forEach(
            Consumer { constraintPolicy: ConstraintPolicy -> constraintPolicy.verify(product) },
        )
        return discountPolicies
            .stream()
            .map { discountPolicy: DiscountPolicy -> discountPolicy.calculateDiscount(product.price) }
            .reduce(Money.ZERO) { obj: Money, other: Money -> obj.plus(other) }
    }
}
