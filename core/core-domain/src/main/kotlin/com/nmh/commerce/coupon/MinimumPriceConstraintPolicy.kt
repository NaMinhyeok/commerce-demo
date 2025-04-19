package com.nmh.commerce.coupon

import com.nmh.commerce.domain.Money
import com.nmh.commerce.product.Product

class MinimumPriceConstraintPolicy(
    val minimumPrice: Money
) : ConstraintPolicy {
    override fun verify(product: Product) {
        require(!product.price.isLessThan(minimumPrice)) { "쿠폰을 적용 할 수 있는 최소 금액 미달입니다." }
    }
}
