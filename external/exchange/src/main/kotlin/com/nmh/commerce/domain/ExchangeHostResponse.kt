package com.nmh.commerce.domain

data class ExchangeHostResponse(
    val success: Boolean,
    val terms: String,
    val privacy: String,
    val timestamp: Long,
    val source: String,
    val quotes: Map<String, Double>,
)
