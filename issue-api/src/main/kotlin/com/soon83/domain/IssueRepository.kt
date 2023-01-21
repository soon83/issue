package com.soon83.domain

import org.springframework.data.jpa.repository.JpaRepository

interface IssueRepository: JpaRepository<Issue, Long> {
    fun findAllByStatusOrderByCreatedAtDesc(status: Issue.IssueStatus): List<Issue>?
}