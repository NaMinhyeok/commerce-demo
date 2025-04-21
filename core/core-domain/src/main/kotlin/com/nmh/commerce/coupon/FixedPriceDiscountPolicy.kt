package com.nmh.commerce.coupon

import com.nmh.commerce.domain.Money

data class FixedPriceDiscountPolicy(
    val discountPrice: Money,
) : DiscountPolicy {
    override fun calculateDiscount(originalPrice: Money): Money = originalPrice.minus(discountPrice)
}
