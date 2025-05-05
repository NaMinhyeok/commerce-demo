package com.nmh.commerce.feign

import com.nmh.commerce.config.FeignConfig
import com.nmh.commerce.domain.ExchangeHostResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(name = "exchange-host", url = "\${exchange.host.url}", configuration = [FeignConfig::class])
interface ExchangeHostClient {

    @GetMapping("/live")
    fun getLive(): ExchangeHostResponse
}
