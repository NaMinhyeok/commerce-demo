package com.nmh.commerce.coupon;

import com.nmh.commerce.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorColumn(name = "constraint_policy_type")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class ConstraintPolicyEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private CouponEntity coupon;

    protected ConstraintPolicyEntity(CouponEntity coupon) {
        this.coupon = coupon;
        coupon.addConstraintPolicy(this);
    }

    public static ConstraintPolicyEntity from(ConstraintPolicy policy, CouponEntity coupon) {
        if (policy instanceof MinimumPriceConstraintPolicy minimumPriceConstraintPolicy) {
            return MinimumPriceConstraintPolicyEntity.from(minimumPriceConstraintPolicy, coupon);
        }
        throw new IllegalArgumentException("지원하지 않는 제약 정책입니다: " + policy.getClass());
    }

    public abstract ConstraintPolicy toDomain();
}
