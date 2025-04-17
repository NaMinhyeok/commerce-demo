package com.nmh.commerce.domain

import java.math.BigDecimal

class DiscountRate private constructor(value: BigDecimal) {
    @JvmField
    val value: BigDecimal

    init {
        require(!(value < BigDecimal.ZERO || value > BigDecimal.ONE)) { "할인율은 0 이상 1 이하의 값이어야 합니다." }
        this.value = value
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val discountRate = o as DiscountRate
        return value.compareTo(discountRate.value) == 0
    }

    override fun hashCode(): Int {
        return value.stripTrailingZeros().hashCode()
    }

    companion object {
        @JvmStatic
        fun of(value: BigDecimal): DiscountRate {
            return DiscountRate(value)
        }
    }
}
