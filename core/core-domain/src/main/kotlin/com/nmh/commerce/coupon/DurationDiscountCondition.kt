package com.nmh.commerce.coupon

import com.nmh.commerce.product.Product
import kotlin.time.Duration

data class DurationDiscountCondition(
    val duration: Duration,
) : DiscountCondition {
    override fun isSatisfiedBy(product: Product): Boolean {
        TODO("기간을 어떻게 확인해야 되는지 다이어그램을 통해서 인자가 product가 맞는지, coupon 쪽에 policy가 맞는지 확인 필요")
    }
}
