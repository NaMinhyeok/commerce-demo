package com.nmh.commerce.coupon

import com.nmh.commerce.coupon.mock.FakeCouponStockRepository
import com.nmh.commerce.domain.Quantity
import com.nmh.commerce.domain.Quantity.Companion.of
import org.assertj.core.api.BDDAssertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

internal class CouponStockManagerTest {
    private lateinit var couponStockManager: CouponStockManager
    private lateinit var stockRepository: CouponStockRepository

    @BeforeEach
    fun setUp() {
        val fakeCouponStockRepository = FakeCouponStockRepository()
        this.stockRepository = fakeCouponStockRepository
        val couponStockManager = CouponStockManager(fakeCouponStockRepository)
        this.couponStockManager = couponStockManager
        val stock1 = CouponStock(1L, 1L, of(1), null)
        val stock2 = CouponStock(2L, 2L, of(100), null)
        fakeCouponStockRepository.save(stock1)
        fakeCouponStockRepository.save(stock2)
    }

    @Test
    fun 쿠폰의_재고를_줄인다() {
        // given
        // when
        val deductedCouponStock = couponStockManager.deductStock(1L)
        // then
        BDDAssertions.then<Quantity?>(deductedCouponStock.remainingQuantity).isEqualTo(of(0))
    }

    @Test
    @Throws(InterruptedException::class)
    fun 동시에_100개의_쿠폰의_재고를_감소시킨다() {
        // given
        val threadCount = 100
        val executorService = Executors.newFixedThreadPool(32)

        val latch = CountDownLatch(threadCount)
        // when
        for (i in 0..<threadCount) {
            executorService.execute(Runnable {
                try {
                    couponStockManager.deductStock(2L)
                } finally {
                    latch.countDown()
                }
            })
        }

        latch.await()

        val stock: CouponStock = stockRepository.findByCouponId(2L)
        // then
        BDDAssertions.then(stock.remainingQuantity.value).isEqualTo(0)
    }
}