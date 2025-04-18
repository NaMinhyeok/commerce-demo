package com.nmh.commerce.coupon

import com.nmh.commerce.domain.Money

interface DiscountPolicy {
    fun calculateDiscount(originalPrice: Money): Money
}
