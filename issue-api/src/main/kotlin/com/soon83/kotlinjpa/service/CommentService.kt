package com.soon83.kotlinjpa.service

import com.soon83.kotlinjpa.domain.Comment
import com.soon83.kotlinjpa.domain.CommentRepository
import com.soon83.kotlinjpa.domain.IssueRepository
import com.soon83.kotlinjpa.exception.NotFoundException
import com.soon83.kotlinjpa.model.CommentRequest
import com.soon83.kotlinjpa.model.CommentResponse
import com.soon83.kotlinjpa.model.toResponse
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
        issue.comments.add(comment)
        val createdComment = commentRepository.save(comment)
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