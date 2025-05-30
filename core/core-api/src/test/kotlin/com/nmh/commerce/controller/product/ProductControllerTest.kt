package com.nmh.commerce.controller.product

import com.nmh.commerce.controller.RestDocsTest
import com.nmh.commerce.controller.RestDocsUtils.requestPreprocessor
import com.nmh.commerce.controller.RestDocsUtils.responsePreprocessor
import com.nmh.commerce.controller.product.request.ProductRequest
import com.nmh.commerce.domain.Money
import com.nmh.commerce.product.Product
import com.nmh.commerce.product.ProductService
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.restassured.http.ContentType
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.requestFields
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.request.RequestDocumentation
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName

class ProductControllerTest : RestDocsTest() {
    private lateinit var controller: ProductController
    private lateinit var productService: ProductService

    @BeforeEach
    fun setUp() {
        productService = mockk()
        controller = ProductController(productService)
        mockMvc = mockController(controller)
    }

    @Test
    fun getProduct() {
        every { productService.getProduct(any()) } returns Product(1L, "상품 이름", Money.ZERO)

        given()
            .contentType(ContentType.JSON)
            .get("/products/{id}", 1L)
            .then()
            .status(HttpStatus.OK)
            .apply(
                document(
                    "getProduct",
                    requestPreprocessor(),
                    responsePreprocessor(),
                    RequestDocumentation.pathParameters(
                        parameterWithName("id").description("상품 ID"),
                    ),
                    responseFields(
                        fieldWithPath("result").type(JsonFieldType.STRING).description("ResultType"),
                        fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("상품 ID"),
                        fieldWithPath("data.name").type(JsonFieldType.STRING).description("상품 이름"),
                        fieldWithPath("data.price.value").type(JsonFieldType.NUMBER).description("상품 가격"),
                        fieldWithPath("error").type(JsonFieldType.NULL).ignored(),
                    ),
                ),
            )
    }

    @Test
    fun `상품을 등록한다`() {
        every { productService.saveProduct(any()) } just Runs

        given()
            .contentType(ContentType.JSON)
            .body(
                ProductRequest(
                    name = "상품 이름",
                    price = 10000,
                ),
            )
            .post("/products")
            .then()
            .status(HttpStatus.OK)
            .apply(
                document(
                    "registerProduct",
                    requestPreprocessor(),
                    responsePreprocessor(),
                    requestFields(
                        fieldWithPath("name").type(JsonFieldType.STRING).description("상품 이름"),
                        fieldWithPath("price").type(JsonFieldType.NUMBER).description("상품 가격"),
                    ),
                    responseFields(
                        fieldWithPath("result").type(JsonFieldType.STRING).description("ResultType"),
                        fieldWithPath("data").type(JsonFieldType.NULL).description("결과"),
                        fieldWithPath("error").type(JsonFieldType.NULL).ignored(),
                    ),
                ),
            )
    }
}
