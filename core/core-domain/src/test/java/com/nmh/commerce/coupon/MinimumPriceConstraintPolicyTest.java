package com.nmh.commerce.coupon;

import com.nmh.commerce.domain.Money;
import com.nmh.commerce.product.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.BDDAssertions.thenThrownBy;

class MinimumPriceConstraintPolicyTest {

    @Test
    void 최소_가격제한을_만족하지_못하면_쿠폰을_사용할_수_없다() {
        // given
        MinimumPriceConstraintPolicy policy = new MinimumPriceConstraintPolicy(
            Money.of(BigDecimal.valueOf(3000))
        );

        Product product = Product.builder()
            .price(Money.of(BigDecimal.valueOf(2000)))
            .build();
        // when
        // then
        thenThrownBy(() -> policy.verify(product))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("쿠폰을 적용 할 수 있는 최소 금액 미달입니다.");
    }

}