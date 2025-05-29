package com.nmh.commerce.product

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class ProductRepositoryImpl(
    private val productJpaRepository: ProductJpaRepository,
) : ProductRepository {
    override fun findById(id: Long): Product = productJpaRepository.findByIdOrNull(id)?.toDomain() ?: throw NoSuchElementException("Product not found by $id")

    override fun save(product: Product): Product = productJpaRepository.save(ProductEntity.from(product)).toDomain()

    override fun findAll(): List<Product> = productJpaRepository.findAll().map { it.toDomain() }

    override fun deleteById(id: Long) {
        productJpaRepository.deleteById(id)
    }
}
