package com.nmh.commerce.coupon

import com.nmh.commerce.BaseEntity
import com.nmh.commerce.user.UserEntity
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne
import java.time.LocalDateTime

@Entity
class IssuedCouponEntity private constructor(
    override val id: Long = 0,
    @field:ManyToOne val coupon: CouponEntity,
    @field:ManyToOne val user: UserEntity,
    val issuedAt: LocalDateTime,
) : BaseEntity<Long>() {
    fun toDomain(): IssuedCoupon = IssuedCoupon(id, coupon.toDomain(), user.toDomain(), issuedAt)

    companion object {
        fun from(issuedCoupon: IssuedCoupon): IssuedCouponEntity =
            IssuedCouponEntity(
                issuedCoupon.id,
                CouponEntity.from(issuedCoupon.coupon),
                UserEntity.from(issuedCoupon.user),
                issuedCoupon.issuedAt,
            )
    }
}
