package com.nmh.commerce.coupon;

import java.time.LocalDateTime;

public interface ExpirationPeriodPolicy {
    void verify(LocalDateTime now);
}
