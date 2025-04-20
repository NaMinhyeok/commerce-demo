package com.nmh.commerce.coupon

import com.nmh.commerce.BaseEntity
import jakarta.persistence.*

@Entity
@DiscriminatorColumn(name = "constraint_policy_type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
abstract class ConstraintPolicyEntity protected constructor(
    override val id: Long = 0,
    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private val coupon: CouponEntity
) : BaseEntity<Long>() {

    init {
        coupon.addConstraintPolicy(this)
    }

    abstract fun toDomain(): ConstraintPolicy

    companion object {
        fun from(policy: ConstraintPolicy, coupon: CouponEntity): ConstraintPolicyEntity {
            if (policy is MinimumPriceConstraintPolicy) {
                return MinimumPriceConstraintPolicyEntity.from(policy, coupon)
            }
            throw IllegalArgumentException("지원하지 않는 제약 정책입니다: " + policy.javaClass)
        }
    }
}
