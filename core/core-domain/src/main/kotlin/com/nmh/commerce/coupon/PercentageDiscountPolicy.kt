package com.nmh.commerce.coupon

import com.nmh.commerce.domain.DiscountRate
import com.nmh.commerce.domain.Money

class PercentageDiscountPolicy(
    val discountRate: DiscountRate
) : DiscountPolicy {
    override fun calculateDiscount(originalPrice: Money): Money {
        val discountPrice = originalPrice.apply(discountRate)
        return originalPrice.minus(discountPrice)
    }
}
