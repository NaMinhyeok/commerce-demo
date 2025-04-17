package com.nmh.commerce.coupon;

import com.nmh.commerce.coupon.mock.FakeCouponStockRepository;
import com.nmh.commerce.domain.Quantity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.BDDAssertions.then;

class CouponStockManagerTest {

    private CouponStockManager couponStockManager;
    private CouponStockRepository stockRepository;

    @BeforeEach
    void setUp() {
        FakeCouponStockRepository fakeCouponStockRepository = new FakeCouponStockRepository();
        this.stockRepository = fakeCouponStockRepository;
        this.couponStockManager = CouponStockManager.builder()
            .stockRepository(fakeCouponStockRepository)
            .build();
        CouponStock stock1 = CouponStock.builder()
            .id(1L)
            .couponId(1L)
            .remainingQuantity(Quantity.of(1))
            .build();
        CouponStock stock2 = CouponStock.builder()
            .id(2L)
            .couponId(2L)
            .remainingQuantity(Quantity.of(100))
            .build();
        fakeCouponStockRepository.save(stock1);
        fakeCouponStockRepository.save(stock2);
    }

    @Test
    void 쿠폰의_재고를_줄인다() {
        // given
        // when
        CouponStock deductedCouponStock = couponStockManager.deductStock(1L);
        // then
        then(deductedCouponStock.getRemainingQuantity()).isEqualTo(Quantity.of(0));
    }

    @Test
    void 동시에_100개의_쿠폰의_재고를_감소시킨다() throws InterruptedException {
        // given
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);

        CountDownLatch latch = new CountDownLatch(threadCount);
        // when
        for(int i =0; i< threadCount; i++) {
            executorService.execute(() -> {
                try {
                    couponStockManager.deductStock(2L);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        CouponStock stock = stockRepository.findByCouponId(2L).orElseThrow();
        // then
        then(stock.getRemainingQuantity().value).isEqualTo(0);
    }

}