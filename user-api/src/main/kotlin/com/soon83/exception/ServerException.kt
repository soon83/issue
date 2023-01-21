package com.soon83.exception

sealed class ServerException(
    val code: Int,
    override val message: String,
) : RuntimeException(message)
