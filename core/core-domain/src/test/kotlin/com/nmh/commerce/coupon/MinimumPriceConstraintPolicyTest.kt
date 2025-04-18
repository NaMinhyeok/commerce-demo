package com.nmh.commerce.coupon

import com.nmh.commerce.domain.Money
import com.nmh.commerce.product.Product
import org.assertj.core.api.BDDAssertions
import org.assertj.core.api.ThrowableAssert
import org.junit.jupiter.api.Test
import java.math.BigDecimal

internal class MinimumPriceConstraintPolicyTest {
    @Test
    fun 최소_가격제한을_만족하지_못하면_쿠폰을_사용할_수_없다() {
        // given
        val policy = MinimumPriceConstraintPolicy(
            Money.of(BigDecimal.valueOf(3000))
        )
        val product = Product(0L, "상품", Money.of(BigDecimal.valueOf(2000)))
        // when
        // then
        BDDAssertions.thenThrownBy(ThrowableAssert.ThrowingCallable { policy.verify(product) })
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("쿠폰을 적용 할 수 있는 최소 금액 미달입니다.")
    }
}