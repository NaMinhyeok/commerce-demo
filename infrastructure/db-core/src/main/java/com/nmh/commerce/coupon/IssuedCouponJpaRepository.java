package com.nmh.commerce.coupon;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IssuedCouponJpaRepository extends JpaRepository<IssuedCouponEntity, Long> {
}
