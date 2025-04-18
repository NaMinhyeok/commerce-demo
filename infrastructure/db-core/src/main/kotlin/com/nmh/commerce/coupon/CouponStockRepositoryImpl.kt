package com.nmh.commerce.coupon

import jakarta.persistence.OptimisticLockException
import org.hibernate.StaleObjectStateException
import org.springframework.stereotype.Repository

@Repository
class CouponStockRepositoryImpl(private val couponStockJpaRepository: CouponStockJpaRepository) :
    CouponStockRepository {
    override fun save(couponStock: CouponStock): CouponStock {
        return couponStockJpaRepository.save(CouponStockEntity.from(couponStock)).toDomain()
    }

    override fun findByCouponId(couponId: Long): CouponStock {
        return couponStockJpaRepository.findByCouponId(couponId)
            ?.toDomain()
            ?: throw NoSuchElementException("Coupon stock not found for couponId: $couponId")
    }

    override fun update(couponStock: CouponStock): CouponStock {
        try {
            return couponStockJpaRepository.save<CouponStockEntity>(CouponStockEntity.from(couponStock)).toDomain()
        } catch (e: OptimisticLockException) {
            throw IllegalStateException("Failed to update coupon stock", e)
        } catch (e: StaleObjectStateException) {
            throw IllegalStateException("Failed to update coupon stock", e)
        } catch (e: Exception) {
            throw RuntimeException("An unexpected error occurred", e)
        }
    }
}
