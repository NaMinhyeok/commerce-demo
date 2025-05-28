package com.nmh.commerce.config

import com.nmh.commerce.CoreCacheContextTest
import com.nmh.commerce.cache.CacheSpec
import org.assertj.core.api.BDDAssertions.then
import org.junit.jupiter.api.Test
import org.springframework.cache.CacheManager

class CacheConfigTest(
    private val cacheManager: CacheManager,
) : CoreCacheContextTest() {

    @Test
    fun `모든 CacheSpec의 캐시는 등록되어야 한다`() {
        val registeredCaches = cacheManager.cacheNames.toSet()
        val expectedCaches = CacheSpec.entries.map { it.cacheName }.toSet()

        then(registeredCaches).isEqualTo(expectedCaches)
    }
}
