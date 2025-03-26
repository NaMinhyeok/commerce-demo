package com.nmh.commerce.coupon;

import com.nmh.commerce.BaseEntity;
import jakarta.persistence.*;

@Entity
@DiscriminatorColumn(name = "constraint_policy_type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class ConstraintPolicyEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private CouponEntity coupon;

    public static ConstraintPolicyEntity from(ConstraintPolicy policy) {
        if (policy instanceof MinimumPriceConstraintPolicy minimumPriceConstraintPolicy) {
            return MinimumPriceConstraintPolicyEntity.from(minimumPriceConstraintPolicy);
        }
        throw new IllegalArgumentException("지원하지 않는 제약 정책입니다: " + policy.getClass());
    }

    public abstract ConstraintPolicy toDomain();
}
