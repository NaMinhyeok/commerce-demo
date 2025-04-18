package com.nmh.commerce

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan

@ConfigurationPropertiesScan
@SpringBootApplication
class CoreApplicationTestApplication {
    fun main(args: Array<String>) {
        SpringApplication.run(CoreApplicationTestApplication::class.java, *args)
    }
}
