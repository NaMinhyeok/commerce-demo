package com.nmh.commerce.coupon

import com.nmh.commerce.domain.Money
import com.nmh.commerce.utils.MoneyConverter
import jakarta.persistence.Convert
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity

@DiscriminatorValue("MinimumPriceConstraintPolicy")
@Entity
class MinimumPriceConstraintPolicyEntity private constructor(
    coupon: CouponEntity,
    @field:Convert(converter = MoneyConverter::class)
    private val minimumPrice: Money,
) : ConstraintPolicyEntity(coupon = coupon) {
    override fun toDomain(): ConstraintPolicy = MinimumPriceConstraintPolicy(minimumPrice)

    companion object {
        fun from(
            minimumPriceConstraintPolicy: MinimumPriceConstraintPolicy,
            coupon: CouponEntity,
        ): MinimumPriceConstraintPolicyEntity =
            MinimumPriceConstraintPolicyEntity(coupon, minimumPriceConstraintPolicy.minimumPrice)
    }
}
