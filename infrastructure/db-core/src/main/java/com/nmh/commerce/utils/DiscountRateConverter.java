package com.nmh.commerce.utils;

import com.nmh.commerce.domain.DiscountRate;
import jakarta.persistence.AttributeConverter;

import java.math.BigDecimal;

public class DiscountRateConverter implements AttributeConverter<DiscountRate, BigDecimal> {
    @Override
    public BigDecimal convertToDatabaseColumn(DiscountRate discountRate) {
        return discountRate.getValue();
    }

    @Override
    public DiscountRate convertToEntityAttribute(BigDecimal bigDecimal) {
        return new DiscountRate(bigDecimal);
    }
}
