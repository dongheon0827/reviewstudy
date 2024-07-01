package com.study.reviewstudy.domain.comment.controller

import com.study.reviewstudy.common.exception.handler.InvalidCredentialException
import com.study.reviewstudy.domain.comment.dto.CommentCreateRequest
import com.study.reviewstudy.domain.comment.dto.CommentIdResponse
import com.study.reviewstudy.domain.comment.dto.CommentUpdateRequest
import com.study.reviewstudy.domain.comment.service.CommentService
import com.study.reviewstudy.infra.security.UserPrincipal
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/comments")
class CommentController(
    private val commentService: CommentService
) {
    @PostMapping("/{postId}")
    fun createComment(
        @AuthenticationPrincipal userPrincipal: UserPrincipal?,
        @PathVariable postId: Long,
        @RequestBody commentCreateRequest: CommentCreateRequest
    ): ResponseEntity<CommentIdResponse> {
        if (userPrincipal == null) throw InvalidCredentialException()

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(commentService.createComment(postId, commentCreateRequest, userPrincipal.nickname))
    }

    @PutMapping("/{commentId}")
    fun updateComment(
        @AuthenticationPrincipal userPrincipal: UserPrincipal?,
        @PathVariable commentId: Long,
        @RequestBody commentUpdateRequest: CommentUpdateRequest
    ): ResponseEntity<Unit> {
        if (userPrincipal == null) throw InvalidCredentialException()

        return ResponseEntity.status(HttpStatus.OK)
            .body(commentService.updateComment(commentId, commentUpdateRequest, userPrincipal.nickname))
    }

    @DeleteMapping("/{commentId}")
    fun deleteComment(
        @AuthenticationPrincipal userPrincipal: UserPrincipal?,
        @PathVariable commentId: Long
    ): ResponseEntity<Unit> {
        if (userPrincipal == null) throw InvalidCredentialException()

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
            .body(commentService.deleteComment(commentId, userPrincipal.nickname))
    }
}