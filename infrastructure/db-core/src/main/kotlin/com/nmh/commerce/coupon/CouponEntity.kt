package com.nmh.commerce.coupon

import com.nmh.commerce.BaseEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany

@Entity
class CouponEntity(
    override val id: Long = 0,
    var name: String,
) : BaseEntity<Long>() {
    @OneToMany(mappedBy = "coupon", cascade = [CascadeType.ALL])
    private val discountPolicies: MutableList<DiscountPolicyEntity> = ArrayList()

    @OneToMany(mappedBy = "coupon", cascade = [CascadeType.ALL])
    private val constraintPolicies: MutableList<ConstraintPolicyEntity> = ArrayList()

    @OneToMany(mappedBy = "coupon", cascade = [CascadeType.ALL])
    private val expirationPeriodPolicies: MutableList<ExpirationPeriodPolicyEntity> = ArrayList()

    fun addDiscountPolicy(discountPolicy: DiscountPolicyEntity) {
        discountPolicies.add(discountPolicy)
    }

    fun addConstraintPolicy(constraintPolicy: ConstraintPolicyEntity) {
        constraintPolicies.add(constraintPolicy)
    }

    fun addExpirationPeriodPolicy(expirationPeriodPolicy: ExpirationPeriodPolicyEntity) {
        expirationPeriodPolicies.add(expirationPeriodPolicy)
    }

    fun toDomain(): Coupon =
        Coupon(
            id,
            name,
            discountPolicies.stream().map { obj: DiscountPolicyEntity -> obj.toDomain() }.toList(),
            constraintPolicies
                .stream()
                .map { obj: ConstraintPolicyEntity -> obj.toDomain() }
                .toList(),
            expirationPeriodPolicies
                .stream()
                .map { obj: ExpirationPeriodPolicyEntity -> obj.toDomain() }
                .toList(),
        )

    companion object {
        fun from(coupon: Coupon): CouponEntity {
            val couponEntity =
                CouponEntity(
                    coupon.id,
                    coupon.name,
                )
            addDiscountPolicies(coupon, couponEntity)
            addConstraintPolicies(coupon, couponEntity)
            addExpirationPeriodPolicies(coupon, couponEntity)

            return couponEntity
        }

        private fun addExpirationPeriodPolicies(
            coupon: Coupon,
            couponEntity: CouponEntity,
        ) {
            couponEntity.expirationPeriodPolicies.addAll(
                coupon.expirationPeriodPolicies
                    .stream()
                    .map { policy: ExpirationPeriodPolicy ->
                        ExpirationPeriodPolicyEntity.from(
                            policy,
                            couponEntity,
                        )
                    }.toList(),
            )
        }

        private fun addDiscountPolicies(
            coupon: Coupon,
            couponEntity: CouponEntity,
        ) {
            couponEntity.discountPolicies.addAll(
                coupon.discountPolicies
                    .stream()
                    .map { policy: DiscountPolicy ->
                        DiscountPolicyEntity.from(
                            policy,
                            couponEntity,
                        )
                    }.toList(),
            )
        }

        private fun addConstraintPolicies(
            coupon: Coupon,
            couponEntity: CouponEntity,
        ) {
            couponEntity.constraintPolicies.addAll(
                coupon.constraintPolicies
                    .stream()
                    .map { policy: ConstraintPolicy ->
                        ConstraintPolicyEntity.from(
                            policy,
                            couponEntity,
                        )
                    }.toList(),
            )
        }
    }
}
