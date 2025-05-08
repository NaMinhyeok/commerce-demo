package com.nmh.commerce.coupon

import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Recover
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class CouponStockManager(
    private val stockRepository: CouponStockRepository,
) {
    @Retryable(
        retryFor = [IllegalStateException::class],
        maxAttempts = 5,
        backoff = Backoff(delay = 10000L),
    )
    @Transactional
    fun deductStock(couponId: Long): CouponStock {
        val stock = stockRepository.findByCouponId(couponId)
        val updatedStock = stock.deductQuantity()
        return stockRepository.update(updatedStock)
    }

    @Recover
    fun recover(
        e: IllegalStateException,
        couponId: Long?,
    ): CouponStock? = throw e
}
