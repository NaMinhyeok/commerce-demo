package com.nmh.commerce.config

import feign.RequestInterceptor
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.openfeign.EnableFeignClients

@EnableFeignClients
class FeignConfig {

    fun requestInterceptor(
        @Value("\${exchange.host.access-key}") accessKey: String,
    ): RequestInterceptor = RequestInterceptor { requestTemplate ->
        requestTemplate.query("access_key", accessKey)
    }
}
