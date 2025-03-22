package com.nmh.commerce.utils;

import com.nmh.commerce.domain.Percentage;
import jakarta.persistence.AttributeConverter;

import java.math.BigDecimal;

public class PercentageConverter implements AttributeConverter<Percentage, BigDecimal> {
    @Override
    public BigDecimal convertToDatabaseColumn(Percentage percentage) {
        return percentage.getValue();
    }

    @Override
    public Percentage convertToEntityAttribute(BigDecimal bigDecimal) {
        return new Percentage(bigDecimal);
    }
}
