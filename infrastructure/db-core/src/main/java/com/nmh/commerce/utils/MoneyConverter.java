package com.nmh.commerce.utils;

import com.nmh.commerce.domain.Money;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Converter;

import java.math.BigDecimal;

@Converter(autoApply = true)
public class MoneyConverter implements AttributeConverter<Money, BigDecimal> {
    @Override
    public BigDecimal convertToDatabaseColumn(Money money) {
        if (money == null) {
            return null;
        }
        return money.getValue();
    }

    @Override
    public Money convertToEntityAttribute(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return null;
        }
        return Money.of(bigDecimal);
    }
}
