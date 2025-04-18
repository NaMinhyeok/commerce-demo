package com.nmh.commerce.coupon;

import com.nmh.commerce.BaseEntity;
import com.nmh.commerce.user.UserEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class IssuedCouponEntity extends BaseEntity {
    @ManyToOne
    private CouponEntity coupon;

    @ManyToOne
    private UserEntity user;

    private LocalDateTime issuedAt;

    @Builder
    private IssuedCouponEntity(CouponEntity coupon, UserEntity user, LocalDateTime issuedAt) {
        this.coupon = coupon;
        this.user = user;
        this.issuedAt = issuedAt;
    }

    public static IssuedCouponEntity from(IssuedCoupon issuedCoupon) {
        return IssuedCouponEntity.builder()
            .coupon(CouponEntity.from(issuedCoupon.coupon))
            .user(UserEntity.from(issuedCoupon.user))
            .issuedAt(issuedCoupon.issuedAt)
            .build();
    }

    public IssuedCoupon toDomain() {
        return new IssuedCoupon(getId(), coupon.toDomain(), user.toDomain(), issuedAt);
    }
}
