package com.soon83.kotlinjpa.model

import com.soon83.kotlinjpa.domain.Issue

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