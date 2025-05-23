package com.nmh.commerce.domain

import java.math.BigDecimal
import java.math.RoundingMode

class Money private constructor(
    val value: BigDecimal,
) {
    fun plus(other: Money): Money = Money(value.add(other.value))

    fun minus(other: Money): Money = Money(value.subtract(other.value))

    fun multiply(other: Money): Money = Money(value.multiply(other.value))

    fun divide(other: Money): Money = Money(value.divide(other.value, 0, RoundingMode.HALF_UP))

    fun isLessThan(other: Money): Boolean = value.compareTo(other.value) < 0

    fun isGreaterThanOrEqual(other: Money): Boolean = value.compareTo(other.value) >= 0

    fun apply(rate: DiscountRate): Money = Money(value.multiply(rate.value))

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val money = o as Money
        return value.compareTo(money.value) == 0
    }

    override fun hashCode(): Int = value.stripTrailingZeros().hashCode()

    companion object {
        val ZERO: Money = of(BigDecimal.ZERO)

        fun of(value: BigDecimal): Money = Money(value)
    }
}
