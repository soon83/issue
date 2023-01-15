package com.soon83.kotlinjpa.model

import com.soon83.kotlinjpa.domain.Comment

data class CommentResponse(
    val id: Long,
    val issueId: Long,
    val userId: Long,
    val userName: String? = null,
    val body: String,
)

fun Comment.toResponse() = CommentResponse(
    id = id!!,
    issueId = issue.id!!,
    userId = userId,
    userName = userName,
    body = body,
)