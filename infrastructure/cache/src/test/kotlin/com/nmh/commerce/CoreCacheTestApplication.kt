package com.nmh.commerce

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication
class CoreCacheTestApplication

fun main(args: Array<String>) {
    runApplication<CoreCacheTestApplication>(*args)
}
