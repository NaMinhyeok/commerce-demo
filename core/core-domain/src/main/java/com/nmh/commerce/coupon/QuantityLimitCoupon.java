package com.nmh.commerce.coupon;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public class QuantityLimitCoupon extends FixedAmountCoupon {
    private final int quantityLimit;


}
