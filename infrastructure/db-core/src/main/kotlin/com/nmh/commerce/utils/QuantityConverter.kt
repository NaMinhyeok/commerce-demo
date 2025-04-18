package com.nmh.commerce.utils

import com.nmh.commerce.domain.Quantity
import com.nmh.commerce.domain.Quantity.Companion.of
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter(autoApply = true)
class QuantityConverter : AttributeConverter<Quantity?, Int?> {
    override fun convertToDatabaseColumn(quantity: Quantity?): Int? {
        if (quantity == null) {
            return null
        }
        return quantity.value
    }

    override fun convertToEntityAttribute(integer: Int?): Quantity? {
        if (integer == null) {
            return null
        }
        return of(integer)
    }
}
