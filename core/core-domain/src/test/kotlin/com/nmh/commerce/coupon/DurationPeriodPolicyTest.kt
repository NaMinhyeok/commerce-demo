package com.nmh.commerce.coupon

import org.assertj.core.api.BDDAssertions
import org.assertj.core.api.ThrowableAssert
import org.junit.jupiter.api.Test
import java.time.Duration
import java.time.LocalDateTime

internal class DurationPeriodPolicyTest {
    @Test
    fun 쿠폰의_발급일이_사용가능기한_이후면_사용할_수_없다() {
        // given
        val policy = DurationPeriodPolicy(
            Duration.ofDays(1)
        )
        // when
        // then
        BDDAssertions.thenThrownBy(ThrowableAssert.ThrowingCallable { policy.verify(LocalDateTime.now().minusDays(2)) })
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("쿠폰이 만료되었습니다.")
    }
}