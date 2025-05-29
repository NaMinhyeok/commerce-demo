package com.nmh.commerce.product

interface ProductRepository {
    fun findById(id: Long): Product
    fun save(product: Product): Product
    fun findAll(): List<Product>
    fun deleteById(id: Long)
}
