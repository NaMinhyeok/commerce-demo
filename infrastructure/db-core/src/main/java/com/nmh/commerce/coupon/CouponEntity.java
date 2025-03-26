package com.nmh.commerce.coupon;

import com.nmh.commerce.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class CouponEntity extends BaseEntity {

    private String name;

    @OneToMany(mappedBy = "coupon", cascade = CascadeType.ALL)
    private List<DiscountPolicyEntity> discountPolicies = new ArrayList<>();

    @OneToMany(mappedBy = "coupon", cascade = CascadeType.ALL)
    private List<ConstraintPolicyEntity> constraintPolicies = new ArrayList<>();

    @Embedded
    private ExpirationPeriodEmbeddable expirationPeriod;

    @Builder
    private CouponEntity(String name, List<DiscountPolicyEntity> discountPolicies, List<ConstraintPolicyEntity> constraintPolicies, ExpirationPeriodEmbeddable expirationPeriod) {
        this.name = name;
        this.discountPolicies = discountPolicies;
        this.constraintPolicies = constraintPolicies;
        this.expirationPeriod = expirationPeriod;
    }

    public static CouponEntity from(Coupon coupon) {
        return CouponEntity.builder()
            .name(coupon.getName())
            .discountPolicies(coupon.getDiscountPolicies().stream().map(DiscountPolicyEntity::from).toList())
            .constraintPolicies(coupon.getConstraintPolicies().stream().map(ConstraintPolicyEntity::from).toList())
            .expirationPeriod(ExpirationPeriodEmbeddable.from(coupon.getExpirationPeriod()))
            .build();
    }

    public Coupon toDomain() {
        return Coupon.builder()
            .id(getId())
            .name(name)
            .discountPolicies(discountPolicies.stream().map(DiscountPolicyEntity::toDomain).toList())
            .constraintPolicies(constraintPolicies.stream().map(ConstraintPolicyEntity::toDomain).toList())
            .expirationPeriod(expirationPeriod.toDomain())
            .build();
    }
}
