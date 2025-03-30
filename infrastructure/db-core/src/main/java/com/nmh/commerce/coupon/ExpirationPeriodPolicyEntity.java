package com.nmh.commerce.coupon;

import com.nmh.commerce.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorColumn(name = "expiration_period_policy_type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Entity
public abstract class ExpirationPeriodPolicyEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private CouponEntity coupon;

    protected ExpirationPeriodPolicyEntity(CouponEntity coupon) {
        this.coupon = coupon;
        coupon.addExpirationPeriodPolicy(this);
    }

    public static ExpirationPeriodPolicyEntity from(ExpirationPeriodPolicy policy) {
        if (policy instanceof DeadLinePeriodPolicy deadLinePeriodPolicy) {
            return DeadLinePeriodPolicyEntity.from(deadLinePeriodPolicy);
        }
        if (policy instanceof DurationPeriodPolicy durationPeriodPolicy) {
            return DurationPeriodPolicyEntity.from(durationPeriodPolicy);
        }
        throw new IllegalArgumentException("지원하지 않는 만료 기간 정책입니다: " + policy.getClass());
    }

    public abstract ExpirationPeriodPolicy toDomain();
}
