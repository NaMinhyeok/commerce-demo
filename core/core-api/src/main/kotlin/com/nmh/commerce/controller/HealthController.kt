package com.nmh.commerce.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthController {
    fun health(): ResponseEntity<Any?> = ResponseEntity.status(HttpStatus.OK).build<Any?>()
}
