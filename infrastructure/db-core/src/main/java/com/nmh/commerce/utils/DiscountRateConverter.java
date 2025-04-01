package com.nmh.commerce.utils;

import com.nmh.commerce.domain.DiscountRate;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.math.BigDecimal;

@Converter(autoApply = true)
public class DiscountRateConverter implements AttributeConverter<DiscountRate, BigDecimal> {
    @Override
    public BigDecimal convertToDatabaseColumn(DiscountRate discountRate) {
        if (discountRate == null) {
            return null;
        }
        return discountRate.getValue();
    }

    @Override
    public DiscountRate convertToEntityAttribute(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return null;
        }
        return DiscountRate.of(bigDecimal);
    }
}
