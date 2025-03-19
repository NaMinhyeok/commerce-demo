package com.nmh.commerce.coupon;

import com.nmh.commerce.user.User;
import com.nmh.commerce.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class CouponIssuer {
    private final CouponRepository couponRepository;
    private final IssuedCouponRepository issuedCouponRepository;
    private final UserRepository userRepository;

    public IssuedCoupon issue(Long couponId, Long userId) {
        AbstractCoupon coupon = couponRepository.findById(couponId).orElseThrow(() -> new IllegalArgumentException("해당 쿠폰을 찾을 수 없습니다."));
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다."));
        IssuedCoupon issuedCoupon = IssuedCoupon.issue(coupon, user, LocalDateTime.now());
        return issuedCouponRepository.save(issuedCoupon);
    }
}
