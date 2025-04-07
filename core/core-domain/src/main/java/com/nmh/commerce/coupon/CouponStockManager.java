package com.nmh.commerce.coupon;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Builder
@RequiredArgsConstructor
@Component
public class CouponStockManager {
    private final CouponStockRepository stockRepository;
    private final Map<Long, Lock> lockMap = new ConcurrentHashMap<>();

    public CouponStock deductStock(Long couponId) {
        Lock lock = lockMap.computeIfAbsent(couponId, id -> new ReentrantLock());
        lock.lock();
        try {
            CouponStock stock = stockRepository.findByCouponId(couponId)
                .orElseThrow(() -> new IllegalArgumentException("해당 쿠폰 재고 정보를 찾을 수 없습니다."));
            return stockRepository.save(stock.deductQuantity());
        } finally {
            lock.unlock();
        }
    }
}
