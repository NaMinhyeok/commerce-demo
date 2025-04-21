package com.nmh.commerce.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CoreDataSourceConfig {
    @Bean
    @ConfigurationProperties(prefix = "storage.datasource.core")
    fun coreHikariConfig(): HikariConfig = HikariConfig()

    @Bean
    fun coreDataSource(config: HikariConfig): HikariDataSource = HikariDataSource(config)
}
