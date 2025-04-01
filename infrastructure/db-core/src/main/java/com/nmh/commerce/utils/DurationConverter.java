package com.nmh.commerce.utils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.Duration;

@Converter(autoApply = true)
public class DurationConverter implements AttributeConverter<Duration, String> {
    @Override
    public String convertToDatabaseColumn(Duration duration) {
        if(duration == null) {
            return null;
        }
        return duration.toString();
    }

    @Override
    public Duration convertToEntityAttribute(String s) {
        if(s == null) {
            return null;
        }
        return Duration.parse(s);
    }
}
