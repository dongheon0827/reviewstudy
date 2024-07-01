package com.study.reviewstudy.domain.comment.dto

import java.time.LocalDateTime

data class CommentResponse(
    val id: Long,
    val author: String,
    val content: String,
    val createdAt: LocalDateTime
)
