package com.soon83.config

import io.r2dbc.spi.ConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator

@Configuration
class R2dbcConfig {

    @Bean
    fun init(connectionFactory: ConnectionFactory): ConnectionFactoryInitializer {
        val initializer = ConnectionFactoryInitializer().apply {
            setConnectionFactory(connectionFactory)
            setDatabasePopulator(
                ResourceDatabasePopulator(
                    ClassPathResource("scripts/scheme.sql")
                )
            )
        }
        return initializer
    }
}