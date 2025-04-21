package com.nmh.commerce.coupon

import com.nmh.commerce.CoreApplicationIntegrationTest
import com.nmh.commerce.domain.Quantity.Companion.of
import org.assertj.core.api.BDDAssertions
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

class CouponStockConcurrencyTest(
    private val couponStockRepository: CouponStockRepository,
    private val couponStockManager: CouponStockManager,
) : CoreApplicationIntegrationTest() {
    @Disabled("잠시 비활성화")
    @Test
    @Throws(InterruptedException::class)
    fun 동시에_100개의_쿠폰의_재고를_감소시킨다() {
        // given
        val couponStock = CouponStock(1L, 1L, of(10), null)
        val savedStock = couponStockRepository.save(couponStock)

        val threadCount = 10
        val executorService = Executors.newFixedThreadPool(2)

        val latch = CountDownLatch(threadCount)
        // when
        for (i in 0..<threadCount) {
            executorService.execute(
                Runnable {
                    try {
                        couponStockManager.deductStock(savedStock.couponId)
                    } finally {
                        latch.countDown()
                    }
                },
            )
        }

        latch.await()

        val stock: CouponStock = couponStockRepository.findByCouponId(savedStock.couponId)
        // then
        BDDAssertions.then(stock.remainingQuantity.value).isEqualTo(0)
    }
}
