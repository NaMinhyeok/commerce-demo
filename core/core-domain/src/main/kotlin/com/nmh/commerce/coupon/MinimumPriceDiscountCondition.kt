package com.nmh.commerce.coupon

import com.nmh.commerce.domain.Money
import com.nmh.commerce.product.Product

data class MinimumPriceDiscountCondition(
    val minimumPrice: Money,
) : DiscountCondition {
    override fun isSatisfiedBy(product: Product): Boolean = minimumPrice.isGreaterThanOrEqual(product.price)
}
