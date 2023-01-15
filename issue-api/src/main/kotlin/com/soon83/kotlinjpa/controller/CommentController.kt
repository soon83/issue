package com.soon83.kotlinjpa.controller

import com.soon83.kotlinjpa.config.AuthUser
import com.soon83.kotlinjpa.model.CommentRequest
import com.soon83.kotlinjpa.model.CommentResponse
import com.soon83.kotlinjpa.service.CommentService
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/issues/{issueId}/comments")
class CommentController(
    private val commentService: CommentService,
) {

    private val log = KotlinLogging.logger {}

    @PostMapping
    fun create(
        authUser: AuthUser,
        @PathVariable issueId: Long,
        @RequestBody request: CommentRequest,
    ): CommentResponse {
        log.debug("### authUser: $authUser, issueId: $issueId, reqeust.body: $request")
        return commentService.create(issueId, authUser.userId, authUser.userName, request)
    }

    @PutMapping("/{id}")
    fun edit(
        authUser: AuthUser,
        @PathVariable id: Long,
        @RequestBody request: CommentRequest
    ) = commentService.edit(id, authUser.userId, request)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(
        authUser: AuthUser,
        @PathVariable issueId: Long,
        @PathVariable id: Long,
    ) = commentService.delete(issueId, id, authUser.userId)
}