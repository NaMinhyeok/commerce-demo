package com.nmh.commerce.product

import com.nmh.commerce.domain.Money

class Product(
    private val id: Long?,
    private val name: String,
    val price: Money
) {
    companion object {
        fun of(id: Long?, name: String, price: Money): Product {
            return Product(id, name, price)
        }
    }
}
