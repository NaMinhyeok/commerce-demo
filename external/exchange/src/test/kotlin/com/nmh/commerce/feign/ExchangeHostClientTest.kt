package com.nmh.commerce.feign

import com.nmh.commerce.ExternalApplicationTest
import org.junit.jupiter.api.Test

class ExchangeHostClientTest(
    private val exchangeHostClient: ExchangeHostClient,
) : ExternalApplicationTest() {

    @Test
    fun `호출요청이 올바른지 확인한다`() {
        exchangeHostClient.getLive("USD")
            .apply {
                println("response = $this")
            }
    }
}
