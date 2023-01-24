package com.soon83.domain.repository

import com.soon83.domain.entity.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository : JpaRepository<Comment, Long> {
    fun findByIdAndUserId(id: Long, userId: Long): Comment?
}