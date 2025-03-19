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
            .coupon(CouponEntity.from(issuedCoupon.getCoupon()))
            .user(UserEntity.from(issuedCoupon.getUser()))
            .issuedAt(issuedCoupon.getIssuedAt())
            .build();
    }

    public IssuedCoupon toDomain() {
        return IssuedCoupon.builder()
            .id(getId())
            .coupon(coupon.toDomain())
            .user(user.toDomain())
            .issuedAt(issuedAt)
            .build();
    }
}
