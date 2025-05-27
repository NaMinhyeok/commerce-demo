package com.nmh.commerce.cache

import java.time.Duration

enum class CacheSpec(
    val cacheName: String,
    val ttl: Duration,
) {
    USER(CacheName.USER, Duration.ofHours(1)),
}
