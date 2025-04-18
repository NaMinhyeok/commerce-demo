package com.nmh.commerce.coupon

import java.time.Duration
import java.time.LocalDateTime

class DurationPeriodPolicy(@JvmField val duration: Duration) : ExpirationPeriodPolicy {
    override fun verify(issuedAt: LocalDateTime) {
        require(!LocalDateTime.now().isAfter(issuedAt.plus(duration))) { "쿠폰이 만료되었습니다." }
    }
}
