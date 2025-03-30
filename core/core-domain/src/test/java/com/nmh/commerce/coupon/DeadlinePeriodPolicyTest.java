package com.nmh.commerce.coupon;

import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class DeadlinePeriodPolicyTest {

    @Test
    void 쿠폰을_사용하는_시점이_유효기한_보다_빠르면_사용할_수_없다() {
        // given
        LocalDateTime now = LocalDateTime.now();
        DeadlinePeriodPolicy policy = new DeadlinePeriodPolicy(
            now.minusDays(1),
            now.plusDays(1)
        );
        // when
        // then
        BDDAssertions.thenThrownBy(() -> policy.verify(now.minusDays(2)))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("쿠폰이 아직 사용할 수 없습니다.");
    }

    @Test
    void 쿠폰을_사용하는_시점이_유효기간_보다_느리면_사용할_수_없다() {
        // given
        LocalDateTime now = LocalDateTime.now();
        DeadlinePeriodPolicy policy = new DeadlinePeriodPolicy(
            now.minusDays(1),
            now.plusDays(1)
        );
        // when
        // then
        BDDAssertions.thenThrownBy(() -> policy.verify(now.plusDays(2)))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("쿠폰이 만료되었습니다.");
    }

}