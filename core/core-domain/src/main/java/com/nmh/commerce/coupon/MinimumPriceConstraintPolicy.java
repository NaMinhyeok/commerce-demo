package com.nmh.commerce.coupon;

import com.nmh.commerce.domain.Money;
import com.nmh.commerce.product.Product;
import lombok.Getter;

@Getter
public class MinimumPriceConstraintPolicy implements ConstraintPolicy {
    private final Money minimumPrice;

    public MinimumPriceConstraintPolicy(Money minimumPrice) {
        this.minimumPrice = minimumPrice;
    }

    @Override
    public void verify(Product product) {
        if (product.getPrice().isLessThan(minimumPrice)) {
            throw new IllegalArgumentException("쿠폰을 적용 할 수 있는 최소 금액 미달입니다.");
        }
    }
}
