package com.nmh.commerce.coupon

import org.assertj.core.api.BDDAssertions
import org.assertj.core.api.ThrowableAssert
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class DeadlinePeriodPolicyTest {
    @Test
    fun 쿠폰을_사용하는_시점이_유효기한_보다_빠르면_사용할_수_없다() {
        // given
        val now = LocalDateTime.now()
        val policy =
            DeadlinePeriodPolicy(
                now.minusDays(1),
                now.plusDays(1),
            )
        // when
        // then
        BDDAssertions
            .thenThrownBy(ThrowableAssert.ThrowingCallable { policy.verify(now.minusDays(2)) })
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("쿠폰이 아직 사용할 수 없습니다.")
    }

    @Test
    fun 쿠폰을_사용하는_시점이_유효기간_보다_느리면_사용할_수_없다() {
        // given
        val now = LocalDateTime.now()
        val policy =
            DeadlinePeriodPolicy(
                now.minusDays(1),
                now.plusDays(1),
            )
        // when
        // then
        BDDAssertions
            .thenThrownBy(ThrowableAssert.ThrowingCallable { policy.verify(now.plusDays(2)) })
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("쿠폰이 만료되었습니다.")
    }
}
