package com.nmh.commerce.coupon

import com.nmh.commerce.domain.Money
import com.nmh.commerce.product.Product

abstract class AbstractDiscountPolicy(
    private val discountConditions: List<DiscountCondition>,
) {

    fun calculateDiscount(product: Product): Money {
        for (condition in discountConditions) {
            if (condition.isSatisfiedBy(product)) {
                return getDiscountAmount(product)
            }
        }
        return Money.ZERO
    }

    abstract fun getDiscountAmount(product: Product): Money
}
