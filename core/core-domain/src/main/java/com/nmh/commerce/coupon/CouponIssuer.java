package com.nmh.commerce.coupon;

import com.nmh.commerce.user.User;
import com.nmh.commerce.user.UserRepository;
import com.nmh.commerce.utils.LocalDateTimeHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class CouponIssuer {
    private final CouponRepository couponRepository;
    private final IssuedCouponRepository issuedCouponRepository;
    private final UserRepository userRepository;
    private final CouponStockManager stockManager;
    private final LocalDateTimeHolder localDateTimeHolder;

    @Transactional
    public IssuedCoupon issue(Long couponId, Long userId) {
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(() -> new IllegalArgumentException("해당 쿠폰을 찾을 수 없습니다."));
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다."));
        IssuedCoupon issuedCoupon = IssuedCoupon.issue(coupon, user, localDateTimeHolder.now());
        stockManager.deductStock(couponId);
        return issuedCouponRepository.save(issuedCoupon);
    }
}
