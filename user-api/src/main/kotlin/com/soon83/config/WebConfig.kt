package com.soon83.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.WebFluxConfigurer

@Configuration
class WebConfig : WebFluxConfigurer {

    val all = "*";

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins(all)
            .allowedMethods(all)
            .maxAge(3600)
    }
}