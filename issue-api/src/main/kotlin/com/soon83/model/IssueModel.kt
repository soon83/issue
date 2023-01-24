package com.soon83.model

import com.fasterxml.jackson.annotation.JsonFormat
import com.soon83.domain.entity.Issue
import java.time.LocalDateTime

data class IssueRequest(
    val summary: String,
    val description: String,
    val type: Issue.IssueType,
    val priority: Issue.IssuePriority,
    val status: Issue.IssueStatus,
) {
    fun toEntity(userId: Long) =
        Issue(
            userId = userId,
            summary = summary,
            description = description,
            type = type,
            priority = priority,
            status = status,
        )

    fun updateAll(userId: Long, issue: Issue): Issue {
        issue.summary = summary
        issue.description = description
        issue.userId = userId
        issue.type = type
        issue.priority = priority
        issue.status = status

        return issue
    }
}

data class IssueResponse(
    val id: Long,
    val userId: Long,
    val summary: String,
    val description: String,
    val type: Issue.IssueType,
    val priority: Issue.IssuePriority,
    val status: Issue.IssueStatus,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime?,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val updatedAt: LocalDateTime?,
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
                    comments = comments.sortedByDescending(com.soon83.domain.entity.Comment::id).map(com.soon83.domain.entity.Comment::toResponse)
                )
            }
    }
}