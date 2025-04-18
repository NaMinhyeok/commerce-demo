package com.nmh.commerce.coupon;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Duration;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("DURATION")
@Entity
public class DurationPeriodPolicyEntity extends ExpirationPeriodPolicyEntity {

    private Duration duration;

    private DurationPeriodPolicyEntity(CouponEntity coupon, Duration duration) {
        super(coupon);
        this.duration = duration;
    }

    public static DurationPeriodPolicyEntity from(DurationPeriodPolicy durationPeriodPolicy, CouponEntity coupon) {
        return new DurationPeriodPolicyEntity(coupon, durationPeriodPolicy.duration);
    }

    @Override
    public ExpirationPeriodPolicy toDomain() {
        return new DurationPeriodPolicy(duration);
    }
}
