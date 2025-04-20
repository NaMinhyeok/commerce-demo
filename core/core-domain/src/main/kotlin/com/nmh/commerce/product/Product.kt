package com.nmh.commerce.product

import com.nmh.commerce.domain.Money

data class Product(
    private val id: Long = 0,
    private val name: String,
    val price: Money
) {
    companion object {
        fun of(id: Long, name: String, price: Money): Product {
            return Product(id, name, price)
        }
    }
}
