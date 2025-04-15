package com.nmh.commerce;

import com.nmh.commerce.coupon.*;
import com.nmh.commerce.domain.Quantity;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.BDDAssertions.then;

public class StockConcurrencyTest extends CoreDbContextTest {
    private final CouponStockRepository couponStockRepository;
    private final CouponStockManager couponStockManager;

    public StockConcurrencyTest(CouponStockRepository couponStockRepository, CouponStockManager couponStockManager) {
        this.couponStockRepository = couponStockRepository;
        this.couponStockManager = couponStockManager;
    }

    @Test
    void 동시에_100개의_쿠폰의_재고를_감소시킨다() throws InterruptedException {
        // given
        CouponStock savedStock = couponStockRepository.save(CouponStock.of(1L, Quantity.of(100)));

        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);

        CountDownLatch latch = new CountDownLatch(threadCount);
        // when
        for (int i = 0; i < threadCount; i++) {
            executorService.execute(() -> {
                try {
                    couponStockManager.deductStock(savedStock.getCouponId());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        CouponStock stock = couponStockRepository.findByCouponId(savedStock.getCouponId()).orElseThrow();
        // then
        then(stock.getRemainingQuantity().getValue()).isEqualTo(0);
    }
}
