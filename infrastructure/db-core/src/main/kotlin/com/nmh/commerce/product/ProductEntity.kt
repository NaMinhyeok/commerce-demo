package com.nmh.commerce.product

import com.nmh.commerce.BaseEntity
import com.nmh.commerce.domain.Money

class ProductEntity private constructor(
    override val id: Long = 0,
    val name: String,
    val price: Money,
) : BaseEntity<Long>() {
    fun toDomain(): Product = Product.of(id, name, price)

    companion object {
        fun from(product: Product): ProductEntity = ProductEntity(product.id, product.name, product.price)
    }
}
