package com.nmh.commerce.coupon

import com.nmh.commerce.product.Product

interface DiscountCondition {

    fun isSatisfiedBy(product: Product): Boolean
}
