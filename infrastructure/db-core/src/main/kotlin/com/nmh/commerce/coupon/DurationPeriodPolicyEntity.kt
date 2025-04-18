package com.nmh.commerce.coupon

import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import java.time.Duration

@DiscriminatorValue("DURATION")
@Entity
class DurationPeriodPolicyEntity private constructor(coupon: CouponEntity, private val duration: Duration) :
    ExpirationPeriodPolicyEntity(coupon) {
    override fun toDomain(): ExpirationPeriodPolicy {
        return DurationPeriodPolicy(duration)
    }

    companion object {
        @JvmStatic
        fun from(durationPeriodPolicy: DurationPeriodPolicy, coupon: CouponEntity): DurationPeriodPolicyEntity {
            return DurationPeriodPolicyEntity(coupon, durationPeriodPolicy.duration)
        }
    }
}
