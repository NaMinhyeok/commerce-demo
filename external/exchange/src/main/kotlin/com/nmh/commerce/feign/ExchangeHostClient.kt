package com.nmh.commerce.feign

import com.nmh.commerce.config.FeignConfig
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "exchange-host", url = "\${exchange.host.url}", configuration = [FeignConfig::class])
interface ExchangeHostClient {

    @GetMapping("/live")
    fun getLive(
        @RequestParam("currencies") currencies: String,
    )
}
