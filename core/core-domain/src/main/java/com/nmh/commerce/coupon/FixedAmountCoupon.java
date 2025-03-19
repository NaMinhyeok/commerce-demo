package com.nmh.commerce.coupon;

import com.nmh.commerce.domain.Money;
import com.nmh.commerce.product.Product;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
public class FixedAmountCoupon extends AbstractCoupon {
    private final Money amount;

    private FixedAmountCoupon(Long id, String name, Money minimumAmount, LocalDateTime startsAt, LocalDateTime expiresAt, Money amount) {
        super(id, name, minimumAmount, startsAt, expiresAt);
        this.amount = amount;
    }

    @Override
    Money apply(Product product) {
        super.verify(product);
        return product.getPrice().subtract(amount);
    }
}
