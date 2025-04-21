package com.nmh.commerce.coupon

import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import java.time.Duration

@DiscriminatorValue("DURATION")
@Entity
class DurationPeriodPolicyEntity private constructor(
    coupon: CouponEntity,
    private val duration: Duration,
) : ExpirationPeriodPolicyEntity(coupon = coupon) {
    override fun toDomain(): ExpirationPeriodPolicy = DurationPeriodPolicy(duration)

    companion object {
        fun from(
            durationPeriodPolicy: DurationPeriodPolicy,
            coupon: CouponEntity,
        ): DurationPeriodPolicyEntity = DurationPeriodPolicyEntity(coupon, durationPeriodPolicy.duration)
    }
}
