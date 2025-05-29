package com.nmh.commerce.product

import com.nmh.commerce.domain.Money

data class Product(
    val id: Long,
    val name: String,
    val price: Money,
) {
    companion object {
        fun of(
            id: Long,
            name: String,
            price: Money,
        ): Product = Product(id, name, price)
    }
}
