package com.nmh.commerce.utils

import com.nmh.commerce.domain.DiscountRate
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import java.math.BigDecimal

@Converter(autoApply = true)
class DiscountRateConverter : AttributeConverter<DiscountRate?, BigDecimal?> {
    override fun convertToDatabaseColumn(discountRate: DiscountRate?): BigDecimal? {
        if (discountRate == null) {
            return null
        }
        return discountRate.value
    }

    override fun convertToEntityAttribute(bigDecimal: BigDecimal?): DiscountRate? {
        if (bigDecimal == null) {
            return null
        }
        return DiscountRate.of(bigDecimal)
    }
}
