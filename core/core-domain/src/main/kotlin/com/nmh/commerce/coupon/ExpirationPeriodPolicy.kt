package com.nmh.commerce.coupon

import java.time.LocalDateTime

interface ExpirationPeriodPolicy {
    fun verify(baseTime: LocalDateTime)
}
