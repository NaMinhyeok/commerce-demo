package com.nmh.commerce.coupon

import com.nmh.commerce.BaseEntity
import com.nmh.commerce.domain.Quantity
import com.nmh.commerce.utils.QuantityConverter
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.Version

@Entity
class CouponStockEntity private constructor(
    override val id: Long = 0,
    private val couponId: Long,
    @field:Convert(converter = QuantityConverter::class)
    private val remainingQuantity: Quantity,
    @field:Version
    private val version: Long?,
) : BaseEntity<Long>() {
    fun toDomain(): CouponStock = CouponStock(id, couponId, remainingQuantity, version)

    companion object {
        fun from(couponStock: CouponStock): CouponStockEntity =
            CouponStockEntity(
                couponStock.id,
                couponStock.couponId,
                couponStock.remainingQuantity,
                couponStock.version,
            )
    }
}
