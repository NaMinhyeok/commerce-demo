package com.nmh.commerce.coupon

import java.time.LocalDateTime

class DeadlinePeriodPolicy(@JvmField val startAt: LocalDateTime?, @JvmField val endAt: LocalDateTime?) : ExpirationPeriodPolicy {
    override fun verify(now: LocalDateTime) {
        require(!now.isBefore(startAt)) { "쿠폰이 아직 사용할 수 없습니다." }
        require(!now.isAfter(endAt)) { "쿠폰이 만료되었습니다." }
    }
}
