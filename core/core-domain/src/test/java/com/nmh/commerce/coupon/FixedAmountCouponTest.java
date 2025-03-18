package com.nmh.commerce.coupon;

import com.nmh.commerce.domain.Money;
import com.nmh.commerce.product.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.BDDAssertions.then;

class FixedAmountCouponTest {

    @Test
    void 쿠폰을_적용한다() {
        // given
        Product product = Product.builder()
            .price(Money.of(BigDecimal.valueOf(10000)))
            .build();

        FixedAmountCoupon coupon = FixedAmountCoupon.builder()
            .startsAt(LocalDateTime.now().minusDays(3))
            .expiresAt(LocalDateTime.now().plusDays(3))
            .minimumAmount(Money.of(BigDecimal.valueOf(10000)))
            .amount(Money.of(BigDecimal.valueOf(1000)))
            .build();
        // when
        Money discountedMoney = coupon.apply(product);
        // then
        then(discountedMoney).extracting("value")
            .isEqualTo(BigDecimal.valueOf(9000));
    }

}