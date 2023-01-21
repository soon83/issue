package com.soon83.service

import com.soon83.domain.Issue
import com.soon83.domain.IssueRepository
import com.soon83.exception.NotFoundException
import com.soon83.model.IssueRequest
import com.soon83.model.IssueResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class IssueService(
    private val issueRepository: IssueRepository
) {

    @Transactional
    fun create(userId: Long, request: IssueRequest): IssueResponse {
        val issue = request.toEntity(userId)
        val createdIssue = issueRepository.save(issue)
        return IssueResponse(createdIssue)
    }

    fun getAll(status: Issue.IssueStatus) =
        issueRepository.findAllByStatusOrderByCreatedAtDesc(status)
            ?.map { IssueResponse(it) }

    fun get(id: Long): IssueResponse {
        val issue = issueRepository.findByIdOrNull(id) ?: throw NotFoundException("Issue 가 존재하지 않습니다.")
        return IssueResponse(issue)
    }

    @Transactional
    fun edit(userId: Long, id: Long, request: IssueRequest): IssueResponse {
        val issue = issueRepository.findByIdOrNull(id) ?: throw NotFoundException("Issue 가 존재하지 않습니다.")
        val updatedIssue = request.updateAll(userId, issue)
        return IssueResponse(updatedIssue)
    }

    @Transactional
    fun delete(id: Long) {
        issueRepository.deleteById(id)
    }
}