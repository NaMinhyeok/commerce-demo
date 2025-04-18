package com.nmh.commerce.coupon

import org.springframework.data.jpa.repository.JpaRepository

interface IssuedCouponJpaRepository : JpaRepository<IssuedCouponEntity, Long>
