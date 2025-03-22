package com.nmh.commerce.coupon;

import com.nmh.commerce.product.Product;

public interface ConstraintPolicy {
    void verify(Product product);
}
