package com.nmh.commerce.coupon;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@DiscriminatorValue("DEADLINE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class DeadLinePeriodPolicyEntity extends ExpirationPeriodPolicyEntity {
    private LocalDateTime startAt;
    private LocalDateTime endAt;

    @Override
    public ExpirationPeriodPolicy toDomain() {
        return new DeadLinePeriodPolicy(startAt, endAt);
    }
}
