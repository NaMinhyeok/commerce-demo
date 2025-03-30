package com.nmh.commerce.coupon;

import lombok.Getter;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
public class DurationPeriodPolicy implements ExpirationPeriodPolicy {
    private final Duration duration;

    public DurationPeriodPolicy(Duration duration) {
        this.duration = duration;
    }

    @Override
    public void verify(LocalDateTime issuedAt) {
        if (LocalDateTime.now().isAfter(issuedAt.plus(duration))) {
            throw new IllegalArgumentException("쿠폰이 만료되었습니다.");
        }
    }
}
