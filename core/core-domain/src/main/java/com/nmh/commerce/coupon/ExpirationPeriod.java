package com.nmh.commerce.coupon;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ExpirationPeriod {
    private final LocalDateTime startAt;
    private final LocalDateTime endAt;

    public ExpirationPeriod(LocalDateTime startAt, LocalDateTime endAt) {
        this.startAt = startAt;
        this.endAt = endAt;
    }

    public boolean isValid() {
        return LocalDateTime.now().isAfter(startAt) && LocalDateTime.now().isBefore(endAt);
    }
}
