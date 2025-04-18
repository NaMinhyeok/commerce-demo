package com.nmh.commerce.domain

import com.nmh.commerce.domain.Quantity.Companion.of
import org.assertj.core.api.BDDAssertions
import org.assertj.core.api.ThrowableAssert
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

internal class QuantityTest {
    @Test
    fun 수량은_0_이상이어야_한다() {
        // given
        // when
        // then
        BDDAssertions.thenThrownBy(ThrowableAssert.ThrowingCallable { of(-1) })
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("수량은 0 이상이어야 합니다.")
    }

    @Test
    fun 수량을_더한다() {
        // given
        val quantity = of(10)
        // when
        val result = quantity.add(of(5))
        // then
        BDDAssertions.then(result.value).isEqualTo(15)
    }

    @Test
    fun 수량을_뺀다() {
        // given
        val quantity = of(10)
        // when
        val result = quantity.subtract(of(5))
        // then
        BDDAssertions.then(result.value).isEqualTo(5)
    }

    @Test
    fun 재고가_0보다_작아지게_subtract_할_수_없다() {
        // given
        val quantity = of(10)
        // when
        // then
        BDDAssertions.thenThrownBy(ThrowableAssert.ThrowingCallable { quantity.subtract(of(11)) })
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("수량이 부족합니다.")
    }

    @Test
    fun 재고가_남아있는지_여부를_확인한다() {
        Assertions.assertAll(
            { BDDAssertions.then(of(1).isRemaining).isTrue() },
            { BDDAssertions.then(of(0).isRemaining).isFalse() }
        )
    }
}