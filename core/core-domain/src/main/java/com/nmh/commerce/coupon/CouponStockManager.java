package com.nmh.commerce.coupon;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Builder
@RequiredArgsConstructor
@Component
public class CouponStockManager {
    private final CouponStockRepository stockRepository;

    public CouponStock deductStock(Long couponId) {
        CouponStock stock = stockRepository.findByCouponId(couponId)
            .orElseThrow(() -> new IllegalArgumentException("해당 쿠폰 재고 정보를 찾을 수 없습니다."));
        return stockRepository.save(stock.deductQuantity());
    }
}
