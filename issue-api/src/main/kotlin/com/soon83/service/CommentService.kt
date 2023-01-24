package com.soon83.service

import com.soon83.domain.entity.Comment
import com.soon83.domain.repository.CommentRepository
import com.soon83.domain.repository.IssueRepository
import com.soon83.exception.NotFoundException
import com.soon83.model.CommentRequest
import com.soon83.model.CommentResponse
import com.soon83.model.toResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class CommentService(
    private val commentRepository: CommentRepository,
    private val issueRepository: IssueRepository,
) {

    @Transactional
    fun create(
        issueId: Long,
        userId: Long,
        userName: String,
        request: CommentRequest,
    ): CommentResponse {
        val issue = issueRepository.findByIdOrNull(issueId) ?: throw NotFoundException("Issue 가 존재하지 않습니다.")
        val comment = Comment(
            issue = issue,
            userId = userId,
            userName = userName,
            body = request.body,
        )
        val createdComment = commentRepository.save(comment)
        issue.comments.add(comment)
        return createdComment.toResponse()
    }

    @Transactional
    fun edit(id: Long, userId: Long, request: CommentRequest): CommentResponse? {
        return commentRepository.findByIdAndUserId(id, userId)?.run {
            body = request.body
            val updatedComment = commentRepository.save(this)
            updatedComment.toResponse()
        }
    }

    @Transactional
    fun delete(issueId:Long, id: Long, userId: Long) {
        val issue = issueRepository.findByIdOrNull(issueId) ?: throw NotFoundException("Issue 가 존재하지 않습니다.")
        commentRepository.findByIdAndUserId(id, userId)?.let {
            issue.comments.remove(it)
        }
    }
}