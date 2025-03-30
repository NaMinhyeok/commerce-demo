package com.nmh.commerce.coupon;

import com.nmh.commerce.BaseEntity;
import jakarta.persistence.CascadeType;
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

    @OneToMany(mappedBy = "coupon", cascade = CascadeType.ALL)
    private List<ExpirationPeriodPolicyEntity> expirationPeriodPolicies = new ArrayList<>();

    @Builder
    private CouponEntity(String name, List<DiscountPolicyEntity> discountPolicies, List<ConstraintPolicyEntity> constraintPolicies, List<ExpirationPeriodPolicyEntity> expirationPeriodPolicies) {
        this.name = name;
        this.discountPolicies = discountPolicies;
        this.constraintPolicies = constraintPolicies;
        this.expirationPeriodPolicies = expirationPeriodPolicies;
    }

    public static CouponEntity from(Coupon coupon) {
        return CouponEntity.builder()
            .name(coupon.getName())
            .discountPolicies(coupon.getDiscountPolicies().stream().map(DiscountPolicyEntity::from).toList())
            .constraintPolicies(coupon.getConstraintPolicies().stream().map(ConstraintPolicyEntity::from).toList())
            .expirationPeriodPolicies(coupon.getExpirationPeriodPolicies().stream().map(ExpirationPeriodPolicyEntity::from).toList())
            .build();
    }

    public void addDiscountPolicy(DiscountPolicyEntity discountPolicy) {
        discountPolicies.add(discountPolicy);
    }

    public void addConstraintPolicy(ConstraintPolicyEntity constraintPolicy) {
        constraintPolicies.add(constraintPolicy);
    }

    public void addExpirationPeriodPolicy(ExpirationPeriodPolicyEntity expirationPeriodPolicy) {
        expirationPeriodPolicies.add(expirationPeriodPolicy);
    }

    public Coupon toDomain() {
        return Coupon.builder()
            .id(getId())
            .name(name)
            .discountPolicies(discountPolicies.stream().map(DiscountPolicyEntity::toDomain).toList())
            .constraintPolicies(constraintPolicies.stream().map(ConstraintPolicyEntity::toDomain).toList())
            .expirationPeriodPolicies(expirationPeriodPolicies.stream().map(ExpirationPeriodPolicyEntity::toDomain).toList())
            .build();
    }
}
