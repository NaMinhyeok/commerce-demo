package com.nmh.commerce.coupon;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class ExpirationPeriodEmbeddable {
    private LocalDateTime startAt;
    private LocalDateTime endAt;

    private ExpirationPeriodEmbeddable(LocalDateTime startAt, LocalDateTime endAt) {
        this.startAt = startAt;
        this.endAt = endAt;
    }

    public static ExpirationPeriodEmbeddable from(ExpirationPeriod expirationPeriod) {
        return new ExpirationPeriodEmbeddable(expirationPeriod.getStartAt(), expirationPeriod.getEndAt());
    }

    public ExpirationPeriod toDomain() {
        return new ExpirationPeriod(startAt, endAt);
    }
}
