package com.nmh.commerce.coupon

import com.nmh.commerce.BaseEntity
import jakarta.persistence.*
import lombok.AccessLevel
import lombok.NoArgsConstructor

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "discount_policy_type")
abstract class DiscountPolicyEntity protected constructor(coupon: CouponEntity) : BaseEntity() {
    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private var coupon: CouponEntity?

    init {
        this.coupon = coupon
        coupon.addDiscountPolicy(this)
    }

    abstract fun toDomain(): DiscountPolicy?

    companion object {
        fun from(policy: DiscountPolicy, coupon: CouponEntity): DiscountPolicyEntity {
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
