package com.soon83.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt")
data class JWTProperties(
    val issuer: String,
    val subject: String,
    val expireTime: Long,
    val secret: String,
)