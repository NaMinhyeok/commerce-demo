package com.nmh.commerce.utils

import com.nmh.commerce.domain.Money
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import java.math.BigDecimal

@Converter(autoApply = true)
class MoneyConverter : AttributeConverter<Money?, BigDecimal?> {
    override fun convertToDatabaseColumn(money: Money?): BigDecimal? {
        if (money == null) {
            return null
        }
        return money.value
    }

    override fun convertToEntityAttribute(bigDecimal: BigDecimal?): Money? {
        if (bigDecimal == null) {
            return null
        }
        return Money.of(bigDecimal)
    }
}
