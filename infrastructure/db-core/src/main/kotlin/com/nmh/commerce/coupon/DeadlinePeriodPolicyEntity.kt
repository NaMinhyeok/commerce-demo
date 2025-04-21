package com.nmh.commerce.coupon

import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import java.time.LocalDateTime

@DiscriminatorValue("DEADLINE")
@Entity
class DeadlinePeriodPolicyEntity private constructor(
    coupon: CouponEntity,
    private val startAt: LocalDateTime,
    private val endAt: LocalDateTime,
) : ExpirationPeriodPolicyEntity(coupon = coupon) {
    override fun toDomain(): ExpirationPeriodPolicy = DeadlinePeriodPolicy(startAt, endAt)

    companion object {
        fun from(
            deadLinePeriodPolicy: DeadlinePeriodPolicy,
            coupon: CouponEntity,
        ): DeadlinePeriodPolicyEntity =
            DeadlinePeriodPolicyEntity(coupon, deadLinePeriodPolicy.startAt, deadLinePeriodPolicy.endAt)
    }
}
