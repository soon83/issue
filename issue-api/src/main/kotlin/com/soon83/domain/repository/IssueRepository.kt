package com.soon83.domain.repository

import com.soon83.domain.entity.Issue
import org.springframework.data.jpa.repository.JpaRepository

interface IssueRepository: JpaRepository<Issue, Long> {
    fun findAllByStatusOrderByCreatedAtDesc(status: Issue.IssueStatus): List<Issue>?
}