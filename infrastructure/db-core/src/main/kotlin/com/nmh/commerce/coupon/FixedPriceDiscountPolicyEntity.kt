package com.nmh.commerce.coupon

import com.nmh.commerce.domain.Money
import com.nmh.commerce.utils.MoneyConverter
import jakarta.persistence.Convert
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity

@Entity
@DiscriminatorValue("FixedPriceDiscountPolicy")
class FixedPriceDiscountPolicyEntity(
    coupon: CouponEntity,
    @field:Convert(converter = MoneyConverter::class)
    private val discountPrice: Money,
) : DiscountPolicyEntity(coupon = coupon) {
    override fun toDomain(): DiscountPolicy = FixedPriceDiscountPolicy(discountPrice)

    companion object {
        fun from(
            fixedPriceDiscountPolicy: FixedPriceDiscountPolicy,
            coupon: CouponEntity,
        ): FixedPriceDiscountPolicyEntity = FixedPriceDiscountPolicyEntity(coupon, fixedPriceDiscountPolicy.discountPrice)
    }
}
