package com.nmh.commerce.coupon

import com.nmh.commerce.domain.Money
import com.nmh.commerce.product.Product
import java.util.function.Consumer

class Coupon (
    val id: Long?,
    @JvmField val name: String?,
    @JvmField val discountPolicies: MutableList<DiscountPolicy>,
    @JvmField val constraintPolicies: MutableList<ConstraintPolicy>,
    @JvmField val expirationPeriodPolicies: MutableList<ExpirationPeriodPolicy>
) {
    fun apply(product: Product): Money {
        constraintPolicies.forEach(Consumer { constraintPolicy: ConstraintPolicy -> constraintPolicy.verify(product) })
        return discountPolicies.stream()
            .map<Money> { discountPolicy: DiscountPolicy -> discountPolicy.calculateDiscount(product.price) }
            .reduce(Money.ZERO) { obj: Money, other: Money -> obj.plus(other) }
    }
}
