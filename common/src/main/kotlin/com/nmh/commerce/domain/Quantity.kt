package com.nmh.commerce.domain

import java.util.*

class Quantity private constructor(@JvmField val value: Int) {
    fun add(quantity: Quantity): Quantity {
        return Quantity(this.value + quantity.value)
    }

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

    override fun hashCode(): Int {
        return Objects.hashCode(value)
    }

    companion object {
        @JvmStatic
        fun of(value: Int): Quantity {
            require(value >= 0) { "수량은 0 이상이어야 합니다." }
            return Quantity(value)
        }
    }
}
