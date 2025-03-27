package com.nmh.commerce.coupon;

import java.time.Duration;
import java.time.LocalDateTime;

public class DurationPeriodPolicy implements ExpirationPeriodPolicy {
    private final Duration duration;

    public DurationPeriodPolicy(Duration duration) {
        this.duration = duration;
    }

    @Override
    public void verify(LocalDateTime now) {
        if (now.isAfter(now.plus(duration))) {
            throw new IllegalArgumentException("쿠폰이 만료되었습니다.");
        }
    }
}
