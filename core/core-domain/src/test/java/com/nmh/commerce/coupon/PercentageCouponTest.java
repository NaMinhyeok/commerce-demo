package com.nmh.commerce.coupon;

import com.nmh.commerce.domain.Money;
import com.nmh.commerce.product.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.BDDAssertions.then;

class PercentageCouponTest {

    @Test
    void 쿠폰을_적용한다() {
        // given
        Product product = Product.builder()
            .price(Money.of(BigDecimal.valueOf(10000)))
            .build();

        PercentageCoupon coupon = PercentageCoupon.builder()
            .startsAt(LocalDateTime.now().minusDays(3))
            .expiresAt(LocalDateTime.now().plusDays(3))
            .minimumAmount(Money.of(BigDecimal.valueOf(10000)))
            .percentage(10)
            .build();
        // when
        Money discountedMoney = coupon.apply(product);
        // then
        then(discountedMoney).extracting("value")
            .isEqualTo(BigDecimal.valueOf(9000));
    }

}