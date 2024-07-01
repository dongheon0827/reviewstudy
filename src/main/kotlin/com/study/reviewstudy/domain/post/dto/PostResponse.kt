package com.study.reviewstudy.domain.post.dto

import java.time.LocalDateTime

data class PostResponse(
    val id: Long,
    val title: String,
    val author: String,
    val createdAt: LocalDateTime,
    val content: String,
)
