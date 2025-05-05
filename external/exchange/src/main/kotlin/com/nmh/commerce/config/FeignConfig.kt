package com.nmh.commerce.config

import feign.RequestInterceptor
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@EnableFeignClients(basePackages = ["com.nmh.commerce.feign"])
@Configuration
class FeignConfig {

    @Bean
    fun requestInterceptor(
        @Value("\${exchange.host.access-key}") accessKey: String,
    ): RequestInterceptor = RequestInterceptor { requestTemplate ->
        requestTemplate.query("access_key", accessKey)
        requestTemplate.query("format", "1")
    }
}
