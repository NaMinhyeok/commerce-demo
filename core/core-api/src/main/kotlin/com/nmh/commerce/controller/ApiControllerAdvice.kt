package com.nmh.commerce.controller

import com.nmh.commerce.support.error.CustomException
import com.nmh.commerce.support.error.ErrorType
import com.nmh.commerce.support.response.ApiResponse
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController

@RestController
class ApiControllerAdvice {
    @ExceptionHandler(CustomException::class)
    fun handleCoreException(e: CustomException): ResponseEntity<ApiResponse<*>?> {
        //        ApiControllerAdvice.log.info("CustomException : {}", e.message, e)
        return ResponseEntity<ApiResponse<*>?>(
            ApiResponse.error(e.errorType, e.data),
            HttpStatusCode.valueOf(e.errorType.status),
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ApiResponse<*>?> {
        //        ApiControllerAdvice.log.error("Exception : {}", e.message, e)
        return ResponseEntity<ApiResponse<*>?>(
            ApiResponse.error(ErrorType.DEFAULT_ERROR),
            HttpStatusCode.valueOf(
                ErrorType.DEFAULT_ERROR.status,
            ),
        )
    }
}
