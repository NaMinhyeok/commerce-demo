package com.nmh.commerce.coupon;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.time.Duration;

@DiscriminatorValue("DURATION")
@Entity
public class DurationPeriodPolicyEntity extends ExpirationPeriodPolicyEntity {

    private Duration duration;

    @Override
    public ExpirationPeriodPolicy toDomain() {
        return new DurationPeriodPolicy(duration);
    }
}
