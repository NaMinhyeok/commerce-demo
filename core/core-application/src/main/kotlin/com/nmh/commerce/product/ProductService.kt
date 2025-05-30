package com.nmh.commerce.product

import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository,
) {
    fun getProduct(id: Long): Product = productRepository.findById(id)

    fun saveProduct(product: Product) {
        productRepository.save(product)
    }

    fun getAllProducts(): List<Product> = productRepository.findAll()

    fun deleteProductById(id: Long) {
        productRepository.deleteById(id)
    }
}
