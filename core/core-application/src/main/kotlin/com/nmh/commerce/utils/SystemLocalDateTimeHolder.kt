package com.nmh.commerce.utils

import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class SystemLocalDateTimeHolder : LocalDateTimeHolder {
    override fun now(): LocalDateTime {
        return LocalDateTime.now()
    }
}
