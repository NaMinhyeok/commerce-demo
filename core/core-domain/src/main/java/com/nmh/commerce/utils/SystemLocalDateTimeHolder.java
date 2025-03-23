package com.nmh.commerce.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SystemLocalDateTimeHolder implements LocalDateTimeHolder {
    @Override
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
