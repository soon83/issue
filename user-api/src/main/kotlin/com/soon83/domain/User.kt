package com.soon83.domain

import org.springframework.data.relational.core.mapping.Table
import java.time.ZonedDateTime

@Table("users")
class User(
    val id: Int,
    val email: String,
    val username: String,
    val password: String,
    val createAt: ZonedDateTime? = null,
    val updatedAt: ZonedDateTime? = null,
)