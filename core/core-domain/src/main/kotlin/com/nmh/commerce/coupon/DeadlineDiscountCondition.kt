package com.nmh.commerce.coupon

import com.nmh.commerce.product.Product
import java.time.LocalDateTime

data class DeadlineDiscountCondition(
    val startAt: LocalDateTime,
    val endAt: LocalDateTime,
) : DiscountCondition {
    override fun isSatisfiedBy(product: Product): Boolean {
        val now = LocalDateTime.now()
        return now.isAfter(startAt) && now.isBefore(endAt)
    }
}
