package com.nmh.commerce.coupon

import com.nmh.commerce.BaseEntity
import com.nmh.commerce.user.UserEntity
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne
import java.time.LocalDateTime

@Entity
class IssuedCouponEntity private constructor(
    @field:ManyToOne
    val coupon: CouponEntity,
    @field:ManyToOne
    val user: UserEntity,
    val issuedAt: LocalDateTime?
) : BaseEntity() {
    fun toDomain(): IssuedCoupon {
        return IssuedCoupon(id, coupon.toDomain(), user.toDomain(), issuedAt)
    }

    companion object {
        fun from(issuedCoupon: IssuedCoupon): IssuedCouponEntity {
            return IssuedCouponEntity(
                CouponEntity.from(issuedCoupon.coupon),
                UserEntity.from(issuedCoupon.user), issuedCoupon.issuedAt
            )
        }
    }
}
