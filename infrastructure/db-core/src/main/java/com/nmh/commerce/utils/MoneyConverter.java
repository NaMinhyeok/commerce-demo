package com.nmh.commerce.utils;

import com.nmh.commerce.domain.Money;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;

import java.math.BigDecimal;

@Convert
public class MoneyConverter implements AttributeConverter<Money, BigDecimal> {
    @Override
    public BigDecimal convertToDatabaseColumn(Money money) {
        return money.getValue();
    }

    @Override
    public Money convertToEntityAttribute(BigDecimal bigDecimal) {
        return Money.of(bigDecimal);
    }
}
