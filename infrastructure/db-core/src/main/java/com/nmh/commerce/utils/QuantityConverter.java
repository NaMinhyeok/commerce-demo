package com.nmh.commerce.utils;

import com.nmh.commerce.domain.Quantity;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class QuantityConverter implements AttributeConverter<Quantity, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Quantity quantity) {
        if (quantity == null) {
            return null;
        }
        return quantity.value;
    }

    @Override
    public Quantity convertToEntityAttribute(Integer integer) {
        if (integer == null) {
            return null;
        }
        return Quantity.of(integer);
    }
}
