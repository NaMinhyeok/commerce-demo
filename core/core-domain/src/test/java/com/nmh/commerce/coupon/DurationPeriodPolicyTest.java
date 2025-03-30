package com.nmh.commerce.coupon;

import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

class DurationPeriodPolicyTest {

    @Test
    void 쿠폰의_발급일이_사용가능기한_이후면_사용할_수_없다() {
        // given
        DurationPeriodPolicy policy = new DurationPeriodPolicy(
            Duration.ofDays(1)
        );
        // when
        // then
        BDDAssertions.thenThrownBy(() -> policy.verify(LocalDateTime.now().minusDays(2)))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("쿠폰이 만료되었습니다.");
    }

}