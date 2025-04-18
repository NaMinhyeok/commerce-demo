package com.nmh.commerce.coupon;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Builder
@RequiredArgsConstructor
@Component
public class CouponStockManager {
    private final CouponStockRepository stockRepository;

    @Retryable(
        retryFor = {IllegalStateException.class},
        maxAttempts = 5,
        backoff = @Backoff(delay = 10000L)
    )
    @Transactional
    public CouponStock deductStock(Long couponId) {
        CouponStock stock = stockRepository.findByCouponId(couponId)
            .orElseThrow(() -> new IllegalArgumentException("해당 쿠폰 재고 정보를 찾을 수 없습니다."));
        log.info("시도: couponId={}, version={}", couponId, stock.version);
        CouponStock updatedStock = stock.deductQuantity();
        return stockRepository.update(updatedStock);
    }

    @Recover
    public CouponStock recover(IllegalStateException e, Long couponId) {
        log.error("쿠폰 재고 차감에 모든 재시도가 실패했습니다. couponId={}, error={}", couponId, e.getMessage());
        throw e;
    }
}
