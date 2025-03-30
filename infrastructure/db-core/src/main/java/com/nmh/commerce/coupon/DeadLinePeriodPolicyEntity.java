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

    private DeadLinePeriodPolicyEntity(CouponEntity coupon, LocalDateTime startAt, LocalDateTime endAt) {
        super(coupon);
        this.startAt = startAt;
        this.endAt = endAt;
    }

    public static DeadLinePeriodPolicyEntity from(DeadLinePeriodPolicy deadLinePeriodPolicy, CouponEntity coupon) {
        return new DeadLinePeriodPolicyEntity(coupon, deadLinePeriodPolicy.getStartAt(), deadLinePeriodPolicy.getEndAt());
    }

    @Override
    public ExpirationPeriodPolicy toDomain() {
        return new DeadLinePeriodPolicy(startAt, endAt);
    }
}
