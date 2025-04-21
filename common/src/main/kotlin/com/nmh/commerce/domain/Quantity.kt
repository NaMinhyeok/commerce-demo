package com.nmh.commerce.domain

import java.util.Objects

class Quantity private constructor(
    val value: Int,
) {
    fun add(quantity: Quantity): Quantity = Quantity(this.value + quantity.value)

    fun subtract(quantity: Quantity): Quantity {
        require(this.value >= quantity.value) { "수량이 부족합니다." }
        return Quantity(this.value - quantity.value)
    }

    val isRemaining: Boolean
        get() = this.value > 0

    override fun equals(o: Any?): Boolean {
        if (o == null || javaClass != o.javaClass) return false
        val quantity = o as Quantity
        return value == quantity.value
    }

    override fun hashCode(): Int = Objects.hashCode(value)

    companion object {
        fun of(value: Int): Quantity {
            require(value >= 0) { "수량은 0 이상이어야 합니다." }
            return Quantity(value)
        }
    }
}
