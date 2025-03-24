package com.nmh.commerce.coupon;

import com.nmh.commerce.product.Product;

import java.time.LocalDateTime;

public class UseDateConstraintPolicy implements CouponItselfPolicy {
    private final LocalDateTime startAt;
    private final LocalDateTime endAt;

    public UseDateConstraintPolicy(LocalDateTime startAt, LocalDateTime endAt) {
        this.startAt = startAt;
        this.endAt = endAt;
    }

    @Override
    public void verify() {
        if (LocalDateTime.now().isBefore(startAt) || LocalDateTime.now().isAfter(endAt)) {
            throw new IllegalArgumentException("쿠폰 사용 기한이 아닙니다.");
        }
    }
}
