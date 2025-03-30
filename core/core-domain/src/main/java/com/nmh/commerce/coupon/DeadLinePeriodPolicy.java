package com.nmh.commerce.coupon;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class DeadLinePeriodPolicy implements ExpirationPeriodPolicy {
    private final LocalDateTime startAt;
    private final LocalDateTime endAt;

    public DeadLinePeriodPolicy(LocalDateTime startAt, LocalDateTime endAt) {
        this.startAt = startAt;
        this.endAt = endAt;
    }

    @Override
    public void verify(LocalDateTime now) {
        if (now.isBefore(startAt)) {
            throw new IllegalArgumentException("쿠폰이 아직 사용할 수 없습니다.");
        }
        if (now.isAfter(endAt)) {
            throw new IllegalArgumentException("쿠폰이 만료되었습니다.");
        }
    }
}
