package com.study.reviewstudy.domain.post.dto

import com.study.reviewstudy.domain.comment.dto.CommentResponse
import java.time.LocalDateTime

data class PostWithCommentResponse(
    val id: Long,
    val title: String,
    val author: String,
    val createdAt: LocalDateTime,
    val content: String,
    val comments: List<CommentResponse>
    )
