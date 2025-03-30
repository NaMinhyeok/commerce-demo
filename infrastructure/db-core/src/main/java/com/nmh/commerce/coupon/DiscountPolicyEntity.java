package com.nmh.commerce.coupon;

import com.nmh.commerce.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "discount_policy_type")
public abstract class DiscountPolicyEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private CouponEntity coupon;

    protected DiscountPolicyEntity(CouponEntity coupon) {
        this.coupon = coupon;
        coupon.addDiscountPolicy(this);
    }

    public static DiscountPolicyEntity from(DiscountPolicy policy) {
        if (policy instanceof FixedPriceDiscountPolicy fixed) {
            return FixedPriceDiscountPolicyEntity.from(fixed);
        }
        if (policy instanceof PercentageDiscountPolicy percentage) {
            return PercentageDiscountPolicyEntity.from(percentage);
        }
        throw new IllegalArgumentException("지원하지 않는 할인 정책입니다: " + policy.getClass());
    }

    public abstract DiscountPolicy toDomain();
}
