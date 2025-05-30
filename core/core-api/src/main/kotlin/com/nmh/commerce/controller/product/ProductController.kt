package com.nmh.commerce.controller.product

import com.nmh.commerce.product.Product
import com.nmh.commerce.product.ProductService
import com.nmh.commerce.support.response.ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductController(
    val productService: ProductService,
) {
    @GetMapping("/products/{id}")
    fun getProduct(@PathVariable id: Long): ApiResponse<Product> {
        val product = productService.getProduct(id)
        return ApiResponse.success(product)
    }
}
