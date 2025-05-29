package com.nmh.commerce.controller.product

import com.nmh.commerce.product.ProductService
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductController(
    private val productService: ProductService,
)
