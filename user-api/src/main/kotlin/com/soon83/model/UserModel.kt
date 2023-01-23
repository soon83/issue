package com.soon83.model

import com.fasterxml.jackson.annotation.JsonFormat
import com.soon83.domain.entity.User
import java.time.LocalDateTime

data class UserEditRequest(
    val username: String,
)

data class MeResponse(
    val id: Long,
    val profileUrl: String? = null,
    val username: String,
    val email: String,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime? = null,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val updatedAt: LocalDateTime? = null,
) {
    companion object {
        operator fun invoke(user: User) =
            with(user) {
                MeResponse(
                    id = id!!,
                    profileUrl = if (profileUrl.isNullOrBlank()) null else "http://localhost:8081/images/$profileUrl",
                    username = username,
                    email = email,
                    createdAt = createdAt,
                    updatedAt = updatedAt,
                )
            }
    }
}