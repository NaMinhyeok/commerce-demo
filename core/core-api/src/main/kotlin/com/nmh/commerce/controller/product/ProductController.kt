package com.nmh.commerce.controller.product

import com.nmh.commerce.controller.product.request.ProductRequest
import com.nmh.commerce.domain.Money
import com.nmh.commerce.product.Product
import com.nmh.commerce.product.ProductService
import com.nmh.commerce.support.response.ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

@RestController
class ProductController(
    val productService: ProductService,
) {
    @GetMapping("/products/{id}")
    fun getProduct(@PathVariable id: Long): ApiResponse<Product> {
        val product = productService.getProduct(id)
        return ApiResponse.success(product)
    }

    @PostMapping("/products")
    fun registerProduct(@RequestBody request: ProductRequest): ApiResponse<Any> {
        val product = Product.of(
            id = 0,
            name = request.name,
            price = Money.of(BigDecimal(request.price)),
        )
        productService.saveProduct(product)
        return ApiResponse.success()
    }
}
