package com.nmh.commerce.coupon

import com.nmh.commerce.domain.DiscountRate
import com.nmh.commerce.utils.DiscountRateConverter
import jakarta.persistence.Convert
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity

@Entity
@DiscriminatorValue("PercentageDiscountPolicy")
class PercentageDiscountPolicyEntity(
    coupon: CouponEntity,
    @field:Convert(converter = DiscountRateConverter::class)
    private val discountRate: DiscountRate,
) : DiscountPolicyEntity(coupon = coupon) {
    override fun toDomain(): DiscountPolicy = PercentageDiscountPolicy(discountRate)

    companion object {
        fun from(
            percentageDiscountPolicy: PercentageDiscountPolicy,
            coupon: CouponEntity,
        ): PercentageDiscountPolicyEntity = PercentageDiscountPolicyEntity(coupon, percentageDiscountPolicy.discountRate)
    }
}
