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
        CouponEntity couponEntity = CouponEntity.builder()
            .name(coupon.name)
            .build();
        addDiscountPolicies(coupon, couponEntity);
        addConstraintPolicies(coupon, couponEntity);
        addExpirationPeriodPolicies(coupon, couponEntity);

        return couponEntity;
    }

    private static void addExpirationPeriodPolicies(Coupon coupon, CouponEntity couponEntity) {
        couponEntity.expirationPeriodPolicies.addAll(coupon.expirationPeriodPolicies.stream()
            .map(policy -> ExpirationPeriodPolicyEntity.from(policy, couponEntity))
            .toList());
    }

    private static void addDiscountPolicies(Coupon coupon, CouponEntity couponEntity) {
        couponEntity.discountPolicies.addAll(coupon.discountPolicies.stream()
            .map(policy -> DiscountPolicyEntity.from(policy, couponEntity))
            .toList());
    }

    private static void addConstraintPolicies(Coupon coupon, CouponEntity couponEntity) {
        couponEntity.constraintPolicies.addAll(coupon.constraintPolicies.stream()
            .map(policy -> ConstraintPolicyEntity.from(policy, couponEntity))
            .toList());
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
        return new Coupon(getId(),
            name,
            discountPolicies.stream().map(DiscountPolicyEntity::toDomain).toList(),
            constraintPolicies.stream().map(ConstraintPolicyEntity::toDomain).toList(),
            expirationPeriodPolicies.stream().map(ExpirationPeriodPolicyEntity::toDomain).toList());

    }
}
