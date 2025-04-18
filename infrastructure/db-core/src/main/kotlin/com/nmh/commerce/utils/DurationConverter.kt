package com.nmh.commerce.utils

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import java.time.Duration

@Converter(autoApply = true)
class DurationConverter : AttributeConverter<Duration?, String?> {
    override fun convertToDatabaseColumn(duration: Duration?): String? {
        if (duration == null) {
            return null
        }
        return duration.toString()
    }

    override fun convertToEntityAttribute(s: String?): Duration? {
        if (s == null) {
            return null
        }
        return Duration.parse(s)
    }
}
