package com.nmh.commerce.coupon

import com.nmh.commerce.BaseEntity
import jakarta.persistence.*

@DiscriminatorColumn(name = "expiration_period_policy_type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Entity
abstract class ExpirationPeriodPolicyEntity protected constructor(
    override val id: Long = 0,
    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private val coupon: CouponEntity
) : BaseEntity<Long>() {

    init {
        coupon.addExpirationPeriodPolicy(this)
    }

    abstract fun toDomain(): ExpirationPeriodPolicy

    companion object {
        fun from(policy: ExpirationPeriodPolicy, coupon: CouponEntity): ExpirationPeriodPolicyEntity {
            if (policy is DeadlinePeriodPolicy) {
                return DeadlinePeriodPolicyEntity.from(policy, coupon)
            }
            if (policy is DurationPeriodPolicy) {
                return DurationPeriodPolicyEntity.from(policy, coupon)
            }
            throw IllegalArgumentException("지원하지 않는 만료 기간 정책입니다: " + policy.javaClass)
        }
    }
}
