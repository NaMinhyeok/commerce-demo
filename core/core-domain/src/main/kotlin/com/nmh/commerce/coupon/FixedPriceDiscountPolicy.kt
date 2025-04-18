package com.nmh.commerce.coupon

import com.nmh.commerce.domain.Money

class FixedPriceDiscountPolicy(@JvmField val discountPrice: Money) : DiscountPolicy {
    override fun calculateDiscount(originalPrice: Money): Money {
        return originalPrice.minus(discountPrice)
    }
}
