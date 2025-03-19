package com.nmh.commerce.coupon;

import com.nmh.commerce.domain.Money;
import com.nmh.commerce.product.Product;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
public abstract class AbstractCoupon {
    protected final Long id;
    protected final String name;
    protected final Money minimumAmount;
    protected final LocalDateTime startsAt;
    protected final LocalDateTime expiresAt;

    protected AbstractCoupon(Long id, String name, Money minimumAmount, LocalDateTime startsAt, LocalDateTime expiresAt) {
        this.id = id;
        this.name = name;
        this.minimumAmount = minimumAmount;
        this.startsAt = startsAt;
        this.expiresAt = expiresAt;
    }

    abstract Money apply(Product product);

    protected void verify(Product product) {
        if (!isDateValid()) {
            throw new IllegalArgumentException("쿠폰 사용 기간이 아닙니다.");
        }
        if (!isMinimumAmountValid(product)) {
            throw new IllegalArgumentException("쿠폰 사용 최소 금액이 부족합니다.");
        }
    }

    private boolean isDateValid() {
        return LocalDateTime.now().isAfter(startsAt) && LocalDateTime.now().isBefore(expiresAt);
    }

    private boolean isMinimumAmountValid(Product product) {
        return product.getPrice().isGreaterThanOrEqual(minimumAmount);
    }
}
