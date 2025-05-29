package com.nmh.commerce.config

import com.github.benmanes.caffeine.cache.Caffeine
import com.nmh.commerce.cache.CacheSpec
import org.ehcache.jsr107.EhcacheCachingProvider
import org.slf4j.LoggerFactory
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.cache.jcache.JCacheCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import java.time.Duration
import java.util.concurrent.TimeUnit
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
        val jCacheManager = provider.cacheManager

        CacheSpec.entries.forEach { spec ->
            val config = createCacheConfig(spec.cacheName, spec.ttl)
            if (jCacheManager.cacheNames.contains(spec.cacheName)) {
                logger.warn("캐시가 이미 존재합니다: ${spec.cacheName}, 기존 캐시를 재사용합니다.")
                return@forEach
            }
            jCacheManager.createCache(spec.cacheName, config)
        }

        logger.info("캐시 설정이 초기화되었습니다: ${jCacheManager.cacheNames}")

        return JCacheCacheManager(jCacheManager)
    }

    private fun createCacheConfig(cacheName: String, ttl: Duration) = MutableConfiguration<Any, Any>().apply {
        setTypes(Any::class.java, Any::class.java)
        setStoreByValue(false)
        setStatisticsEnabled(true)
        setExpiryPolicyFactory(
            CreatedExpiryPolicy.factoryOf(
                javax.cache.expiry.Duration(
                    TimeUnit.SECONDS,
                    ttl.seconds,
                ),
            ),
        )
        logger.info("캐시 설정 완료 캐시이름 : $cacheName with TTL: $ttl")
    }

    @Primary
    @Bean
    fun caffeineCacheManager(): CacheManager = CaffeineCacheManager().apply {
        setCaffeine(Caffeine.newBuilder().recordStats())
        CacheSpec.entries.forEach { spec ->
            registerCustomCache(
                spec.cacheName,
                Caffeine.newBuilder()
                    .recordStats()
                    .expireAfterWrite(spec.ttl)
                    .build(),
            )
            logger.info("Caffeine 캐시 설정 완료: ${spec.cacheName} with TTL: ${spec.ttl}")
        }
    }
}
