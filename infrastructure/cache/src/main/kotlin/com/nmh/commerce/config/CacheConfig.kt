package com.nmh.commerce.config

import com.nmh.commerce.cache.CacheSpec
import org.ehcache.jsr107.EhcacheCachingProvider
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Duration
import java.util.concurrent.TimeUnit
import javax.cache.CacheManager
import javax.cache.Caching
import javax.cache.configuration.MutableConfiguration
import javax.cache.expiry.CreatedExpiryPolicy

@Configuration
@EnableCaching
class CacheConfig {
    private val logger = LoggerFactory.getLogger(CacheConfig::class.java)

    @Bean
    fun ehCacheConfig(): CacheManager {
        val provider = Caching.getCachingProvider() as EhcacheCachingProvider
        val cacheManager = provider.cacheManager

        CacheSpec.entries.forEach { spec ->
            val config = createCacheConfig(spec.cacheName, spec.ttl)
            if (cacheManager.cacheNames.contains(spec.cacheName)) {
                logger.warn("캐시가 이미 존재합니다: ${spec.cacheName}, 기존 캐시를 재사용합니다.")
                return@forEach
            }
            cacheManager.createCache(spec.cacheName, config)
        }

        logger.info("캐시 설정이 초기화되었습니다: ${cacheManager.cacheNames}")

        return cacheManager
    }

    private fun createCacheConfig(cacheName: String, ttl: Duration) = MutableConfiguration<Any, Any>().apply {
        setTypes(Any::class.java, Any::class.java)
        setStoreByValue(false)
        setStatisticsEnabled(true)
        setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(javax.cache.expiry.Duration(TimeUnit.SECONDS, ttl.seconds)))
        logger.info("캐시 설정 완료 캐시이름 : $cacheName with TTL: $ttl")
    }
}
