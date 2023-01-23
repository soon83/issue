package com.soon83.model

import com.fasterxml.jackson.annotation.JsonFormat
import com.soon83.domain.Comment
import com.soon83.domain.Issue
import java.time.ZonedDateTime

data class IssueResponse(
    val id: Long,
    val userId: Long,
    val summary: String,
    val description: String,
    val type: Issue.IssueType,
    val priority: Issue.IssuePriority,
    val status: Issue.IssueStatus,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: ZonedDateTime?,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val updatedAt: ZonedDateTime?,
    val comments: List<CommentResponse> = emptyList(),
) {
    companion object {
        operator fun invoke(issue: Issue) =
            with(issue) {
                IssueResponse(
                    id = id!!,
                    userId = userId,
                    summary = summary,
                    description = description,
                    type = type,
                    priority = priority,
                    status = status,
                    createdAt = createdAt,
                    updatedAt = updatedAt,
                    comments = comments.sortedByDescending(Comment::id).map(Comment::toResponse)
                )
            }
    }
}