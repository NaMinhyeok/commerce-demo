package com.nmh.commerce.coupon;

import com.nmh.commerce.domain.Money;
import com.nmh.commerce.product.Product;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@SuperBuilder
public class PercentageCoupon extends AbstractCoupon {
    private final int percentage;

    private PercentageCoupon(Long id, String name, Money minimumAmount, LocalDateTime startsAt, LocalDateTime expiresAt, int percentage) {
        super(id, name, minimumAmount, startsAt, expiresAt);
        this.percentage = percentage;
    }

    @Override
    Money apply(Product product) {
        super.verify(product);
        Money discountedPrice = product.getPrice()
            .multiply(Money.of(BigDecimal.valueOf(percentage)))
            .divide(Money.of(BigDecimal.valueOf(100)));
        return product.getPrice().subtract(discountedPrice);
    }
}
