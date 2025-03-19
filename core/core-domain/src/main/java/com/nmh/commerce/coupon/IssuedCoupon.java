package com.nmh.commerce.coupon;

import com.nmh.commerce.user.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class IssuedCoupon {
    private final Long id;
    private final AbstractCoupon coupon;
    private final User user;
    private final LocalDateTime issuedAt;

    @Builder
    private IssuedCoupon(Long id, AbstractCoupon coupon, User user, LocalDateTime issuedAt) {
        this.id = id;
        this.coupon = coupon;
        this.user = user;
        this.issuedAt = issuedAt;
    }

    public static IssuedCoupon issue(AbstractCoupon coupon, User user, LocalDateTime issuedAt) {
        return IssuedCoupon.builder()
            .coupon(coupon)
            .user(user)
            .issuedAt(issuedAt)
            .build();
    }

}
