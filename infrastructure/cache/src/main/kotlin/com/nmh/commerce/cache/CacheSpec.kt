package com.nmh.commerce.cache

import java.time.Duration

object CacheName {
    const val USER = "user"
}

enum class CacheSpec(
    val cacheName: String,
    val ttl: Duration,
) {
    USER(CacheName.USER, Duration.ofHours(1)),
}
