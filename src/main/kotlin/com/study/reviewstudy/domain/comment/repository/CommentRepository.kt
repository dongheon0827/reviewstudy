package com.study.reviewstudy.domain.comment.repository

import com.study.reviewstudy.domain.comment.model.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository : JpaRepository<Comment, Long> {
}