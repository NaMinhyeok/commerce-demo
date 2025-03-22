package com.nmh.commerce.coupon;

import com.nmh.commerce.BaseEntity;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class CouponEntity extends BaseEntity {
    private String name;
    @Embedded
    private DiscountPolicyEmbeddable discountPolicy;
    @Embedded
    private ConstraintPolicyEmbeddable constraintPolicy;

    @Builder
    private CouponEntity(String name, DiscountPolicyEmbeddable discountPolicy, ConstraintPolicyEmbeddable constraintPolicy) {
        this.name = name;
        this.discountPolicy = discountPolicy;
        this.constraintPolicy = constraintPolicy;
    }

    public static CouponEntity from(Coupon coupon) {
        return CouponEntity.builder()
            .name(coupon.getName())
            .discountPolicy(DiscountPolicyEmbeddable.from(coupon.getDiscountPolicy()))
            .constraintPolicy(ConstraintPolicyEmbeddable.from(coupon.getConstraintPolicy()))
            .build();
    }

    public Coupon toDomain() {
        return Coupon.builder()
            .id(getId())
            .name(name)
            .discountPolicy(discountPolicy.toDomain())
            .constraintPolicy(constraintPolicy.toDomain())
            .build();
    }

}
