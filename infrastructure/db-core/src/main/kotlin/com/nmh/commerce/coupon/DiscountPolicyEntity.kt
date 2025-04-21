package com.nmh.commerce.coupon

import com.nmh.commerce.BaseEntity
import jakarta.persistence.DiscriminatorColumn
import jakarta.persistence.Entity
import jakarta.persistence.Inheritance
import jakarta.persistence.InheritanceType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "discount_policy_type")
abstract class DiscountPolicyEntity protected constructor(
    override val id: Long = 0,
    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private var coupon: CouponEntity,
) : BaseEntity<Long>() {
    init {
        coupon.addDiscountPolicy(this)
    }

    abstract fun toDomain(): DiscountPolicy

    companion object {
        fun from(
            policy: DiscountPolicy,
            coupon: CouponEntity,
        ): DiscountPolicyEntity {
            if (policy is FixedPriceDiscountPolicy) {
                return FixedPriceDiscountPolicyEntity.from(policy, coupon)
            }
            if (policy is PercentageDiscountPolicy) {
                return PercentageDiscountPolicyEntity.from(policy, coupon)
            }
            throw IllegalArgumentException("지원하지 않는 할인 정책입니다: " + policy.javaClass)
        }
    }
}
