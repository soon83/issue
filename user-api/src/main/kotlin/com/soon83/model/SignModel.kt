package com.soon83.model

data class SignUpRequest(
    val email: String,
    val password: String,
    val username: String,
)

data class SignUpResponse(
    val id: Long,
)

data class SignInRequest(
    val email: String,
    val password: String,
)

data class SignInResponse(
    val email: String,
    val username: String,
    val token: String,
)