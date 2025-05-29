package com.nmh.commerce.learning

import com.github.benmanes.caffeine.cache.Caffeine
import org.assertj.core.api.BDDAssertions.then
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.Duration

class CaffeineCacheTest {

    @Test
    fun `TTL을 설정한다`() {
        val ttlCache = Caffeine.newBuilder()
            .expireAfterWrite(Duration.ofSeconds(5))
            .recordStats()
            .build<Any, Any>()

        ttlCache.put("key", "value")

        Thread.sleep(Duration.ofSeconds(6))

        val value = ttlCache.getIfPresent("key")
        then(value).isNull()
    }

    @Test
    fun `TTL은 생성이후 일정 시간이 지나면 만료된다`() {
        Caffeine.newBuilder()
            .expireAfterWrite(Duration.ofSeconds(5))
            .recordStats()
            .build<Any, Any>().apply {
                put("key", "value")

                Thread.sleep(Duration.ofSeconds(6))
                val expiredValue = getIfPresent("key")
                then(expiredValue).isNull()
            }
    }

    @Test
    fun `TTI를 설정한다 TTI는 접근이후 시간이다`() {
        Caffeine.newBuilder()
            .expireAfterAccess(Duration.ofSeconds(5))
            .recordStats()
            .build<Any, Any>().apply {
                put("key", "value")

                Thread.sleep(Duration.ofSeconds(6))
                val expiredValue = getIfPresent("key")
                then(expiredValue).isNull()
            }
    }

    @Test
    fun `TTI는 설정과 동시에 캐시가 생성된다`() {
        Caffeine.newBuilder()
            .expireAfterAccess(Duration.ofSeconds(5))
            .recordStats()
            .build<Any, Any>().apply {
                put("key", "value")

                val value = getIfPresent("key")
                then(value).isEqualTo("value")
            }
    }

    @Test
    fun `TTI는 접근할때마다 만료시간을 갱신한다`() {
        Caffeine.newBuilder()
            .expireAfterAccess(Duration.ofSeconds(5))
            .recordStats()
            .build<Any, Any>().apply {
                put("key", "value")

                Thread.sleep(Duration.ofSeconds(2))
                val valueBeforeAccess = getIfPresent("key")
                then(valueBeforeAccess).isEqualTo("value")

                Thread.sleep(Duration.ofSeconds(4))
                val valueAfterAccess = getIfPresent("key")
                then(valueAfterAccess).isEqualTo("value")
            }
    }

    @Test
    fun `TTL과 TTI를 동시에 설정할 수 있다`() {
        Caffeine.newBuilder()
            .expireAfterWrite(Duration.ofSeconds(10))
            .expireAfterAccess(Duration.ofSeconds(5))
            .recordStats()
            .build<Any, Any>().apply {
                put("key", "value")

                Thread.sleep(Duration.ofSeconds(3))
                val valueAfter3Seconds = getIfPresent("key")
                then(valueAfter3Seconds).isEqualTo("value")
            }
    }

    @Nested
    inner class `TTL과 TTI가 동시에 설정된 경우 우선 만족되는 조건에 따라 만료된다` {
        @Test
        fun `TTL의 만료기준에 먼저 만족되는 경우 TTI에 상관없이 만료된다`() {
            Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofSeconds(10))
                .expireAfterAccess(Duration.ofSeconds(5))
                .recordStats()
                .build<Any, Any>().apply {
                    put("key", "value")

                    Thread.sleep(Duration.ofSeconds(3))
                    val valueAfter3Seconds = getIfPresent("key")
                    then(valueAfter3Seconds).isEqualTo("value")

                    Thread.sleep(Duration.ofSeconds(8))
                    val valueAfter11Seconds = getIfPresent("key")
                    then(valueAfter11Seconds).isNull()
                }
        }

        @Test
        fun `TTI의 만료기준에 먼저 만족되는 경우 TTL에 상관없이 만료된다`() {
            Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofSeconds(10))
                .expireAfterAccess(Duration.ofSeconds(5))
                .recordStats()
                .build<Any, Any>().apply {
                    put("key", "value")

                    Thread.sleep(Duration.ofSeconds(6))
                    val valueAfter6Seconds = getIfPresent("key")
                    then(valueAfter6Seconds).isNull()
                }
        }
    }

    @Test
    fun `캐시의 최대 용량을 제한 할 수 있다`() {
        val cache = Caffeine.newBuilder()
            .maximumSize(2)
            .recordStats()
            .build<Any, Any>().apply {
                put("key1", "value1")
                put("key2", "value2")
                put("key3", "value3")
            }

        val cacheAllMaps = cache.getAllPresent(listOf("key1", "key2", "key3"))
        then(cacheAllMaps.size).isLessThanOrEqualTo(2)
        println("현재 캐시에 남아있는 항목: $cacheAllMaps")
    }

    @Test
    fun `weight를 제한하여 가변크기에 대해 제한 할 수 있다`() {
        val cache = Caffeine.newBuilder()
            .maximumWeight(10)
            .weigher { key: String, value: String -> value.length }
            .build<String, String>().apply {
                put("key1", "value1")
                put("key2", "value2")
                put("key3", "value3")
            }

        val allPresent = cache.getAllPresent(listOf("key1", "key2", "key3"))

        println("현재 캐시에 남아있는 항목: $allPresent")

        then(allPresent.size).isLessThan(3)
        then(allPresent.values.sumOf { it.length }).isLessThanOrEqualTo(10)
    }
}
