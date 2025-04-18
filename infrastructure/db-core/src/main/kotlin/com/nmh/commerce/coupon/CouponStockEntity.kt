package com.nmh.commerce.coupon

import com.nmh.commerce.domain.Quantity
import jakarta.persistence.*

@Entity
class CouponStockEntity private constructor(
    @field:GeneratedValue(strategy = GenerationType.IDENTITY) @field:Id
    private val id: Long?,
    private val couponId: Long,
    private val remainingQuantity: Quantity,
    @field:Version private val version: Long?
) {
    fun toDomain(): CouponStock {
        return CouponStock(id, couponId, remainingQuantity, version)
    }

    companion object {
        fun from(couponStock: CouponStock): CouponStockEntity {
            return CouponStockEntity(
                couponStock.id,
                couponStock.couponId,
                couponStock.remainingQuantity,
                couponStock.version
            )
        }
    }
}
