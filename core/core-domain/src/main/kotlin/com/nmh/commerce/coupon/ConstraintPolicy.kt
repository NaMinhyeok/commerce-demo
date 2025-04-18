package com.nmh.commerce.coupon

import com.nmh.commerce.product.Product

interface ConstraintPolicy {
    fun verify(product: Product)
}
